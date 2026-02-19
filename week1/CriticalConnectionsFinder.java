/*
Question 3: Dynamic Network Vulnerability (Tarjan / Bridges)

Given an undirected graph with n nodes,
find all critical connections (bridges).

A connection is critical if removing it disconnects the graph.

Time Complexity Requirement: O(V + E)
*/

import java.util.*;

class CriticalConnectionsFinder {

    private List<List<Integer>> adjacencyList;
    private int[] discoveryTime;
    private int[] lowestReachableTime;
    private int currentTime;
    private List<List<Integer>> bridgeList;

    public List<List<Integer>> findCriticalConnections(
            int totalNodes,
            List<List<Integer>> connections) {

        adjacencyList = new ArrayList<>();
        for (int node = 0; node < totalNodes; node++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (List<Integer> connection : connections) {
            int sourceNode = connection.get(0);
            int destinationNode = connection.get(1);

            adjacencyList.get(sourceNode).add(destinationNode);
            adjacencyList.get(destinationNode).add(sourceNode);
        }

        discoveryTime = new int[totalNodes];
        lowestReachableTime = new int[totalNodes];
        bridgeList = new ArrayList<>();
        currentTime = 0;

        dfs(0, -1);

        return bridgeList;
    }

    private void dfs(int currentNode, int parentNode) {

        discoveryTime[currentNode] =
                lowestReachableTime[currentNode] = ++currentTime;

        for (int neighbor : adjacencyList.get(currentNode)) {

            if (neighbor == parentNode)
                continue;

            if (discoveryTime[neighbor] == 0) {

                dfs(neighbor, currentNode);

                lowestReachableTime[currentNode] =
                        Math.min(lowestReachableTime[currentNode],
                                lowestReachableTime[neighbor]);

                if (lowestReachableTime[neighbor]
                        > discoveryTime[currentNode]) {

                    bridgeList.add(
                            Arrays.asList(currentNode, neighbor));
                }

            } else {

                lowestReachableTime[currentNode] =
                        Math.min(lowestReachableTime[currentNode],
                                discoveryTime[neighbor]);
            }
        }
    }
}
