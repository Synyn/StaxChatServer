package com.staxchat.message;

import com.staxchat.db.model.User;
import com.staxchat.dto.LoginRequestDTO;
import com.staxchat.dto.Message;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

public class LoginMessageFunction extends MessageFunction {

    private Logger logger = Logger.getLogger(LoginMessageFunction.class);
    public LoginMessageFunction(ChannelHandlerContext ctx, Message message) {
        super(ctx, message);
    }

    @Override
    public void execute() {
        LoginRequestDTO login = (LoginRequestDTO) getMessage(message.getBody(), LoginRequestDTO.class);

        User user = User.findUserByUsername(login.getUsername());
        logger.info("Db User -> " + user.getUsername());


    }
}
