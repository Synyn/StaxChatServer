package com.staxchat.message;

import com.staxchat.constants.ErrorMessages;
import com.staxchat.dto.Message;
import com.staxchax.core.exception.StaxException;
import io.netty.channel.ChannelHandlerContext;

public class MessageFactory {

    public static MessageFunction createMessage(Message message, ChannelHandlerContext ctx) {
        switch (message.getMessageType()) {
            case HELLO_WORLD -> {
                return new HelloWorldMessageFunction(ctx, message);
            }
            case LOGIN -> {
                return new LoginMessageFunction(ctx, message);
            }
            default -> {
                throw new StaxException(ErrorMessages.MESSAGE_TYPE_UNKNOWN);
            }
        }
    }
}
