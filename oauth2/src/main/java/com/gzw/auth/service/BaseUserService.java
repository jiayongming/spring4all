package com.gzw.auth.service;

import com.gzw.auth.domain.RoleConstant;
import com.gzw.auth.domain.SysUser;
import com.gzw.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Primary
public class BaseUserService {

    @Autowired
    private UserRepository userRepository;

    public BaseUserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * 注册用户
     * @param sysUser
     * @return
     */
    public boolean register(SysUser sysUser) {
        long phone = sysUser.getPhone();
        if (exist(phone))
            return false;
        sysUser.setPassword(new BCryptPasswordEncoder().encode(sysUser.getPassword()));
        sysUser.setRole(RoleConstant.ADMIN.getName());
        if (userRepository.save(sysUser) != null)
            return true;
        return false;
    }

    /**
     * 判断用户是否存在
     * @param phone 账号
     * @return 密码
     */
    private boolean exist(Long phone){
        SysUser userInfo = userRepository.findOne(phone);
        return (userInfo != null);
    }

}
