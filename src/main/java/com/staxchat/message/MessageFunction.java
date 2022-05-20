package com.staxchat.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.staxchat.constants.Constants;
import com.staxchat.constants.ErrorMessages;
import com.staxchat.dto.ErrorResponse;
import com.staxchat.dto.ErrorType;
import com.staxchat.dto.Message;
import com.staxchax.core.exception.StaxException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public abstract class MessageFunction {
    protected ChannelHandlerContext ctx;

    protected Message message;

    private Logger logger = Logger.getLogger(MessageFunction.class);

    protected ObjectMapper mapper = new ObjectMapper();

    MessageFunction(ChannelHandlerContext ctx, Message message) {
        this.ctx = ctx;
        this.message = message;
    }

    public abstract void execute();

    public void validate() {

    }

    protected void sendMessage(Object json) {
        try {
            String jsonString = mapper.writeValueAsString(json);
            logger.info("Sending Message -> " + jsonString);
            ctx.writeAndFlush(Unpooled.copiedBuffer(jsonString, Constants.DEFAULT_CHARSET));
//            ctx.flush();
        } catch (JsonProcessingException exception) {
            throw new StaxException(exception.getMessage());
        }
    }

    protected Object getMessage(Object message, Class<?> clazz) throws JsonProcessingException {
        ByteBuf inBuff = (ByteBuf) message;
        String json = inBuff.toString(Constants.DEFAULT_CHARSET);

        return mapper.readValue(json, clazz);
    }

    protected Object getMessage(JsonNode node, Class<?> clazz) throws JsonProcessingException {
        return mapper.treeToValue(node, clazz);
    }


}
