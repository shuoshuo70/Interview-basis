import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created by shuoshuo on 2017/9/28.
 */
public class Sorts {
    public static void main(String[] args) {
        int[] nums = {6, 2, 3, 12, 56, 8};
//        bubbleSort(nums);
        mergeSort(nums, 0, nums.length - 1);
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

    /**
     * O(nlogn), stable, used for sorting linkedLists with no extra space; count inversions; external sort
     * @param nums
     * @param left
     * @param right
     */
    private static void mergeSort(int[] nums, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(nums, left , mid);
            mergeSort(nums, mid + 1, right);
            merge(nums, left, mid, right);
        }
    }

    private static void merge(int[] nums, int left, int mid, int right) {
        int n1 = mid - left + 1, n2 = right - mid;
        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i=0; i<n1; i++) {
            L[i] = nums[left + i];
        }
        for (int i=0; i<n2; i++) {
            R[i] = nums[mid + 1 + i];
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (L[i] < R[j]) {
                nums[k++] = L[i++];
            } else {
                nums[k++] = R[j++];
            }
        }

        while (i < n1) {
            nums[k++] = L[i++];
        }

        while (j < n2) {
            nums[k++] = R[j++];
        }
    }
}
