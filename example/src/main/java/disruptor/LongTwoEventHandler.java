package disruptor;

import com.lmax.disruptor.EventHandler;

public class LongTwoEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println("2------msg:"+longEvent.getValue()+",seq:"+l+"bool:"+b);
    }
}
