package netty.exercise.ch06;

import org.junit.Test;

import java.nio.ByteBuffer;

public class ByteBufferTest3 {
    @Test
    public void test() {
        ByteBuffer firstBuffer = ByteBuffer.allocate(11);
        System.out.println("바이트 버퍼 초기값: " + firstBuffer);

        firstBuffer.put((byte) 1);
        System.out.println(firstBuffer.get());
        System.out.println(firstBuffer);
    }
}
