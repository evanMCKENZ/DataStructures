/*Author: Evan McKenzie
 * 
 * This program implements the Queue interface with abstract data type E and overrides 
 * all methods from that class as well as implementing a nested Node class This program also introduces 
 * the concept of globally defined head and tail Nodes
 */

package cs2321;

import net.datastructures.Queue;

/**
 * @author ruihong-adm
 * @param <E>
 *
 */

public class CircularArrayQueue<E> implements Queue<E> {

	public class Node{									//nested Node class with helped method to make later method easier
		private E value;									//abstract type E represents the value of the Node
		private Node next;									//queues must remember the order in which they are entered, so nodes need a next node 
		Node(E value) { this.value = value;}						//default constructor for a new Node when given a value
		Node getNext() {return next;}
		void setNext(Node next) { this.next = next;}			//helper methods

	}

	private int size = 0;										//globally defined variables
	private int capacity = 0;
	private Node head = null;									//head is the start of the queue (the first in, and the first out)
	private Node tail = null;									//tail is the back of the queue (last in, last out)

	public CircularArrayQueue(int queueSize) {					//constructor for  the queue, when given a size
		capacity = queueSize;
	}

	@Override
	public int size() {
		return size;										//size is the number of elements in the queue
	}

	@Override
	public boolean isEmpty() {
		return ( head == null );							//if the first element in the queue is empty, the queue is empty, returns a boolean value true or false
	}

	@Override
	public void enqueue(E e) {								//method for adding elements to the queue

		Node newNode = new Node(e);							//create a new node with the value e (passed into method)
		if( isEmpty( ) ) {									//if the queue is empty, the added element is both the head and tail
			tail = newNode;
			head = tail;
		}
		else {											//if the queue is not empty
			tail.setNext( newNode );					//set the tails next element to the new Node
			tail = newNode;								//set the tail to the new Node
		}
		size++;									//increment the size as we add one element
	}

	@Override
	public E first() {									//returns the first value in the queue without removing it, basically like a peek for a stack
		if(isEmpty( ) ) {								//if the queue is empty, return nothing, because there is no first element
			return null;
		}
		return head.value;									//otherwise, return the value of the head Node
	}

	@Override
	public E dequeue() {								//method for removing elements from the queue
		Node oldNode = head;								//make a Node and set it equal to the head Node
		head = head.getNext( );								//set the head node to the next node after it
		if ( isEmpty ( ) ) {								//if the queue is empty, set head and tail to null
			head = null;
			tail = null;
		}
		size--;												//decrement the size as we remove one element
		return oldNode.value;									//return the value previously stored in the head Node
	}

}
