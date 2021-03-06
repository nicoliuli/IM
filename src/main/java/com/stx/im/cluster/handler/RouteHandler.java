package com.stx.im.cluster.handler;

import com.alibaba.fastjson.JSON;
import com.stx.im.cluster.model.ChatMsg;
import com.stx.im.cluster.model.MsgType;
import com.stx.im.cluster.session.Redis;
import com.stx.im.cluster.session.ServerSession;
import com.stx.im.cluster.session.ServerSessionMap;
import com.stx.im.cluster.session.UserLoginMap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;

/**
 * 起到服务端聊天消息路由的作用
 */
public class RouteHandler extends ChannelInboundHandlerAdapter {
    private String serverId;
    public RouteHandler(String serverId) {
        this.serverId = serverId;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof ChatMsg){
            ChatMsg chatMsg = (ChatMsg) msg;
            Integer toId = chatMsg.getToId();
            String sId = UserLoginMap.getServerId(toId + "");
            if(sId == null || "".equals(sId)){
                //对方不在线
                List<ServerSession> sessions = ServerSessionMap.getSessionByUid(chatMsg.getFromId());
                if(sessions!=null && sessions.size()>0){
                    for(ServerSession session:sessions){
                        Channel channel = session.getChannel();
                        ChatMsg chat = new ChatMsg();
                        chat.setToId(chatMsg.getFromId());
                        chat.setFromId(10000001);
                        chat.setMsgType(MsgType.MSG_TYPE_CHATMSG);
                        chat.setText(toId+"不在线");
                        channel.writeAndFlush(JSON.toJSONString(chat));
                    }
                }
                return;
            }
            if(this.serverId.equals(sId)){
                List<ServerSession> sessions = ServerSessionMap.getSessionByUid(toId);
                if(sessions!=null && sessions.size()>0){
                    for(ServerSession session:sessions){
                        Channel channel = session.getChannel();
                        channel.writeAndFlush(JSON.toJSONString(chatMsg));
                    }
                }
                return;
            }
            if("1".equals(sId)){
                Redis.q1.add(chatMsg);
            }else if("2".equals(sId)){
                Redis.q2.add(chatMsg);
            }
        }
    }
}
