/*
Question 3: Dynamic Network Vulnerability (Tarjan / Bridges)

Given an undirected graph with n nodes,
find all critical connections (bridges).

A connection is critical if removing it disconnects the graph.

Time Complexity Requirement: O(V + E)
*/

#include <bits/stdc++.h>
using namespace std;

class CriticalConnectionsFinder {
private:
    vector<vector<int>> adj;
    vector<int> disc, low;
    vector<vector<int>> bridges;
    int timeCounter;

    void dfs(int node, int parent) {
        disc[node] = low[node] = ++timeCounter;

        for (int neighbor : adj[node]) {
            if (neighbor == parent) continue;

            if (!disc[neighbor]) {
                dfs(neighbor, node);

                low[node] = min(low[node], low[neighbor]);

                if (low[neighbor] > disc[node])
                    bridges.push_back({node, neighbor});
            }
            else {
                low[node] = min(low[node], disc[neighbor]);
            }
        }
    }

public:
    vector<vector<int>> findCriticalConnections(
        int n, vector<vector<int>>& connections) {

        adj.assign(n, {});
        disc.assign(n, 0);
        low.assign(n, 0);
        bridges.clear();
        timeCounter = 0;

        for (auto& edge : connections) {
            adj[edge[0]].push_back(edge[1]);
            adj[edge[1]].push_back(edge[0]);
        }

        dfs(0, -1);

        return bridges;
    }
};
