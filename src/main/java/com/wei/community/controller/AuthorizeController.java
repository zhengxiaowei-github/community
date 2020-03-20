package com.wei.community.controller;

import com.wei.community.Provider.GithubProvider;
import com.wei.community.dto.AccessTokenDTO;
import com.wei.community.dto.GithubUser;
import com.wei.community.mapper.UserMapper;
import com.wei.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    /**
     * 不知道为什么读取不了application.properties的配置文件
     */
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;
    /**
     * 当成功获取登陆信息后，返回调用这个方法
     */
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state")String state,
                            HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("9c102c0040de62f3e14c");
        accessTokenDTO.setClient_secret("796b0b080480715c75ba4c764cbe16bf2a4caf1d");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url("http://localhost:8080/callback");
        accessTokenDTO.setState(state);
//        accessTokenDTO.setClient_id(clientId);
//        accessTokenDTO.setClient_secret(clientSecret);
//        accessTokenDTO.setCode(code);
//        accessTokenDTO.setRedirect_url(redirectUrl);
//        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println("accesstoken"+accessToken);
        System.out.println("user"+githubUser);
        System.out.println(githubUser.getName());
        if (githubUser != null){
            //登陆成功
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);  //ctrl+alt+V抽取变量
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            //登陆失败
            System.out.println("登陆失败");
            return "redirect:/";
        }
  }
}
