package my.test.project.netty.chapter7.messagepack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/11/29 16:43
 * @Modified by:
 */
public class MsgpackServer {

    public void bind(int port) throws Exception {
        EventLoopGroup bossGruop = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGruop, workGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChildChannelHandler());

        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            bossGruop.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            // TODO Auto-generated method stub
            ch.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(1024, 0, 2,0,2));
            ch.pipeline().addLast("msgpack decoder",new MsgpackDecoder());
            ch.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
            ch.pipeline().addLast("msgpack encoder",new MsgpackEncoder());
            ch.pipeline().addLast(new MsgpackServerHandler());
        }
    }

    public static void main(String[] args) {
        int port = 8999;
        try {
            new MsgpackServer().bind(port);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
