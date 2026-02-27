/*
Question 4: Range Performance Monitor (Segment Tree)

Support:
1) update(index, value)
2) queryMax(left, right)

Both operations must run in O(log N).
*/

#include <bits/stdc++.h>
using namespace std;

class SegmentTree {
private:
    vector<int> tree;
    int n;

    void build(vector<int>& arr, int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start];
            return;
        }

        int mid = (start + end) / 2;

        build(arr, 2*node+1, start, mid);
        build(arr, 2*node+2, mid+1, end);

        tree[node] = max(tree[2*node+1], tree[2*node+2]);
    }

    void update(int node, int start, int end,
                int idx, int val) {

        if (start == end) {
            tree[node] = val;
            return;
        }

        int mid = (start + end) / 2;

        if (idx <= mid)
            update(2*node+1, start, mid, idx, val);
        else
            update(2*node+2, mid+1, end, idx, val);

        tree[node] = max(tree[2*node+1], tree[2*node+2]);
    }

    int query(int node, int start, int end,
              int l, int r) {

        if (r < start || l > end)
            return INT_MIN;

        if (l <= start && end <= r)
            return tree[node];

        int mid = (start + end) / 2;

        return max(
            query(2*node+1, start, mid, l, r),
            query(2*node+2, mid+1, end, l, r)
        );
    }

public:
    SegmentTree(vector<int>& arr) {
        n = arr.size();
        tree.resize(4*n);
        build(arr, 0, 0, n-1);
    }

    void updateValue(int idx, int val) {
        update(0, 0, n-1, idx, val);
    }

    int queryMax(int l, int r) {
        return query(0, 0, n-1, l, r);
    }
};
