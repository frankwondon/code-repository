package execute;

import bean.CrmChance;
import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 在生产环境一次java.lang.StackOverflowError
 * 原因是因为递归调用方法，该方法局部变量很多递归深度 预估200+
 */
public class RecursiveExecute {
    long executeMaxCount=10000;
    static AtomicLong atomicLong=new AtomicLong(0);
    static AtomicLong atomicLong1=new AtomicLong(0);


    void execute(){
        Stopwatch stopwatch=Stopwatch.createStarted();
        try {
            this.recursive(new CrmChance());
            System.out.println("===========未出错,用时"+stopwatch.stop().elapsed(TimeUnit.MILLISECONDS)+"ms===============");
            System.out.println("递归深度："+atomicLong);
            System.out.println("==============================");
        } catch (Throwable e) {
            System.out.println("===========出错，用时"+stopwatch.stop().elapsed(TimeUnit.MILLISECONDS)+"ms===============");
            System.out.println("递归深度："+atomicLong);
            System.out.println("==============================");
//            e.printStackTrace();
        }
    }


    void execute1(){
        Stopwatch stopwatch=Stopwatch.createStarted();
        try {
            while (!this.recursive1(new CrmChance()));
            System.out.println("===========未出错,用时"+stopwatch.stop().elapsed(TimeUnit.MILLISECONDS)+"ms===============");
            System.out.println("循环次数："+atomicLong1);
            System.out.println("==============================");
        } catch (Throwable e) {
            System.out.println("============出错"+stopwatch.stop().elapsed(TimeUnit.MILLISECONDS)+"ms============");
            System.out.println("循环次数："+atomicLong1);
            System.out.println("==============================");
//            e.printStackTrace();
        }
    }

    private boolean recursive(CrmChance icrmChance){
        atomicLong.incrementAndGet();
        CrmChance crmChance=new CrmChance();
        CrmChance crmChance1=new CrmChance();
        CrmChance crmChance2=new CrmChance();
        CrmChance crmChance3=new CrmChance();
        CrmChance crmChance4=new CrmChance();
        CrmChance crmChance5=new CrmChance();
        CrmChance crmChance6=new CrmChance();
        CrmChance crmChance7=new CrmChance();
        if (atomicLong.get()==executeMaxCount){
            return true;
        }else {
            return recursive(icrmChance);
        }
    }


    private boolean recursive1(CrmChance icrmChance){
        atomicLong1.incrementAndGet();
        CrmChance crmChance=new CrmChance();
        CrmChance crmChance1=new CrmChance();
        CrmChance crmChance2=new CrmChance();
        CrmChance crmChance3=new CrmChance();
        CrmChance crmChance4=new CrmChance();
        CrmChance crmChance5=new CrmChance();
        CrmChance crmChance6=new CrmChance();
        CrmChance crmChance7=new CrmChance();
        if (atomicLong1.get()==executeMaxCount){
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) {
        RecursiveExecute recursiveExecute=new RecursiveExecute();
        recursiveExecute.execute();
        recursiveExecute.execute1();
    }
}
