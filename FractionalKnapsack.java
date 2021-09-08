/*Author: Evan McKenzie
 * 
 * This program is designed to solve the FractionalKnapsack problem using the Greedy method, while
 * using a Priority Queue as the data structure with which we can evaluate and manipulate the entries.
 * This class has one method, MaximumValue, that takes in a 2D array of ints representing the weight and benefit
 * of different "packages" and another int argument representing the knapsack maximum capacity. The method uses a HeapPQ
 * created in a previous project to store both the value ( benefit / weight ) and the weight of each individual element,
 * then uses logic to determine the maximum value of the benefit used to fill the knapsack completely.
 */
package cs2321;


/**
 * @author:
 *
 */
public class FractionalKnapsack {
	/**
	 * Goal: Choose items with maximum total benefit but with weight at most W.
	 *       You are allowed to take fractional amounts from items.
	 *       
	 * @param items items[i][0] is weight for item i
	 *              items[i][1] is benefit for item i
	 * @param knapsackWeight
	 * @return The maximum total benefit. Please use double type operation. For example 5/2 = 2.5
	 * 		 
	 */
									//weight,benefit
	public static double MaximumValue(int[][] items, int knapsackWeight) {
		MaxComparator comp = new MaxComparator<>();									//instantiate new comparator (need maxcomparator because we want max value at first index)
		HeapPQ <Double, Integer> knapsack = new HeapPQ<Double, Integer>(comp);			//instantiate new HeapPQ with comparator already created
		for( int i = 0; i < items.length; i++) {									//insert the elements from the 2D array into our HeapPQ
			double value = ((double)(items[i][1])) / (items[i][0]);				//cast to double to be safe (index 1 / index 0 gives the value, this is benefit / weight)
			knapsack.insert(value, items[i][0]);							//the insert method contains a reference to upheap, which uses the comparator to organize the elements using the maxcomparator
		}
		
		
		double maxvalue = 0;					//this is our returned value counter
		while(knapsackWeight > 0 && !(knapsack.isEmpty())) {				//use knapsackWeight as a decrementing counter 
			int temp = knapsack.min().getValue();						//get the weight of the element at the first index ( 0 )
			if(knapsackWeight - temp >= 0) {					//if the total knapsackWeight minus the value we just got is greater than or equal to zero we will be taking all of that element
				knapsackWeight = knapsackWeight - temp;				//decrement the knapWeight counter
				double tmp = knapsack.min().getKey();					//get the value of the element at index 0
				double max = temp * tmp;							//get the benefit of this element by multiplying the value and weight of the object
				maxvalue = maxvalue + max;					//increment the returning counter by all of the benefit
				knapsack.removeMin();				//remove the first element since we are done with it, letting the next element move forward
			}
			else {															//if the knapsackWeight - temp is less than 0, we will be using only some of that element
				double temporary = ((double) knapsackWeight )/ temp ;					//get the ratio of the total weight to the weight of the current element
				knapsackWeight = knapsackWeight - temp;								//decrement the knapsackWeight counter, making it negative and ending the while loop
				double tmp2 = knapsack.min().getKey();							//get the value of the current element
				double wrong = temp * tmp2;												//get the benefit of the element by multiplying the value and the weight
				double max = temporary * wrong;									//multiply the benefit by the ratio found earlier to find how much of the benefit will actually be used
				maxvalue = maxvalue + max;								//add this value to the total counter
				knapsack.removeMin();								//this is kind of pointless, since we wont be looking at another element anyway, but for the sake of understandability I left it in
			}
		}
		return maxvalue;			//return the returned value counter
	}
}
