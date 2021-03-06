package my.test.project.netty.chapter5.delimiterbasedframedecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/11/27 13:08
 * @Modified by:
 */
public class DelimiterTCPClient {

    public void connect(int port, String host) throws InterruptedException {
        //配置客户端nio线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //处理粘包
                            ByteBuf delimiter = Unpooled.copiedBuffer(MySeparator.MY_SEPARATOR.getBytes());
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
                            socketChannel.pipeline().addLast(new StringDecoder());

                            socketChannel.pipeline().addLast(new DelimiterTCPClientHandler("QUERY TIME"));
                        }
                    });

            //发起异步连接
            ChannelFuture f = b.connect(host, port).sync();

            //close
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new DelimiterTCPClient().connect(8999, "127.0.0.1");
    }
}
