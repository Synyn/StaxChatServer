package com.staxchat.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.staxchat.constants.ErrorMessages;
import com.staxchat.db.DataSource;
import com.staxchat.dto.ErrorResponse;
import com.staxchat.dto.ErrorType;
import com.staxchat.dto.HelloWorldMessageRequestDTO;
import com.staxchat.dto.Message;
import com.staxchat.util.MessageUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

public class HelloWorldMessageFunction extends MessageFunction {
    public HelloWorldMessageFunction(ChannelHandlerContext ctx, Message message) {
        super(ctx, message);
    }

    @Override
    public void execute() {
        HelloWorldMessageRequestDTO messageRequestDTO =
                (HelloWorldMessageRequestDTO) getMessage(message.getBody(), HelloWorldMessageRequestDTO.class);

        String message = messageRequestDTO.getMessage();
        HashMap<Object, Object> responseMap = new HashMap<>();
        responseMap.put("message", message);

        ChannelFuture future = MessageUtil.sendMessage(super.ctx, responseMap);
        if(future.isSuccess()) {
            MessageUtil.sendMessage(super.ctx, responseMap);
        }

//        ctx.close();

    }

    @Override
    public void validate() {

    }
}
