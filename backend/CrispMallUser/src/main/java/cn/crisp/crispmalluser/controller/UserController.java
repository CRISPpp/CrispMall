package cn.crisp.crispmalluser.controller;


import cn.crisp.common.R;
import cn.crisp.crispmalluser.dto.LoginDto;
import cn.crisp.crispmalluser.entity.User;
import cn.crisp.crispmalluser.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("用户")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation("获取所有用户")
    @GetMapping
    public R<Page> getUser(int page, int pageSize){

        Page<User> ret = new Page<>(page, pageSize);
        ret = userService.page(ret);

        return R.success(ret);

    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public R<User> login(@RequestBody LoginDto loginDto){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, loginDto.getUsername());
        User user = userService.getOne(wrapper);
        if(user == null) return R.error("没有这个用户，你干嘛哎呦");
        if(!user.getPassword().equals(loginDto.getPassword())) return R.error("密码错误，你干嘛哎呦");

        return R.success(user);
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public R<String> register(@RequestBody LoginDto loginDto){
        if(StringUtils.isEmpty(loginDto.getUsername())) return R.error("用户名不能为空，你干嘛哎哟");
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, loginDto.getUsername());
        User user = userService.getOne(wrapper);
        if(user != null) return R.error("这个用户已经存在，你干嘛哎呦");
        User user1 = new User();
        user1.setUsername(loginDto.getUsername());
        user1.setPassword(loginDto.getPassword());
        userService.save(user1);
        return R.success("注册成功");
    }
}
