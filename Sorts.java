import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created by shuoshuo on 2017/9/28.
 */
public class Sorts {
    public static void main(String[] args) {
        int[] nums = {6, 2, 3, 12, 56, 6, 8};
        heapSort(nums);
//        quickSort(nums, 0, nums.length - 1);
        for (int num : nums) {
            System.out.print(num + "  ");
        }
//        System.out.println(expSearch(nums, 3));

        char[] arr = {'g', 'e', 'e', 'k', 's', 'f', 'o',
                'r', 'g', 'e', 'e', 'k', 's'};
        countSort(arr);
        for (char c : arr) {
            System.out.print(c + "  ");
        }

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
     * O(nlogn), worst O(nlogn), stable, used for sorting linkedLists with no extra space; count inversions; external sort
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

    /**
     * O(nlogn), worst O(n^2), instable, used for sorting arrays
     * array : quick sort  because merge sort need O(n) extra space
     * linkedlist : merge sort because list don't need extra space and insert operation only need O(1) time, list can't
     * do random access like array which quick sort needs
     * @param nums
     * @param low
     * @param high
     */
    private static void quickSort(int[] nums, int low, int high) {
        //attention: use if not while, for recursive has been done in if
        //recursion and iterator only need one
        if (low < high) {
            int index = partition(nums, low, high);
            quickSort(nums, low, index - 1);
            quickSort(nums, index + 1, high);
        }
    }

    private static int partition(int[] nums, int low, int high) {
        int i = low, j = high;
        int pivot = nums[i];

        while (i < j) {
            while (i < j && nums[j] >= pivot) {
                j--;
            }

            if (i < j) {
                nums[i++] = nums[j];
            }

            while (i < j && nums[i] < pivot) {
                i++;
            }

            if (i < j) {
                nums[j--] = nums[i];
            }
        }
        nums[i] = pivot;

        return i;
    }

    /**
     * O(nlogn), used for sorted nearby sort array; K top numbers
     * @param nums
     */
    private static void heapSort(int[] nums) {
        buildHeap(nums);
        for (int i=nums.length - 1; i >= 0; i--) {
            int temp = nums[i];
            nums[i] = nums[0];
            nums[0] = temp;

            adjustHeap(nums, 0, i - 1);
        }
    }

    private static void buildHeap(int[] nums) {
        for (int i=nums.length / 2; i>=0; i--) {
            adjustHeap(nums, i, nums.length - 1);
        }
    }

    private static void adjustHeap(int[] nums, int start, int end) {
        int lChild = 2 * start + 1, rChild = 2 * start + 2, largeIndex = start;

        if (lChild <= end && nums[lChild] > nums[largeIndex]) {
            largeIndex = lChild;
        }

        if (rChild <= end && nums[rChild] > nums[largeIndex]) {
            largeIndex = rChild;
        }

        if (largeIndex != start) {
            int temp = nums[start];
            nums[start] = nums[largeIndex];
            nums[largeIndex] = temp;
            adjustHeap(nums, largeIndex, end);
        }
    }

    /**
     * O(n), used for known and not large input range
     * @param arr
     */
    private static void countSort(char[] arr) {
        int[] cnt = new int[256];

        for(char c : arr) {
            cnt[c]++;
        }

        int index = 0;
        for (int i = 0; i < 256; i++) {
            while (cnt[i]-- > 0) {
                arr[index++] = (char)i;
            }
        }
    }

}
