/*Author: Evan McKenzie
 * With Help From: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * 
 * This program extends the AbstractMap interface and implements the Map interface
 * while overriding the fundamental methods to implement an Unordered map using an 
 * ArrayList filled with UnorderedMaps to keep track of key-value pairs that hold 
 * the map itself. This class uses several helper methods to complete some of the methods
 * and the average time complexity for the fundamental methods is O(1)
 */
package cs2321;

import net.datastructures.*;

public class HashMap<K, V> extends AbstractMap<K,V> implements Map<K, V> {
	
	/* Use Array of UnorderedMap<K,V> for the Underlying storage for the map of entries.
	 * 
	 */
	private UnorderedMap<K,V>[]  table;
	int 	size;  // number of mappings(entries) 
	int 	capacity; // The size of the hash table. 
	int     DefaultCapacity = 17; //The default hash table size
	
	/* Maintain the load factor <= 0.75.
	 * If the load factor is greater than 0.75, 
	 * then double the table, rehash the entries, and put then into new places. 
	 */
	double  loadfactor= 0.75;  
	
	/**
	 * Constructor that takes a hash size
	 * @param hashtable size: the number of buckets to initialize 
	 */
	@TimeComplexity("O(n)")
	public HashMap(int hashtablesize) {
		/*
		 * TCJ
		 * loop over every element in array
		 */
		capacity = hashtablesize;							//set capacity equal to the given capacity
		table = (UnorderedMap<K, V>[]) new UnorderedMap[capacity];					//create array of UnorderedMaps
		for (int index = 0; index < capacity; index++) {
            table[index] = new UnorderedMap<>();							//instantiate each index with an UnorderedMap
        }
	}
	
	/**
	 * Constructor that takes no argument
	 * Initialize the hash table with default hash table size: 17
	 */
	@TimeComplexity("O(n)")
	public HashMap() {
		/*
		 * TCJ
		 * loop over every element in array
		 */
		capacity = DefaultCapacity;									//set capacity equal to the defaulcapacity already defined
		table = (UnorderedMap<K, V>[]) new UnorderedMap[capacity];				//create array of UnorderedMaps
		for (int index = 0; index < capacity; index++) {
            table[index] = new UnorderedMap<>();						//instantiate each index with an UnorderedMap
        }
	}
	
	@TimeComplexity("O(n)")
	protected void createTable( ) {	
		/*
		 * TCJ
		 * loop over every element in array
		 */
		table = (UnorderedMap<K, V>[]) new UnorderedMap[capacity];
		for (int index = 0; index < capacity; index++) {						//used when we need to resize array
            table[index] = new UnorderedMap<>();
        }
	}
	
	protected V bucketGet(int h, K k) {					//helper method for buckets in the get method
	    UnorderedMap<K,V> bucket = table[h];
	    if (bucket == null) {
	    	return null;
	    }
	    return bucket.get(k);
	}
	
	protected V bucketPut(int h, K k, V v) {							//helper method for buckets in the put method
	    UnorderedMap<K,V> bucket = table[h];
	    if (bucket == null) {
	      bucket = table[h] = new UnorderedMap<>();
	    }
	    int oldSize = bucket.size();
	    V oldie = table[h].get(k);
	    bucket.put(k,v);
	    size += (bucket.size() - oldSize);   // size may have increased
	    return oldie;
	}
	
	protected V bucketRemove(int h, K k) {						//helper method for buckets in the remove method
	    UnorderedMap<K,V> bucket = table[h];
	    if (bucket == null) {
	    	return null;
	    }
	    int oldSize = bucket.size();
	    V answer = bucket.remove(k);
	    size -= (oldSize - bucket.size());   // size may have decreased
	    return answer;
	}
	
	/* This method should be called by map an integer to the index range of the hash table 
	 */
	private int hashValue(K key) {
		return Math.abs(key.hashCode()) % capacity;
	}
	
	/*
	 * The purpose of this method is for testing if the table was doubled when rehashing is needed. 
	 * Return the the size of the hash table. 
	 * It should be 17 initially, after the load factor is more than 0.75, it should be doubled.
	 */
	@TimeComplexity("O(1)")
	public int tableSize() {
		return capacity;			//return global capacity
	}
	
	
	@Override
	@TimeComplexity("O(1)")
	public int size() {
		return size;						//return global size variable
	}

	@Override
	@TimeComplexity("O(1)")
	public boolean isEmpty() {
		return size == 0;					//boolean checker to global size variable
	}

	@Override
	@TimeComplexityExpected("O(1)")
	public V get(K key) {
		return bucketGet(hashValue(key), key);			//call bucket method
	}
	
	//helper method 
	private void resize(int newCap) {
		ArrayList<Entry<K,V>> buffer = new ArrayList<>(size);				//create buffer with new size
		for(Entry<K,V> e : entrySet()) {							//copy all elements over
			buffer.addLast(e);
		}
		capacity = newCap;				//reset the capacity of the table
		createTable();					//create the new table
		size = 0;						//reset the size
		for(Entry<K,V> e : buffer) {
			put(e.getKey(), e.getValue());				//add all elements into new table
		}
	}

	@Override
	@TimeComplexityExpected("O(1)")
	public V put(K key, V value) {
		V answer = bucketPut(hashValue(key), key, value);				//call bucket method
		if(size / capacity >= loadfactor) {						//if loadfactor > 0.75
			resize(2 * capacity - 1);					//call resize method with new capacity
		}
		return answer;					//return old value
	}

	@Override
	@TimeComplexityExpected("O(1)")
	public V remove(K key) {
		return bucketRemove(hashValue(key), key);			//call bucket method
	}

	@Override
	@TimeComplexity("O(n^2)")
	public Iterable<Entry<K, V>> entrySet() {
		/*
		 * TCJ
		 * double for loop over all elements in the array
		 */
		ArrayList<Entry<K,V>> buffer = new ArrayList<>();
		for(int h = 0; h < capacity; h++) {
			if(table[h] != null) {
				for(Entry<K,V> entry : table[h].entrySet()) {
					buffer.addLast(entry);
				}
			}
		}
		return buffer;
	}
	

}
