package com.stx.im.single.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stx.im.single.pojo.ChatMsg;
import com.stx.im.single.pojo.MsgType;
import com.stx.im.single.pojo.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 将JSON转化为POJO
 */
public class JsonMsgDecoder extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof String) {
            String jsonMsg = (String) msg;
            JSONObject jsonObject = JSON.parseObject(jsonMsg);
            if(jsonObject.containsKey("msgType")){
                Integer msgType = jsonObject.getInteger("msgType");
                if(msgType == MsgType.MSG_TYPE_USER){
                    User user = JSON.parseObject(jsonMsg, User.class);
                    ctx.fireChannelRead(user);
                }else if(msgType == MsgType.MSG_TYPE_CHATMSG){
                    ChatMsg chatMsg = JSON.parseObject(jsonMsg, ChatMsg.class);
                    ctx.fireChannelRead(chatMsg);
                }
            }

        }
    }
}
