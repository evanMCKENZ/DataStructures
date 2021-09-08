/*Author: Evan McKenzie
 * 
 * Tests for PostfixExpression method
 */


package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PostfixExpressionTest {
	PostfixExpression T = new PostfixExpression();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEvaluate1() {													//first test a easy, normal expression, 3-2
		String exp = "3 2 -"; 
		org.junit.Assert.assertEquals("evaluate()1", 1, T.evaluate(exp));
	}

	@Test
	public void testEvaluate2() {
		String exp = "2 3 1 * + 1 -"; 													//little more complicated expression, multiple operations
		org.junit.Assert.assertEquals("evaluate()2", 4, T.evaluate(exp));
	}

	@Test
	public void testEvaluate3() {
		String exp = "2 3 1 * + 9 -"; 													//test to make sure the program can return negative numbers
		org.junit.Assert.assertEquals("evaluate()3", -4, T.evaluate(exp));
	}

	@Test
	public void testEvaluate4() {
		String exp = "2 3 * 15 5 / +"; 															//again, multiple operations and ordered integers
		org.junit.Assert.assertEquals("evaluate()4", 9, T.evaluate(exp));
	}

	@Test
	public void testEvaluate5() {
		String exp = "100 97 - 2 -"; 											//test for larger integers
		org.junit.Assert.assertEquals("evaluate()5", 1, T.evaluate(exp));
	}

	@Test
	public void testEvaluate6() {
		String exp = "50 0 -"; 
		org.junit.Assert.assertEquals("evaluate()6", 50, T.evaluate(exp));
	}

	@Test
	public void testEvaluate7() {
		String exp = "5 4 * 3 * 2 * 1 *"; 											//test to see if it can do 5! (5 factorial)
		org.junit.Assert.assertEquals("evaluate()7", 120, T.evaluate(exp));
	}

	@Test
	public void testEvaluate8() {
		String exp = "15 3 / 7 * 2 - 3 4 * 10 * +"; 										//most complicated input 
		org.junit.Assert.assertEquals("evaluate()7", 153, T.evaluate(exp));
	}

	@Test
	public void testEvaluate9() throws Throwable{								//extra test to see if it will throw an exception when asked to multiply by 0
		String exp = "0 * 1"; 

		{ boolean thrown = false;
		try {
			T.evaluate(exp);
		} catch (Throwable t) {
			thrown = true;
			org.junit.Assert.assertThat("T.evaluate(exp) throws exception", t, org.hamcrest.CoreMatchers.instanceOf(NullPointerException.class));
		}
		if(!thrown){
			org.junit.Assert.fail("T.evaluate(exp) throws exception: Expected Throwable NullPointerException");
		}
		}
	}
}
