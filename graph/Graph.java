package cpen221.mp2.graph;

import java.util.*;

/**
 * Represents a graph with vertices of type V.
 *
 * @param <V> represents a vertex type
 */
public class Graph<V extends Vertex, E extends Edge<V>> implements ImGraph<V, E>, IGraph<V, E> {
    // The Edge<V> on the right-hand side of "E" has to be a defined type such as int or String,
    // as it is well-defined in this case, because it is of type Edge<V> and V
    // is properly defined to be of type Vertex.

    private Map<V, Set<E>> mapping = new HashMap<V, Set<E>>();
    // Rep Invariant:?????????????
    // Abstraction Function:
    // The Graph's vertices are represented by the keySet of the "mapping",
    // and the edges that each vertex is connected to inside the Graph
    // is represented a map of a Vertex into
    // a set of edges it is connected to inside a Graph.
    // TODO: Implement this type

    // Constructor:
    public Graph() {
        this.mapping = new HashMap<>();
    }

    /**
     * Add a vertex to the graph
     *
     * @param v vertex to add
     * @return true if the vertex was added successfully and false either
     * if the Vertex already exists in the Graph or if the Name and ID of
     * v is equal to the Name and ID of any Vertex in the Graph.
     */
    // No two vertices in the graph should have the same ID
    @Override
    public boolean addVertex(V v) {
        if (mapping.containsKey(v)) {
            return false;
        } else {
            for (V vertexElement : mapping.keySet()) {
                if (vertexElement.equals(v)) {
                    return false;
                }
            }
        }
        Set<E> emptySet = new HashSet<>();
        mapping.put(v, emptySet);
        return true;
    }

    /**
     * Check if a vertex is part of the graph
     *
     * @param v vertex to check in the graph
     * @return true of v is part of the graph and false otherwise
     */
    @Override
    public boolean vertex(V v) {
        return mapping.containsKey(v);
    }

    /**
     * Add an edge of the graph
     *
     * @param e the edge to add to the graph
     * @return true if the edge was successfully added, and false if either
     * of the Vertices do not exist in the Graph, or if e already exists in the
     * Graph.
     */
    // private Map<V, Set<E>> mapping = new HashMap<>();
    @Override
    /*we are just connecting the two already existing vertices */
    public boolean addEdge(E e) {
        if (!mapping.containsKey(e.v1()) || !mapping.containsKey(e.v2())) {
            return false;
        } else if (this.edge(e.v1(), e.v2())) {
            return false;
        } else {
            Set<E> intermediate1 = new HashSet<>();
            intermediate1 = mapping.get(e.v1());
            intermediate1.add(e);
            Set<E> intermediate2 = new HashSet<>();
            intermediate2 = mapping.get(e.v2());
            intermediate2.add(e);
            return true;
        }
    }

    /**
     * Check if an edge is part of the graph
     *
     * @param e the edge to check in the graph
     * @return true if e is an edge in the graoh and false otherwise
     */
    @Override
    // Note: Edge can never not have empty vertices associated with it.
    public boolean edge(E e) {
        if (vertex(e.v1()) && vertex(e.v2())) {   // check if the two vertices of the edge are part of the graph.
            for (Vertex element : this.mapping.keySet()) {
                if (mapping.get(element).contains(e)) { // checking a set if it contains the Edge "e".
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if v1-v2 is an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return true of the v1-v2 edge is part of the graph and false if either
     * v1 or v2 do not exist in the Graph, or if v1-v2 edge (same as v2-v1 edge)
     * is not part of the Graph.
     */
    // The order of v1, v2 does not matter.
    @Override
    public boolean edge(V v1, V v2) {
        // The graph has to contain both v1 and v2.
        if (vertex(v1) && vertex(v2)) {
            for (Edge setElement : mapping.get(v1)) {
                if (setElement.v2().equals(v2) || setElement.v1().equals(v2)) {
                    return true;
//                    for (Edge setElem : mapping.get(v2)) {
//                        if (setElem.v1().equals(v1) || setElem.v1().equals(v2)) {
//                            return true;
//                        }
//                    }
                }
            }
        }
        return false;
    }

    /**
     * Find the edge that connects two vertices if such an edge exists.
     * This method should not permit graph mutations.
     *
     * @param v1 one end of the edge
     * @param v2 the other end of the edge
     * @return the edge connecting v1 and v2. Return null if no such Edge exists
     * int the Graph.
     */
    //private Map<V, Set<E>> mapping = new HashMap<V, Set<E>>();
    @Override
    public E getEdge(V v1, V v2) {
        if (this.edge(v1, v2)) {
            for (E edgeElement : mapping.get(v1)) {
                if (edgeElement.v2().equals(v2) || edgeElement.v1().equals(v2)) {
                    return (E) edgeElement;
//                    for (E edgeElem : mapping.get(v2)) {
//                        if (edgeElem.v1().equals(v1) || edgeElem.v1().equals(v2)) {
//                            // a deep copy for v1, v2 and edge (not necessary, as told by Dr. Sathish)
//                           // Vertex newV1 = new Vertex(edgeElement.v1().id(), edgeElement.v1().name());
//                           // Vertex newV2 = new Vertex(edgeElement.v2().id(), edgeElement.v2().name());
//                            return (E) edgeElem;
//                            // student of UBC is like E, and Edge is like member of UBC
//                        }
//                    }
                }
            }
        }
        return null;
    }

    /**
     * Determine the length on an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return the length of the v1-v2 edge if this edge is part of the graph
     */
    @Override
    public int edgeLength(V v1, V v2) {
        if (this.edge(v1, v2)) {  // first check if v1-v2 is an edge in the Graph
            E foundedEdge = this.getEdge(v1, v2);
            return foundedEdge.length();
        } else {
            return 0;
        }
    }

    /**
     * Obtain the sum of the lengths of all edges in the graph
     *
     * @return the sum of the lengths of all edges in the graph
     */
    @Override
    public int edgeLengthSum() {
        int sum = 0;
        Set<E> setOfAllEdges = new HashSet<>();

        if (this.mapping.isEmpty()) {
            return 0;
        } else {
            for (Set subset : this.mapping.values()) {
                setOfAllEdges.addAll(subset);
            }
            for (E elementOfSet : setOfAllEdges) {
                sum = sum + elementOfSet.length();
            }
            return sum;
        }
    }

    /**
     * Remove an edge from the graph
     *
     * @param e the edge to remove
     * @return true if e was successfully removed and false otherwise
     */
    @Override
    public boolean remove(E e) {
        if (this.edge(e)) {
            for (E elem : mapping.get(e.v1())) { // iterating through a set of elements of type E
                if (elem.equals(e)) {
                    mapping.get(e.v1()).remove(elem);
                    for (E element : mapping.get(e.v2())) {
                        if (element.equals(e)) {
                            mapping.get(e.v2()).remove(element);
                            return true;
                        }
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }


    /**
     * Remove a vertex from the graph
     *
     * @param v the vertex to remove
     * @return true if v was successfully removed and false otherwise
     */
    @Override
    public boolean remove(V v) {
        if (this.vertex(v)) {
            for (E element : this.mapping.get(v)) {
                V otherVertex = element.v1().equals(v) ? element.v2() : element.v1();
                mapping.get(otherVertex).remove(element);
            }
            mapping.remove(v); // remove the key "v" along with the set of its connected edges.
            return true;
        }
        return false;
    }



    /**
     * Obtain a set of all vertices in the graph.
     * Access to this set does permit graph mutations.
     *
     * @return a set of all vertices in the graph. If there are not Vertices,
     * return an empty set.
     */
    @Override
    public Set<V> allVertices() {
        if (this.mapping.isEmpty()) {
            return new HashSet<>();  // return an empty set if the Graph is empty
        } else {
            Set<V> vertices = new HashSet<>();
            for (V element : this.mapping.keySet()) {
                // make new Vertex and then put it into the Set
                // Deep copying to avoid mutation. (Not necessary, as told by Dr. Sathish)
               // V v1New = (V) new Vertex(element.id(), element.name());
                vertices.add(element);
            }
            return vertices;
        }
    }

    /**
     * Obtain a set of all vertices incident on v.
     * Access to this set does permit graph mutations.
     *
     * @param v the vertex of interest
     * @return all edges incident on v. Return an empty set if there are no such
     * vertices that are incident on v.
     */
    //public boolean incident(V v) {
    @Override
    public Set<E> allEdges(V v) {
        Set<E> edges = new HashSet<>();
        if (vertex(v)) {
            for (E element: this.mapping.get(v)) {
                if (element.incident(v)) {
                    // Deep copying to avoid mutation:
                    // take the first and second vertex and assign them
                    // into new vertices to avoid mutability. (Not neccessary, so not doing it).
                   // V v1New = (V) new Vertex(element.v1().id(), element.v1().name());
                   // V v2New = (V) new Vertex(element.v2().id(), element.v2().name());
                   // E newEdge = (E) new Edge<Vertex>(v1New, v2New, element.length());
                    edges.add(element);
                }
            }
            return edges;
        }
        return new HashSet<>();   // The Graph is empty.
    }


    /**
     * Obtain a set of all edges in the graph.
     * Access to this set does permit graph mutations.
     *
     * @return all edges in the graph. Return an empty set if there are no edges
     * in the graph.
     */
    @Override
    public Set<E> allEdges() {
        Set<E> setOfAll = new HashSet<>();
        if (this.mapping.isEmpty()) {
            return new HashSet<>();
        } else {
            for (V vertexElement: this.mapping.keySet()) {
                for (E edgeElement : this.mapping.get(vertexElement)) {
                    // Deep copying to avoid mutation (not doing it)
                    // V v1New = (V) new Vertex(edgeElement.v1().id(), edgeElement.v1().name());
                   //  V v2New = (V) new Vertex(edgeElement.v2().id(), edgeElement.v2().name());
                   //  E newEdge = (E) new Edge<Vertex>(v1New, v2New, edgeElement.length());
                    setOfAll.add(edgeElement);
                }
            }
        }
        // To check to see if any element has been added into "setOfAll" set:
        if (setOfAll.isEmpty()) {
            return new HashSet<>();
        } else {
            return setOfAll;
        }
    }


    /**
     * Obtain all the neighbours of vertex v.
     * Access to this map does permit graph mutations.
     *
     * @param v is the vertex whose neighbourhood we want.
     * @return a map containing each vertex w that neighbors v and the edge between v and w.
     */
    @Override
    public Map<V, E> getNeighbours(V v) {
        Map<V, E> result = new HashMap<>();
        if (!vertex(v)) {
            return new HashMap<>(); // return an empty map if the Vertex v is not part of the Graph.
        } else {
            for (Edge edgeElement: mapping.get(v)) {
                Vertex vertexNeighbor = edgeElement.v1().equals(v) ? edgeElement.v2() : edgeElement.v1();
                // Deep copying the "vertexNeighbor" Vertex (not doing deep copying here!)
                // V newVertex = (V) new Vertex(vertexNeighbor.id(), vertexNeighbor.name());
                // Deep copying the "edgeElement" Edge:
                // E newEdge = (E) new Edge<Vertex>(edgeElement.v1(), edgeElement.v2(), edgeElement.length());

                result.put((V)vertexNeighbor, (E) edgeElement);
            }
        }

        // checking to see if anything has been added into the "result" HashMap:
        if (result.isEmpty()) {
            return new HashMap<>();
        } else {
            return result;
        }
    }


    /**
     * Compute the shortest path from source to sink
     *
     * @param source the start vertex
     * @param sink   the end vertex
     * @return the vertices, in order, on the shortest path from source
     * to sink (both end points are part of the list). Return an empty list
     * if there is no such shortest path from source to sink.
     */
    @Override
    public List<V> shortestPath(V source, V sink) {
        List<V> returnedList = new ArrayList<>();
        List<V> verticesList = new ArrayList<>(this.mapping.keySet());

        //Set<V> visited = new HashSet<>();
        Set<V> unvisited = new HashSet<>();    // our "Q" set

        // initially all the Vertices are in the unvisited or "Q" set:
        for (V element: verticesList) {
            unvisited.add(element);
        }

        // Map of a Vertex to a tentative distance:
        Map<V, Integer> distanceMap = new HashMap<>();
        // Map of a Vertex to the previous Vertex:
        Map<V, V> prev = new HashMap<>();    // the path to go back to our source.

        // initialized the vertices in the "distanceMap" with Infinity:
        for (int i = 0; i < verticesList.size(); i++) {
            distanceMap.put(verticesList.get(i), Integer.MAX_VALUE);
        }
        // Assign zero as the cost of our source node:
        distanceMap.put(source, 0);

        // Assigning null to the Previous of the Vertices:

        for (V element: verticesList) {
            prev.put(element, null);
        }

        Map<V, E> neighborVertices = new HashMap<>();
        V uVertex = null;
        while(!unvisited.isEmpty()) {
            int minLength = Integer.MAX_VALUE;
            // searches for the vertex u in the vertex set Q that has the least dist[u] value:
            // finding min distance Vertex
            for (V vertexElement : unvisited) {
                if (distanceMap.get(vertexElement) <= minLength) {
                    minLength = distanceMap.get(vertexElement);
                    uVertex = vertexElement;
                }
            }
            // Remove 'u' from 'Q'
            if (uVertex != null) {
                unvisited.remove(uVertex);
            }
            // If the "sink" Vertex has already been visited, get out of the loop.
            if (uVertex.equals(sink)) {
                break;
            }
            // for each neighbor v of u:
            if (uVertex != null) {
                neighborVertices = new HashMap<>(this.getNeighbours(uVertex));
            }
            for (V neighborElement : neighborVertices.keySet()) {
                if (unvisited.contains(neighborElement)) {
                    int alt = distanceMap.get(uVertex) + this.edgeLength(uVertex, neighborElement);
                    if (alt < distanceMap.get(neighborElement)) {
                        distanceMap.put(neighborElement, alt);
                        prev.put(neighborElement, uVertex);
                    }
                }
            }
        }

        // getting from the Destination to the Source:
        V i = sink;
        while(prev.get(i) != null) {
            returnedList.add(i);
            V vertexPrevious = prev.get(i);
            i = vertexPrevious;
            if (i.equals(source)) {
                returnedList.add(i);
            }
        }

        // reversing the ArrayList:
        List<V> reversedList = new ArrayList<>();
        int k = returnedList.size() - 1;
        while(returnedList.size() != reversedList.size()) {
            reversedList.add(returnedList.get(k));
            --k;
        }

        return reversedList;
    }


    /**
     * Compute the minimum spanning tree of the graph.
     * See https://en.wikipedia.org/wiki/Minimum_spanning_tree
     *
     * @return a list of edges that forms a minimum spanning tree of the graph
     */

    @Override
    public List<E> minimumSpanningTree() {
        List<E> finallyReturnedEdgeList = new ArrayList<>();

        List<V> Qset = new ArrayList<>(); // we terminate once the Qset is empty.
        List<V> forest = new ArrayList<>();

        ArrayList<V> vertexArray = new ArrayList<>(mapping.keySet());
        if (vertexArray.size() != 0) {
            forest.add(vertexArray.get(0));   // initialize the Forest with some random Vertex, which in this case it is the initialVertex
            for (int i = 1; i < vertexArray.size(); i++) {   // the remaining Vertices go into Qset.
                Qset.add(vertexArray.get(i));
            }
        }

        while (!Qset.isEmpty()) {
            int minLength = Integer.MAX_VALUE;
            Set<E> edgeSet = new HashSet<>();    // At each remove operation from Qset and Adding into the Forest, this is being updated.
            System.out.println(forest);
            for (int i = 0; i < forest.size(); i++) {
                for (int k = 0; k < Qset.size(); k++) {
                    if (this.edge(forest.get(i), Qset.get(k))) {   // if such an Edge exists in the Graph.
                        E nextEdge = this.getEdge(forest.get(i), Qset.get(k));
                        System.out.println("Forest vertex: " + forest.get(i));
                        System.out.println("Qset vertex: " + Qset.get(k));
                        edgeSet.add(nextEdge);
                        System.out.println(nextEdge);
                        System.out.println();
                    }
                }
            }
            //calling Helper method for finding the smallest edgeLength:
            if (compare(edgeSet) == null) { // meaning there was no connection between Vertices
                return new ArrayList<>();        // as the output of the MinimumSpanningTree method.
            } else {
                E returnedEdge = compare(edgeSet);
                finallyReturnedEdgeList.add(returnedEdge);
                if (Qset.contains(returnedEdge.v1())) {
                    Qset.remove(returnedEdge.v1());     // remove Vertex from Qset and add it into Forest
                    forest.add(returnedEdge.v1());
                } else if (Qset.contains(returnedEdge.v2())) {
                    Qset.remove(returnedEdge.v2());
                    forest.add(returnedEdge.v2());
                }
            }
        }

        return finallyReturnedEdgeList;    // the output of the MinimumSpanningTree
    }




    // Helper method for finding smallest edgeLength in a set of Edges.
    private E compare(Set<E> edgeSet) {
        int initialLength = Integer.MAX_VALUE;
        E returnedEdge = null;
        for (E element: edgeSet) {
            if (element.length() < initialLength) {
                initialLength = element.length();
                returnedEdge = element;
            }
        }
        return returnedEdge;
    }





    /**
     * Compute the length of a given path
     *
     * @param path indicates the vertices on the given path
     * @return the length of path. Return Integer.MAX_VALUE if there is no
     * path between two vertices.
     */
    @Override
    public int pathLength(List<V> path) {
        int sum = 0;
        if (path.isEmpty()) {
            return Integer.MAX_VALUE;     // return INFINITY for a disconnected path
        } else {
            for (int i = 0; i < path.size(); i++) {
                if ((i + 1) < path.size()) {
                    Edge<V> foundEdge = this.getEdge(path.get(i), path.get(i + 1));
                    sum = sum + foundEdge.length();
                }
            }
            return sum;
        }
    }



    /**
     * Obtain all vertices w that are no more than a <em>path distance</em> of range from v.
     *
     * @param v     the vertex to start the search from.
     * @param range the radius of the search.
     * @return a set of vertices that are within range of v (this set does not contain v).
     */
    @Override
    public Set<V> search(V v, int range) {
        Set<V> initialAllVertices = this.allVertices();
        Set<V> returnedAllVertices = new HashSet<>();
        if (initialAllVertices.contains(v)) {
            initialAllVertices.remove(v);
        }

        for (V vertexElement: initialAllVertices) {
                List<V> shortPath = this.shortestPath(v, vertexElement);
                int shortestPathLength = this.pathLength(shortPath);
                if (shortestPathLength <= range) {
                    returnedAllVertices.add(vertexElement);
                }
        }

        return returnedAllVertices;
    }





















    /**
     * Compute the diameter of the graph.
     * <ul>
     * <li>The diameter of a graph is the length of the longest shortest path in the graph.</li>
     * <li>If a graph has multiple components then we will define the diameter
     * as the diameter of the largest component.</li>
     * </ul>
     *
     * @return the diameter of the graph. Return zero if there
     * is no edge between any vertices in the Graph; meaning, return zero
     * if the Graph is disconnected.
     */
    @Override
    public int diameter() {
        List<V> allVertices = new ArrayList<>(this.mapping.keySet());
        List<Integer> distanceList = new ArrayList<>();

        for (int i = 0; i < allVertices.size(); i++) {
            for (int k = i + 1; k < allVertices.size(); k++) {
                List<V> shortestpath = this.shortestPath(allVertices.get(i),
                        allVertices.get(k));

                int shortLength = this.pathLength(shortestpath);
                if (shortLength != Integer.MAX_VALUE) {
                    distanceList.add(shortLength);
                }
            }
        }

        int minLength = 0;
        for (Integer component: distanceList) {
            if (component >= minLength) {
                minLength = component;
            }
        }

        return minLength;
    }




    //// add all new code above this line ////
    /**
     * This method removes some edges at random while preserving connectivity
     * <p>
     * DO NOT CHANGE THIS METHOD
     * </p>
     * <p>
     * You will need to implement allVertices() and allEdges(V v) for this
     * method to run correctly
     *</p>
     * <p><strong>requires:</strong> this graph is connected</p>
     *
     * @param rng random number generator to select edges at random
     */
    public void pruneRandomEdges(Random rng) {
        class VEPair {
            V v;
            E e;

            public VEPair(V v, E e) {
                this.v = v;
                this.e = e;
            }
        }
        /* Visited Nodes */
        Set<V> visited = new HashSet<>();
        /* Nodes to visit and the cpen221.mp2.graph.Edge used to reach them */
        Deque<VEPair> stack = new LinkedList<VEPair>();
        /* Edges that could be removed */
        ArrayList<E> candidates = new ArrayList<>();
        /* Edges that must be kept to maintain connectivity */
        Set<E> keep = new HashSet<>();

        V start = null;
        for (V v : this.allVertices()) {
            start = v;
            break;
        }
        if (start == null) {
            // nothing to do
            return;
        }
        stack.push(new VEPair(start, null));
        while (!stack.isEmpty()) {
            VEPair pair = stack.pop();
            if (visited.add(pair.v)) {
                keep.add(pair.e);
                for (E e : this.allEdges(pair.v)) {
                    stack.push(new VEPair(e.distinctVertex(pair.v), e));
                }
            } else if (!keep.contains(pair.e)) {
                candidates.add(pair.e);
            }
        }
        // randomly trim some candidate edges
        int iterations = rng.nextInt(candidates.size());
        for (int count = 0; count < iterations; ++count) {
            int end = candidates.size() - 1;
            int index = rng.nextInt(candidates.size());
            E trim = candidates.get(index);
            candidates.set(index, candidates.get(end));
            candidates.remove(end);
            remove(trim);
        }
    }

}
