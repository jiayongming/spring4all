package com.gzw.auth.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gzw.auth.domain.Result;
import com.gzw.auth.enums.ResultEnum;

/**
 * Created by qzj on 2018/2/25
 */
public class ResultUtil {

    public static JSONObject toJSONString(ResultEnum resultEnum, Object object) {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMessage(resultEnum.getMsg());
        result.setData(object);
        JSONObject json = (JSONObject) JSON.toJSON(result);
        return  json;
    }

        public static Result error(Integer code, String msg) {
            Result result = new Result();
            result.setCode(code);
            result.setMessage(msg);
            return result;
        }

}
