package cn.crisp.crispmallorder.feign;

import cn.crisp.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import cn.crisp.crispmalluser.entity.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("CrispMallUser")
public interface UserFeignService {

    @GetMapping("/user/{id}")
    public R<User> getUserById(@PathVariable Long id);

    }
