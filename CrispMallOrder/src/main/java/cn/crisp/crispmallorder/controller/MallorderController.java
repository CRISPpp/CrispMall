package cn.crisp.crispmallorder.controller;

import cn.crisp.common.R;
import cn.crisp.crispmallorder.entity.Mallorder;
import cn.crisp.crispmallorder.feign.UserFeignService;
import cn.crisp.crispmallorder.service.MallorderService;
import cn.crisp.crispmalluser.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("订单")
@RestController
public class MallorderController {
    @Autowired
    private MallorderService mallorderService;

    @Autowired
    private UserFeignService userFeignService;

    @Value("${test.nacos}")
    private String testNacos;

    @ApiOperation("获取所有订单")
    @GetMapping("/mallorder/")
    public R<List<Mallorder>> getAllOrder(){
        return R.success(mallorderService.list());
    }

    @ApiOperation("测试feign")
    @GetMapping("/testFeign")
    public R<List<User>> testFeign(){
        return userFeignService.getUser();
    }


    @ApiOperation("测试nacos")
    @GetMapping("/testNacos/")
    public R<String> testNacos(){
        return R.success(testNacos);
    }
}
