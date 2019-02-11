package guava;

import com.google.common.collect.*;
import common.UserEntity;

import java.math.BigInteger;

public class MultiMap {
    public static void TreeMultimap() throws Exception {
        Multiset multiset= TreeMultiset.create();
        int base=1000000;
        int split=1000;
        if (base%split!=0){
            throw  new Exception("不能整除！");
        }
        int fori=base/split;
        for (int i = 1; i <=fori ; i++) {
            int group=i;
            for (int j = 1; j <=split ; j++) {
                multiset.add(group,1);
            }
        }
        System.out.println( "分组count:"+multiset);
    }

    public static void FilterMultimap(){

    }

}
