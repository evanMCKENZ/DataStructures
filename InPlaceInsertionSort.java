/*Author: Evan McKenzie
 * With Help From: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * 
 * This program implements the Sorter interface with the generic type K to create a 
 * in place insertion sort 
 */
package cs2321;

public class InPlaceInsertionSort<K extends Comparable<K>> implements Sorter<K> {

	/**
	 * sort - Perform an in-place insertion sort
	 * @param array - Array to sort
	 */
	@TimeComplexity("O(n^2)")
	public void sort(K[] array) {
		int len = array.length;						//get the length of the passed in array
		for(int i = 1; i < len; i++) {					//start at index 1 and go to the end of the array
			K rem = array[i];							//temporary value to remember
			int j = i - 1;								//temporary index value to remember
			while(j >= 0 && (array[j].compareTo(rem) > 0)) {				//comparison between element at index i and the element before it at index j
				array[j + 1] =  array[j];							//swap the two entries if needed		
				j = j - 1;										//decrement the j index
			}
			array[j + 1] = rem;			//set j + 1 to the remembered value from beginning
		}
	}
}
