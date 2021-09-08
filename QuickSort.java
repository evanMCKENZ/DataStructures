/*Author: Evan McKenzie
 * With Help From: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * 
 * This program implements the Sorter interface with the generic type E to create a 
 * quick sort with the help of a helper method
 */
package cs2321;

public class QuickSort<E extends Comparable<E>> implements Sorter<E> {

	@TimeComplexity("O(n^2)")
	@TimeComplexityExpected("O(n lg n)")
	public void sort(E[] array) {
		int a = 0;						//zero index variable
		int b = array.length - 1;		//end index variable
		quicksorthelper(array, a, b);			//call to helper method

	}
	void quicksorthelper(E[] array, int low, int high) {		
		if(array == null || array.length == 0) {			//make sure there are actually entries to manipulate (non empty array check)
			return;											//exit helper method if true
		}
		if(low >= high) {		//if low equals high or is greater than high, we only have 1 entry or the indexes were passed in wrong
			return;									//exit helper method
		}
		
		int pivotpoint = low + (high - low) / 2;			//get the pivot point in the array 
		E pivot = array[pivotpoint];						//store the E type element that is stored at the pivot point index
		
		int i = low, j = high;						//local variables
		while(i <= j) {									//while i is less than or equal to j, or while the two indexes have not met in the middle yet
			while(array[i].compareTo(pivot) < 0) {				//compare statement using Comparable class
				i++;										//iterate UPWARDS for i
			}
			while(array[j].compareTo(pivot) > 0) {			//need the values after the pivot point to be greater than the value at that point, so > 0 is needed
				j--;										//iterate DOWNWARDS for j
			}
			if(i <= j) {				//time to swap entries
				E temp = array[i];			//temp holding variable
				array[i] = array[j];			//set the two indexes equal
				array[j] = temp;				//then reset the j index element to the holding variable
				i++;						//increment i
				j--;						//decrement j
			}
		}
		if(low < j) {				//if the final value of j is still less than the initial low index, we missed some elements
			quicksorthelper(array, low, j);				//recursive call for any missed values
		}
		if(high > i) {								//if the final value of i is still less than the initial high index
			quicksorthelper(array, i, high);			//recursive call for any missed values
		}
	}
}
