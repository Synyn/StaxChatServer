package com.staxchat.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.staxchat.constants.Constants;
import com.staxchat.constants.ErrorMessages;
import com.staxchat.db.TokenDao;
import com.staxchat.db.model.Role;
import com.staxchat.db.model.Token;
import com.staxchat.dto.ErrorResponse;
import com.staxchat.dto.ErrorType;
import com.staxchat.dto.Message;
import com.staxchax.core.exception.StaxException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public abstract class MessageFunction {
    protected ChannelHandlerContext ctx;

    protected Message message;

    protected Token token;

    private Logger logger = Logger.getLogger(MessageFunction.class);

    protected ObjectMapper mapper = new ObjectMapper();

    MessageFunction(ChannelHandlerContext ctx, Message message) {
        this.ctx = ctx;
        this.message = message;
    }

    /**
     * This method is used for the business logic of the message
     */
    public abstract void execute();

    /**
     * This method is used for verifying the role if needed
     */
    public abstract void validateRole();

    protected Object getMessage(JsonNode node, Class<?> clazz) {
        try {
            return mapper.treeToValue(node, clazz);
        } catch (JsonProcessingException exception) {
            throw new StaxException(exception.getMessage());
        }
    }


    public void validateToken() {
        String token = message.getToken();

        if (StringUtils.isEmpty(token)) {
            throw new StaxException(ErrorMessages.UNAUTORIZED);
        }

        try {
            this.token = TokenDao.findToken(message.getToken());
        } catch (SQLException e) {
            throw new StaxException(e.getMessage());
        }

    }
}
