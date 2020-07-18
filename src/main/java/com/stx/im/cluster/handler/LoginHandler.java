package com.stx.im.cluster.handler;

import com.stx.im.cluster.dao.UserDao;
import com.stx.im.cluster.model.User;
import com.stx.im.cluster.session.ServerSession;
import com.stx.im.cluster.session.UserLoginMap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class LoginHandler extends ChannelInboundHandlerAdapter {

    /**
     * 每台机器需要一个唯一的serverId来标示机器，本例暂时用 1 2 等来标示
     */
    private String serverId;

    public LoginHandler(String serverId) {
        this.serverId = serverId;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof User){
            User u = (User) msg;
            if(checkLogin(u)){
                //创建并绑定会话
                ServerSession serverSession = new ServerSession(ctx.channel());
                serverSession.setUser(u);
                serverSession.bind();
                //在缓存里记录客户端链接打到哪一台机器上
                UserLoginMap.add(u.getId()+"",serverId);
                //删除handler
                ctx.pipeline().remove(LoginHandler.this);
                System.out.println("login:"+u);
            }
        }
    }

    private boolean checkLogin(User u){
        return UserDao.getUserById(u.getId()) != null;
    }
}
