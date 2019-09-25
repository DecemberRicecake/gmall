package com.gmall.service.impl;

import com.gmall.common.Const;
import com.gmall.common.ServerResponse;
import com.gmall.dao.UserMapper;
import com.gmall.pojo.User;
import com.gmall.service.IUserService;
import com.gmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0 ){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        //MD5加密
        String md5Password = MD5Util.MD5EncodeUtf8(password);

        User user = userMapper.selectLogin(username, md5Password);
        if (user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(null);
        return ServerResponse.createBySuccess("登录成功", user);
    }

    @Override
    public ServerResponse<String> register(User user){
        ServerResponse vaildResponse = this.checkVaild(user.getEmail(), Const.Email);
        //设置用户身份是普通用户
        user.setRole(Const.Role.ROLE_CUSTOMER);

        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        resultCount = userMapper.insert(user);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServerResponse<String> checkVaild(String str, String type){
        if(StringUtils.isNoneBlank(str)){
            if (type.equals(Const.Email)){
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0 ){
                    return ServerResponse.createByErrorMessage("邮箱已注册");
                }
            }
            if (type.equals(Const.USERNAME)){
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0 ){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            return ServerResponse.createBySuccessMessage("校验通过");
        }else {
            if (type.equals(Const.Email)){
                return ServerResponse.createByErrorMessage("邮箱不能为空");
            }
            if (type.equals(Const.USERNAME)){
                return ServerResponse.createByErrorMessage("用户名不能为空");
            }
            return ServerResponse.createByErrorMessage("参数错误");
        }
    }
}
