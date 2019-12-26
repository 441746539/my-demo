package my.test.project.netty.chapter4.bad_tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/11/27 13:08
 * @Modified by:
 */
public class BadTCPClientHandler extends ChannelHandlerAdapter {

    private final byte[] req;
    public final static String UTF8 = "UTF-8";

    //初始化第一条消息
    public BadTCPClientHandler(String firstMessage) {
        firstMessage = firstMessage + System.getProperty("line.separator");
        this.req = firstMessage.getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
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
