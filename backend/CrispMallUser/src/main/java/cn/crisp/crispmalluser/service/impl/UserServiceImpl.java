package cn.crisp.crispmalluser.service.impl;

import cn.crisp.crispmalluser.entity.User;
import cn.crisp.crispmalluser.mapper.UserMapper;
import cn.crisp.crispmalluser.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
