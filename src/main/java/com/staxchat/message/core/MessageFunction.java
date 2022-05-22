package com.staxchat.message.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.staxchat.constants.ErrorMessages;
import com.staxchat.db.TokenDao;
import com.staxchat.db.model.Token;
import com.staxchat.dto.Message;
import com.staxchat.core.exception.StaxException;
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

    public MessageFunction(ChannelHandlerContext ctx, Message message) {
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
            Token dbToken = TokenDao.findToken(message.getToken());

            if (dbToken == null) {
                throw new StaxException(ErrorMessages.UNAUTORIZED);
            }

        } catch (SQLException e) {
            throw new StaxException(e.getMessage());
        }

    }
}
