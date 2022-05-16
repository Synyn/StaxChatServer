package com.staxchat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.staxchat.constants.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public class MessageService {

    private static ObjectMapper mapper = new ObjectMapper();

    public static void sendMessage(ChannelHandlerContext ctx, Object json) throws JsonProcessingException {
        String jsonString = mapper.writeValueAsString(json);
        ctx.write(Unpooled.copiedBuffer(jsonString, Constants.DEFAULT_CHARSET));
    }

    public Object getMessage(Object message, Class<?> clazz) throws JsonProcessingException {
        ByteBuf inBuff = (ByteBuf) message;
        String json = inBuff.toString(Constants.DEFAULT_CHARSET);

        return mapper.readValue(json, clazz);
    }

}
