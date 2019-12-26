package my.test.project.netty.chapter4.linebasedframedecoder;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/11/27 13:08
 * @Modified by:
 */
public class GoodTCPClientHandler extends ChannelHandlerAdapter {

    private final String firstMessage;
    public final static String UTF8 = "UTF-8";
    private AtomicInteger counter = new AtomicInteger(0);


    //初始化第一条消息
    public GoodTCPClientHandler(String firstMessage) {
        this.firstMessage = firstMessage;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 5; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer(firstMessage + System.getProperty("line.separator"), CharsetUtil.UTF_8));
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
        String body = msg.toString();
        System.out.println("返回值：" + body + ", count = " + counter.getAndIncrement());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //TODO LOG
        ctx.close();
    }

}
