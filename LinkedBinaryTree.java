/*Author: Evan McKenzie
 * With Help From: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * 
 * This program implements the BinaryTree interface and
 * overrides the fundamental methods to implement a LinkedBinaryTree using an ArrayList filled with Entries
 * of key-value pairs to hold the map itself. This class uses several helper methods
 * to complete some of the methods and provides time complexity annotations for the non-trivial methods
 */
package cs2321;
import java.util.Iterator;

import net.datastructures.*;
	

/**
 * @author ruihong-adm
 *
 * @param <E>
 */
public class LinkedBinaryTree<E> implements BinaryTree<E>{

	//nested Node class
	protected static class Node<E> implements Position<E>{
		private E element;
		private Node<E> parent;
		private Node<E> left;
		private Node<E> right;
		
		//nested constructor
		public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild) {
			element = e;
			parent = above;
			left = leftChild;
			right =  rightChild;
		}
		
		//nested method from class
		public E getElement() { return element; }
		public Node<E> getParent() { return parent; }
		public Node<E> getLeft() { return left; }
		public Node<E> getRight() { return right; }
		
		public void setElement(E e) { element = e; }
		public void setParent(Node<E> parentNode) { parent = parentNode; }
		public void setLeft(Node<E> leftChild) { left = leftChild; }
		public void setRight(Node<E> rightChild) { right = rightChild; }
	}
	
	//helper method to create a new node
	protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right){
		return new Node<E>(e, parent, left, right);								//call constructor with given parameters
	}
	
	//global declared variables
	protected Node<E> root = null;
	private int size = 0;
	
	//helper method to validate 
	protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
		if(!(p instanceof Node)) {
			throw new IllegalArgumentException("Not a valid position type");			//make sure p is a Node
		}
		Node<E> node = (Node<E>) p;				//cast to Node
		if(node.getParent() == node) {
			throw new IllegalArgumentException("p is no longer in the tree");			//make sure the given position node exits
		}
		return node;				//return the node at position p
	}
	
	@Override
	@TimeComplexity("O(1)")
	public Position<E> root() {
		return root;					//return root of the tree
	}
	
	public  LinkedBinaryTree( ) { }		//public constructor for linked binary tree
	
	@Override
	@TimeComplexity("O(1)")
	public Position<E> parent(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);				//call validate to check the position
		return node.getParent();				//get the parent node
	}

	@Override
	@TimeComplexity("O(1)")
	public Iterable<Position<E>> children(Position<E> p) throws IllegalArgumentException {
		List<Position<E>> snapshot = new ArrayList<>(2);				//create list of positions with size of 2
		if(left(p) != null) {
			snapshot.add(0, left(p));			//check both children and add any non-null ones to the list
		}
		if(right(p) != null) {
			snapshot.add(1, right(p));
		}
		return snapshot;				//return List 
	}

	@Override
	@TimeComplexity("O(1)")
	/* count only direct child of the node, not further descendant. */
	public int numChildren(Position<E> p) throws IllegalArgumentException {
		int count = 0;						//same as children, except count instead of List
		if(left(p) != null) {
			count++;					//increment count if non-null
		}
		if(right(p) != null) {
			count++;					//increment count if non-null
		}
		return count;				//return final count
	}

	@Override
	@TimeComplexity("O(1)")
	public boolean isInternal(Position<E> p) throws IllegalArgumentException {
		return numChildren(p) > 0;					//boolean, if greater than 0 the current node is internal (has children)
	}

	@Override
	@TimeComplexity("O(1)")
	public boolean isExternal(Position<E> p) throws IllegalArgumentException {
		return numChildren(p) == 0;				//boolean, if return 0 current node has no children 
	}

	@Override
	@TimeComplexity("O(1)")
	public boolean isRoot(Position<E> p) throws IllegalArgumentException {
		return p == root();					//boolean comparison to root node returned by root() call
	}

	@Override
	@TimeComplexity("O(1)")
	public int size() {
		return size;				//return size global variable
	}

	@Override
	@TimeComplexity("O(1)")
	public boolean isEmpty() {
		return size() == 0;					//return boolean checker of 
	}
	
	//helper iterator method
	private class ElementIterator implements Iterator<E> {
		Iterator<Position<E>> posIterator = positions().iterator();
		public boolean hasNext() { return posIterator.hasNext(); }
		public E next() { return posIterator.next().getElement(); }
		public void remove() { posIterator.remove(); }
	}
	
	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();
	}

	//helper method to get the inOrder subtree
	private void inorderSubtree(Position<E> p, ArrayList<Position<E>> snapshot) {
		if(left(p) != null) {
			inorderSubtree(left(p), snapshot);			//get subtree from left child and put into snapshot arraylist
		}
		snapshot.addLast(p);				//add the position p in between the two trees
		if(right(p) != null) {
			inorderSubtree(right(p), snapshot);			//get subtree from right child and put into snapshot arraylist
		}
	}
	
	//helper method 
	@TimeComplexity("O(n)")
	public Iterable<Position<E>> inorder() {
		/*
		 * TCJ
		 * iterate over all elements
		 */
		ArrayList<Position<E>> snapshot = new ArrayList<>();				//create snapshot arraylist
		if(!isEmpty()) {			//make sure the tree is not empty
			inorderSubtree(root(), snapshot);			//return the subtree from the root() position
		}
		return snapshot;			//return arraylist
	}
	
	
	@Override
	@TimeComplexity("O(n)")
	public Iterable<Position<E>> positions() {
		/*
		 * TCJ
		 * call to inorder
		 */
		return inorder();
	}

	@Override
	@TimeComplexity("O(1)")
	public Position<E> left(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);			//validate position
		return node.getLeft();				//get left child of given node
	}

	@Override
	@TimeComplexity("O(1)")
	public Position<E> right(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);			//validate position
		return node.getRight();				//get right child of given node
	}
	
	@TimeComplexity("O(1)")
	public void setElement(Position<E> p, E element) throws IllegalArgumentException {
		Node<E> node = validate(p);				//validate position
		node.setElement(element);					//set the value of the current node to the parameter value
	}
	
	@Override
	@TimeComplexity("O(1)")
	public Position<E> sibling(Position<E> p) throws IllegalArgumentException {
		Position<E> parent = parent(p);			//get the position of the parent node to the current
		if(parent == null) {				//if no parent of current node there is no sibling
			return null;
		}
		if(p == left(parent)) {				
			return right(parent);			//if the left node has a parent
		}
		else {
			return left(parent);			//if the right node has a parent
		}
	}
	
	/* creates a root for an empty tree, storing e as element, and returns the 
	 * position of that root. An error occurs if tree is not empty. 
	 */
	@TimeComplexity("O(1)")
	public Position<E> addRoot(E e) throws IllegalStateException {
		if(!isEmpty()) {												//if tree is not empty there there already is a root
			throw new IllegalStateException("Tree is not empty");
		}
		root = createNode(e, null, null, null);					//create node with given values
		size = 1;									//reset the size variable
		return root;					//return the root
	}
	
	/* creates a new left child of Position p storing element e, return the left child's position.
	 * If p has a left child already, throw exception IllegalArgumentExeption. 
	 */
	@TimeComplexity("O(1)")
	public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);			//validate position
		if(parent.getLeft() != null) {						//if there is already a left child, throw exception 
			throw new IllegalArgumentException("p already has a left child");
		}
		Node<E> child = createNode(e, parent, null, null);			//create node with the given parameter
		parent.setLeft(child);							//set the left child to the given element
		size++;							//increment size
		return child;					//return the child (baby yoda)
	}

	/* creates a new right child of Position p storing element e, return the right child's position.
	 * If p has a right child already, throw exception IllegalArgumentExeption. 
	 */
	@TimeComplexity("O(1)")
	public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> parent = validate(p);			//validate position
		if(parent.getRight() != null) {					//if there is already a right child, throw exception
			throw new IllegalArgumentException("p already has a right child");
		}
		Node<E> child = createNode(e, parent, null, null);			//create node with the given parameter
		parent.setRight(child);						//set the right child to the given element
		size++;						//increment size
		return child;				//return the child (baby yoda)
	}
	
	/* Attach trees t1 and t2 as left and right subtrees of external Position. 
	 * if p is not external, throw IllegalArgumentExeption.
	 */
	@TimeComplexity("O(1)")
	public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2)
			throws IllegalArgumentException {
		Node<E> node = validate(p);							//validate position
		if(isInternal(p)) {								//cant attach trees of internal nodes
			throw new IllegalArgumentException("p must be a leaf");
		}
		size += t1.size() + t2.size();				//increase the size
		if(!t1.isEmpty()) {						//make sure this is not an empty tree
			t1.root.setParent(node);
			node.setLeft(t1.root);
			t1.root = null;				//set tree 1 root to null
			t1.size = 0;				//reset the size
		}
		if(!t2.isEmpty()) {					//make sure this is not an empty tree
			t2.root.setParent(node);
			node.setRight(t2.root);
			t2.root = null;					//set tree 2 root to null
			t2.size = 0;					//reset the size
		}
	}
	
	
	/**
	 * If p is an external node ( that is it has no child), remove it from the tree.
	 * If p has one child, replace it with its child. 
	 * If p has two children, throw IllegalAugumentException. 
	 * @param p who has at most one child. 
	 * @return the element stored at position p if p was removed.
	 */
	@TimeComplexity("O(1)")
	public E remove(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);										//validate position
		if(numChildren(p) == 2) {										//make sure there is not two children of the current node
			throw new IllegalArgumentException("p has two children");
		}
		Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());			//ternary operator
		if(child != null) {
			child.setParent(node.getParent());					//if no child, reset parent
		}
		if(node == root) {				//if given node is the root, set root equal to the child of the root
			root = child;
		}
		else {
			Node<E> parent = node.getParent();			//create new node from the parent node
			if(node == parent.getLeft()) {
				parent.setLeft(child);					//reset left to lefts child
			}
			else {
				parent.setRight(child);					//reset right to rights child
			}
		}
		size--;							//decrement size
		E temp = node.getElement();			//get the value of the node to be removed 
		node.setElement(null);				//set element of the current node
		node.setLeft(null);					//set left child
		node.setRight(null);				//set right child
		node.setParent(node);				//set parent node
		return temp;				//return the replaced value
	}
}
