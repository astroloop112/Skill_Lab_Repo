/*
Question 4: Range Performance Monitor (Segment Tree)

Support:
1) update(index, value)
2) queryMax(left, right)

Both operations must run in O(log N).
*/

class SegmentTree {

    private int[] segmentTree;
    private int totalElements;

    public SegmentTree(int[] inputArray) {
        totalElements = inputArray.length;
        segmentTree = new int[4 * totalElements];
        buildTree(inputArray, 0, 0, totalElements - 1);
    }

    private void buildTree(
            int[] inputArray,
            int currentNode,
            int startIndex,
            int endIndex) {

        if (startIndex == endIndex) {
            segmentTree[currentNode] = inputArray[startIndex];
            return;
        }

        int middleIndex = (startIndex + endIndex) / 2;

        buildTree(inputArray, 2 * currentNode + 1,
                startIndex, middleIndex);

        buildTree(inputArray, 2 * currentNode + 2,
                middleIndex + 1, endIndex);

        segmentTree[currentNode] =
                Math.max(segmentTree[2 * currentNode + 1],
                        segmentTree[2 * currentNode + 2]);
    }

    public void updateValue(int indexToUpdate, int newValue) {
        updateTree(0, 0, totalElements - 1,
                indexToUpdate, newValue);
    }

    private void updateTree(
            int currentNode,
            int startIndex,
            int endIndex,
            int indexToUpdate,
            int newValue) {

        if (startIndex == endIndex) {
            segmentTree[currentNode] = newValue;
            return;
        }

        int middleIndex = (startIndex + endIndex) / 2;

        if (indexToUpdate <= middleIndex) {
            updateTree(2 * currentNode + 1,
                    startIndex, middleIndex,
                    indexToUpdate, newValue);
        } else {
            updateTree(2 * currentNode + 2,
                    middleIndex + 1, endIndex,
                    indexToUpdate, newValue);
        }

        segmentTree[currentNode] =
                Math.max(segmentTree[2 * currentNode + 1],
                        segmentTree[2 * currentNode + 2]);
    }

    public int queryMax(int leftRange, int rightRange) {
        return queryTree(0, 0, totalElements - 1,
                leftRange, rightRange);
    }

    private int queryTree(
            int currentNode,
            int startIndex,
            int endIndex,
            int leftRange,
            int rightRange) {

        if (rightRange < startIndex || leftRange > endIndex)
            return Integer.MIN_VALUE;

        if (leftRange <= startIndex && endIndex <= rightRange)
            return segmentTree[currentNode];

        int middleIndex = (startIndex + endIndex) / 2;

        return Math.max(
                queryTree(2 * currentNode + 1,
                        startIndex, middleIndex,
                        leftRange, rightRange),
                queryTree(2 * currentNode + 2,
                        middleIndex + 1, endIndex,
                        leftRange, rightRange)
        );
    }
}
