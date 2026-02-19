/*
Question 2: Streaming Max Analytics (Monotonic Queue)

Given a stream of latency values and window size k,
calculate maximum latency in each sliding window.

Each incoming data point must be processed in amortized O(1).
*/

import java.util.*;

class StreamingMax {

    public int[] findMaxInSlidingWindow(int[] latencyValues, int windowSize) {

        Deque<Integer> indexDeque = new ArrayDeque<>();
        int totalElements = latencyValues.length;

        int[] maximumValues = new int[totalElements - windowSize + 1];
        int resultIndex = 0;

        for (int currentIndex = 0; currentIndex < totalElements; currentIndex++) {

            // Remove indices outside window
            if (!indexDeque.isEmpty()
                    && indexDeque.peekFirst() <= currentIndex - windowSize) {
                indexDeque.pollFirst();
            }

            // Maintain decreasing order
            while (!indexDeque.isEmpty()
                    && latencyValues[indexDeque.peekLast()]
                    <= latencyValues[currentIndex]) {
                indexDeque.pollLast();
            }

            indexDeque.offerLast(currentIndex);

            if (currentIndex >= windowSize - 1) {
                maximumValues[resultIndex++] =
                        latencyValues[indexDeque.peekFirst()];
            }
        }

        return maximumValues;
    }
}
