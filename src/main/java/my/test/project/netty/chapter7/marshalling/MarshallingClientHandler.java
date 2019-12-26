package my.test.project.netty.chapter7.marshalling;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/12/10 14:40
 * @Modified by:
 */
public class MarshallingClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof String){
            System.out.println(msg);
        }else{
            MsgInfo m = (MsgInfo)msg;
            System.out.println("client: "+m.getBody());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MsgInfo msg = new MsgInfo();
        msg.setHeader((byte)0xa);
        msg.setLength(34);
        MsgInfo.User u = new MsgInfo().new User();
        u.setUserName("Name");
        msg.setUser(u);
        msg.setBody("这个是body！");

        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client is close");
    }
}
