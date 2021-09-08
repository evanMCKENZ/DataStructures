/*Author: Evan McKenzie
 * With Help From: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * 
 * This program implements the Sorter interface with the generic type E to create a 
 * merge sort with the help of two helper methods
 */
package cs2321;

public class MergeSort<E extends Comparable<E>> implements Sorter<E> {

	@TimeComplexity("O(n lg n)")
	public void sort(E[] array) {
		int zero = 0;					//zero index variable
		int finall = array.length - 1;		//end index variable
		
		mergesorter(array, zero, finall);			//call first helper method with defined start and end indexes
	}
	
	//helper method number 1
	void mergesorter(E[] array, int l, int r) {
		if(l < r) {
			int middle = (l + r) / 2;				//given start and end indexes, find the middle index
			
			mergesorter(array, l, middle);			//recursive call for the first half of the array
			mergesorter(array, middle + 1, r);		//recursive call for the second half of the array
			
			mergesorthelper(array, l, middle, r);			//call second helper method with all four parameters
		}
	}
	
	//helper method number 2
	void mergesorthelper(E[] array, int l, int m, int r) {
		int len1 = m - l + 1;			//length for the first array
		int len2 = r - m;				//length for the second array
		
		E one[] = (E[]) new Comparable[len1];							//create two empty arrays with the found lengths
		E two[] = (E[]) new Comparable[len2];					//safe cast
		
		for(int i = 0; i < len1; i++) {				//copy values over from original array to first created array
			one[i] = array[l + i];					//l + i corrects offset of indexes
		}
		for(int j = 0; j < len2; j++) {				//copy values over from original array to second created array			
			two[j] = array[m + 1 + j];				//m + 1 + j corrects index offset
		}
		
		int i = 0, j = 0;					//local variables
		int k = l;							//k equals zero index still	for the original array		
		
		while(i < len1 && j < len2) {									//while these zero based variables are less than the length of their respective arrays
			if(one[i].compareTo(two[j]) < 0 || one[i].compareTo(two[j]) == 0) {				//compare values from one array to the other with the Comparable class compareTo method
				array[k] = one[i];											//if the value from one is greater than or equal to the value from two, overwrite the value at k in the original array with this value
				i++;							//and increment i
			}
			else {
				array[k] = two[j];				//otherwise, overwrite the original array with the value in the second array
				j++;										//and increment j instead
			}
			k++;				//increment the k index of the original array
		}
		
		while(i < len1) {			//these two while loops cover any missed values
			array[k] = one[i];			//set the k index element in the original array to the one left over the first copy
			i++;						//increment i
			k++;						//increment k
		}
		while(j < len2) {			//same thing for the second array if it has any values left in it
			array[k] = two[j];			//set the k index element of the original array equal to the value left in the second array
			j++;						//increment j
			k++;						//increment k
		}
	}
}

