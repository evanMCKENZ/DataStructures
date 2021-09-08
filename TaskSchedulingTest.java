/*Author: Evan McKenzie
 * 
 * Tests for the TaskScheduling problem, NumOfMachines method.
 */
package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TaskSchedulingTest {

	@Before
	public void setUp() throws Exception {					//no setup since no global variables
	}

	@Test
	public void testNumOfMachines() {
		int[][] tasks = {
				{1, 4},					//test 2D array
				{1, 3},
				{2, 5},
				{3, 7},
				{4, 7},
				{6, 9},
				{7, 8},
		};
		
		int result = TaskScheduling.NumOfMachines(tasks);				//value returned by the NumOfMachines method
		
		assertEquals(3, result, 0);				//comparison statement, difference of 0 since answers should be eaxctly the same
	}
	
	@Test
	public void testNumOfMachines2() {
		int[][] tasks = {
				{7, 8},
				{2, 11},
				{7, 8},
				{1, 7},
				{2, 3},
				{2, 7},
				{1, 9},
		};
		
		int result = TaskScheduling.NumOfMachines(tasks);
		
		assertEquals(5, result, 0);
	}
	
	@Test
	public void testNumOfMachines3() {
		int[][] tasks = {
				{0, 1},
				{0, 2},
				{0, 3},
				{1, 2},
				{2, 3},
				{3, 4},
				{0, 4},
		};
		
		int result = TaskScheduling.NumOfMachines(tasks);
		
		assertEquals(4, result, 0);
	}
	
	@Test
	public void testNumOfMachines4() {
		int[][] tasks = {
				{0, 1},
				{0, 2},
				{0, 3},
				{0, 4},
				{0, 5},
		};
		
		int result = TaskScheduling.NumOfMachines(tasks);
		
		assertEquals(5, result, 0);
	}
	
	@Test
	public void testNumOfMachines5() {
		int[][] tasks = {
				{0, 1},
				{1, 2},
				{2, 3},
				{3, 4},
				{4, 5},
				{5, 6},
				{6, 7},
		};
		
		int result = TaskScheduling.NumOfMachines(tasks);
		
		assertEquals(1, result, 0);
	}
	
	@Test
	public void testNumOfMachines6() {
		int[][] tasks = {
				{0, 1},
				{0, 1},
				{0, 2},
				{0, 2},
				{1, 3},
				{1, 4},
				{2, 5},
				{3, 5},
		};
		
		int result = TaskScheduling.NumOfMachines(tasks);
		
		assertEquals(4, result, 0);
	}
}
