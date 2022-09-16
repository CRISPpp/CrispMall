package cn.crisp.crispmalluser.controller;


import cn.crisp.common.R;
import cn.crisp.crispmalluser.entity.User;
import cn.crisp.crispmalluser.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("用户")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation("获取所有用户")
    @GetMapping("/user")
    public R<List<User>> getUser(){
        return R.success(userService.list());
    }
}
