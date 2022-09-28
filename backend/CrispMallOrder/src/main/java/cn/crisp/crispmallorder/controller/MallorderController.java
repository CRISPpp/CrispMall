package cn.crisp.crispmallorder.controller;

import cn.crisp.common.R;
import cn.crisp.crispmallorder.entity.Mallorder;
import cn.crisp.crispmallorder.feign.UserFeignService;
import cn.crisp.crispmallorder.service.MallorderService;
import cn.crisp.crispmalluser.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("订单")
@RestController
@RequestMapping("/order")
public class MallorderController {
    @Autowired
    private MallorderService mallorderService;

    @Autowired
    private UserFeignService userFeignService;

    @Operation(tags = "分页查询订单")
    @GetMapping
    public R<Page> getOrders(int page, int pageSize){
        Page<Mallorder> ret = new Page<>(page, pageSize);
        ret = mallorderService.page(ret);
        return R.success(ret);
    }
}
