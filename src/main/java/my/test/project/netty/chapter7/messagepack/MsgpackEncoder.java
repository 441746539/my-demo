package my.test.project.netty.chapter7.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/11/29 16:39
 * @Modified by:
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
//        MessagePack msgpack = new MessagePack();
//        out.writeBytes(msgpack.write(msg));


        byte[] raw =  MessagePackUtil.toBytes(msg); // 使用了ObjectMapper
        out.writeBytes(raw);
    }

}
