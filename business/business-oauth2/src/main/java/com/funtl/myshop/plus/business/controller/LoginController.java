package com.funtl.myshop.plus.business.controller;

import com.funtl.myshop.plus.business.dto.LoginInfo;
import com.funtl.myshop.plus.business.dto.LoginParam;
import com.funtl.myshop.plus.business.feign.ProfileFeign;
import com.funtl.myshop.plus.commons.dto.ResponseResult;
import com.funtl.myshop.plus.commons.utils.MapperUtils;
import com.funtl.myshop.plus.commons.utils.OkHttpClientUtil;
import com.funtl.myshop.plus.provider.domain.UmsAdmin;
import com.google.common.collect.Maps;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jws.Oneway;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 登录管理
 */
//解决跨域问题,现在不需要了
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LoginController {

    private static final String URL_OAUTH_TOKEN = "http://localhost:9001/oauth/token";

    @Value("${business.oauth2.grant_type}")
    public String oauth2_grant_type;

    @Value("${business.oauth2.client_id}")
    public String oauth2_client_id;

    @Value("${business.oauth2.client_secret}")
    public String oauth2_client_secret;

    @Resource(name = "userDetailsService")
    public UserDetailsService userDetailsService;

    @Resource
    public BCryptPasswordEncoder passwordEncoder;

    @Resource
    public TokenStore tokenStore;

    @Resource
    private ProfileFeign profileFeign;


    /**
     * 登录
     * @param loginParam 登录参数
     * @return
     */
    @PostMapping(value = "/user/login")
    public ResponseResult<Map<String, Object>> login(@RequestBody LoginParam loginParam) {
        Map<String, Object> result = Maps.newHashMap();

        //验证账号密码
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginParam.getUsername());

        if (userDetails == null || !passwordEncoder.matches(loginParam.getPassword(), userDetails.getPassword())) {
            return new ResponseResult<Map<String, Object>>(ResponseResult.CodeStatus.FAIL, "账号或密码错误", null);
        }
        Map<String, String> params = Maps.newHashMap();
        params.put("username", loginParam.getUsername());
        params.put("password", loginParam.getPassword());
        params.put("grant_type", oauth2_grant_type);
        params.put("client_id", oauth2_client_id);
        params.put("client_secret", oauth2_client_secret);

        try (Response response = OkHttpClientUtil.getInstance().postData(URL_OAUTH_TOKEN, params)) {
            //防止空指针异常
            String jsonString = Objects.requireNonNull(response.body()).string();
            Map<String, Object> jsonMap = MapperUtils.json2map(jsonString);
            String token = String.valueOf(jsonMap.get("access_token"));
            result.put("token", token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseResult<Map<String, Object>>(ResponseResult.CodeStatus.OK, "登录成功！", result);
    }

    @GetMapping(value = "/user/info")
    public ResponseResult<LoginInfo> info() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String jsonString = profileFeign.info(authentication.getName());
        UmsAdmin umsAdmin = MapperUtils.json2pojoByTree(jsonString, "data", UmsAdmin.class);

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setName(umsAdmin.getUsername());
        loginInfo.setAvatar(umsAdmin.getIcon());
        return new ResponseResult<LoginInfo>(ResponseResult.CodeStatus.OK, "获取用户信息", loginInfo);
    }

    @PostMapping(value = "/user/logout")
    public ResponseResult<Void> logout(HttpServletRequest request) {
        String token = request.getParameter("access_token");
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        tokenStore.removeAccessToken(oAuth2AccessToken);

        return new ResponseResult<Void>(ResponseResult.CodeStatus.OK, "用户注销", null);
    }
}
