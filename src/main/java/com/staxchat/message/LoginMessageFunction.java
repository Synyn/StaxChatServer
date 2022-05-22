package com.staxchat.message;

import com.staxchat.constants.ErrorMessages;
import com.staxchat.db.UserDao;
import com.staxchat.db.model.User;
import com.staxchat.dto.LoginRequestDTO;
import com.staxchat.dto.Message;
import com.staxchat.core.exception.StaxException;
import com.staxchat.message.core.MessageFunction;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class LoginMessageFunction extends MessageFunction {

    private Logger logger = Logger.getLogger(LoginMessageFunction.class);

    public LoginMessageFunction(ChannelHandlerContext ctx, Message message) {
        super(ctx, message);
    }

    @Override
    public void execute() {
        LoginRequestDTO login = (LoginRequestDTO) getMessage(message.getBody(), LoginRequestDTO.class);

        try {
            User user = UserDao.findUserByUsername(login.getUsername());

            if (user == null) {
                throw new StaxException(ErrorMessages.USER_NOT_FOUND);
            }
            // TODO: Handle the login functionality
            logger.info("Db User -> " + user.getUsername());
        } catch (SQLException sqlException) {
            throw new StaxException(sqlException.getMessage());
        }

    }

    @Override
    public void validateToken() {
        return;
    }

    @Override
    public void validateRole() {

    }
}
