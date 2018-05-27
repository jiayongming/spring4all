package com.gzw.auth;

import com.gzw.auth.domain.RoleConstant;
import com.gzw.auth.domain.SysUser;
import com.gzw.auth.service.BaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class AuthApplication {

	@Autowired
	private BaseUserService baseUserService;

	@RequestMapping("/user")
	public Principal user(Principal principal) {

		return principal;
	}

	public void register(){
		SysUser user = new SysUser();
		long phone = 17623324210L;
		user.setPhone(phone);
		user.setPassword("111111");
		user.setRole(RoleConstant.ADMIN.getName());
		user.setRegisterTime(new Date());
		this.baseUserService.register(user);
	}
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}
}
