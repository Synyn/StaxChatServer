package com.staxchat.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.staxchat.constants.Constants;
import com.staxchat.core.exception.StaxException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class MessageUtil {

    private static Logger logger = Logger.getLogger(MessageUtil.class);
    private static ObjectMapper mapper = new ObjectMapper();

    public static ChannelFuture sendMessage(ChannelHandlerContext ctx, Object json) {
        try {
            String jsonString = mapper.writeValueAsString(json);
            logger.info("Sending message -> " + jsonString);
            return ctx.writeAndFlush(Unpooled.copiedBuffer(jsonString, Constants.DEFAULT_CHARSET));
        } catch (JsonProcessingException exception) {
            throw new StaxException(exception.getMessage());
        }
    }

    public static Object getMessage(Object message, Class<?> clazz) throws JsonProcessingException {
        ByteBuf inBuff = (ByteBuf) message;
        String json = inBuff.toString(Constants.DEFAULT_CHARSET);

        return mapper.readValue(json, clazz);
    }

    public static Object getMessage(String jsonMessage, Class<?> clazz) throws JsonProcessingException {
        return mapper.readValue(jsonMessage, clazz);
    }

}
