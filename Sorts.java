import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created by shuoshuo on 2017/9/28.
 */
public class Sorts {
    public static void main(String[] args) {
        int[] nums = {6, 2, 3, 12, 56, 8};
        bubbleSort(nums);
        for (int num : nums) {
            System.out.print(num + "  ");
        }
//        System.out.println(expSearch(nums, 3));
    }

    /**
     * O(n^2) stable, used when number of elements is small, or input array is almost sorted, only few elements misplaced
     * @param nums
     */
    public static void insertSort(int[] nums) {
        for (int i=1; i<nums.length; i++) {
            int temp = nums[i];

            int j = i - 1;
            for (; j >= 0; j--) {
                if (temp < nums[j]) {
                    nums[j + 1] = nums[j];
                } else {
                    break;
                }
            }

            nums[j + 1] = temp;
        }
    }

    private static void kSort(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(k + 1);

        for (int i=0; i<=k; i++) {
            pq.offer(nums[i]);
        }

        int index = 0;
        for (int i=k+1; i<nums.length; i++) {
            nums[index++] = pq.poll();
            pq.offer(nums[i]);
        }

        while(!pq.isEmpty()) {
            nums[index++] = pq.poll();
        }

    }

    private static int expSearch(int[] nums, int x) {
        if (nums[0] == x) {
            return 0;
        }

        int i = 1;
        while (i < nums.length && nums[i] <= x) {
            i *= 2;
        }

        return Arrays.binarySearch(nums, i/2, Math.min(i, nums.length), x);
    }

    /**
     * O(n^2), it never takes more than o(n) swaps and be useful when memory write is a costly operation
     * @param nums
     */
    private static void selectSort(int[] nums) {
        for (int i=0; i<nums.length; i++) {
            int minIndex = i;
            for (int j=i+1; j<nums.length; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }

            int temp = nums[i];
            nums[i] = nums[minIndex];
            nums[minIndex] = temp;
        }
    }

    /**
     * O(n^2), stable, detect a very small error in almost-sorted array and fix it with just linear complexity(2n)
     * @param nums
     */
    private static void bubbleSort(int[] nums) {
        boolean swaped = true;
        for (int i=0; i<nums.length; i++) {
            swaped = false;
            for (int j=0; j<nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                    swaped = true;
                }
            }

            if (!swaped) {
                break;
            }
        }
    }
}
