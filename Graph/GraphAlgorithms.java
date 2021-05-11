import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.HashMap;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Hane Yie
 * @version 1.0
 * @userid hyie3
 * @GTID 903441211
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (graph == null || start == null
                || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Input null or "
                    + "start not in graph");
        } else {
            List<Vertex<T>> list = new ArrayList<>();
            Queue<Vertex<T>> q = new LinkedList<>();
            q.add(start);
            list.add(start);
            while (!q.isEmpty()) {
                Vertex<T> vert = q.remove();
                for (VertexDistance<T> vertexd : graph.getAdjList().get(vert)) {
                    if (!list.contains(vertexd.getVertex())) {
                        q.add(vertexd.getVertex());
                        list.add(vertexd.getVertex());
                    }
                }
            }
            return list;
        }
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     * <p>
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     * <p>
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     * <p>
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (graph == null || start == null
                || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Input null or "
                    + "start not in graph");
        } else {
            List<Vertex<T>> list = new ArrayList<>();
            Set<Vertex<T>> set = new HashSet<>();
            dfsHelper(start, graph, list, set);
            return list;
        }
    }

    /**
     * Helper method for dfs method on above
     *
     * @param start vertex t start
     * @param graph graph t graph
     * @param list  list vertex t list
     * @param set   set vertex t set
     * @param <T>   data
     */

    private static <T> void dfsHelper(Vertex<T> start, Graph<T> graph,
                                      List<Vertex<T>> list,
                                      Set<Vertex<T>> set) {
        set.add(start);
        list.add(start);
        for (VertexDistance<T> vertexd : graph.getAdjList().get(start)) {
            if (!set.contains(vertexd.getVertex())) {
                dfsHelper(vertexd.getVertex(), graph, list, set);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     * <p>
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     * <p>
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     * <p>
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     * <p>
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (graph == null || start == null
                || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Input is null or "
                    + "start not in graph");
        } else {
            PriorityQueue<VertexDistance<T>> q = new PriorityQueue<>();
            Map<Vertex<T>, Integer> mapint = new HashMap<>();
            for (Vertex<T> vert : graph.getAdjList().keySet()) {
                if (vert.equals(start)) {
                    mapint.put(vert, 0);
                } else {
                    mapint.put(vert, Integer.MAX_VALUE);
                }
            }
            q.add(new VertexDistance<>(start, 0));
            while (!q.isEmpty()) {
                VertexDistance<T> vertexd = q.remove();
                for (VertexDistance<T> vd
                        : graph.getAdjList().get(vertexd.getVertex())) {
                    if (mapint.get(vd.getVertex())
                            >= vertexd.getDistance() + vd.getDistance()) {
                        mapint.put(vd.getVertex(),
                                vertexd.getDistance() + vd.getDistance());
                        q.add(new VertexDistance<>(vd.getVertex(),
                                vertexd.getDistance() + vd.getDistance()));
                    }
                }
            }
            return mapint;
        }
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     * <p>
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     * <p>
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     * <p>
     * You may assume that there will only be one valid MST that can be formed.
     * <p>
     * You should NOT allow self-loops or parallel edges in the MST.
     * <p>
     * You may import/use PriorityQueue, java.util.Set, and any class that
     * implements the aforementioned interface.
     * <p>
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     * <p>
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (graph == null || start == null
                || !graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Input it null or "
                    + "start not in graph");
        } else {
            PriorityQueue<Edge<T>> q = new PriorityQueue<>();
            Set<Edge<T>> seted = new HashSet<>();
            Set<Vertex<T>> setvert = new HashSet<>();
            for (VertexDistance<T> vertexd : graph.getAdjList().get(start)) {
                q.add(new Edge<>(start, vertexd.getVertex(),
                        vertexd.getDistance()));
            }
            setvert.add(start);
            while (!q.isEmpty()) {
                Edge<T> edge = q.remove();
                if (!setvert.contains(edge.getV())) {
                    setvert.add(edge.getV());
                    seted.add(edge);
                    seted.add(new Edge<>(edge.getV(),
                            edge.getU(), edge.getWeight()));
                    for (VertexDistance<T> vd
                            : graph.getAdjList().get(edge.getV())) {
                        if (!setvert.contains(vd.getVertex())) {
                            q.add(new Edge<>(edge.getV(),
                                    vd.getVertex(), vd.getDistance()));
                        }
                    }
                }
            }
            if (setvert.size() >= graph.getAdjList().size()) {
                return seted;
            } else {
                return null;
            }
        }
    }
}