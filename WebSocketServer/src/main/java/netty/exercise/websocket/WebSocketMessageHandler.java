package netty.exercise.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketMessageHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketMessageHandler.class);
    private static final ChannelGroup allChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof TextWebSocketFrame) {
            final String text = ((TextWebSocketFrame) frame).text();
            LOGGER.info("Received text frame {}", text);
            allChannels.stream().filter(c -> c != ctx.channel())
                    .forEach(c -> {
                        c.writeAndFlush(frame);
                        LOGGER.info("flushing " + ((TextWebSocketFrame) frame).text());
                    });
        } else
            // For now, let's ignore binary frames
            throw new UnsupportedOperationException("Invalid websocket frame received");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("Adding new channel {} to list of channels", ctx.channel().remoteAddress());
        allChannels.add(ctx.channel());
    }
}
