package cn.crisp.crispmallorder.feign;

import cn.crisp.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import cn.crisp.crispmalluser.entity.User;
import java.util.List;

@FeignClient("CrispMallUser")
public interface UserFeignService {

    @GetMapping("/user")
    public R<List<User>> getUser();

    }
