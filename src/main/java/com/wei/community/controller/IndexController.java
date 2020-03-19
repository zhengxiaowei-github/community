package com.wei.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    /**
     * 调用首页--------可以查看api文档（https://developer.github.com/apps/building-oauth-apps/）-----------------------------------
     *  0.==>首先英该在github上注册服务(GitHub Apps)--才能使用github登陆api
     * 1.==》发起https://github.com/login/oauth/authorize请求---携带client_id和redirect_uri和state
     *   ==》返回code
     * 2.==》发起https://github.com/login/oauth/access_token请求---携带参数（accessTokenDTO）--包括client_id,client_sectet,code,redirect_url,state共五个参数
     *   ==》返回token(即access_token)
     * 3.==》发起https://api.github.com/user请求，携带access_token
     *   ==>返回user信息
     */
    /**
     * idea中将项目上传到github
     * 1.git state  ==》查看修改状态
     * 2.git add .
     * 3.git commit -m "描述信息"
     * 4.git push 上传
     */
    @RequestMapping("/index")
    public String hello(){
        return "index";
    }
}
