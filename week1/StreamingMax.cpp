/*
Question 2: Streaming Max Analytics (Monotonic Queue)

Given a stream of latency values and window size k,
calculate maximum latency in each sliding window.

Each incoming data point must be processed in amortized O(1).
*/

#include <bits/stdc++.h>
using namespace std;

class StreamingMax {
public:
    vector<int> findMaxInSlidingWindow(vector<int>& latencyValues, int k) {
        deque<int> dq;
        vector<int> result;

        for (int i = 0; i < latencyValues.size(); i++) {

            if (!dq.empty() && dq.front() <= i - k)
                dq.pop_front();

            while (!dq.empty() &&
                   latencyValues[dq.back()] <= latencyValues[i])
                dq.pop_back();

            dq.push_back(i);

            if (i >= k - 1)
                result.push_back(latencyValues[dq.front()]);
        }

        return result;
    }
};
