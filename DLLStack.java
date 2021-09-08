/*Author: Evan McKenzie
 * 
 * This program implements the Stack interface with abstract data type E and overrides 
 * all methods from that class, as well as introducing two constructors for initialization
 */


package cs2321;

import net.datastructures.Stack;

public class DLLStack<E> implements Stack<E> {


	public static final int CAPACITY = 1000;						//initial capacity
	private E[] data;												//array of E type element that the stack will use
	private int t = -1;												//index variable

	public DLLStack() { this(CAPACITY); }							//default constructor
	public DLLStack (int capacity) {								//constructor to be used when a capacity is passed in
		data = (E[ ]) new Object[capacity];									//create a new array of the given capacity
	}

	@Override
	public int size() {								//returns the size of the stack
		return (t+1);										//this line maintains that the stack can't have negative size (minimum zero)
	}

	@Override
	public boolean isEmpty() {						//returns a boolean of whether the stack is empty or not
		return (t == -1);								//if t == -1, the stack is empty
	}

	@Override
	public void push(E e) {																	//method to add new element to the stack
		if( size() == data.length ) throw new IllegalStateException("Stack is full");			//check to make sure the stack is not already full
		data[++t] = e;																		//have to add one to t here to make sure it is a valid index (not -1)

	}

	@Override
	public E top() {										//returns the first element in the stack without removing it
		if(isEmpty() ) return null;												//make sure the stack is not already empty
		return data[t];										//return the element that is currently at the top of the stack (index t)
	}

	@Override
	public E pop() {									//returns the first element by removing it from the stack
		if(isEmpty()) return null;							//check to make sure the stack is not already empty
		E answer = data[t];									//the return variable is saved with the value of the removed element
		data[t] = null;											//set the position to null
		t--;													//decrement the t index
		return answer;										//return the return variable
	}

}
