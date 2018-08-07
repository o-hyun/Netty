package netty.exercise.ch06;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class ReadByteBufferTest {
    @Test
    public void readTest() {
        byte[] tempArray = {1, 2, 3, 4, 5, 0, 0, 0, 0, 0, 0};
        ByteBuffer firstBuffer = ByteBuffer.wrap(tempArray);
        assertEquals(0, firstBuffer.position());
        assertEquals(11, firstBuffer.limit());

        IntStream.range(1, 5).forEach(i -> assertEquals(i, firstBuffer.get()));
        assertEquals(4, firstBuffer.position());
        assertEquals(11, firstBuffer.limit());

        firstBuffer.flip();
        assertEquals(0, firstBuffer.position());
        assertEquals(4, firstBuffer.limit());

        firstBuffer.get(3);
        assertEquals(0, firstBuffer.position());
    }
}
