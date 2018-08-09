package netty.exercise.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketMessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketMessageHandler.class);
    private static final ChannelGroup allChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception { // (1)
        Channel incoming = ctx.channel();
        for (Channel channel : allChannels) {
            if (channel != incoming){
                channel.writeAndFlush(new TextWebSocketFrame("[" + incoming.remoteAddress() + "]" + msg.text()));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text() ));
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel incoming = ctx.channel();

        // Broadcast a message to multiple Channels
        allChannels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 접속완료"));

        allChannels.add(incoming);
        LOGGER.info("Client:"+incoming.remoteAddress() +" 접속완료");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();

        // Broadcast a message to multiple Channels
        allChannels.writeAndFlush(new TextWebSocketFrame("[SERVER] - " + incoming.remoteAddress() + " 접속종료"));

        LOGGER.info("Client:"+incoming.remoteAddress() +" 접속종료");

        // A closed Channel is automatically removed from ChannelGroup,
        // so there is no need to do "channels.remove(ctx.channel());"
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        System.out.println("Client:"+incoming.remoteAddress()+" online");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        LOGGER.info("Client:"+incoming.remoteAddress()+" offline");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)	// (7)
            throws Exception {
        Channel incoming = ctx.channel();
        LOGGER.error("Client:"+incoming.remoteAddress()+"비정상종료");
        // 예외발생시 채널 종료
        cause.printStackTrace();
        ctx.close();
    }
}
