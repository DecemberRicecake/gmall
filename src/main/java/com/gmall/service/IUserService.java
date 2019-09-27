package com.gmall.service;

import com.gmall.common.ServerResponse;
import com.gmall.pojo.User;

import javax.servlet.http.HttpSession;

public interface IUserService {
    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkVaild(String str, String type);


}
