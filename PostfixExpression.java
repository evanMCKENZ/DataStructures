/*Author: Evan McKenzie
 * 
 * This program takes in a string of an algebraic expression in postfix
 * order, and then evaluates it by parsing the string element by element
 * and using arithmetic to output the integer result of the expression
 */


package cs2321;

public class PostfixExpression {

	/**
	 * Evaluate a postfix expression. 
	 * Postfix expression notation has operands first, following by the operations.
	 * For example:
	 *    13 5 *           is same as 13 * 5 
	 *    4 20 5 + * 6 -   is same as 4 * (20 + 5) - 6  
	 *    
	 * In this homework, expression in the argument only contains
	 *     integer, +, -, *, / and a space between every number and operation. 
	 * You may assume the result will be integer as well. 
	 * 
	 * @param exp The postfix expression
	 * @return the result of the expression
	 */
	public static int evaluate(String exp) {
		DLLStack<Integer> stack = new DLLStack<>();									//initialize a new stack of type Integer
		String[] important = exp.split("\\ ");												//split the input string using spaces ( " " ) as the delimiter

		for(int i = 0; i < important.length; i++) {									//for every element in the string split array
			String string = important[i];										//temporary variable for the string at index i in the array

			char chrt = string.charAt(0);						//get the character at the first index in the string

			if(string == " ") {											//this statement does nothing, but when i took it out something broke so it stays in
				continue;
			}

			else if(Character.isDigit(chrt) || string.length() > 1) {						//if the character is a digit OR the string length is greater than 1, push the number to the stack
				stack.push(Integer.valueOf(string));								//have to get the integer value of the string
			}

			else {
				int value = stack.pop();				//get the first element from the stack
				int value2 = stack.pop();				//get the second element from the stack

				switch(chrt) {								//SWICH STATEMENT is the best way to do the logic for this

				case '+':								//if character chrt is a '+', we need to add the two values we pulled
					stack.push(value2 + value);				//push the new value to the stack
					break;
				case '-':								//if character chrt is a '-', we need to subtract the two values we pulled
					stack.push(value2 - value);				//push the new value to the stack
					break;
				case '/':									//if character chrt is a '/', we need to divide the two values we pulled
					stack.push(value2 / value);					//push the new value to the stack
					break;
				case '*':								//if character chrt is a '*', we need to multiply the two values we pulled
					stack.push(value2 * value);					//push the new value to the stack
					break;
				}
			}
		}
		return stack.pop();				//after all that logic, the final value left in the stack will be the value of the expression	
	}
}
