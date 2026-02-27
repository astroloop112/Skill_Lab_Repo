/*
Question 5: Optimal Resource Allocation (Bitmask DP)

Given N tasks and N workers (N ≤ 20),
assign each worker exactly one task such that total cost is minimized.

Improve brute force O(N!) to O(N * 2^N) using state compression.
*/

#include <bits/stdc++.h>
using namespace std;

class ResourceAllocation {
public:
    int findMinimumCost(vector<vector<int>>& costMatrix) {
        int n = costMatrix.size();
        int totalStates = 1 << n;

        vector<int> dp(totalStates, INT_MAX);
        dp[0] = 0;

        for (int mask = 0; mask < totalStates; mask++) {

            int worker = __builtin_popcount(mask);

            for (int task = 0; task < n; task++) {

                if (!(mask & (1 << task))) {

                    int newMask = mask | (1 << task);

                    dp[newMask] = min(
                        dp[newMask],
                        dp[mask] + costMatrix[worker][task]
                    );
                }
            }
        }

        return dp[totalStates - 1];
    }
};
