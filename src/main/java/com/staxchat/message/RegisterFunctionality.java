package com.staxchat.message;

import com.staxchat.constants.ErrorMessages;
import com.staxchat.core.exception.StaxException;
import com.staxchat.db.UserDao;
import com.staxchat.db.model.User;
import com.staxchat.dto.Message;
import com.staxchat.dto.RegisterMessage;
import com.staxchat.message.core.MessageFunction;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.Objects;

public class RegisterFunctionality extends MessageFunction {

    public RegisterFunctionality(ChannelHandlerContext ctx, Message message) {
        super(ctx, message);
    }

    @Override
    public void execute() {
        RegisterMessage registerMessage = (RegisterMessage) getMessage(message.getBody(), RegisterMessage.class);

        validateRegister(registerMessage);
        


    }

    private void validateRegister(RegisterMessage registerMessage) {
        if (StringUtils.isEmpty(registerMessage.getUsername())) {
            throw new IllegalArgumentException(ErrorMessages.EMPTY_USERNAME);
        }

        if (StringUtils.isEmpty(registerMessage.getPassword())) {
            throw new IllegalArgumentException(ErrorMessages.EMPTY_PASSWORD);
        }

        if (StringUtils.isEmpty(registerMessage.getConfirmPassword())) {
            throw new IllegalArgumentException(ErrorMessages.EMPTY_CONFIRM_PASSWORD);
        }

        if (StringUtils.isEmpty(registerMessage.getEmail())) {
            throw new IllegalArgumentException(ErrorMessages.EMPTY_EMAIL);
        }

        if (StringUtils.isEmpty(registerMessage.getDateOfBirth())) {
            throw new IllegalArgumentException(ErrorMessages.EMPTY_DATE_OF_BIRTH);
        }

        if (!Objects.equals(registerMessage.getPassword(), registerMessage.getConfirmPassword())) {
            throw new IllegalArgumentException(ErrorMessages.PASSWORD_MISMATCH_ERROR);
        }

        try {
            User user = UserDao.findUserByUsername(registerMessage.getUsername());

            if (user != null) {
                throw new StaxException(ErrorMessages.USER_ALREADY_EXISTS);
            }

        } catch (SQLException e) {
            throw new StaxException(e.getMessage());
        }

    }


    @Override
    public void validateRole() {

    }

    @Override
    public void validateToken() {

    }
}
