package com.staxchat.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.staxchat.constants.ErrorMessages;
import com.staxchat.db.DataSource;
import com.staxchat.dto.ErrorResponse;
import com.staxchat.dto.ErrorType;
import com.staxchat.dto.HelloWorldMessageRequestDTO;
import com.staxchat.dto.Message;
import com.staxchat.util.MessageUtil;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

public class HelloWorldMessageFunction extends MessageFunction {
    public HelloWorldMessageFunction(ChannelHandlerContext ctx, Message message) {
        super(ctx, message);
    }

    @Override
    public void execute() {
        HelloWorldMessageRequestDTO messageRequestDTO;
        try {
            messageRequestDTO = (HelloWorldMessageRequestDTO) getMessage(message.getBody(), HelloWorldMessageRequestDTO.class);
        } catch (JsonProcessingException exception) {
            ErrorResponse response = new ErrorResponse.Builder(ctx)
                    .withErrorType(ErrorType.WARNING)
                    .withMessage(ErrorMessages.JSON_UNMARSHAL_FAILURE)
                    .build();
            response.send();
            return;
        }

        String message = messageRequestDTO.getMessage();

        HashMap<Object, Object> responseMap = new HashMap<>();
        responseMap.put("message", message);

        sendMessage(responseMap);
//            Thread.sleep(100);
        sendMessage(responseMap);

//        ctx.close();

    }

    @Override
    public void validate() {

    }
}
