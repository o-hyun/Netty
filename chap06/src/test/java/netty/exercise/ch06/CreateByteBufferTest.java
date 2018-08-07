package netty.exercise.ch06;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class CreateByteBufferTest {
    @Test
    public void createTest() {
        CharBuffer heapBuffer = CharBuffer.allocate(11);
        assertEquals(11, heapBuffer.capacity());
        assertEquals(false, heapBuffer.isDirect());

        ByteBuffer directBuffer = ByteBuffer.allocateDirect(11);
        assertEquals(11, directBuffer.capacity());
        assertEquals(true, directBuffer.isDirect());

        List<Integer> arr = IntStream.range(1, 10).boxed().collect(toList());
        arr.addAll(Arrays.asList(0, 0));
        IntBuffer intHeapBuffer = IntBuffer.wrap(arr.stream().mapToInt(Integer::intValue).toArray());
        assertEquals(11, intHeapBuffer.capacity());
        assertEquals(false, intHeapBuffer.isDirect());
    }
}
