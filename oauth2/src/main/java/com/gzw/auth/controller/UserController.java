package com.gzw.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.gzw.auth.aliyun.AliyunSMS;
import com.gzw.auth.domain.SysUser;
import com.gzw.auth.enums.ResultEnum;
import com.gzw.auth.properties.SecurityConstants;
import com.gzw.auth.service.AutomicLogin;
import com.gzw.auth.service.BaseUserService;
import com.gzw.auth.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created on 2018/3/27.
 *
 * @author qzj
 * @since 1.0
 */
@RestController
public class UserController {

    @Autowired
    private BaseUserService baseUserService;

   /* @Autowired
    private AutomicLogin automicLogin;*/

    @PostMapping(SecurityConstants.DEFAULT_REGISTER_URL)
    public JSONObject register(String mobile,String smsCode,String password) {
        SysUser sysUser = new SysUser();
        //从请求中获取手机号码
        //String alicode = httpServletRequest.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS);
        //String mobile = httpServletRequest.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
        //String password = httpServletRequest.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_PASSWORD);
        String params = mobile+","+password;
        //进行验证码对比校验
        long phone = Long.valueOf(mobile);
        try {
            String code = AliyunSMS.querySendDetails(mobile);
            if (code.equals(smsCode)){
                sysUser.setPhone(phone);
                sysUser.setPassword(password);
                sysUser.setRegisterTime(new Date());
                sysUser.setLastLoginTime(new Date());
                if (baseUserService.register(sysUser)){
                  //  automicLogin.shili(httpServletRequest,mobile,password);
                    return ResultUtil.toJSONString(ResultEnum.SUCCESS,params);
                }else {
                    return ResultUtil.toJSONString(ResultEnum.ACCOUNT_EXISTED,mobile);
                }
            }else {
                return ResultUtil.toJSONString(ResultEnum.CODE_ERROT,mobile);
            }
        } catch (ClientException e) {
            e.printStackTrace();
            return ResultUtil.toJSONString(ResultEnum.UNKONW_ERROR,mobile);
        }
    }

}