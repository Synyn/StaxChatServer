package com.staxchat.message;

import com.staxchat.dto.Message;
import io.netty.channel.ChannelHandlerContext;

public abstract class MessageFunction {
    private ChannelHandlerContext ctx;

    private Message message;

    MessageFunction(ChannelHandlerContext ctx, Message message) {
        this.ctx = ctx;
        this.message = message;
    }

    public abstract void execute();

    public void validate() {

    }
}
