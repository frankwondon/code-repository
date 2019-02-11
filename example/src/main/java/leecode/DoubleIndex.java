package leecode;

/**
 * 双指针算法
 */
public class DoubleIndex {
    public static void main(String[] args) {
        int[]num=new int[]{1,3,5,8,11,18,19};
        int target=13;
        int[] ints = DoubleIndex.twoSum(num, target);
        boolean b = DoubleIndex.judgeSquareSum(target);

    }

    /**
     * 查找有序数组中是否有两个数相加等于目标值
     * @param numbers 有序数组
     * @param target 目标值
     */
    public static int[] twoSum(int[] numbers, int target) {
        //i为数组头，j为数组尾。取值相加
        //如果大于目标值则j向前移动一位
        //如果小于目标值则i向后移动一位
        int i = 0, j = numbers.length - 1;
        while (i < j) {
            int sum = numbers[i] + numbers[j];
            if (sum == target) {
                return new int[]{numbers[i], numbers[j]};
            } else if (sum < target) {
                i++;
            } else {
                j--;
            }
        }
        return null;
    }

    /**
     * 查找当前值是否存在两个数的平方和
     * @param c 当前值
     * @return
     */
    public static boolean judgeSquareSum(int c) {
        //取当前值的开方
        int i = 0, j = (int) Math.sqrt(c);
        while (i <= j) {
            int powSum = i * i + j * j;
            if (powSum == c) {
                return true;
            } else if (powSum > c) {
                j--;
            } else {
                i++;
            }
        }
        return false;
    }
}
