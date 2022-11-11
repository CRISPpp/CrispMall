package cn.crisp.crispmallorder.controller;

import cn.crisp.common.R;
import cn.crisp.crispmallorder.entity.Mallorder;
import cn.crisp.crispmallorder.feign.ProductFeignService;
import cn.crisp.crispmallorder.feign.UserFeignService;
import cn.crisp.crispmallorder.service.MallorderService;
import cn.crisp.crispmallorder.vo.OrderVo;
import cn.crisp.crispmallproduct.entity.Product;
import cn.crisp.crispmalluser.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api("订单")
@RestController
@RequestMapping("/order")
public class MallorderController {
    @Autowired
    private MallorderService mallorderService;

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private ProductFeignService productFeignService;

    @ApiOperation("分页查询订单")
    @GetMapping
    public R<Page> getOrders(int page, int pageSize){
        Page<Mallorder> retT = new Page<>(page, pageSize);
        retT = mallorderService.page(retT);
        Page<OrderVo> ret = new Page<>();
        BeanUtils.copyProperties(retT, ret, "records");

        ret.setRecords(retT.getRecords().stream().map(item->{
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(item, orderVo);
            orderVo.setUsername(userFeignService.getUserById(item.getUserId()).getData().getUsername());
            orderVo.setProductName(productFeignService.getProductById(item.getProductId()).getData().getProductName());
            return orderVo;
        }).collect(Collectors.toList()));

        return R.success(ret);
    }

    @ApiOperation("添加订单")
    @PostMapping
    public R<String> addOrder(@RequestBody Mallorder mallorder) {
        if (userFeignService.getUserById(mallorder.getUserId()).getCode() == 0) return R.error("请检查用户信息");
        R<Product> tmpRet = productFeignService.getProductById(mallorder.getProductId());
        if (tmpRet.getCode() == 0) {
            return R.error(tmpRet.getMsg());
        }

        Product product = tmpRet.getData();
        product.setNum(product.getNum() - mallorder.getNum());
        product.setSale(product.getSale() + mallorder.getNum());
        R<String> updateRet = productFeignService.updateProduct(product);
        if (updateRet.getCode() == 0) {
            return R.error(updateRet.getMsg());
        }

        if (mallorderService.save(mallorder)) {
            return R.success("订单添加成功");
        }else {
            return R.error("订单添加失败，请检查参数");
        }
    }

    @ApiOperation("分页查询用户订单")
    @GetMapping("/{userId}")
    public R<Page> getOrdersByUser(int page, int pageSize, @PathVariable Long userId){
        Page<Mallorder> retT = new Page<>(page, pageSize);
        LambdaQueryWrapper<Mallorder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Mallorder::getUserId, userId);

        if (userFeignService.getUserById(userId).getCode() == 0) {
            return R.error("请检查用户信息");
        }

        retT = mallorderService.page(retT, wrapper);
        Page<OrderVo> ret = new Page<>();
        BeanUtils.copyProperties(retT, ret, "records");

        ret.setRecords(retT.getRecords().stream().map(item->{
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(item, orderVo);
            orderVo.setUsername(userFeignService.getUserById(item.getUserId()).getData().getUsername());
            orderVo.setProductName(productFeignService.getProductById(item.getProductId()).getData().getProductName());
            return orderVo;
        }).collect(Collectors.toList()));

        return R.success(ret);
    }
}
