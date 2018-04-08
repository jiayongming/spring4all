package com.gzw.auth.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzw.auth.enums.ResultEnum;
import com.gzw.auth.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * * 自定义登录成功处理
 * Created by qzj on 2018/2/2
 */

@Component
public class AjaxLoginAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;


    private static final Logger logger = LoggerFactory.getLogger(AjaxLoginAuthSuccessHandler.class);
    /**
     * 登录成功处理器
     * @param request
     * @param response
     * @param authentication
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        logger.info("【ajaxAuthSuccessHandler】 onAuthenticationSuccess authentication={}", authentication);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(ResultUtil.toJSONString(ResultEnum.SUCCESS, authentication.getName())));
    }
}
