package my.test.project.netty.chapter3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import static io.netty.buffer.Unpooled.copiedBuffer;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/11/27 13:08
 * @Modified by:
 */
public class SimpleClientHandler extends ChannelHandlerAdapter {

    private final ByteBuf firstMessage;
    public final static String UTF8 = "UTF-8";

    //初始化第一条消息
    public SimpleClientHandler(String firstMessage) {
        this.firstMessage = copiedBuffer(firstMessage.getBytes());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, UTF8);
        System.out.println("返回值：" + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //TODO LOG
        ctx.close();
    }

}
