package cn.crisp.crispmallorder.controller;

import cn.crisp.common.R;
import cn.crisp.crispmallorder.entity.Mallorder;
import cn.crisp.crispmallorder.service.MallorderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("订单")
@RestController
public class MallorderController {
    @Autowired
    MallorderService mallorderService;

    @ApiOperation("获取所有订单")
    @GetMapping("/mallorder/")
    public R<List<Mallorder>> getAllOrder(){
        return R.success(mallorderService.list());
    }
}
