import java.util.HashMap;
import java.util.Map;

public class KnapsackProblem {

    public static void main(String[] args) {
        int[] weight = {4,5,1};
        int[] profit = {1,2,3};
        int w = 4;
        int n = weight.length;

        System.out.println(knapsack(weight, profit, w, n));
        System.out.println(knapsackMemo(weight, profit, w, n));
        System.out.println(knapsackTabu(weight, profit, w, n));
    }

    public static int knapsack(int[] weight, int[] profit, int w, int n) {
        if (w == 0 || n == 0) {
            return 0;
        }

        if (weight[n - 1] > w) {
            return knapsack(weight, profit, w, n - 1);
        }

        return Math.max(profit[n - 1] + knapsack(weight, profit, w - weight[n - 1], n - 1),
                knapsack(weight, profit, w, n - 1));
    }

    private static final Map<String, Integer> memo = new HashMap<>();
    public static int knapsackMemo(int[] weight, int[] profit, int w, int n) {
        if (w == 0 || n == 0) {
            return 0;
        }

        String key = w + "-" + n;

        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        if (weight[n - 1] > w) {
            int result = knapsack(weight, profit, w, n - 1);
            memo.put(key, result);
            return result;
        }

        int result = Math.max(profit[n - 1] + knapsack(weight, profit, w - weight[n - 1], n - 1),
                knapsack(weight, profit, w, n - 1));
        memo.put(key, result);

        return result;
    }

    public static int knapsackTabu(int[] weight, int[] profit, int w, int n) {
        int[][] dp = new int[n + 1][w + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= w; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
                else if (weight[i - 1] > j) {
                    dp[i][j] = dp[i - 1][j];
                }
                else {
                    dp[i][j] = Math.max(profit[i - 1] + dp[i - 1][j - weight[i - 1]], dp[i - 1][j]);
                }
            }
        }
        return dp[n][w];
    }
}
