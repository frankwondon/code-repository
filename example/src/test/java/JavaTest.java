import org.junit.Test;

public class JavaTest {
    @Test
    public void strEquals(){
        String a="abc";
        String b="abc";
        System.out.println(a==b);
        String c=new String("abc");
        System.out.println(a==c);
    }
}
