import java.util.HashMap;
import java.util.Map;

public class PaintersPartitionProblem {

    public static void main(String[] args) {
        int[] arr = {10, 10, 10, 10};
        int painters = 2;
        int sections = arr.length;

        System.out.println(paintersPartition(arr, painters, sections));
        System.out.println(paintersPartitionMemo(arr, painters, sections));
        System.out.println(paintersPartitionTabu(arr, painters, sections));
    }
    public static int calculateMaxSum(int[] arr, int start, int end) {
        int maxSum = 0;
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum += arr[i];
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }

    public static int paintersPartition(int[] arr, int painters, int sections) {
        if (painters == 1) {
            return calculateMaxSum(arr, 0, sections - 1);
        }
        if (sections == 1) {
            return arr[0];
        }

        int minMaxSum = Integer.MAX_VALUE;

        for (int i = 1; i <= sections; i++) {
            int currentMaxSum = calculateMaxSum(arr, i, sections - 1);
            int nextPartition = paintersPartition(arr, painters - 1, i);
            int totalMaxSum = Math.max(currentMaxSum, nextPartition);
            minMaxSum = Math.min(minMaxSum, totalMaxSum);
        }

        return minMaxSum;
    }

    private static final Map<String, Integer> memo = new HashMap<>();

    public static int paintersPartitionMemo(int[] arr, int painters, int sections) {
        String key = painters + "-" + sections;

        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        if (painters == 1) {
            int maxSum = calculateMaxSum(arr, 0, sections - 1);
            memo.put(key, maxSum);
            return maxSum;
        }

        if (sections == 1) {
            int sectionValue = arr[0];
            memo.put(key, sectionValue);
            return sectionValue;
        }

        int minMaxSum = Integer.MAX_VALUE;

        for (int i = 1; i <= sections; i++) {
            int currentMaxSum = calculateMaxSum(arr, i, sections - 1);
            int nextPartition = paintersPartition(arr, painters - 1, i);
            int totalMaxSum = Math.max(currentMaxSum, nextPartition);
            minMaxSum = Math.min(minMaxSum, totalMaxSum);
        }

        memo.put(key, minMaxSum);

        return minMaxSum;
    }

    public static int paintersPartitionTabu(int[] arr, int painters, int sections) {
        int[][] dp = new int[painters + 1][sections + 1];

        for (int i = 1; i <= painters; i++) {
            dp[i][1] = arr[0];
        }
        for (int j = 1; j <= sections; j++) {
            dp[1][j] = calculateMaxSum(arr, 0, j - 1);
        }

        for (int i = 2; i <= painters; i++) {
            for (int j = 2; j <= sections; j++) {
                int minMaxSum = Integer.MAX_VALUE;
                for (int k = 1; k <= j; k++) {
                    int currentMaxSum = calculateMaxSum(arr, k, j - 1);
                    int totalMaxSum = Math.max(currentMaxSum, dp[i - 1][k]);
                    minMaxSum = Math.min(minMaxSum, totalMaxSum);
                }
                dp[i][j] = minMaxSum;
            }
        }
        return dp[painters][sections];
    }


}

