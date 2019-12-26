package my.test.project.netty.chapter7.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @Author: qiang.su
 * @Descreption:
 * @Date: Create in  2019/11/29 16:40
 * @Modified by:
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
//旧代码
//        final int length = msg.readableBytes();
//        byte[] b = new byte[length];
//        msg.getBytes(msg.readerIndex(), b, 0, length);
//        MessagePack msgpack = new MessagePack();
//        out.add(msgpack.read(b));

        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        out.add(MessagePackUtil.toObject(bytes, Object.class));//和旧代码区别是使用了ObjectMapper， 入参多了 obj.class
    }

}

