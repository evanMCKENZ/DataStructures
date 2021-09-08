/*Author: Evan McKenzie
 * 
 * This program is designed to solve the TaskScheduling problem using the Greedy method, while
 * using a Priority Queue as the data structure with which we can evaluate and manipulate the entries.
 * This class has one method, NumOfMachines, that takes in a 2D array of ints representing a series of tasks, where
 * the first index in each row represents a start time and the second represents an end time. The method uses a HeapPQ
 * created in a previous project to store both the start and end time, as well as an ArrayList that 
 * represents the machines current end times. The program then uses logic comparisons to find the minimum number
 * of machines needed to cover all the tasks in the 2D array
 */
package cs2321;

import net.datastructures.*;

public class TaskScheduling {
	/**
	 * Goal: Perform all the tasks using a minimum number of machines. 
	 * 
	 *       
	 * @param tasks tasks[i][0] is start time for task i
	 *              tasks[i][1] is end time for task i
	 * @return The minimum number or machines
	 */
   public static int NumOfMachines(int[][] tasks) {
	  HeapPQ <Integer, Integer> times = new HeapPQ<>();					//create a new HeapPQ USING THE DEFAULT COMPARATOR
	  ArrayList <Integer> array1 = new ArrayList<>();							//create an array list to hold all the machines (which is really only holding the end times of each machine
	  for( int i = 0; i < tasks.length; i++) {						//insert the tasks into the PQ
			times.insert(tasks[i][0], tasks[i][1]);					//insert sorts from smallest to largest due to the default comparator and the call to up heap
	  }
	  
	  while(!times.isEmpty()) {										//do this for all entries in the PQ
		  Entry<Integer, Integer> entry = times.removeMin();			//remove the first element from the PQ and create a new Entry for it
		  
		  boolean avialiable = false;					//boolean for creating a new machine, needs to be false for the first entry as we need to make a new machine right away


		  for(int j = 0; j < array1.size(); j++) {					//for every machine in the array list
			  int temp = array1.get(j);								//get the integer stored in it (which, again, is just the current end time for that machine)
			  //Entry<Integer, Integer> entry1 = times.min();
			  int starttime = (int)entry.getKey();						//then get the start time of the first element in the HeapPQ
			  if(starttime >= temp) {								//if it is greater than or equal to the end time of the machine we are currently on, we do not need to make a new machine
				  avialiable = true;									//set the boolean value to true
				  int tmp = (int)entry.getValue();						//get the end time of the first element in the HeapPQ (same element)
				  array1.set(j, tmp);								//reset the end time of the current machine (we basically just added this task to the end of the current machine)
				  break;								//go back to start of while
			  }
		  }
		  
		  if(avialiable == false ) {						//however if the start time is less than the end time and the boolean does not change, we need to make a new machine
			  int endtime = (int) entry.getValue();						//get the end time of the current entry in the PQ
			  array1.addLast(endtime);							//and add this time to the array list, representing a new machine
		  }
	  }
	  
	  return array1.size();					//the size of the array is the number of machines needed to complete the tasks
   }
}
