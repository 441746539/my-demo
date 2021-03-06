package my.test.project.netty.chapter3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/11/27 10:50
 * @Modified by:
 */
public class SimpleServerHandler extends ChannelHandlerAdapter {

    public final static String UTF8 = "UTF-8";
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, UTF8);
        System.out.println("接收参数：" + body +", count = " + counter.getAndDecrement());

        //业务处理
        ByteBuf resp = doProcessResp(body);
        ctx.write(resp);

        // https://blog.csdn.net/andyzhu_2005/article/details/88774367
        ReferenceCountUtil.release(buf);
    }

    private ByteBuf doProcessResp(String body) {
        String currentTime = "QUERY TIME".equalsIgnoreCase(body) ? new java.util.Date(System.currentTimeMillis()).toString() : "BAD REQ ";
        return Unpooled.copiedBuffer(currentTime.getBytes());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
