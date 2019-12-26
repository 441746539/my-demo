package my.test.project.netty.chapter4.linebasedframedecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/11/27 13:08
 * @Modified by:
 */
public class GoodTCPClient {

    public void connect(int port,String host) throws InterruptedException {
        //配置客户端nio线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //处理粘包
                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            socketChannel.pipeline().addLast(new StringDecoder());

                            socketChannel.pipeline().addLast(new GoodTCPClientHandler("QUERY TIME"));
                        }
                    });

            //发起异步连接
            ChannelFuture f = b.connect(host,port).sync();

            //close
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new GoodTCPClient().connect(8999,"127.0.0.1");
    }
}
