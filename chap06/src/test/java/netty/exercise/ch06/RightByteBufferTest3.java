package netty.exercise.ch06;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class RightByteBufferTest3 {
    @Test
    public void test() {
        ByteBuffer firstBuffer = ByteBuffer.allocate(11);
        System.out.println("바이트 버퍼 초기값: " + firstBuffer);

        firstBuffer.put((byte) 1);
        firstBuffer.put((byte) 2);
        assertEquals(2, firstBuffer.position());

        firstBuffer.rewind();
        assertEquals(0, firstBuffer.position());

        assertEquals(1, firstBuffer.get());
        assertEquals(1, firstBuffer.position());

        System.out.println(firstBuffer);
    }
}
