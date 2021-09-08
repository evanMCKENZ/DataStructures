/*Author: Evan McKenzie
 * With Help From: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * 
 * This program solves the Depth-First, Breadth-First, and Dijkstra problems by implementing
 * several helper methods to find the shortest possible route between two cities, which are given to us
 * as arrays of string arrays and are then added to Graphs and HashMaps in order to find the shortestOutgoing
 * combination of ways to get to them
 */

package cs2321;

import net.datastructures.*;

/**
 * @author Ruihong Zhang
 * Reference textbook R14.16 P14.81 
 *
 */
public class Travel {
	
	static AdjListGraph<String, Integer> graphglob = new AdjListGraph<>();			//global graph
	HashMap<String, Vertex<String>> mapglob = new HashMap<>();						//global hashmap
	/**
	 * @param routes: Array of routes between cities. 
	 *                routes[i][0] and routes[i][1] represent the city names on both ends of the route. 
	 *                routes[i][2] represents the cost in string type. 
	 *                Hint: In Java, use Integer.valueOf to convert string to integer. 
	 */
	public Travel(String [][] routes) {
		
		Vertex<String> start;			//start vertex
		Vertex<String> end;				//end vertex
		int cost;					//cost between the two vertices
		
		int dep = 0;			//array indexes
		int dest = 1;
		int weight = 2;
		
		for(int i = 0; i < routes.length; i++) {			//iterate over entire 1D array
			String[] trip = routes[i];				//save to new 1D array
			if( mapglob.get(trip[dep]) != null) {
				start = mapglob.get(trip[dep]);			//get the start vertex if the first index in our array is not blank
			}
			else {
				start = graphglob.insertVertex(trip[dep]);		//otherwise add the start city to our graph
				mapglob.put(trip[dep], start);					//and the hashmap
			}
			if( mapglob.get(trip[dest]) != null) {			//get the end vertex if the second index in our array is not blank
				end = mapglob.get(trip[dest]);
			}
			else {
				end = graphglob.insertVertex(trip[dest]);			//otherwise add the end city to our graph
				mapglob.put(trip[dest], end);						//and the hashmap
			}
			cost = Integer.valueOf(trip[weight]);				//get the cost with the valueOf function
			graphglob.insertEdge(start, end, cost);				//insert the cost as an edge of the two vertices
		}
		
	}
	
	/**
	 * @param departure: the departure city name 
	 * @param destination: the destination city name
	 * @return Return the path from departure city to destination using Depth First Search algorithm. 
	 *         The path should be represented as ArrayList or DoublylinkedList of city names. 
	 *         The order of city names in the list should match order of the city names in the path.  
	 *         
	 * @IMPORTANT_NOTE: The outgoing edges should be traversed by the order of the city names stored in
	 *                 the opposite vertices. For example, if V has 3 outgoing edges as in the picture below,
	 *                           V
	 *                        /  |  \
	 *                       /   |    \
	 *                      B    A     F  
	 *              your algorithm below should visit the outgoing edges of V in the order of A,B,F.
	 *              This means you will need to create a helper function to sort the outgoing edges by 
	 *              the opposite city names.
	 *              	              
	 *              See the method sortedOutgoingEdges below. 
	 */
	public Iterable<String> DFSRoute(String departure, String destination ) {

		HashMap<Vertex<String>, Edge<Integer>> known = new HashMap<>();				
		Map<Vertex<String>, Edge<Integer>> forest = new HashMap<>();
		DFSComplete(graphglob, mapglob.get(departure), known, forest);				//pass hashmaps to DFS helper method
		DoublyLinkedList<String> returner = new DoublyLinkedList<>();			//doubly linked iterable list to be returned
		returner.addLast(departure);			//add header node
		
		for( Edge<Integer> e : constructPath(graphglob, mapglob.get(departure), mapglob.get(destination), forest)) {
			returner.addLast(graphglob.opposite(mapglob.get(returner.last().getElement()), e).getElement());			//add strings to DLL based on the results of foreach loop
		}
		
		return returner;			//return the doubly linked list
	}
	
	//DFS helper method
	public static <V,E> void DFSComplete(AdjListGraph<String,Integer> graph, Vertex<String> givenv, HashMap<Vertex<String>, Edge<Integer>> known, Map<Vertex<String>,Edge<Integer>> forest) {
        known.put(givenv, null); 			//set header node
        for (Edge<Integer> e : sortedOutgoingEdges(givenv)) { 			//call to helper method sortedOutgoingEdges to return a sorted iterable list of edges
            Vertex<String> v = graph.opposite(givenv, e);
            if (!contains(v, known)) {				//check to see if the vertex already exists
                forest.put(v, e); 					//if it does not, add to the map
                DFSComplete(graph, v, known, forest); 			//recursive call
            }
        }
    }
	
	//helper method to check the map for an existing vertex
	public static <V,E> boolean contains(Vertex<String> givenv, Map<Vertex<String>, Edge<Integer>> known ) {
        for (Vertex<String> v : known.keySet()) {
            if (v.equals(givenv))
                return true;			//return boolean true if vertex exists
        }    
        return false;			//otherwise return false
    }
	
	//helper method to construct a path between given vertices
	public static <V,E> DoublyLinkedList<Edge<Integer>> constructPath(AdjListGraph<String, Integer> graph, Vertex<String> first, Vertex<String> second, Map<Vertex<String>,Edge<Integer>> forest) {
        DoublyLinkedList<Edge<Integer>> shortestPath = new DoublyLinkedList<>( );			//create returnable DLL
        if (forest.get(second) != null) { 			//if vertex v exists and is non-null				
            Vertex<String> step = second; 			//create temp vertex
            while (step != first) {				//while our found vertex is not the given second vertex
                Edge<Integer> edge = forest.get(step);			//get the edge from the found vertex
                shortestPath.addFirst(edge); 			//add the found edge to the DLL
                step = graph.opposite(step, edge); 			//reset walk to the opposite vertex
            }
        }
        return shortestPath;		//return DLL
    }
	
	
	/**
	 * @param departure: the departure city name 
	 * @param destination: the destination city name
     * @return Return the path from departure city to destination using Breadth First Search algorithm. 
	 *         The path should be represented as ArrayList or DoublylinkedList of city names. 
	 *         The order of city names in the list should match order of the city names in the path.  
	 *         
	 * @IMPORTANT_NOTE: The outgoing edges should be traversed by the order of the city names stored in
	 *                 the opposite vertices. For example, if V has 3 outgoing edges as in the picture below,
	 *                           V
	 *                        /  |  \
	 *                       /   |    \
	 *                      B    A     F  
	 *              your algorithm below should visit the outgoing edges of V in the order of A,B,F.
	 *              This means you will need to create a helper function to sort the outgoing edges by 
	 *              the opposite city names.
	 *              	             
	 *              See the method sortedOutgoingEdges below. 
	 */
	
	public Iterable<String> BFSRoute(String departure, String destination ) {
		
		HashMap<Vertex<String>, Edge<Integer>> known = new HashMap<>();				
		Map<Vertex<String>, Edge<Integer>> forest = new HashMap<>();
		BFSComplete(graphglob, mapglob.get(departure), known, forest);				//pass hashmaps to BFS helper method
		DoublyLinkedList<String> returner = new DoublyLinkedList<>();			//returner DLL
		returner.addLast(departure);				//add header node
		
		for( Edge<Integer> e : constructPath(graphglob, mapglob.get(departure), mapglob.get(destination), forest)) {			//for each edge in our constructed path
			returner.addLast(graphglob.opposite(mapglob.get(returner.last().getElement()), e).getElement());				//add the string of the opposite vertex to the DLL
		}
		
		return returner;			//return the DLL
	}
	
	//helper method for BFS
	public static <V,E> void BFSComplete(AdjListGraph<String,Integer> graph, Vertex<String> givenv, HashMap<Vertex<String>, Edge<Integer>> known, Map<Vertex<String>,Edge<Integer>> forest) {
        DoublyLinkedList<Vertex<String>> firstdll = new DoublyLinkedList<>( );			//create DLL to hold vertices
        contains(givenv, forest);			//check hashmap for the given vertex
        firstdll.addLast(givenv); 				//create header node
        while (!firstdll.isEmpty( )) {						//while the DLL is not empty
            DoublyLinkedList<Vertex<String>> seconddll = new DoublyLinkedList<>( );				//new vertices DLL
            for (Vertex<String> vertex : firstdll)				//for each vertex in the first vertex DLL
                for (Edge<Integer> edge : sortedOutgoingEdges(vertex)) {				//and for each edge in the sorted list from the current vertex
                    Vertex<String> v = graph.opposite(vertex, edge);				//get the opposite vertex
                    if (!contains(v, forest)) {					//if the hashmap does not contain it
                        known.put(v, edge);					//insert the current vertex and edge to the first given hashmap
                        forest.put(v, edge); 					//insert the current vertex and edge to the second given hashmap
                        seconddll.addLast(v); 					//and add vertex to second create vertex DLL
                    }
                }
            firstdll = seconddll; 				//set the first DLL equal to the expanded second DLL
        }
    }
	
	/**
	 * @param departure: the departure city name 
	 * @param destination: the destination city name
	 * @param itinerary: an empty DoublylinkedList object will be passed in to the method. 
	 * 	       When a shorted path is found, the city names in the path should be added to the list in the order. 
	 * @return return the cost of the shortest path from departure to destination. 
	 *         
	 * @IMPORTANT_NOTE: The outgoing edges should be traversed by the order of the city names stored in
	 *                 the opposite vertices. For example, if V has 3 outgoing edges as in the picture below,
	 *                           V
	 *                        /  |  \
	 *                       /   |    \
	 *                      B    A     F  
	 *              your algorithm below should visit the outgoing edges of V in the order of A,B,F.
	 *              This means you will need to create a helper function to sort the outgoing edges by 
	 *              the opposite city names.
	 *              
	 *              See the method sortedOutgoingEdges below. 
	 */

	public int DijkstraRoute(String departure, String destination, DoublyLinkedList<String> itinerary ) {
		
		Map<Vertex<String>, Integer> cloudyboi = shortestPathLengths(graphglob, mapglob.get(departure));			//pass to shortestPathLengths function to create cloud map
		Map<Vertex<String>,Edge<Integer>> forest = spTree(graphglob, mapglob.get(departure), cloudyboi);			//pass to spTree function to create a forest map
		int counter = 0;				//this is the cost counter
		
		itinerary.addLast(departure);			//create header node
		
		for(Edge<Integer> e : constructPath(graphglob, mapglob.get(departure), mapglob.get(destination), forest)) {				//for each edge in the constructed path
			itinerary.addLast(graphglob.opposite(mapglob.get(itinerary.last().getElement()), e).getElement());						//add the opposite vertex city string value to the DLL
			counter += e.getElement();					//and increment the counter
		}
		
		return counter;					//return the final count of the cost
	}
	
	/**
	   * Computes shortest-path distances from src vertex to all reachable vertices of g.
	   *
	   * This implementation uses Dijkstra's algorithm.
	   *
	   * The edge's element is assumed to be its integral weight.
	   */
	
	public static <V> Map<Vertex<V>, Integer>
	  shortestPathLengths(Graph<V,Integer> g, Vertex<V> src) {
	    // d.get(v) is upper bound on distance from src to v
	    Map<Vertex<V>, Integer> d = new HashMap<>();
	    // map reachable v to its d value
	    Map<Vertex<V>, Integer> cloud = new HashMap<>();
	    // pq will have vertices as elements, with d.get(v) as key
	    AdaptablePriorityQueue<Integer, Vertex<V>> pq;
	    pq = new HeapPQ<>();
	    // maps from vertex to its pq locator
	    Map<Vertex<V>, Entry<Integer,Vertex<V>>> pqTokens;
	    pqTokens = new HashMap<>();

	    // for each vertex v of the graph, add an entry to the priority queue, with
	    // the source having distance 0 and all others having infinite distance
	    for (Vertex<V> v : g.vertices()) {
	      if (v == src)
	        d.put(v,0);
	      else
	        d.put(v, Integer.MAX_VALUE);
	      pqTokens.put(v, pq.insert(d.get(v), v));       // save entry for future updates
	    }
	    // now begin adding reachable vertices to the cloud
	    while (!pq.isEmpty()) {
	      Entry<Integer, Vertex<V>> entry = pq.removeMin();
	      int key = entry.getKey();
	      Vertex<V> u = entry.getValue();
	      cloud.put(u, key);                             // this is actual distance to u
	      pqTokens.remove(u);                            // u is no longer in pq
	      for (Edge<Integer> e : g.outgoingEdges(u)) {
	        Vertex<V> v = g.opposite(u,e);
	        if (cloud.get(v) == null) {
	          // perform relaxation step on edge (u,v)
	          int wgt = e.getElement();
	          if (d.get(u) + wgt < d.get(v)) {              // better path to v?
	            d.put(v, d.get(u) + wgt);                   // update the distance
	            pq.replaceKey(pqTokens.get(v), d.get(v));   // update the pq entry
	          }
	        }
	      }
	    }
	    return cloud;         // this only includes reachable vertices
	  }
	
	
	/**
	   * Reconstructs a shortest-path tree rooted at vertex s, given distance map d.
	   * The tree is represented as a map from each reachable vertex v (other than s)
	   * to the edge e = (u,v) that is used to reach v from its parent u in the tree.
	   */
	
	  public static <V> Map<Vertex<V>,Edge<Integer>>
	  spTree(Graph<V,Integer> g, Vertex<V> s, Map<Vertex<V>,Integer> d) {
	    Map<Vertex<V>, Edge<Integer>> tree = new HashMap<>();
	    for (Vertex<V> v : d.keySet())
	      if (v != s)
	        for (Edge<Integer> e : g.incomingEdges(v)) {   // consider INCOMING edges
	          Vertex<V> u = g.opposite(v, e);
	          int wgt = e.getElement();
	          if (d.get(v) == d.get(u) + wgt)
	            tree.put(v, e);                            // edge is is used to reach v
	        }
	    return tree;
	  }
	
	

	/**
	 * I strongly recommend you to implement this method to return sorted outgoing edges for vertex V
	 * You may use any sorting algorithms, such as insert sort, selection sort, etc.
	 * 
	 * @param v: vertex v
	 * @return a list of edges ordered by edge's name
	 */
	public static Iterable<Edge<Integer>> sortedOutgoingEdges(Vertex<String> v)  {
        HeapPQ<String, Edge<Integer>> priorQ = new HeapPQ<>();				//create a priority queue that contains entries of strings and edge<Integer>
        for (Edge<Integer> edge: graphglob.outgoingEdges(v)) {				//for each edge in the global graph
            Vertex<String> temp = graphglob.opposite(v, edge);					//get the opposite vertex
            priorQ.insert(temp.getElement(), edge);						//and insert the created vertex into the PQ
        }

        DoublyLinkedList<Edge<Integer>> sortedList = new DoublyLinkedList<>();			//create returnable iterable DLL
        while (!priorQ.isEmpty()) {						//while the priority queue is not empty
            Entry<String, Edge<Integer>> entry = priorQ.removeMin();						//pop the minimum entry off the queue
            sortedList.addLast(entry.getValue());						//add to sorted DLL				
        }
        return sortedList;			//return the DLL
    }
}
