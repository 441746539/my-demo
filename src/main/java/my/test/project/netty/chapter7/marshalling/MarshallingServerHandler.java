package my.test.project.netty.chapter7.marshalling;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/12/10 14:40
 * @Modified by:
 */
public class MarshallingServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof String) {
            System.out.println(msg.toString());
        } else {
            ctx.writeAndFlush("received your msg");
            MsgInfo m = (MsgInfo) msg;
            System.out.println("client: " + m.getBody());
            System.out.println("client name : " + m.getUser().getUserName());
            m.setBody("人生苦短，快用python");
            ctx.writeAndFlush(m);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();;
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
