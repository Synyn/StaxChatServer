package com.staxchat.message;

import com.staxchat.dto.Message;
import io.netty.channel.ChannelHandlerContext;

public class HelloWorldMessageFunction extends MessageFunction {
    public HelloWorldMessageFunction(ChannelHandlerContext ctx, Message message) {
        super(ctx, message);
    }

    @Override
    public void execute() {
        String jsonBody = message.getBody();
    }

    @Override
    public void validate() {

    }
}
