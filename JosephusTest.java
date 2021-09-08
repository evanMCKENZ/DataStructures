/*Author: Evan McKenzie
 * 
 * Tests for the Josephus' game program
 */


package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JosephusTest {
	Josephus T = new Josephus();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testOrder1() {																		//test for a normal game with normal inputs
		String[] persons = {"stacy", "brain", "dave", "chuck", "katie", "lucy", "chad"};					//test array					
		DoublyLinkedList<String> result = T.order(persons, 4);
		String winner = result.removeLast();
		org.junit.Assert.assertEquals("order()1 space 4", "brain", winner);		
	}

	@Test
	public void testOrder2() {																		//test one but with a different integer to show a different result
		String[] persons = {"stacy", "brain", "dave", "chuck", "katie", "lucy", "chad"};
		DoublyLinkedList<String> result = T.order(persons, 3);
		String winner = result.removeLast();
		org.junit.Assert.assertEquals("order()1 space 3", "chuck", winner);	
	}

	@Test
	public void testOrder3() {																//test of an array with only one string, will be both first removed and the winner
		String[] persons = {"stacy"};
		DoublyLinkedList<String> result = T.order(persons, 10);
		String winner = result.removeLast();
		org.junit.Assert.assertEquals("order() w one", winner, "stacy");	
	}

	@Test
	public void testOrder4() throws Throwable{													//test of an empty array, which throws a NullPointerException
		String[] persons = {};
		DoublyLinkedList<String> result = T.order(persons, 4);
		{ boolean thrown = false;
		try {
			String winner = result.removeFirst();
		}
		catch(Throwable t){
			thrown = true;
			org.junit.Assert.assertThat("result.removeFirst() throws exception", t, org.hamcrest.CoreMatchers.instanceOf(NullPointerException.class));
		}
		if(!thrown) {
			org.junit.Assert.fail("result.removeFirst() throws exception: Expected Throwable NullPointerException");
		}
		}
	}

}
