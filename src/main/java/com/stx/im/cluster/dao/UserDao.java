package com.stx.im.cluster.dao;


import com.stx.im.cluster.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 用集合模拟数据库
 */
public class UserDao {
    private static Map<Integer, User> users = new HashMap<>();

    static {
        for (int i = 1; i <= 1000; i++) {
            User u = new User(i, "姓名" + i, i);
            users.put(i, u);
        }
    }

    public static User getUserById(Integer id) {
        return users.get(id);
    }
}
