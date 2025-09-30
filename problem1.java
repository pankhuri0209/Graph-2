class Solution {
    int[] discovery;
    int[] lowest;
    int time;
    HashMap<Integer, List<Integer>> map;
    List<List<Integer>> res;
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {

        time=0;
        discovery=new int[n];
        lowest=new int[n];
        map=new HashMap<>();
        res=new ArrayList<>();

        Arrays.fill(discovery, -1); // mark all nodes as unvisited

        // Build undirected graph (add edge in both directions)
        // Example processing [1,2]:
        //   map.get(1).add(2)  → map = {1:[2], ...}
        //   map.get(2).add(1)  → map = {2:[1], ...}

        for (List<Integer> connection : connections) {
            int u= connection.get(0);
            int v= connection.get(1);
            map.get(u).add(v); // Add v to u's neighbors
            map.get(v).add(u); // Add u to v's neighbors (undirected)
        }
        // After building adjacency list for example graph:
        // map = {
        //   0: [1],
        //   1: [0, 2, 3],
        //   2: [1, 4],
        //   3: [1, 4],
        //   4: [2, 3]
        // }

        helper(0,-1);  // child, parent
        return res;
    }
    /**
     * DFS Helper using Tarjan's Algorithm
     *
     * @param u - current node being visited
     * @param v - parent node (node we came from)
     *
     * KEY CONCEPTS:
     * - discovery[u] = when we first visit node u
     * - lowest[u] = earliest node reachable from u's subtree
     * - If lowest[neighbor] > discovery[u], then edge (u, neighbor) is a bridge
     *
     * WHY? Because neighbor cannot reach back to u or any ancestor of u
     * without using the edge (u, neighbor)
     */
    private void helper(int u,int v){
        if (discovery[u]!=-1){ // seen before node
            return;
        }
        discovery[u]=time;
        lowest[u]=time;

        // increment global timestamp
        time++;

        //explore neighbors
        for (int ne: map.get(u))
        {
            //skip parent to avoid going back immediatey
            if (ne==v){continue;}

            // recusively visit neighbors
            helper(ne,u);  // u becomes parent for the neighbor

            // critical bridge check..
            if (lowest[ne]> discovery[u]){
                res.add(Arrays.asList(ne, u));
            }
            lowest[u]= Math.min(lowest[ne], lowest[u]);

        }

    }
}