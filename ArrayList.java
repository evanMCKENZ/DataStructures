/*Author: Evan McKenzie
 * With Help From: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * 
 * This program implements the List interface with abstract data type E and overrides 
 * all methods from that class as well as implementing helper methods like checkIndex()
 * and capacity(). This program also introduces an iterator as well as addFirst(), addLast()
 * removeLast(), and removeFirst() methods
 */

package cs2321;

import java.util.Iterator;

import net.datastructures.List;

public class ArrayList<E> implements List<E> {

	public static final int CAPACITY = 16;						//static default capacity
	private E[] data;											//array of abstract type E objects
	private int size = 0;										//initial size of array (also doubles as number of elements

	public ArrayList() {									//default constructor
		this(CAPACITY);
	}

	public ArrayList(int capacity) {						//constructor when a capacity is given (used when array needs to grow)
		data = (E[]) new Object[capacity];
	}


	private class ArrayIterator implements Iterator<E> {					//nested ArrayIterator class
		private int j = 0;													//global variables for iterator
		private boolean removable = false;


		public boolean hasNext() { return j < size; }						//implement all methods from Iterator interface

		public E next() throws NullPointerException{
			if(j == size) throw new NullPointerException ( "No next element" );
			removable = true;
			return data[j++];
		}

		public void remove() throws IllegalStateException {
			if(!removable) throw new IllegalStateException( "nothing to remove ");
			ArrayList.this.remove(j-1);
			j--;
			removable = false;
		}
	}

	@Override
	public int size() {
		return size;						//returns size (number of elements in the array) NOT CAPACITY
	}

	@Override
	public boolean isEmpty() {
		return size == 0;								//return a boolean true or false value whether the array is empty
	}

	@Override
	public E get(int i) throws IndexOutOfBoundsException {
		checkIndex(i, size);													//checks whether the passed in index is valid (all methods that manipulate the array have this)
		return data[i];												//return element at specified index
	}

	@Override
	public E set(int i, E e) throws IndexOutOfBoundsException {
		checkIndex(i, size);
		E tmp = data[i];											//temporary variable of type E that represents what was in the given index
		data[i] = e;														//set given index to new value passed into method
		return tmp;													//return old value
	}

	@Override
	public void add(int i, E e) throws IndexOutOfBoundsException, IllegalStateException {
		checkIndex(i, size+1);
		if(size == data.length) {									//if number of elements in the array is equal to the array length IT IS FULL
			resize(2 * data.length);								//if array is full we need to double the size, so call resize helper method
		}
		for(int j= size-1; j>= i; j--) {							//shift all elements to the right one space
			data[j+1]= data[j];
		}
		data[i] = e;								//set given index to passed in value
		size++;										//increment size as we add one element

	}

	@Override
	public E remove(int i) throws IndexOutOfBoundsException {
		checkIndex(i, size);
		E tmp = data[i];											//temporary value for what was stored in the given index
		for(int j =i; j < size -1; j++) {							//shift all elements to the right one space
			data[j] = data[j+1];
		}
		data[size-1] = null;									//set the last element to null
		size--;													//decrement size as we remove one element
		return tmp;												//return the removed value
	}


	@Override
	public Iterator<E> iterator() {
		return new ArrayIterator();								//iterator constructor
	}

	public void addFirst(E e)  {					//add a element to the front of the array
		add(0, e);										//since the front of the array is index 0, we can just call the add() method and it will do all the shifting and inserting
	}

	public void addLast(E e)  {						//add element to the end of the array
		add(size, e);								//since size represents the number of elements added, we can add the last element at index size with the add() method
	}

	public E removeFirst() throws IndexOutOfBoundsException {				//remove an element from the front of the array
		E returner = remove(0);												//call remove() method at index 0
		return returner;													//return the value that was at the front of the array before removal
	}

	public E removeLast() throws IndexOutOfBoundsException {							//remove an element from the end of the array
		E returner = remove(size-1);													//since size is end of the array + 1, we just remove size-1 with the remove() method
		return returner;															//return removed element
	}

	// Return the capacity of array, not the number of elements.
	// Notes: The initial capacity is 16. When the array is full, the array should be doubled. 
	public int capacity() {
		return data.length;											//the capacity of the array is also its length
	}

	protected void checkIndex ( int i, int n) throws IndexOutOfBoundsException {								//check whether given index is valid
		if( i < 0 || i >= n ) {																					//if the index is less than 0 or greater than the size of the array, it is not valid
			throw new IndexOutOfBoundsException(" Illegal Index: " + i );
		}
	}

	protected void resize(int capacity) {										//method to double the size of the array when it becomes full
		E[] temporary = (E[]) new Object[capacity];										//create new temporary array that has doubled capacity as compared to the original one
		for(int k = 0; k < size; k++) {											//copy all elements from original array into temporary one
			temporary[k] = data[k];
		}
		data = temporary;							//set old array equal to new one, thus doubled the size of original array
	}

}
