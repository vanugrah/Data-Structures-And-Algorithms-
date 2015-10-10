import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class GraphAlgorithms {
    /**
     * Find the shortest distance between the start vertex and all other
     * vertices given a weighted graph.
     * You should use the adjacency list representation of the graph.
     *
     * Assume the adjacency list contains adjacent nodes of each node in the
     * order they should be visited. There are no negative edge weights in the
     * graph.
     *
     * If there is no path from from the start vertex to a given vertex,
     * have the distance be INF as seen in the graphs class.
     *
     * @throws IllegalArgumentException if graph or start vertex is null
     * @param graph the graph to search
     * @param start the starting vertex
     * @return map of the shortest distance between start and all other vertices
     */
    public static Map<Vertex, Integer> dijkstraShortestPath(Graph graph,
                                                            Vertex start) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null!");
        } else if (start == null) {
            throw new IllegalArgumentException("Start cannot be null");
        } else {
//            PriorityQueue<VertexDistancePair> queue =
// new PriorityQueue<>( new vertexComparator());
//            Vertex current;
//            Map<Vertex, Integer> adjacent;
//            ArrayList<Vertex> visited = new ArrayList<>();
//            Set<Vertex> vertices = graph.getVertices();
//            Set<VertexDistancePair> distances = new HashSet<>();
//
//
//
//            VertexDistancePair vertex;
//
//            // set all distances to INF
//            for (Vertex v : vertices) {
//                vertex = new VertexDistancePair(v, Graph.INF);
//                distances.add(vertex);
//                queue.add(vertex);
//            }

            // add starting vertex to the queue
           // queue.add(start);


//            while (!queue.isEmpty()) {
//                //current = queue.poll();
//                //visited.add(current);
//
//                //adjacent = graph.getAdjacencies(current);
//
//
//            }
            return new HashMap<Vertex, Integer>();
        }
    }

//    static class vertexComparator implements Comparator<VertexDistancePair> {
//        @Override
//        public int compare(VertexDistancePair o1, VertexDistancePair o2) {
//            return (int) o1.getDistance() - o2.getDistance();
//        }
//    }

    /**
     * Run Floyd Warshall on the given graph to find the length of all of the
     * shortest paths between vertices.
     *
     * You will also detect if there are negative cycles in the
     * given graph.
     *
     * You should use the adjacency matrix representation of the graph.
     *
     * If there is no path from from the start vertex to a given vertex,
     * have the distance be INF as seen in the graphs class.
     *
     * @throws IllegalArgumentException if graph is null
     * @param graph the graph
     * @return the distances between each vertex. For example, given {@code arr}
     * represents the 2D array, {@code arr[i][j]} represents the distance from
     * vertex i to vertex j. Return {@code null} if there is a negative cycle.
     */
    public static int[][] floydWarshall(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null!");
        } else {
            int[][] path = graph.getAdjacencyMatrix();

            for (int k = 0; k < path.length; k++) {

                for (int i = 0; i < path.length; i++) {

                    for (int j = 0; j < path.length; j++) {
                        path[i][j] = Math.min(path[i][j], path[i][k]
                                + path[k][j]);
                    }
                }
            }

            return path;
        }
    }

    /**
     * A topological sort is a linear ordering of vertices with the restriction
     * that for every edge uv, vertex u comes before v in the ordering.
     *
     * You should use the adjacency list representation of the graph.
     * When considering which vertex to visit next while exploring the graph,
     * choose the next vertex in the adjacency list.
     *
     * You should start your topological sort with the smallest vertex
     * and if you need to select another starting vertex to continue
     * with your sort (like with a disconnected graph),
     * you should choose the next smallest applicable vertex.
     *
     * @throws IllegalArgumentException if the graph is null
     * @param graph a directed acyclic graph
     * @return a topological sort of the graph
     */
    public static List<Vertex> topologicalSort(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null!");
        } else {
//            Map<Vertex, Map<Vertex, Integer>>
// adjacency_list = graph.getAdjacencyList();
//            Set<Vertex> vertices = graph.getVertices();
//            Vertex start;
//            ArrayList<Vertex> visited = new ArrayList<>();
//
//
//            for (Vertex v : vertices) {
//                if (v.getId() == 0) {
//                    start = v;
//                }
//            }

            return new ArrayList<Vertex>();
        }

        // do it recursively
        // pass in visited list
        // hashmap of vertices

    }


    /**
     * A class that pairs a vertex and a distance. Hint: might be helpful
     * for Dijkstra's.
     */
    private static class VertexDistancePair implements
        Comparable<VertexDistancePair> {
        private Vertex vertex;
        private int distance;

        /**
         * Creates a vertex distance pair
         * @param vertex the vertex to store in this pair
         * @param distance the distance to store in this pair
         */
        public VertexDistancePair(Vertex vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        /**
         * Gets the vertex stored in this pair
         * @return the vertex stored in this pair
         */
        public Vertex getVertex() {
            return vertex;
        }

        /**
         * Sets the vertex to be stored in this pair
         * @param vertex the vertex to be stored in this pair
         */
        public void setVertex(Vertex vertex) {
            this.vertex = vertex;
        }

        /**
         * Gets the distance stored in this pair
         * @return the distance stored in this pair
         */
        public int getDistance() {
            return distance;
        }

        /**
         * Sets the distance to be stored in this pair
         * @param distance the distance to be stored in this pair
         */
        public void setDistance(int distance) {
            this.distance = distance;
        }

        @Override
        public int compareTo(VertexDistancePair v) {
            return (distance < v.getDistance() ? -1
                                          : distance > v.getDistance() ? 1 : 0);
        }
    }
}
