/*Author: Evan McKenzie
 * 
 * Tests for the infixToPostfix method
 */


package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class InfixToPostfixTest {

	InfixToPostfix T = new InfixToPostfix();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConvert1() {															//tests a normal input
		String exp = "1 * ( 3 + 5 )"; 
		org.junit.Assert.assertEquals("convert()1", "1 3 5 + *", T.convert(exp)); 
	}

	@Test
	public void testConvert2() {
		String exp = "2 + ( 2 * 4 )"; 																	//tests a normal input again
		org.junit.Assert.assertEquals("convert()2", "2 2 4 * +", T.convert(exp));
	}

	@Test
	public void testConvert3() {
		String exp = "1 + 1"; 																		//one more normal input
		org.junit.Assert.assertEquals("convert()3", "1 1 +", T.convert(exp));
	}

	@Test
	public void testConvert4() throws Throwable{													//tests an input of a string with a word (or ordered characters)
		String exp = "stacy";
		org.junit.Assert.assertEquals("convert()4", "Invalid Expression", T.convert(exp));
	}

	@Test
	public void testConvert5() {
		String exp = "( 2 * 3 ) + ( 15 / 5 )";													//more complicated input, two sets of parenthesis
		org.junit.Assert.assertEquals("convert()5", "2 3 * 15 5 / +", T.convert(exp));
	}

	@Test
	public void testConvert6() {
		String exp = "3.6 + 4.2";																//decimal numbers
		org.junit.Assert.assertEquals("convert()6", "3.6 4.2 +", T.convert(exp));
	}

	@Test
	public void testConvert7() {
		String exp = "4 + -10";																//negative numbers
		org.junit.Assert.assertEquals("convert()6", "4 -10 +", T.convert(exp));
	}

	@Test
	public void testConvert8() {
		String exp = "( 31.6 + 4.2 ) * ( 4 + 10 )";																	//combination of all of the above
		org.junit.Assert.assertEquals("convert()6", "31.6 4.2 + 4 10 + *", T.convert(exp));
	}
}

