package com.funtl.myshop.plus.business.controller;

import com.funtl.myshop.plus.business.dto.LoginParam;
import com.funtl.myshop.plus.commons.dto.ResponseResult;
import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.Oneway;
import java.util.Map;

/**
 * 登录管理
 */
//解决跨域问题
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LoginController {
    @PostMapping(value = "/user/login")
    public ResponseResult<Map<String, Object>> login(@RequestBody LoginParam loginParam) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("token", "123456");
        return new ResponseResult<Map<String, Object>>(20000, HttpStatus.OK.toString(), result);
    }
}
