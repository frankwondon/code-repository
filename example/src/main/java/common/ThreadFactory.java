package common;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadFactory {
    private ThreadFactory(){}
    private static volatile ThreadPoolExecutor threadPoolExecutor=null;
    public static ThreadPoolExecutor getInstance(){
        if (threadPoolExecutor==null){
            synchronized (ThreadFactory.class){
                if (threadPoolExecutor==null){
                    return new ThreadPoolExecutor(10,
                            20,
                            3000,
                            TimeUnit.MILLISECONDS,
                            new ArrayBlockingQueue<>(100));
                }else {
                    return threadPoolExecutor;
                }
            }
        }else {
            return threadPoolExecutor;
        }
    }
}
