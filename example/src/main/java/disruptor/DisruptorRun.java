package disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

/***
 * 环形队列
 */
public class DisruptorRun {
    public static void main(String[] args) throws Exception{
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        LongEventHandler firstHandler=new LongEventHandler();
        LongTwoEventHandler twoHandler=new LongTwoEventHandler();
        // Connect the handler
        /***
         *这里可以实现1-2串行 并行 3-4串行 2，4执行完5再执行。
         */
        disruptor.handleEventsWith(firstHandler);
        disruptor.after(firstHandler).handleEventsWith(twoHandler);
        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++)
        {
            bb.putLong(0, l);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.setValue(buffer.getLong(0)), bb);
            Thread.sleep(1000);
        }
    }
}
