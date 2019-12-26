package my.test.project.netty.chapter7.marshalling;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/12/10 14:40
 * @Modified by:
 */
public class MarshallingClient {
    private  int port;
    private  String address;

    public MarshallingClient(int port,String address) {
        this.port = port;
        this.address = address;
    }

    public void start(){
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientChannelInitializer());

        try {
            ChannelFuture future = bootstrap.connect(address,port).sync();
            future.channel().writeAndFlush("Hello Netty Server ,I am a common client");
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }

    }

    class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();


            pipeline.addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
            pipeline.addLast(MarshallingCodeCFactory.buildMarshallingEncoder());

            // 客户端的逻辑
            pipeline.addLast("handler", new MarshallingClientHandler());
        }
    }

    public static void main(String[] args) {
        MarshallingClient client = new MarshallingClient(8999,"127.0.0.1");
        client.start();
    }
}
