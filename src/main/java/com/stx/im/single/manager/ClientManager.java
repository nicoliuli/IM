package com.stx.im.single.manager;


import com.stx.im.single.Client;
import com.stx.im.single.dao.UserDao;

public class ClientManager {
    public static void main(String[] args) throws Exception{
        new Client(UserDao.getUserById(1)).onStart();
        System.exit(-1);
    }
}
