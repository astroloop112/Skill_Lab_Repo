/*
Question 5: Optimal Resource Allocation (Bitmask DP)

Given N tasks and N workers (N ≤ 20),
assign each worker exactly one task such that total cost is minimized.

Improve brute force O(N!) to O(N * 2^N) using state compression.
*/

import java.util.*;

class ResourceAllocation {

    public int findMinimumCost(int[][] costMatrix) {

        int totalTasks = costMatrix.length;
        int totalStates = 1 << totalTasks;

        int[] minimumCostForMask = new int[totalStates];
        Arrays.fill(minimumCostForMask, Integer.MAX_VALUE);

        minimumCostForMask[0] = 0;

        for (int taskMask = 0; taskMask < totalStates; taskMask++) {

            int workerIndex = Integer.bitCount(taskMask);

            for (int taskIndex = 0;
                 taskIndex < totalTasks;
                 taskIndex++) {

                if ((taskMask & (1 << taskIndex)) == 0) {

                    int newMask =
                            taskMask | (1 << taskIndex);

                    minimumCostForMask[newMask] =
                            Math.min(
                                    minimumCostForMask[newMask],
                                    minimumCostForMask[taskMask]
                                            + costMatrix[workerIndex][taskIndex]
                            );
                }
            }
        }

        return minimumCostForMask[totalStates - 1];
    }
}
