/*Author: Evan McKenzie
 * With Help From: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * 
 * This program implements the Sorter interface with the generic type K to create a 
 * in place heap sort with the help of a helper method
 */
package cs2321;

public class InPlaceHeapSort<K extends Comparable<K>> implements Sorter<K> {

	/**
	 * sort - Perform an in-place heap sort
	 * @param array - Array to sort
	 */
	@TimeComplexity("O(n lg n)")
	public void sort(K[] array) {
		int len = array.length;									//get length of passed in array
		for(int i = len / 2 - 1; i >= 0; i--) {					//divide array in two and increment backwards through elements from that halfway point
			heapsorthelper(array, len, i);						//call helper method with the original array, the length of said array, and the current index as parameters
		}
		for(int i = len - 1; i >= 0; i--) {						//go to end of array and iterate backwards through elements from there
			K temp = array[0];									//swap entries with temp holding variable
			array[0] = array[i];								//set zero index and index i equal
			array[i] = temp;									//reset element at index i with holding variable
			
			heapsorthelper(array, i, 0);						//second call to helper method with altered array, current index, and 0 as parameters
		}
	}
	
	//helper method
	void heapsorthelper(K[] array, int length, int index) {
		int largest = index;						//local variables
		int l = 2 * index + 1;						//left child location
		int r = 2 * index + 2;						//right child location
		
		if( l < length && array[l].compareTo(array[largest]) > 0) {		//compare statements using Comparable class
			largest = l;												//set largest index to the left child index
		}
		if( r < length && array[r].compareTo(array[largest]) > 0) {		//if greater than 0, array[r] is larger than array[largest]
			largest = r;												//set largest index to the right child index
		}
		if(largest != index) {					//if the largest index is changed by those two if statements, we need to rearrange some entries
			K temp = array[index];							//temp holding variable for K element
			array[index] = array[largest];					//set equal to swap
			array[largest] = temp;							//reset value at largest index
			
			heapsorthelper(array, length, largest);			//recursive call with new values for parameters
		}
	}

}
