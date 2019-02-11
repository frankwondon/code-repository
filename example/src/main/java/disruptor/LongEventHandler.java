package disruptor;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        longEvent.setValue(longEvent.getValue()+1);
        System.out.println("1-----msg:"+longEvent.getValue()+",seq:"+l+"bool:"+b);
    }
}
