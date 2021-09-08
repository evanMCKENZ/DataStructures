/*Author: Evan McKenzie
 * With Help From: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * 
 * This program implements the PositionalList interface (which also extends the Iterable class) 
 * with abstract data type E and overrides all methods from that class as well as implementing nested Node
 * and PositionalIterator classes. This program also includes helper methods like validate() and position()
 * to keep track of positions and verify whether there are elements at certain positions. Finally, this
 * program also introduces an iterator object to iterate over the elements in the doubly linked list
 */


package cs2321;
import java.util.Iterator;

import net.datastructures.Position;
import net.datastructures.PositionalList;


public class DoublyLinkedList<E> implements PositionalList<E> {

	private class PositionIterator implements Iterator<Position<E>> {				//nested Iterator class with abstract type Position<E>
		private Position<E> latest = first();										//global variables for the iterator. includes a call to first() which returns the first Node
		private Position<E> current = null;

		public boolean hasNext() { return latest != null; }								//implemented methods from the PositionalList class

		public Position<E> next() throws NullPointerException {
			if (latest == null) throw new NullPointerException("nothing left");				//if no next Node, throw exception
			current = latest;
			latest = after(latest);
			return current;
		}

		public void remove() throws IllegalStateException {
			if(current == null) throw new IllegalStateException("nothing to remove");				//if current = null, list is empty and there is nothing to remove
			DoublyLinkedList.this.remove(current);										//call the remove() method to remove the current Node
			current = null;
		}
	}

	private static class Node<E> implements Position<E> {							//nested node class that implements the Position<E> interface

		private E element;							//global variables for the Node class
		private Node<E> previous;							//since this is a doubly linked list, Nodes must remember the previous and next Nodes
		private Node<E> next;

		public Node(E e, Node<E> p, Node<E> n) {						//default constructor

			element = e;
			previous = p;
			next = n;
		}

		public E getElement() throws IllegalStateException {							//helper methods for the Node class
			if (next == null ) 
				throw new IllegalStateException( "Position no longer valid ");
			return element;
		}

		public Node<E> getPrev() {
			return previous;
		}

		public Node<E> getNext() {
			return next;
		}

		public void setElement( E e ) {									//requires type E, NOT Position<E>
			element = e;
		}

		public void setPrev( Node<E> p) {
			previous = p;
		}

		public void setNext( Node<E> n) {
			next = n;
		}
	}


	private int size = 0;								//global variables for the program
	private Node<E> head;									//first sentinel Node
	private Node<E> tail;									//last sentinel Node

	public DoublyLinkedList() {								//constructor
		head = new Node<> (null, null, null);
		tail = new Node<> (null, head, null);					//technically second Node, so reference to head as previous is created
		head.setNext(tail);												//create reference to tail as next
	}

	private Node<E> validate(Position<E> p) throws IllegalArgumentException {									//helper method to validate whether a Position even exists
		if(!(p instanceof Node)) throw new IllegalArgumentException("Invalid position ");
		Node<E> node = (Node<E>) p;
		if(node.getNext() == null)
			throw new IllegalArgumentException( "position no longer exists ");

		return node;								//return the Position as a Node
	}

	private Position<E> position(Node <E> node) {					//method to return position for a given node
		if(node == head || node == tail ) 							//if either of these are true the node is either of the two sentinels and need to be avoided
			return null;
		return node;														//return the given Node otherwise
	}

	@Override
	public int size() {
		return size;									//return the size of the list (number of element)
	}

	@Override
	public boolean isEmpty() {
		return size == 0;										//return a boolean true or false about whether or not the list is empty
	}

	@Override
	public Position<E> first() {
		return position(head.getNext( ) );										//returns the position of the Node immediately after the head sentinel Node
	}

	@Override
	public Position<E> last() {
		return position(tail.getPrev( ) );						//returns the position of the Node immediately before the tail sentinel
	}

	@Override
	public Position<E> before(Position<E> p) throws IllegalArgumentException {					//method to get the previous position and Node
		Node<E> node = validate(p);																	//call our validate() method to ensure that p is a valid position
		return position(node.getPrev( ) );															//get the previous Node, call the position() method to get the Position<E>, and return it
	}

	@Override
	public Position<E> after(Position<E> p) throws IllegalArgumentException {						//method to get the next position and Node
		Node<E> node = validate(p);																	//call our validate() method to ensure that p is a valid position
		return position(node.getNext( ) );															//get the next Node, call the position() method to get the Position<E>, and return that value
	}

	private Position<E> addBetween(E e, Node<E> prev, Node<E> aft) {					//method to add a new Node in between two already existing Nodes
		Node<E> newie = new Node<> (e, prev, aft);										//create a new Node by calling the constructor, providing the passed in Nodes as the previous and next
		prev.setNext(newie);																//set old previous nodes next reference to our new Node
		aft.setPrev(newie);																	//set old next Nodes previous reference to our new Node
		size++;													//increment the size
		return newie;														//return the new Node
	}

	@Override
	public Position<E> addFirst(E e) {												//method to add a Node at the beginning of the list
		return addBetween(e, head, head.getNext( ) );										//call the addBetween method to create a new Node with the given value and insert it between the head sentinel and the next reference from the head sentinel
	}

	@Override
	public Position<E> addLast(E e) {												//method to add a Node at the end of the list
		return addBetween(e, tail.getPrev(), tail);									//call the addBetween method to create a new Node and insert it between the tail sentinel and the tail sentinels previous reference
	}

	@Override
	public Position<E> addBefore(Position<E> p, E e)									//method to add a new Node before an already existing one
			throws IllegalArgumentException {									//thrown exception if there is no valid position before the existing Node
		Node<E> node = validate(p);																	//call validate() to see if the position p is valid and create a new Node if it is
		return addBetween(e, node.getPrev(), node);											//call the addBetween method to insert the new Node in between the nodes previous reference and the existing node
	}

	@Override
	public Position<E> addAfter(Position<E> p, E e)							//method to add a new Node after an already existing one
			throws IllegalArgumentException {									//thrown exception if there is no valid position after the existing Node
		Node<E> node = validate(p);										//call validate() to see if the position p is valid and create a new Node if it is 
		return addBetween(e, node, node.getNext( ) );									//call the addBetween method to insert the new Node in between the nodes next reference and the existing node
	}

	@Override
	public E set(Position<E> p, E e) throws IllegalArgumentException {						//method to set a certain position to a node when given the position and the value to insert there
		Node<E> node = validate(p);												//call the validate() method to ensure that there is a valid position at p
		E returner = node.getElement();										//temporary E variable to return (the value inside the Node at position p)
		node.setElement(e);													//set the node at position p to have the value of e that was passed in
		return returner;																	//return the temporary variable
	}

	@Override
	public E remove(Position<E> p) throws IllegalArgumentException {				//method to remove a node at position p if p is a valid position in the list
		Node<E> node = validate(p);																	//call validate() to ensure that p is a valid position
		Node<E> prev = node.getPrev( );													//get the node at the previous reference
		Node<E> aft = node.getNext( );														//get the node at the next reference
		prev.setNext(aft);																		//set the previous Nodes next reference to the next Node
		aft.setPrev(prev);																		//set the next Nodes previous reference to the previous Node
		size--;																//decrement the size
		E returner = node.getElement( );																	//temporary variable to hold the E value of the Node that was removed
		node.setElement(null);														//update all the references of the removed node
		node.setNext(null);
		node.setPrev(null);
		return returner;																//return the type E variable
	}

	private class ElementIterator implements Iterator<E> {												//ElementIterator() method that implements Iterator
		Iterator<Position<E>> first = new PositionIterator();										//create new PositionIterator()
		public boolean hasNext() { return first.hasNext( ); }									//override Iterator() methods
		public E next() { return first.next( ).getElement( ); }
		public void remove() { first.remove( ); }
	}

	@Override
	public Iterator<E> iterator() {													//constructor for an iterator object
		return new ElementIterator( );													//calls the ElementIterator() constructor
	}

	private class PositionIterable implements Iterable<Position<E>> {										//constructor for PositionIterable class
		public Iterator<Position<E>> iterator() { return new PositionIterator(); } 						//calls PositionIterator() constructor
	}

	@Override
	public Iterable<Position<E>> positions() {											//method for getting an iterator of the positions
		return new PositionIterable();
	}

	public E removeFirst() throws IllegalArgumentException {																//method to remove the very first element in the doubly linked list
		if(head == null || head.next == null) { throw new IllegalArgumentException( "empty list" ); }						//if the sentinel head and head.next node are empty the list is empty so nothing to remove

		Node<E> first = head.next;																		//get the first element in the list to return
		head = head.next;																			//very clumsy way of removing the first Node
		head.previous = null;																		//reset the reference to the next node
		size--;																									//decrement the size

		return first.getElement();							//return the element of type E stored in the first variable
	}

	public E removeLast() throws IllegalArgumentException {													//method to remove the last element in the doubly linked list
		if(tail == null) { throw new IllegalArgumentException( "last element is empty" ); }							//if tail sentinel is null then list is empty
		Node<E> last;																						//temporary variable
		if(tail == head) {													//only one element in the array
			last = head;															//set last equal to the head node
		}
		else {
			last = tail.previous;															//otherwise set last equal to the previous node
		}
		tail = tail.previous;													//set new tail
		size--;																				//decrement the size
		if(size == 1) { tail = head;}												//set tail equal to head if only one element in the list 
		return last.getElement();														//return the value stored in the last Node
	}

}
