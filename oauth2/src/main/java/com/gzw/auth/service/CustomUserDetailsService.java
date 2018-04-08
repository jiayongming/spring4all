package com.gzw.auth.service;

import com.gzw.auth.domain.SysUser;
import com.gzw.auth.enums.ResultEnum;
import com.gzw.auth.exception.CommonException;
import com.gzw.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) {
        SysUser sysUser = userRepository.findOne(Long.valueOf(phone));
        if (sysUser == null){
            throw new CommonException(ResultEnum.AUTHENTICATION_ERROR);
        }
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = createAuthorities(sysUser.getRole());
        return new User(phone, sysUser.getPassword(), simpleGrantedAuthorities);
    }

    /**
     * 权限字符串转化
     * 如 "USER,ADMIN" -> SimpleGrantedAuthority("USER") + SimpleGrantedAuthority("ADMIN")
     * @param roleStr 权限字符串
     */
    private List<SimpleGrantedAuthority> createAuthorities(String roleStr){
        String[] roles = roleStr.split(",");
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (String role : roles) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return simpleGrantedAuthorities;
    }
   /* @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
       // SysUser sysUser = sysUserService.fetchUserByPhone(userId);
        return new SocialUser(userId,"123456", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN"));
    }*/
}
