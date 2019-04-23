package com.imooc.dao;

import com.imooc.pojo.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by  OSCAR on 2018/12/19
 */
public interface UserDao {

    User getUserByUsername(String username);

    List<String> queryRolesByUserName(String username);

}
