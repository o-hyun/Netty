package netty.exercise.ch06;

import java.nio.ByteBuffer;

public class ByteBufferTest2 {
    public static void main(String[] args) {
        ByteBuffer firstBuffer = ByteBuffer.allocate(11);
        System.out.println("바이트 버퍼 초기값: " + firstBuffer);

        byte[] source = "Hello world!".getBytes();
        for (byte item : source) {
            firstBuffer.put(item);
            System.out.println("현재 상태: " + firstBuffer + " " + (char) item);
        }
    }
}
