package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.datastructures.AdaptablePriorityQueue;
import net.datastructures.Entry;

public class HeapPQTest {

	HeapPQ<Integer, String> heappq;
	
	@Before
	public void setUp() throws Exception {
		heappq = new HeapPQ<Integer, String>();
	}
	
	
	@Test
	public void testInsert1() {
		/*	
		 *       3
		 *     /    \  
		 *   5       6      
		 *  /      
		 * 8       
		 */
		heappq.insert(3 , "cat");
		heappq.insert(5, "apple");
		heappq.insert(6, "turtle");
		heappq.insert(8, "elephant");
		Object[] data = heappq.data();
		Integer[] expected = {3,5,6,8};
		
		assertEquals("size should be 4", 4, heappq.size());
		for (int i=0;i<heappq.size();i++) {
			assertEquals(i, expected[i], ((Entry<Integer,String>)data[i]).getKey());
		}
	}

	@Test
	public void testInsert2() {
		/*
		 * 			
		 *       3
		 *     /    \  
		 *   5       6      
		 *  /      
		 * 8       
		 */
		heappq.insert(3 , "cat");
		heappq.insert(5, "apple");
		heappq.insert(6, "turtle");
		heappq.insert(8, "elephant");
		 
		/* 		
		 * After insert a new value 1.
		 * 
		 *       1
		 *     /    \  
		 *   3       6      
		 *  /  \    
		 * 8   5    
		 */
		heappq.insert(1, "A");
		
		
		Object[] data = heappq.data();
		Integer[] expected = {1,3,6,8,5};
		
		assertEquals("size should be 4", 5, heappq.size());
		for (int i=0;i<heappq.size();i++) {
			assertEquals(i, expected[i], ((Entry<Integer,String>)data[i]).getKey());
		}
	}
	
	@Test
	public void testRemoveMin() {
		/*
		 *       1
		 *     /    \  
		 *   3       4       
		 *  /  \    /
		 * 8   6  9  
		 */
		
		// after remove min 
		/*
		 *       3
		 *     /    \  
		 *   6       4       
		 *  /  \    
		 * 8    9    
		 */
		 
		heappq.insert(1, "A");
		heappq.insert(3 , "cat");
		heappq.insert(4, "pear");
		heappq.insert(8, "elephant");
		heappq.insert(6, "turtle");
		heappq.insert(9, "pineapple");
	

		Entry<Integer, String> e = heappq.removeMin();
		assertEquals("min should be 1", (Integer)1, (Integer)e.getKey());
		
		Object[] data = heappq.data();
		Integer[] expected = {3,6,4,8,9};
		
		for (int i=0;i<heappq.size();i++) {
			assertEquals(i, expected[i], ((Entry<Integer,String>)data[i]).getKey());
		}
		

	}

	@Test
	public void testRemoveMinThenReplace2() {
		/*
		 *       1
		 *     /   \ 
		 *   4      3
		 */
		
		heappq.insert(1, "A");
		heappq.insert(4, "pear");
		Entry<Integer,String> e=heappq.insert(3 , "cat");
	
		
		assertEquals((Integer)1, heappq.removeMin().getKey());
		heappq.replaceKey(e,5);
	}



	@Test
	public void testRemoveThenReplace2() {
		/*
		 *       2
		 *     /   \ 
		 *   4      3
		 */
		
		heappq.insert(2, "A");
		Entry<Integer,String> e1= heappq.insert(4, "pear");
		Entry<Integer,String> e2= heappq.insert(3 , "cat");
	
		// remove 4
		/*
		 *       2
		 *     /    
		 *   3      
		 */
		heappq.remove(e1);
		
		// remove 3 with 1
		/*
		 *       1
		 *     /    
		 *   2      
		 */
		heappq.replaceKey(e2,1);
		
	
		assertEquals((Integer)1, heappq.removeMin().getKey());
	
	}

	@Test
	public void testIsEmpty1() {
		assertEquals(true, heappq.isEmpty());
	}

 
	@Test
	public void testIsEmpty2() {
		heappq.insert(1, "A");
		assertEquals(false, heappq.isEmpty());
	}
	
	@Test
	public void testMin() {
		heappq.insert(3 , "cat");
		heappq.insert(5, "apple");
		heappq.insert(6, "turtle");
		heappq.insert(8, "elephant");
		heappq.insert(1, "A");
		assertEquals((Integer)1,heappq.min().getKey());
		
		assertEquals("A",heappq.min().getValue());
	}


	@Test
	public void testRemove() {
		/*
		 *       1
		 *     /    \  
		 *   3       4       
		 *  /  \    /
		 * 8   6  9  
		 */
		heappq.insert(1, "A");
		Entry<Integer,String> e= heappq.insert(3 , "cat");
		heappq.insert(4, "pear");
		heappq.insert(8, "elephant");
		heappq.insert(6, "turtle");
		heappq.insert(9, "pineapple");
		
		
		//after remove 3
		/*
		 *       1
		 *     /   \  
		 *   6       4       
		 *  /  \    
		 * 8   9    
		 */
		heappq.remove(e);
		
		
		Integer[] expected = {1,6,4,8,9};
		
		Object[] data = heappq.data();
		for (int i=0;i<heappq.size();i++) {
			assertEquals(i, expected[i], ((Entry<Integer,String>)data[i]).getKey());
		}
		
	}

	@Test
	public void testReplaceKey1() {
		/*
		 *       1
		 *     /    \  
		 *   3       4       
		 *  /  \    /
		 * 8   6  9  
		 */
		heappq.insert(1, "A");
		heappq.insert(3 , "cat");
		heappq.insert(4, "pear");
		Entry<Integer,String> e = heappq.insert(8, "elephant");
		heappq.insert(6, "turtle");
		heappq.insert(9, "pineapple");
		
		
		//after replace 8 with 2
		/*
		 *       1
		 *     /   \  
		 *   2       4       
		 *  /  \    /
		 * 3    6  9
		 */
		heappq.replaceKey(e,2);
		
		
		Integer[] expected = {1,2,4,3,6,9};
		
		Object[] data = heappq.data();
		for (int i=0;i<heappq.size();i++) {
			assertEquals(i, expected[i], ((Entry<Integer,String>)data[i]).getKey());
		}
	}

	@Test
	public void testReplaceKey2() {
		/*
		 *       1
		 *     /    \  
		 *   3       4       
		 *  /  \    /
		 * 8   6  9  
		 */
		Entry<Integer,String> e =heappq.insert(1, "A");
		heappq.insert(3 , "cat");
		heappq.insert(4, "pear");
		heappq.insert(8, "elephant");
		heappq.insert(6, "turtle");
		heappq.insert(9, "pineapple");
		
		
		//after replace 1 with 10
		/*
		 *       3
		 *     /   \  
		 *   6       4       
		 *  /  \    /
		 * 8    10  9
		 */
		heappq.replaceKey(e,10);
		
		
		Integer[] expected = {3,6,4,8,10,9};
		
		Object[] data = heappq.data();
		for (int i=0;i<heappq.size();i++) {
			assertEquals("index"+i, expected[i], ((Entry<Integer,String>)data[i]).getKey());
		}
	}
	
	@Test
	public void testReplaceValue() {
		/*
		 *       1
		 *     /    \  
		 *   3       4       
		 *  /  \    /
		 * 8   6  9  
		 */
		Entry<Integer,String> e =heappq.insert(1, "A");
		heappq.insert(3 , "cat");
		heappq.insert(4, "pear");
		heappq.insert(8, "elephant");
		heappq.insert(6, "turtle");
		heappq.insert(9, "pineapple");
		
		heappq.replaceValue(e, "B");
		assertEquals("B", e.getValue());
	}

	@Test
	public void testSize() {
		heappq.insert(3 , "cat");
		heappq.insert(4, "pear");
		assertEquals(2, heappq.size());
		
	}
}
