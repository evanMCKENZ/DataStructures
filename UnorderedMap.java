/*Author: Evan McKenzie
 * With Help From: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * 
 * This program extends the AbstractMap interface and overrides the fundamental
 * methods to implement an Unordered map using an ArrayList filled with Entries
 * of key-value pairs to hold the map itself. This class uses several helper methods
 * to complete some of the methods
 */
package cs2321;

import net.datastructures.Entry;

public class UnorderedMap<K,V> extends AbstractMap<K,V> {
	
	/* Use ArrayList or DoublyLinked list for the Underlying storage for the map of entries.
	 * TODO:  Uncomment one of these two lines;
	 * private ArrayList<Entry<K,V>> table; 
	 * private DoublyLinkedList<Entry<K,V>> table;
	 */
	
	private ArrayList<Entry<K,V>> table; 	//instantiate arraylist of entries for the map (using arraylist and not doubly linked)
	
	public UnorderedMap() { 
		table = new ArrayList<>();				//create table 
	}			
	

	//helper method
	private int findIndex(K key) {						
		int n = table.size();					//get size					
		for(int j = 0; j < n; j++) {						//loop over every element
			if(table.get(j).getKey().equals(key)) {					//check equals() for similarity
				return j;								//return index of similar key
			}
		}
		return -1;					//no similar key found
	}
	
	@Override
	@TimeComplexity("O(1)")
	public int size() {
		return table.size();					//return size
	}

	@Override
	@TimeComplexity("O(1)")
	public boolean isEmpty() {
		return table.size() == 0;					//boolean checker
	}

	@Override
	@TimeComplexity("O(n)")
	public V get(K key) {
		/*
		 * TCJ
		 * call to findIndex requires loop over entire array
		 */
		int j = findIndex(key);					//call findIndex function to get index
		if(j == -1) {
			return null;						//no key found with this parameter
		}
		return table.get(j).getValue();				//otherwise, get the value of the found key
	}

	@Override
	@TimeComplexity("O(n)")
	public V put(K key, V value) {	
		/*
		 * TCJ
		 * call to findIndex requires loop over entire array
		 */
		int j = findIndex(key);					//call function findIndex to get index of given key
		if(j == -1) {									//if there is no existing match
			table.addFirst(new mapEntry<>(key, value));				//add the key,value pair to the front of the map
			return null;
		}
		else {
			mapEntry<K, V> help = (mapEntry<K, V>) table.get(j);			//otherwise create a new entry
			help.setValue(value);										//set the value of the existing key,value pair
			return help.getValue();						//and return the old value
		}
	}

	@Override
	@TimeComplexity("O(n)")
	public V remove(K key) {
		/*
		 * TCJ
		 * call to findIndex requires loop over entire array
		 */
		int j = findIndex(key);					//call findIndex to find the given key
		int n = size();						//current size variable
		if(j == -1) {							//if no match
			return null;				//exit method
		}
		V answer = table.get(j).getValue();					//V value to be returned
		if(j != n -1) {										//move element to the end of the arraylist
			table.set(j, table.get(n-1));
		}
		table.remove(n-1);						//remove selected element
		return answer;					//return the value of the entry that was removed
	}
	
	@Override
	@TimeComplexity("O(1)")
	public Iterable<Entry<K, V>> entrySet() {						
		return table;								//returns a set of all elements in the map
	}

}
