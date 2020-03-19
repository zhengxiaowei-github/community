package com.wei.community.controller;

import com.wei.community.Provider.GithubProvider;
import com.wei.community.dto.AccessTokenDTO;
import com.wei.community.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state")String state){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("9c102c0040de62f3e14c");
        accessTokenDTO.setClient_secret("796b0b080480715c75ba4c764cbe16bf2a4caf1d");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url("http://localhost:8080/callback");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());

        return "index";
    }
}
