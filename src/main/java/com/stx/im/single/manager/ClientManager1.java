package com.stx.im.single.manager;


import com.stx.im.single.Client;
import com.stx.im.single.dao.UserDao;

public class ClientManager1 {
    public static void main(String[] args) throws Exception{
        new Client(UserDao.getUserById(2)).onStart();
        System.exit(-1);
    }


}
