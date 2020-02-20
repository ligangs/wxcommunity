package com.gang.wxcommunity.service;

import com.gang.wxcommunity.mapper.UserMapper;
import com.gang.wxcommunity.model.User;
import com.gang.wxcommunity.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void addOrUpdate(User user) {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andOpenIdEqualTo(user.getOpenId());

        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            userMapper.insert(user);
        } else {
            User dbUser = users.get(0);
            dbUser.setNickName(user.getNickName());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setToken(user.getToken());
            dbUser.setGmtModified(System.currentTimeMillis());
            userMapper.updateByPrimaryKey(dbUser);
        }
    }

    /**
     * 通过token 查找User
     */
    public User findUserByToken(String token){
        UserExample example = new UserExample();
        example.createCriteria().andTokenEqualTo(token);
        List<User> users = userMapper.selectByExample(example);
        return users.get(0);
    }
}
