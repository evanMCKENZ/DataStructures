/*Author: Evan McKenzie
 * 
 * Tests for the FractionalKnapsack problem, MaximumValue method.
 */
package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.datastructures.Entry;

public class FractionalKnapsackTest {
	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMaximumValue() {
		int[][] items = {
				{4, 12},						//test 2D array
				{8, 32},
				{2, 40},
				{6, 30},
				{1, 50},
		};
		int knapsackWeight = 10;							//knapsackWeight passed in
		double maxValue = 124;										//expected value
		double result = FractionalKnapsack.MaximumValue(items, knapsackWeight);					//value the MaximumValue method returns
		
		assertEquals((Double) maxValue, result, 0);					//difference of 0 because we expect the answers to be exactly equal, no variance
	}

	@Test
	public void testMaximumValue2() {
		int[][] items = {
				{1, 16},
				{1, 32},
				{1, 40},
				{1, 30},
				{1, 50},
		};
		int knapsackWeight = 5;
		double maxValue = 168;
		double result = FractionalKnapsack.MaximumValue(items, knapsackWeight);
		
		assertEquals((Double) maxValue, result, 0);
	}

	
	@Test
	public void testMaximumValue3() {
		int[][] items = {
				{3, 15},
				{20, 32},
				{16, 40},
				{1, 30},
				{40, 50},
		};
		int knapsackWeight = 12;
		double maxValue = 65;
		double result = FractionalKnapsack.MaximumValue(items, knapsackWeight);
		
		assertEquals((Double) maxValue, result, 0);
	}
	
	@Test
	public void testMaximumValue4() {
		int[][] items = {
				{3, 15},
				{20, 32},
				{15, 45},
				{1, 30},
				{40, 50},
		};
		int knapsackWeight = 7;
		double maxValue = 54;
		double result = FractionalKnapsack.MaximumValue(items, knapsackWeight);
		
		assertEquals((Double) maxValue, result, 0);
	}
	
	@Test
	public void testMaximumValue5() {
		int[][] items = {
				{3, 21},
				{20, 32},
				{15, 45},
				{1, 30},
				{3, 50},
		};
		int knapsackWeight = 7;
		double maxValue = 101;
		double result = FractionalKnapsack.MaximumValue(items, knapsackWeight);
		
		assertEquals((Double) maxValue, result, 0);
	}
}
