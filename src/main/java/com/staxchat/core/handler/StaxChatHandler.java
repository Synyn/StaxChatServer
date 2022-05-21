package com.staxchat.core.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.util.StringUtils;
import com.staxchat.constants.ErrorMessages;
import com.staxchat.core.Server;
import com.staxchat.db.TokenDao;
import com.staxchat.db.model.Token;
import com.staxchat.dto.ErrorResponse;
import com.staxchat.dto.ErrorType;
import com.staxchat.dto.Message;
import com.staxchat.message.MessageFunction;
import com.staxchat.message.MessageFactory;
import com.staxchat.util.MessageUtil;
import com.staxchax.core.exception.StaxException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.apache.log4j.Logger;


public class StaxChatHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = Logger.getLogger(StaxChatHandler.class);

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message;

        try {

            /**
             * This method will take the raw message and convert it to the initial message request.
             * <p>
             * The initial message consists of a @MessageType and a Body, which is basically a dynamic json object.
             * <p>
             * If the decoding(unmarshalling) of the json string fails, we will return an error message and close the socket.
             */

            message = (Message) MessageUtil.getMessage(msg, Message.class);
        } catch (JsonProcessingException processingException) {
            processingException.printStackTrace();
            ErrorResponse response =
                    new ErrorResponse.Builder(ctx)
                            .withErrorType(ErrorType.FATAL)
                            .withMessage(ErrorMessages.JSON_UNMARSHAL_FAILURE)
                            .closeSocket()
                            .build();

            response.send();
            return;
        }

        MessageFunction messageFunction = MessageFactory.createMessage(message, ctx);
        try {
            messageFunction.validateToken();
            messageFunction.validateRole();

            messageFunction.execute();

        } catch (StaxException staxException) {
            ErrorResponse response =
                    new ErrorResponse.Builder(ctx)
                            .withErrorType(ErrorType.WARNING)
                            .withMessage(staxException.getMessage())
                            .closeSocket().build();
            response.send();
        }

    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
//        logger.info("Finished Reading");
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        logger.info("Thrown Exception !");
        ErrorResponse response = new ErrorResponse.Builder(ctx)
                .withErrorType(ErrorType.FATAL)
                .withMessage(cause.getMessage())
                .closeSocket()
                .build();

        response.send();
    }
}
