/*Author: Evan McKenzie
 * With Help From: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * 
 * This program implements the Sorter interface with the generic type K to create a 
 * in place selection sort with the help of a helper method
 */
package cs2321;

public class InPlaceSelectionSort<K extends Comparable<K>> implements Sorter<K> {

	/**
	 * sort - Perform an in-place selection sort
	 * @param array - Array to sort
	 */
	@TimeComplexity("O(n^2)")
	public void sort(K[] array) {
		int len = array.length;							//get length of array
		for(int i = 0; i < len - 1; i++) {					//for every element of the passed in array
			int index = i;									//temporary index value
			for(int j = i + 1; j < len; j++) {					//compare element at index i + 1 to the element at index i
				if(array[j].compareTo(array[index]) < 0) {					//use compareTo method from Comparable interface
					index = j;													//if the compareTo returns < 0, index j element is less than index i element
				}
			}
			//swap the two elements
			K temp = array[index];						//temp variable for element at index 
			array[index] = array[i];					//set index element equal to element at index i		
			array[i] = temp;							//reset the value at index i
		}
	}

}
