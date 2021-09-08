/*Author: Evan McKenzie
 * With Help From: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * 
 * This program implements the AdaptablePriorityQueue interface, which in turn extends the
 * PriorityQueue interface, then uses a heap to implement the methods to support both types of 
 * Priority Queues. The heap is created using an ArrayList, which uses the methods defined in the ArrayList
 * class, and two constructors are provided for the Priority Queue, one utilizing the DefaultConstructor
 * defined in the DefaultConstructor class, and another that takes in a user-defined comparator object
 * Time Complexity is defined for all methods, as well as justification for all nontrivial methods.
 */

package cs2321;

import java.util.Comparator;

import net.datastructures.*;
/**
 * A Adaptable PriorityQueue based on an heap. 
 * 
 * Course: CS2321 Section ALL
 * Assignment: #3
 * @author
 */

public class HeapPQ<K,V> implements AdaptablePriorityQueue<K,V> {

	ArrayList <PQEntry<K,V>> heap = new ArrayList<>();				//create new insatnce of arraylist that we copied over to store all entries in
	Comparator comp = new DefaultComparator<>();				//global default comparator

	/* use default comparator, see DefaultComparator.java */
	public HeapPQ() {
		this(new DefaultComparator<K>());				//if no comparator provided, instantiate a new DefaulComparator
	}

	/* use specified comparator */
	public HeapPQ(Comparator<K> c) {
		super();
		comp = c;						//set the default comparator to the provided one
	}

	//nested Entry class
	protected static class PQEntry<K,V> implements Entry<K,V>{
		private K k;			//key with type K
		private V v;			//value with type V
		private int i;				//
		public PQEntry(K key, V value, int index) {		//constructor for default PQ Entry
			k = key;			//assign variables to passed in parameters
			v = value;
			i = index;
		}

		public K getKey() { return k; }			//public methods pertaining to the PQEntry nested class
		public V getValue() { return v; }
		public int getIndex() { return i; }

		protected void setKey(K key) { k = key; }			//protected methods pertaining to the PQEntry nested class
		protected void setValue(V value) { v = value; }
		protected void setIndex(int index) { i = index; }
	}

	//protected global variables
	protected int parent(int j) { return (j - 1) / 2; }				//the arithmetic in the return statement is how the heap is stored in the arraylist
	protected int left(int j) { return 2 * j + 1; }
	protected int right (int j) { return 2 * j + 2; }
	protected boolean hasLeft(int j) { return left(j) < heap.size(); }
	protected boolean hasRight(int j) { return right(j) < heap.size(); }

	//helper method for swapping two entries
	@TimeComplexity("O(1)")
	protected void swap(int i, int j) {
		PQEntry<K, V> temp = heap.get(i);			//create temporary PQEntry and set it equal to the PQEntry at the given index
		heap.set(i, heap.get(j));			//reset value at i					
		heap.get(i).setIndex(i);			//update index at i		
		heap.set(j, temp);					//set value at j equal to original i value
		heap.get(j).setIndex(j);			//update j index
	}

	//helper method for comparing two entries
	@TimeComplexity("O(1)")
	protected int compare(Entry<K,V> a, Entry<K,V> b) {
		return comp.compare(a.getKey(), b.getKey());			//returns a integer evaluation of whether the entries are equal (-1, 0, 1)
	}

	/* 
	 * Return the data array that is used to store entries  
	 * This method is purely for testing purpose of auto-grader
	 */
	@TimeComplexity("O(1)")
	Object[] data() {
		Object[] data = new Object[heap.size()];
		for(int i = 0; i < heap.size(); i++) {
			data[i] = heap.get(i);
		}
		return data;				//use the data variable from the arraylist class to return the array storing the Objects
	}

	/**
	 * The entry should be bubbled up to its appropriate position 
	 * @param int move the entry at index j higher if necessary, to restore the heap property
	 */
	@TimeComplexity("O(n)")
	public void upheap(int j){
		/*
		 * TCJ
		 * the if statement inside the while loop executes n times, so O(n)
		 */
		while(j > 0) {					//loop while j greater than 0
			int p = parent(j);								//the integer i represents the index of the parent entry to the entry at index j
			if(compare(heap.get(j), heap.get(p)) >= 0) {			//compare the two entries, if the compare statement returns a positive number or zero, break out if the if statement
				break;
			}
			swap(j, p);				//call swap to switch the two entries
			j = p;					//set the two entries equal
		}
	}

	/**
	 * The entry should be bubbled down to its appropriate position 
	 * @param int move the entry at index j lower if necessary, to restore the heap property
	 */
	@TimeComplexity("O(n)")
	public void downheap(int j){
		/*
		 * TCJ
		 * again, same as upheap, we have if statements inside of a while loop, the if statements execute n times so O(n)
		 */
		while(hasLeft(j)) {							//while there is a left index, basically is the boolean returned by the hasLeft true
			int leftIndex = left(j);				//leftIndex represents the index of the left child of whatever entry is at the j index
			int smallChildren = leftIndex;				//create an integer representation of the index of the leftIndex
			if(hasRight(j)) {					//if there is also a right index, do these steps
				int rightIndex = right(j);							//create a variable right index and set it equal to the integer index of the right child of the j index
				if(compare(heap.get(leftIndex), heap.get(rightIndex)) > 0) {				//compare the entries at the left and rightIndex
					smallChildren = rightIndex;					//if the comparison returns a number greater than 0, then smallChildren is equal to rightIndex
				}
			}
			if(compare(heap.get(smallChildren), heap.get(j)) >= 0) {		//compare 
				break;
			}
			swap(j, smallChildren);				//swap the given j index entry and the found smallChildren index entry
			j = smallChildren;				//set the two entries equal to one another
		}
	}

	@Override
	@TimeComplexity("O(1)")
	public int size() {
		return heap.size();			//call the size method from the arraylist class to find the size of the list, only runs 1 time
	}

	@Override
	@TimeComplexity("O(1)")
	public boolean isEmpty() {
		return size() == 0;			//evaluate the size method to see if there are zero entries in the arraylist, runs 1 time
	}

	//helper method
	@TimeComplexity("O(1)")
	protected boolean checkKey(K key) throws IllegalArgumentException {
		try {
			return (comp.compare(key, key) == 0);						//compare the key to make sure it is validate
		}
		catch (ClassCastException e) {											//catch block for casting exception
			throw new IllegalArgumentException("Incompatible key");
		}
	}

	@Override
	@TimeComplexity("O(lg n)")
	@TimeComplexityAmortized("O(1)")
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		/*
		 * TCJ
		 * everything in this method runs once, but calling another method within this one makes it a O(log n) TC
		 */
		checkKey(key);															//call method to validate key
		PQEntry<K,V> newest = new PQEntry<>(key, value, heap.size());			//create new PQEntry using the given parameters
		heap.addLast(newest);								//use the addLast method to add the new Entry to the end of the list
		newest.i = heap.size() - 1;							//set the index for the new Entry to the end of the list
		upheap(heap.size() - 1);							//upheap to reset all indexes for entries already in the list
		return newest;							//return the new Entry
	}

	@Override
	@TimeComplexity("O(1)")
	public Entry<K, V> min() {
		if(heap.isEmpty()) {
			return null;						//if list is empty, there is no minimum so return null
		}
		return heap.get(0);							//the minimum value is stored at the first index in the list when it is updated with up or downheap, so return the 0 index entry
	}

	@Override
	@TimeComplexity("O(lg n)")
	public Entry<K, V> removeMin() {
		/*
		 * TCJ
		 * this method is O(log n) because when the remove method is called, it is an unsorted list (minimum at the end), and requires calling the downheap method
		 */
		if(heap.isEmpty()) {
			return null;							//again, if the list is empty, there is no minimum to remove
		}
		PQEntry<K,V> answer = heap.get(0);				//create a new PQEntry of the zero index entry, which is the minimum in a sorted heap
		swap(0, heap.size() - 1);						//swap the minimum to the end of the list
		heap.remove(heap.size() -1);					//remove the last index, which is where the minimum is now stored
		downheap(0);									//downheap to reset all indexes
		return answer;									//return the removed entry
	}

	//helper method
	@TimeComplexity("O(1)")
	protected PQEntry<K,V> validate(Entry<K,V> entry) throws IllegalArgumentException{
		if(!(entry instanceof Entry)) {												//if the given entry is not of the correct type
			throw new IllegalArgumentException("Invalid Entry");						//throw a new Exception
		}
		PQEntry<K,V> locator = (PQEntry<K,V>) entry;				//safe cast
		int j = locator.getIndex();				//get the index of the given entry and save the index to j
		if(j >= heap.size() || heap.get(j) != locator) {				//if the found index is greater than the size of the arraylist, or the entry at that index doesnt match
			throw new IllegalArgumentException("Invalid Entry");			//throw a new Exception
		}
		return locator;				//return the original entry
	}



	@Override
	@TimeComplexity("O(lg n)")
	public void remove(Entry<K, V> entry) throws IllegalArgumentException {
		/*
		 * TCJ
		 * this method requires calls to method upheap AND downheap, so the complexity is O(log n)
		 */
		PQEntry<K,V> locator = validate(entry);							//call the validate method to validate the given entry
		int j = locator.getIndex();										//get the index of the given entry
		if(j == heap.size() - 1) {										//if the found index equals the list size - 1, we are removing the last element in the list and dont need to up or downheap
			heap.remove(heap.size() - 1);
		}
		else {													//otherwise, we will need to up and downheap to reset elements after removal
			swap(j, heap.size() - 1);									//swap the entry to be removed with the one at the end of the list
			heap.remove(heap.size() - 1);								//remove the end of the list element, which is our wanted element					
			upheap(j);										//restore order after removal
			downheap(j);									//restore order after removal
		}
	}

	@Override
	@TimeComplexity("O(lg n)")
	public void replaceKey(Entry<K, V> entry, K key) throws IllegalArgumentException {
		/*
		 * TCJ
		 * this method has calls to both upheap and downheap again so the complexity is O(log n)
		 */
		PQEntry<K,V> locator = validate(entry);						//validate entry using the helper method
		int j = locator.getIndex();									//create integer representation of the index of the given entry
		checkKey(key);												//validate key using the helper method
		locator.setKey(key);										//set the entries key to the new parameter passed in
		upheap(j);													//reset the list
		downheap(j);												//restore the order
	}

	@Override
	@TimeComplexity("O(1)")
	public void replaceValue(Entry<K, V> entry, V value) throws IllegalArgumentException {
		PQEntry<K,V> locator = validate(entry);						//validate entry
		locator.setValue(value);									//set the new value of the given entry

	}
}
