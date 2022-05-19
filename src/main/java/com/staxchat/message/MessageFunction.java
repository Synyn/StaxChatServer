package com.staxchat.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.staxchat.constants.Constants;
import com.staxchat.dto.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public abstract class MessageFunction {
    protected ChannelHandlerContext ctx;

    protected Message message;

    protected ObjectMapper mapper = new ObjectMapper();

    MessageFunction(ChannelHandlerContext ctx, Message message) {
        this.ctx = ctx;
        this.message = message;
    }

    public abstract void execute();

    public void validate() {

    }

    protected void sendMessage(Object json) throws JsonProcessingException {
        String jsonString = mapper.writeValueAsString(json);
        ctx.write(Unpooled.copiedBuffer(jsonString, Constants.DEFAULT_CHARSET));
    }

    protected Object getMessage(Object message, Class<?> clazz) throws JsonProcessingException {
        ByteBuf inBuff = (ByteBuf) message;
        String json = inBuff.toString(Constants.DEFAULT_CHARSET);

        return mapper.readValue(json, clazz);
    }

    protected Object getMessage(String jsonMessage, Class<?> clazz) throws JsonProcessingException{
        return mapper.readValue(jsonMessage, clazz);
    }


}
