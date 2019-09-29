package com.gmall.dao;

import com.gmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    int checkEmail(String email);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    int checkPassword(@Param("userId") Integer userId, @Param("password") String password);

    int resetPassword(@Param("userId") Integer userId, @Param("newPassword") String newPassword);

    int checkEmailByUserId(@Param("userId") Integer userId, @Param("email") String email);

}