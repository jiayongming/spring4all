package com.gzw.auth.endpoint;


import com.alibaba.fastjson.JSONObject;
import com.gzw.auth.enums.ResultEnum;
import com.gzw.auth.utils.JdbcOperateUtils;
import com.gzw.auth.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FrameworkEndpoint
public class RevokeTokenEndpoint {

    @Autowired
    @Qualifier("consumerTokenServices")
    ConsumerTokenServices consumerTokenServices;

    private static final Logger logger = LoggerFactory.getLogger(RevokeTokenEndpoint.class);

    @DeleteMapping("/oauth/exit")
    @ResponseBody
    public JSONObject revokeToken(String principal) {
        String access_token = JdbcOperateUtils.query(principal);
        if (!access_token.equals("gzw")) {
            if (consumerTokenServices.revokeToken(access_token)) {
                logger.info("oauth2 logout success with principal: "+ principal);
                return ResultUtil.toJSONString(ResultEnum.SUCCESS,principal);
            }
        }else {
            logger.info("oauth2 logout fail with principal: "+ principal);
            return ResultUtil.toJSONString(ResultEnum.FAIL,principal);
        }
        return ResultUtil.toJSONString(ResultEnum.UNKONW_ERROR,principal);
    }
}
