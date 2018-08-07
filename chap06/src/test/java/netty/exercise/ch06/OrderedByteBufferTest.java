package netty.exercise.ch06;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public class OrderedByteBufferTest {
    @Test
    public void pooledHeapBufferTest() {
        ByteBuf buf = Unpooled.buffer();
        assertEquals(ByteOrder.BIG_ENDIAN, buf.order());
        buf.writeShort(1);
        buf.markReaderIndex();
        assertEquals(0x0001, buf.readShort());
        buf.resetReaderIndex();
        ByteBuf littleEndianBuf = buf.order(ByteOrder.LITTLE_ENDIAN);
        assertEquals(0x0100, littleEndianBuf.readShort());
    }

    final String source = "Hello world";

    @Test
    public void convertNettyBufferToJavaBuffer() {
        ByteBuf buf = Unpooled.buffer(11);
        buf.writeBytes(source.getBytes());
        assertEquals(source, buf.toString(Charset.defaultCharset()));

        ByteBuffer nioByteBuffer = buf.nioBuffer();
        Assert.assertNotNull(nioByteBuffer);
        assertEquals(source, new String(nioByteBuffer.array(), nioByteBuffer.arrayOffset(), nioByteBuffer.remaining()));
    }

    @Test
    public void convertJavaBufferToNettyBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(source.getBytes());
        ByteBuf nettyBuffer = Unpooled.wrappedBuffer(byteBuffer);
        assertEquals(source, nettyBuffer.toString(Charset.defaultCharset()));
    }
}
