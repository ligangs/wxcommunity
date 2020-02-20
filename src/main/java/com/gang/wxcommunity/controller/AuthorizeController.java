package com.gang.wxcommunity.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.wxcommunity.model.User;
import com.gang.wxcommunity.provide.WeChatProvide;
import com.gang.wxcommunity.service.UserService;
import com.gang.wxcommunity.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Value("${wechat.AppID}")
    private String appid;
    @Value("${wechat.AppSecret}")
    private String secret;

    @Autowired
    private UserService userService;
    @ResponseBody
    @PostMapping("/login")
    public ResponseVo handleLogin(HttpServletRequest req) {

        String code=null;
        String nickName=null;
        String avatarUrl=null;
        String openId=null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
            StringBuffer sb=new StringBuffer();
            String s=null;
            while((s=br.readLine())!=null){
                sb.append(s);
            }
            JSONObject jsonObject = JSONObject.parseObject(sb.toString());
            code = jsonObject.getString("code");
            nickName = jsonObject.getString("nickName");
            avatarUrl = jsonObject.getString("avatarUrl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        WeChatProvide weChatProvide = new WeChatProvide();
        try {
            String result=weChatProvide.run("https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code");

            JSONObject jsonObject = JSONObject.parseObject(result);
            // 获取到key为shoppingCartItemList的值
            openId = jsonObject.getString("openid");
            System.out.println(openId);
        } catch (IOException e){
        }
        //创建User对象
        User user = new User();
        user.setNickName(nickName);
        user.setAvatarUrl(avatarUrl);
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(System.currentTimeMillis());
        user.setOpenId(openId);
        user.setToken(UUID.randomUUID().toString());

        userService.addOrUpdate(user);
        return ResponseVo.getSuccResponse(user.getToken());
    }

}
