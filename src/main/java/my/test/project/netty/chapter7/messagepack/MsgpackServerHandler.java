package my.test.project.netty.chapter7.messagepack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.msgpack.MessagePack;

import java.util.LinkedHashMap;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/11/29 16:46
 * @Modified by:
 */
public class MsgpackServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
//            //直接输出msg
//            System.out.println(msg);
//            String remsg = new String("has received !");
//            //回复has receive 给客户端
//            ctx.write(remsg);


            LinkedHashMap uMap = (LinkedHashMap)msg;
            System.out.println("The Server Recieve message:" + uMap);
            ctx.writeAndFlush(msg); //收到消息后直接返回给Client

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}


