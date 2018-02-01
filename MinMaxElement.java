/**
 * Created by shuoshu on 2018/2/1.
 */
public class MinMaxElement {
    public static void main(String[] args) {
        MinMaxElement solution = new MinMaxElement();
        int[] nums = {1, 24, 23535, 12, 3, 1, 1,5,56};
        int[] ans1 = solution.getMaxAndMin1(nums);
        int[] ans2 = solution.getMaxAndMin2(nums);

        for (int n : ans1) {
            System.out.print(n + " ");
        }
        for (int n : ans2) {
            System.out.print(n + " ");
        }
    }

    public int[] getMaxAndMin1(int[] nums) {
        if (nums.length < 2) {
            return new int[0];
        }

        int[] ans = new int[2];
        int min = nums[nums.length - 1], max = nums[nums.length - 1];

        for (int i = 0; i < nums.length - 1; i += 2) {
            if (nums[i] > nums[i + 1]) {
                max = Math.max(max, nums[i]);
                min = Math.min(min, nums[i + 1]);
            } else {
                max = Math.max(max, nums[i + 1]);
                min = Math.min(min, nums[i]);
            }
        }

        ans[0] = min;
        ans[1] = max;

        return ans;
    }


    public int[] getMaxAndMin2(int[] nums) {
        if (nums.length < 2) {
            return new int[0];
        }
        return helper(nums, 0, nums.length - 1);
    }

    private int[] helper(int[] nums, int start, int end) {
        int[] ans = new int[2];
        ans[0] = Integer.MAX_VALUE;
        ans[1] = Integer.MIN_VALUE;

        if (start > end) {
            return ans;
        }

        if (start == end) {
            ans[0] = nums[start];
            ans[1] = nums[start];
            return ans;
        }

        if (start + 1 == end) {
            ans[0] = Math.min(nums[start], nums[end]);
            ans[1] = Math.max(nums[start], nums[end]);
            return ans;
        }

        int mid = start + (end - start) / 2;
        int[] left = helper(nums, start, mid);
        int[] right = helper(nums, mid + 1, end);

        if (left.length == 0) {
            return right;
        }

        if (right.length == 0) {
            return left;
        }

        ans[0] = Math.min(left[0], right[0]);
        ans[1] = Math.max(left[1], right[1]);

        return ans;
    }
}
