package com.staxchat.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.staxchat.service.EmailService;
import com.staxchat.util.MessageUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class ErrorResponse {
    private final ChannelHandlerContext context;
    private ErrorType errorType;
    private String message;
    private boolean closeSocket;

    private Logger logger = Logger.getLogger(ErrorResponse.class);


    private ErrorResponse(Builder builder) {
        this.context = builder.context;
        this.errorType = builder.errorType;
        this.message = builder.errorMessage;
        this.closeSocket = builder.closeSocket;
    }

    public ErrorType getErrorType() {
        return errorType;
    }


    public String getMessage() {
        return message;
    }

    public void send() {
        ChannelFuture channelFuture = MessageUtil.sendMessage(context, this);
        if (channelFuture.isSuccess() && closeSocket) {
            context.close();
        }
    }

    public static class Builder {
        private final ChannelHandlerContext context;
        private ErrorType errorType;
        private String errorMessage;
        private boolean closeSocket;

        public Builder(ChannelHandlerContext context) {
            this.context = context;
            this.closeSocket = false;
            this.errorType = ErrorType.WARNING;
        }

        public Builder withErrorType(ErrorType errorType) {
            this.errorType = errorType;
            return this;
        }

        public Builder withMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder closeSocket() {
            this.closeSocket = true;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }

    }

}
