/*Author: Evan McKenzie
 * 
 * This program solves the puzzle of Josephus' game by implementing a queue and a Doubly Linked List
 * where the queue holds the plays in a rotating circle, and the Doubly Linked List stores the names
 * of the players as they are eliminated from the game so that the winner of the game is added
 * last
 */


package cs2321;

public class Josephus {
	/**
	 * All persons sit in a circle. When we go around the circle, initially starting
	 * from the first person, then the second person, then the third... 
	 * we count 1,2,3,.., k-1. The next person, that is the k-th person is out. 
	 * Then we restart the counting from the next person, go around, the k-th person 
	 * is out. Keep going the same way, when there is only one person left, she/he 
	 * is the winner. 
	 *  
	 * @parameter persons  an array of string which contains all player names.
	 * @parameter k  an integer specifying the k-th person will be kicked out of the game
	 * @return return a doubly linked list in the order when the players were out of the game. 
	 *         the last one in the list is the winner.  
	 */
	public DoublyLinkedList<String> order(String[] persons, int k ) {
		CircularArrayQueue<String> queue = new CircularArrayQueue<>(persons.length);					//create a queue of String type objects with the length of the passed in array
		DoublyLinkedList<String> order = new DoublyLinkedList<>();											//create a DLL of string type objects
		for(int i = 0; i < persons.length; i++) {									//for every element in the String array, put it in the queue
			queue.enqueue(persons[i]);									//add each element to queue
		}
		if(queue.isEmpty()) {return null;}							//if the queue is empty, no DLL to return so null
		while(queue.size() > 1) {										//while the queue has elements in it
			for(int j = 0; j < k-1; j++) {									//this for loop skips over the objects from o to 1 less than the number to be removed and just moves them to the end of the queue
				String element = queue.dequeue();						//remove from the front
				queue.enqueue(element);									//add to the back
			}
			String loser = queue.dequeue();								//the next element will be the person to be removed from the game
			order.addLast(loser);									//so remove them and add them to the DLL with the addLast() method
		}
		String winner = queue.dequeue();							//after the while loop is over, only the winner will be left, so remove them and add them to the end of the DLL
		order.addLast(winner);										//again, use the addLast() to put them behind the earlier additions
		return order;											//return the DLL with the order of the removed players
	}

}
