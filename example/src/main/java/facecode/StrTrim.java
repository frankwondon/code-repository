package facecode;

public class StrTrim {
    public static void main(String[] args) {
        String hellow_world = StrTrim.replaceSpace(new StringBuffer("hellow world"));
        StringBuffer str=new StringBuffer("hello world");
        str.setCharAt(1,'A');
        System.out.println(str);
    }
    public static String replaceSpace(StringBuffer str) {
        int P1 = str.length() - 1;
        for (int i = 0; i <= P1; i++)
            if (str.charAt(i) == ' ')
                str.append("  ");

        int P2 = str.length() - 1;
        while (P1 >= 0 && P2 > P1) {
            char c = str.charAt(P1--);
            if (c == ' ') {
                str.setCharAt(P2--, '0');
                str.setCharAt(P2--, '2');
                str.setCharAt(P2--, '%');
            } else {
                str.setCharAt(P2--, c);
            }
        }
        return str.toString();
    }
}
