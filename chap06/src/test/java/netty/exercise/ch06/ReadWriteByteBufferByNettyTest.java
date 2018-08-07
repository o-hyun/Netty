package netty.exercise.ch06;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReadWriteByteBufferByNettyTest {
    @Test
    public void createUnpooledHeapBufferTest() {
        ByteBuf buf = Unpooled.buffer(11);
        testBuffer(buf, false);
    }

    @Test
    public void createUnpooledDirectBufferTest() {
        ByteBuf buf = Unpooled.directBuffer(11);
        testBuffer(buf, true);
    }

    @Test
    public void createPooledHeapBufferTest() {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(11);
        testBuffer(buf, false);
    }

    @Test
    public void createPooledDirectBufferTest() {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer(11);
        testBuffer(buf, true);
    }

    private void testBuffer(ByteBuf buf, boolean isDirect) {
        assertEquals(11, buf.capacity());
        assertEquals(isDirect, buf.isDirect());

//        System.out.println(Integer.toBinaryString(65537));
//        System.out.println(Integer.toHexString(65537));
        buf.writeInt(65537);
        // 1 00000000 00000001
        assertEquals(4, buf.readableBytes());
        assertEquals(7, buf.writableBytes());

        // 00 01 00 01
        assertEquals(1, buf.readShort());
        assertEquals(2, buf.readableBytes());
        assertEquals(7, buf.writableBytes());

        assertEquals(true, buf.isReadable());
        assertEquals(true, buf.isReadable(1));
        assertEquals(true, buf.isReadable(2));
        assertEquals(false, buf.isReadable(3));

        buf.clear();

        assertEquals(0, buf.readableBytes());
        assertEquals(11, buf.writableBytes());
    }
}
