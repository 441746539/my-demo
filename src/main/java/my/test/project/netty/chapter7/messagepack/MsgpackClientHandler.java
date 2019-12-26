package my.test.project.netty.chapter7.messagepack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/11/29 16:47
 * @Modified by:
 */
public class MsgpackClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        //发送5个UserInfo给服务器，由于启用了粘包/拆包支持，所以这里连续发送多个也不会出现粘包的现象。
        for (int i = 0; i < 5; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setAge(i + "year");
            userInfo.setUsername("senninha");
//            UserInfo.Address addr = new UserInfo().new Address();
//            addr.setAddress1("address1");
//            userInfo.setAddr(addr);
            ctx.write(userInfo);
        }
        ctx.flush();
        System.out.println("-----------------send over-----------------");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}