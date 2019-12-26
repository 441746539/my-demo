package my.test.project.netty.chapter7.marshalling;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/12/10 14:39
 * @Modified by:
 */
public class MarshallingServer {

    private int port;

    public MarshallingServer(int port) {
        this.port = port;
    }

    public void start(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap().group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer());

        try {
            ChannelFuture future = server.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();

            pipeline.addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
            pipeline.addLast(MarshallingCodeCFactory.buildMarshallingEncoder());

            // 自己的逻辑Handler
            pipeline.addLast("handler", new MarshallingServerHandler());
        }
    }

    public static void main(String[] args) {
        MarshallingServer server = new MarshallingServer(8999);
        server.start();
    }
}
