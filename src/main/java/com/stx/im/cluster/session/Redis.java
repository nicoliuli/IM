package com.stx.im.cluster.session;


import com.stx.im.cluster.model.ChatMsg;

import java.util.concurrent.LinkedBlockingQueue;

public class Redis {
    //Server1监听q1队列
    public static LinkedBlockingQueue<ChatMsg> q1 = new LinkedBlockingQueue<>();
    //Server2监听q2队列
    public static LinkedBlockingQueue<ChatMsg> q2 = new LinkedBlockingQueue<>();
}
