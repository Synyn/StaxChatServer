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
import io.netty.channel.ChannelFuture;
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

    protected Object getMessage(JsonNode node, Class<?> clazz) {
        try {
            return mapper.treeToValue(node, clazz);
        }catch (JsonProcessingException exception) {
            throw new StaxException(exception.getMessage());
        }
    }


}
