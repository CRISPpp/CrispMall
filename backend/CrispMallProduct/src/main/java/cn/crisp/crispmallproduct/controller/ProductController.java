package cn.crisp.crispmallproduct.controller;

import cn.crisp.common.R;
import cn.crisp.crispmallproduct.entity.Product;
import cn.crisp.crispmallproduct.service.ProductService;
import cn.crisp.crispmallproduct.utils.SimpleRedisCache;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Api("产品")
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    SimpleRedisCache redisCache;

    @ApiOperation("获取所有产品")
    @GetMapping
    public R<Page> getProduct(int page, int pageSize){
        Page<Product> cachePage = redisCache.getCacheObject("product" + page+ "size" + pageSize);
        if (cachePage != null) return R.success(cachePage);
        Page<Product> ret = new Page<>(page, pageSize);
        ret = productService.page(ret);
        redisCache.setCacheObject("product" + page + "size" + pageSize, ret, 10, TimeUnit.SECONDS);
        return R.success(ret);
    }

    @ApiOperation("根据id获取产品信息")
    @GetMapping("/{id}")
    public R<Product> getProductById(@PathVariable Long id){
        Product ret = productService.getById(id);
        if(ret == null) return R.error("该订单不存在");
        return R.success(ret);
    }

    @ApiOperation("修改产品")
    @PutMapping
    public R<String> updateProduct(@RequestBody Product product){
        if(product == null || product.getId() == null) return R.error("不能为空，你干嘛哎哟");
        if(product.getNum() < 0) return R.error("修改数量超过库存");
        if(productService.updateById(product)){
            return R.success("修改成功");
        }else{
            return R.error("修改失败，你干嘛哎哟");
        }
    }

    @ApiOperation("新增产品")
    @PostMapping
    public R<String> addProduct(@RequestBody Product product){
        if(product == null) return R.error("不能为空，你干嘛哎哟");
        if(product.getId() != null) return R.error("哪来的id你干嘛哎哟");
        if(productService.save(product)) return R.success("添加成功");
        return R.error("添加失败");
    }
}
