package com.staxchat.core.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.staxchat.constants.ErrorMessages;
import com.staxchat.dto.ErrorResponse;
import com.staxchat.dto.ErrorType;
import com.staxchat.dto.InitialMessage;
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
        InitialMessage initialMessage;

        try {
            initialMessage = formatMessage(msg);
        } catch (JsonProcessingException processingException) {
            ErrorResponse response =
                    new ErrorResponse.Builder(ctx)
                            .withErrorType(ErrorType.FATAL)
                            .withMessage(ErrorMessages.JSON_UNMARSHAL_FAILURE)
                            .closeSocket()
                            .build();

            response.send();
        }
    }

    /**
     * This method will take the raw message and convert it to the initial message request.
     * <p>
     * The initial message consists of a @MessageType and a Body, which is basically a json formatted string.
     * <p>
     * If the decoding(unmarshalling) of the json string fails, we will return an error message and close the socket.
     */
    private InitialMessage formatMessage(Object msg) throws JsonProcessingException {
        ByteBuf byteBuf = (ByteBuf) msg;
        String jsonMessage = byteBuf.toString(CharsetUtil.UTF_8);

        logger.info("JsonMessage -> " + jsonMessage);

        return mapper.readValue(jsonMessage, InitialMessage.class);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
