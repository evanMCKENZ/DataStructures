/*Author: Evan McKenzie
 * 
 * This program takes in a string of an expression in infix order, and
 * uses a stack of character and an adaptable string to return the same
 * string in postfix order
 */


package cs2321;

public class InfixToPostfix {
	/* Convert an infix expression and to a postfix expression
	 * infix expression : operator is between operands. Ex. 3 + 5
	 * postfix Expression: operator is after the operands. Ex. 3 5 +
	 * 
	 * The infixExp expression includes the following
	 *      operands:  integer or decimal numbers 
	 *      and operators: + , - , * , /
	 *      and parenthesis: ( , )
	 *      
	 *      For easy parsing the expression, there is a space between operands and operators, parenthesis. 
	 *  	Ex: "1 * ( 3 + 5 )"
	 *      Notice there is no space before the first operand and after the last operand/parentheses. 
	 *  
	 * The postExp includes the following 
	 *      operands:  integer or decimal numbers 
	 *      and operators: + , - , * , /
	 *      
	 *      For easy parsing the expression, there should have a space between operands and operators.
	 *      Ex: "1 3 5 + *"
	 *      Notice there is space before the first operand and last operator. 
	 *      Notice that postExp does not have parenthesis. 
	 */

	public static String convert(String infixExp) {
		String returner = new String("");									//returned string at the end
		DLLStack<Character> stack = new DLLStack<>();							//create a new stack of characters

		String[] important = infixExp.split("\\ ");								//use .split() to split the input string along the spaces (which stores the separated words in an array)

		for(int i = 0; i < important.length; i++) {					//for every string in the split array			
			char c = important[i].charAt(0);							//get the first character

			String string = important[i];								//temporary variable for each string

			if(Character.isLetter(c)) {									//if the character is a letter, this is an invalid expression
				returner = "Invalid Expression";										//set the return string to read invalid
			}
			else if(Character.isDigit(c) || string.length() > 1) {			//if the first character is a character OR the string has a length greater than 1, it is a number so add it to the output string
				returner += string;										//how to add string to the return string
			}
			else if( c == '(') {								//if the character is a opening parenthesis, push it to the stack
				stack.push(c);									//use the stack methods we built
			}

			else if( c == ')') {											//if the character is a closing parenthesis, add the operators that came in between them
				while( stack.top() != '(') {									//if the top element in a open parenthesis, you've gotten all the operators
					returner += (" ");											//add a space to make sure the output is spaced out according to the instructions
					returner += stack.pop();
				}

				stack.pop();												//pop the next element off the stack after the parenthesis
			}
			else {
				returner += (" ");										//if the character is not a parenthesis, it is an operator, so add space to return string and push character to stack
				stack.push(c);
			}
		}

		while(stack.isEmpty() != true ) { 							//if there are elements on the stack, we need those, so add them to the return string witrh spacing								
			returner += (" ");
			returner += stack.pop();									//get those leftover elements
		}

		return returner;								//return the final string
	}
}