/*Author: Evan McKenzie
 * With Help From: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * 
 * This program implements the Graph interface by using our constructed Doubly Linked List
 * and HashMap to maintain a series of Vertex and Edge nodes. Using a nested InnerVertex and 
 * InnerEdge class, this program overrides the methods from the interface and completes the implementation
 * so that the graph works in both a directed and undirected format. By using a positionally aware container
 * (our DLL), the vertex and edges are able to keep track of this positions in the last, allowing for iterable 
 * lists of the objects in order
 */

package cs2321;

import net.datastructures.*;

/*
 * Implement Graph interface. A graph can be declared as either directed or undirected.
 * In the case of an undirected graph, methods outgoingEdges and incomingEdges return the same collection,
 * and outDegree and inDegree return the same value.
 * 
 * @author CS2321 Instructor
 */
public class AdjListGraph<V, E> implements Graph<V, E> {
	
	private boolean isDirected;								//global boolean for direction
	private	DoublyLinkedList<Vertex<V>> vertices;				//DLL for vertex
	private	DoublyLinkedList<Edge<E>> edges;					//DLL for edges

	//constructor when given directed boolean
	public AdjListGraph(boolean directed) {
		isDirected = directed;
		vertices = new DoublyLinkedList<>( );
		edges = new DoublyLinkedList<>( );
	}

	//default constructor
	public AdjListGraph() { 
		vertices = new DoublyLinkedList<>( );
		edges = new DoublyLinkedList<>( );
	}

	//vertex inner class
	private class InnerVertex<V> implements Vertex<V>{
		private V element;
		private Position<Vertex<V>> pos;
		private Map<Vertex<V>, Edge<E>> outgoing, incoming;
		
		//constructor
		public InnerVertex( V elem, boolean graphsDirected) {
			element = elem;
			outgoing = new HashMap<>();
			if(graphsDirected) {
				incoming = new HashMap<>();
			}
			else {
				incoming = outgoing;
			}
		}
		
		//validate method for vertexes
		public boolean validate(Graph<V,E> graph) {
		      return (AdjListGraph.this == graph && pos != null);
		}
		
		//inner class methods
		public V getElement() { return element; }
		public void setPosition( Position<Vertex<V>> p) { pos = p; }
		public Position<Vertex<V>> getPosition() { return pos; }
		public Map<Vertex<V>, Edge<E>> getOutgoing() { return outgoing; }
		public Map<Vertex<V>, Edge<E>> getIncoming() { return incoming; }
	}
	
	//edge inner class
	private class InnerEdge<E> implements Edge<E> {
		private E element;
		private Position<Edge<E>> pos;
		private Vertex<V>[] endpoints;
		
		//constructor
		public InnerEdge(Vertex<V> u, Vertex<V> v, E elem) {
			element = elem;
			endpoints = (Vertex<V>[]) new Vertex[] {u,v};
		}
		
		//edge validate method
		public boolean validate(Graph<V,E> graph) {
		      return AdjListGraph.this == graph && pos != null;
		}
		
		//inner class methods
		public E getElement() { return element; }
		public Vertex<V>[] getEndpoints() { return endpoints; }
		public void setPosition(Position<Edge<E>> p) { pos = p; }
		public Position<Edge<E>> getPosition() { return pos; }
	}
	
	//private vertex validate method
	private InnerVertex<V> validate(Vertex<V> v) {
	    if (!(v instanceof InnerVertex)) {
	    	throw new IllegalArgumentException("Invalid vertex");			//throw exception
	    }
	    InnerVertex<V> vert = (InnerVertex<V>) v;     // safe cast
	    if (!vert.validate(this)) {
	    	throw new IllegalArgumentException("Invalid vertex");			//throw exception
	    }
	    return vert;
	  }
	
	//private edge validate method
	private InnerEdge<E> validate(Edge<E> e) {
	    if (!(e instanceof InnerEdge)) {
	    	throw new IllegalArgumentException("Invalid edge");			//throw exception
	    }
	    InnerEdge<E> edge = (InnerEdge<E>) e;     // safe cast
	    
	    if (!edge.validate(this)) {
	    	throw new IllegalArgumentException("Invalid edge");			//throw exception
	    }
	    return edge;
	  }

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#edges()
	 */
	@TimeComplexity("O(m)")
	@SpaceComplexity("O(n+m)")
	public Iterable<Edge<E>> edges() {
		return edges;						//return DLL
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#endVertices(net.datastructures.Edge)
	 */
	@TimeComplexity("O(1)")
	@SpaceComplexity("O(n+m)")
	public Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		return edge.getEndpoints();				//array of endpoints
	}


	/* (non-Javadoc)
	 * @see net.datastructures.Graph#insertEdge(net.datastructures.Vertex, net.datastructures.Vertex, java.lang.Object)
	 */
	@TimeComplexity("O(1)")
	@TimeComplexityExpected("O(1)")
	@SpaceComplexity("O(n+m)")
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E o)
			throws IllegalArgumentException {
		if(getEdge(u,v) == null) {
			InnerEdge<E> e = new InnerEdge<>(u,v,o);
			e.setPosition(edges.addLast(e)); 
			InnerVertex<V> origin = validate(u); 
			InnerVertex<V> dest = validate(v);
			origin.getOutgoing().put(v,e); 
			dest.getIncoming().put(u,e);
			return e;
		}
		else {
			throw new IllegalArgumentException("Edge from u to v exists");			//throw exception if edge already exists
		}
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#insertVertex(java.lang.Object)
	 */
	@TimeComplexity("O(1)")
	@SpaceComplexity("O(n+m)")
	public Vertex<V> insertVertex(V o) {
		InnerVertex<V> v = new InnerVertex<>(o, isDirected);
		v.setPosition(vertices.addLast(v));
		return v;								//return the inserted vertex
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#numEdges()
	 */
	@TimeComplexity("O(1)")
	@SpaceComplexity("O(n+m)")
	public int numEdges() {
		return edges.size();			//size of DLL
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#numVertices()
	 */
	@TimeComplexity("O(1)")
	@SpaceComplexity("O(n+m)")
	public int numVertices() {
		return vertices.size();				//size of DLL
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#opposite(net.datastructures.Vertex, net.datastructures.Edge)
	 */
	@TimeComplexity("O(1)")
	@SpaceComplexity("O(n+m)")
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e)
			throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		Vertex<V>[] endpoints = edge.getEndpoints();
		if(endpoints[0] == v) {
			return endpoints[1];
		}
		else if(endpoints[1] == v) {
			return endpoints[0];
		}
		else {
			throw new IllegalArgumentException("v is not incident to this edge");
		}
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#removeEdge(net.datastructures.Edge)
	 */
	@TimeComplexity("O(1)")
	@TimeComplexityExpected("O(1)")
	@SpaceComplexity("O(n+m)")
	public void removeEdge(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		
		Vertex<V>[] verts = edge.getEndpoints();
		
		((InnerVertex<V>)verts[0]).getOutgoing().remove(verts[1]);
	    ((InnerVertex<V>)verts[1]).getIncoming().remove(verts[0]);
	    
	    edges.remove(edge.getPosition());
	    edge.setPosition(null); 
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#removeVertex(net.datastructures.Vertex)
	 */
	@TimeComplexity("O(deg(v))")
	@SpaceComplexity("O(n+m)")
	public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
		/*
		 * TCJ
		 * double for each loop 
		 */
		InnerVertex<V> vert = validate(v);
		for(Edge<E> e : vert.getOutgoing().values()) {
			removeEdge(e);
		}
		for(Edge<E> e : vert.getIncoming().values()) {
			removeEdge(e);
		}
		vertices.remove(vert.getPosition());			//remove from DLL
		vert.setPosition(null);							//erase reference pointer
	}

	/* 
     * replace the element in edge object, return the old element
     */
	public E replace(Edge<E> e, E o) throws IllegalArgumentException {
		return null;
	}

    /* 
     * replace the element in vertex object, return the old element
     */
	public V replace(Vertex<V> v, V o) throws IllegalArgumentException {
		return null;
	}

	/* (non-Javadoc)
	 * @see net.datastructures.Graph#vertices()
	 */
	@TimeComplexity("O(1)")
	@SpaceComplexity("O(n+m)")
	public Iterable<Vertex<V>> vertices() {
		return vertices;					//return DLL
	}

	@Override
	@TimeComplexity("O(1)")
	@SpaceComplexity("O(n+m)")
	public int outDegree(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getOutgoing().size();			//return size of outgoing array
	}

	@Override
	@TimeComplexity("O(1)")
	@SpaceComplexity("O(n+m)")
	public int inDegree(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getIncoming().size();					//return size of incoming array
	}

	@Override
	@TimeComplexity("O(deg(v)")
	@SpaceComplexity("O(n+m)")
	public Iterable<Edge<E>> outgoingEdges(Vertex<V> v)
			throws IllegalArgumentException {
		/*
		 * TCJ
		 * iterable object loops over list
		 */
		InnerVertex<V> vert = validate(v);
		return vert.getOutgoing().values();				//return iterable container of values
	}

	@Override
	@TimeComplexity("O(deg(v)")
	@SpaceComplexity("O(n+m)")
	public Iterable<Edge<E>> incomingEdges(Vertex<V> v)
			throws IllegalArgumentException {
		/*
		 * TCJ
		 * iterable object loops over list
		 */
		InnerVertex<V> vert = validate(v);
		return vert.getIncoming().values();					//return iterable container of values
	}

	@Override
	@TimeComplexity("O(1)")
	@TimeComplexityExpected("O(1)")
	@SpaceComplexity("O(n+m)")
	public Edge<E> getEdge(Vertex<V> u, Vertex<V> v)
			throws IllegalArgumentException {
		InnerVertex<V> origin = validate(u);
		return origin.getOutgoing().get(v);				//get edge from iterable container
	}
	
}
