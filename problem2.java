class Solution {


    // Example
//    Graph (adjacency matrix):
//            0  1  2  3  4
//            0 [  1  1  0  0  0 ]
//            1 [  1  1  1  0  0 ]
//            2 [  0  1  1  0  0 ]
//            3 [  0  0  0  1  1 ]
//            4 [  0  0  0  1  1 ]
//
//    Visual representation:
//            0 ←→ 1 ←→ 2        3 ←→ 4
//            (Component 0)      (Component 1)
    public int minMalwareSpread(int[][] graph,  int[] initial) {
        int n= graph.length; //5
        int[] colors = new int[n];
        Arrays.fill(colors, -1); // [-1,-1,-1,-1,-1]
        int cl=0; // current color

        // initially all nodes are unvisited
        for (int i = 0; i < n; i++) {
            if (colors[i] == -1) {
                dfs(graph, colors, i, cl); // we use dfs to process all nodes
            }
            cl++;
        }
//        colors = [0, 0, 0, 3, 3]
//        cl = 5
//
//        Node:   0   1   2   3   4
//        Color:  0   0   0   3   3
        int[] groups= new int[cl]; // groups = new int[5]
        for (int node: colors)
        {
            groups[node]++; // [3,0,0,2,0]
        }
        int[] infected= new int[cl]; // [1,0,0,1,0]
        for (int node: initial)
        {
            infected[colors[node]]++;
        }
        // finding best node to remove
        int result= Integer.MAX_VALUE;
        for (int node: initial) // loop thru [0,3]
        {
            int gr= colors[node];

            if (infected[gr]==1)
            {
                if (result == Integer.MAX_VALUE)
                {
                    result = node;
                } else if (groups[gr] > groups[colors[result]]) {
                    result = node;
                }
                else if (groups[gr] == groups[colors[result]]) {
                    result = Math.min(result, node);
                }
            }
        }
//        Verification:
//        Remove node 0:
//
//        Start: initial = [3]
//        Component 0 (nodes 0, 1, 2): Not initially infected, stays clean ✓
//        Component 1 (nodes 3, 4): Infected
//        Total infected: 2 nodes
//
//        Remove node 3:
//
//        Start: initial = [0]
//        Component 0 (nodes 0, 1, 2): Infected
//        Component 1 (nodes 3, 4): Not initially infected, stays clean
//        Total infected: 3 nodes
//
//        Best choice: Remove node 0 (2 < 3) ✓
        if (result == Integer.MAX_VALUE)
        {
            for (int node: initial)
            {
                result = Math.min(result, node);
            }
        }
        return result;

    }
    private void dfs(int[][] graph, int[] colors, int i, int cl) {

        // base case
        if (colors[i] != -1) {
            return ;
        }

        //logic
        colors[i] = cl;
        for (int j=0; j<graph.length; j++) {
            if (i==j){continue;}
            if (graph[i][j]==1){
                dfs(graph, colors, j, cl);
            }
        }


    }
}