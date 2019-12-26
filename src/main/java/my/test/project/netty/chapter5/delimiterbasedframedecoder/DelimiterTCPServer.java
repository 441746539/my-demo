package my.test.project.netty.chapter5.delimiterbasedframedecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/11/27 10:29
 * @Modified by:
 */
public class DelimiterTCPServer {

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel arg0) throws Exception {
            //处理粘包
            ByteBuf delimiter = Unpooled.copiedBuffer(MySeparator.MY_SEPARATOR.getBytes());
            arg0.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
            arg0.pipeline().addLast(new StringDecoder());
            arg0.pipeline().addLast(new DelimiterTCPServerHandler());
        }
    }

    public void bind(int port) throws Exception {
        //配置服务端nio线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler() {
                    });
            //绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();

            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            //退出
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        int port = 8999;
        new DelimiterTCPServer().bind(port);
    }

}
