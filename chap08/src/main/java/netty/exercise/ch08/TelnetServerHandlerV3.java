package netty.exercise.ch08;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.exercise.ch08.junit.ResponseGenerator;

import java.net.InetAddress;
import java.util.Date;

public class TelnetServerHandlerV3 extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write(ResponseGenerator.makeHello());
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String request) {
        ResponseGenerator generator = new ResponseGenerator(request);
        String response = generator.response();
        ChannelFuture future = ctx.write(response);

        if (generator.isClose())
            future.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}