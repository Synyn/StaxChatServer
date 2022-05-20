package com.staxchat.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.staxchat.constants.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class MessageUtil {

    private static Logger logger = Logger.getLogger(MessageUtil.class);
    private static ObjectMapper mapper = new ObjectMapper();

    public static void sendMessage(ChannelHandlerContext ctx, Object json) throws JsonProcessingException {
        String jsonString = mapper.writeValueAsString(json);
        logger.info("Sending message -> " + jsonString);
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello " + jsonString, Constants.DEFAULT_CHARSET));
    }

    public static Object getMessage(Object message, Class<?> clazz) throws JsonProcessingException {
        ByteBuf inBuff = (ByteBuf) message;
        String json = inBuff.toString(Constants.DEFAULT_CHARSET);

        return mapper.readValue(json, clazz);
    }

    public static Object getMessage(String jsonMessage, Class<?> clazz) throws JsonProcessingException{
        return mapper.readValue(jsonMessage, clazz);
    }

}
