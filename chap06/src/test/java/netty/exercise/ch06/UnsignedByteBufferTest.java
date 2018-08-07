package netty.exercise.ch06;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnsignedByteBufferTest {
    @Test
    public void unsignedBufferToJavaBuffer() {
        ByteBuf buf = Unpooled.buffer(11);
        buf.writeShort(-1);

        assertEquals(65535, buf.getUnsignedShort(0));
        assertEquals(-1, buf.getShort(0));
    }
}
