/*Author: Evan McKenzie
 * With Help From: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * 
 * This program extends the AbstractMap interface and implements the SortedMap interface and
 * overrides the fundamental methods to implement a Sorted map using an ArrayList filled with Entries
 * of key-value pairs to hold the map itself. This class uses several helper methods
 * to complete some of the methods
 */
package cs2321;

import net.datastructures.*;

public class LookupTable<K extends Comparable<K>, V> extends AbstractMap<K,V> implements SortedMap<K, V> {
	
	/* 
	 * Use Sorted ArrayList for the Underlying storage for the map of entries.
	 * TODO: Uncomment this line;
	 * private ArrayList<Entry<K,V>> table; 
	 */

	private ArrayList<Entry<K,V>> table; 
	
	public LookupTable(){
		table = new ArrayList<>(); 					//instantiate arraylist
	}

	//helper method
	private int findIndex(K key, int low, int high) {			//find an index when given a high and low index
		if(high < low) {
			return high + 1;
		}
		int mid = (low + high) / 2;
		int comp = key.compareTo(table.get(mid).getKey());
		if(comp == 0) {
			return mid;
		}
		else if(comp < 0) {
			return findIndex(key, low, mid - 1);
		}
		else {
			return findIndex(key, mid + 1, high);
		}
	}
	
	//helper method
	private int findIndex(K key) {
		return findIndex(key, 0, table.size() - 1);			//use the entire array (calls secondary function)
	}
	
	
	@Override
	@TimeComplexity("O(lg n)")
	public V get(K key) {
		/*
		 * TCJ
		 * call to findIndex and subsequent secondary function makes log n
		 */
		int j = findIndex(key);
		if(j == size() || key.compareTo(table.get(j).getKey()) != 0){		//compare key to found index key
			return null;
		}
		return table.get(j).getValue();			//return the value at the found index
	}

	@Override
	@TimeComplexity("O(n)")
	public V put(K key, V value) {
		/*
		 * TCJ
		 * call to findIndex and subsequent secondary function makes n
		 */
		int j = findIndex(key);
		V returner = null;
		if(j < size() && key.compareTo(table.get(j).getKey()) == 0) {
			returner = table.get(j).getValue();
			table.set(j, new mapEntry<K,V>(key, value));				//set the index to a new entry to the parameter key and value
			return returner;								//return the old value
		}
		table.add(j, new mapEntry<K,V>(key, value));			//otherwise just add the new entry at the found index
		return null;					//and return nothing
	}

	@Override
	@TimeComplexity("O(n)")
	public V remove(K key) {
		/*
		 * TCJ
		 * call to findIndex and subsequent secondary function makes n
		 */
		int j = findIndex(key);
		if( j == size() || key.compareTo(table.get(j).getKey()) != 0) {				//compare key to found index key and found index to size
			return null;									//return null if these are true
		}
		return table.remove(j).getValue();					//otherwise just remove the entry and return its value
	}

	//helper method for first, last, and ceiling Entry
	private Entry<K,V> safeEntry(int j ){
		if(j < 0 || j >= table.size()) {
			return null;
		}
		return table.get(j);			//get the Entry at index j
	}
	
	@Override
	@TimeComplexity("O(n)")
	public Iterable<Entry<K, V>> entrySet() {
		/*
		 * TCJ
		 * snapshot loop over all n elements
		 */
		return snapshot(0, null);				//from 0 to null, or over all elements in the array
	}

	@Override
	@TimeComplexity("O(1)")
	public Entry<K, V> firstEntry() {
		return safeEntry(0);					//pass zero as the index
	}

	@Override
	@TimeComplexity("O(1)")
	public Entry<K, V> lastEntry() {
		return safeEntry(table.size() - 1);				//pass the maximum index as the index
	}

	@Override
	@TimeComplexity("O(lg n)")
	public Entry<K, V> ceilingEntry(K key)  {
		/*
		 * TCJ
		 * call to both safeEntry and findIndex
		 */
		return safeEntry(findIndex(key));			//pass the found index of the given key as safeEntry argument
	}

	@Override
	@TimeComplexity("O(lg n)")
	public Entry<K, V> floorEntry(K key)  {
		/*
		 * TCJ
		 * call to both safeEntry and findIndex
		 */
		int j = findIndex(key);						
		if(j == size() || !key.equals(table.get(j).getKey())) {		//make sure j is not the last element 
			j--;
		}
		return safeEntry(j);    //pass found j index as argument
	}

	@Override
	@TimeComplexity("O(lg n)")
	public Entry<K, V> lowerEntry(K key) {
		/*
		 * TCJ
		 * call to both safeEntry and findIndex
		 */
		return safeEntry(findIndex(key) - 1);				//passed the found index j - 1 to get a lower entry
	}

	@Override
	@TimeComplexity("O(lg n)")
	public Entry<K, V> higherEntry(K key) {
		/*
		 * TCJ
		 * call to both safeEntry and findIndex
		 */
		int j = findIndex(key);
		if(j < size() && key.equals(table.get(j).getKey())){
			j++;												//add one to get higher entry
		}
		return safeEntry(j);				//pass safeEntry the j index
	}

	//helper method
	private Iterable<Entry<K,V>> snapshot(int startindex, K stop){
		ArrayList<Entry<K,V>> buffer = new ArrayList<>();				//create new arraylist
		int j = startindex;
		while(j < table.size() && (stop == null || stop.compareTo(table.get(j).getKey()) > 0)) {		//loop over elements from start key to end key
			buffer.addLast(table.get(j++));					//add elements to secondary arraylist
		}
		return buffer;					//return secondary arraylist with path
	}
	
	@Override
	@TimeComplexity("O(n + lg n)")
	public Iterable<Entry<K, V>> subMap(K fromKey, K toKey){
		/*
		 * TCJ
		 * depends on how many elements between the two keys for n +, and dual calls to snapshot and findIndex make log n
		 */
		return snapshot(findIndex(fromKey), toKey);			//start and end key of lookup
	}


	@Override
	@TimeComplexity("O(1)")
	public int size() {
		return table.size();				//return call to size method of arraylist
	}


	@Override
	@TimeComplexity("O(1)")
	public boolean isEmpty() {
		return table.size() == 0;			//boolean checker for size
	}


}
