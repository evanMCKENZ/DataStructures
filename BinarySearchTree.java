/*Author: Evan McKenzie
 * With Help From: Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 * 
 * This program extends the AbstractMap interface and implements the SortedMap interface and
 * overrides the fundamental methods to implement a LinkedBinaryTree using an ArrayList filled with Entries
 * of key-value pairs to hold the map itself. This class uses several helper methods
 * to complete some of the methods and provides time complexity annotations for the non-trivial methods
 */
package cs2321;

import net.datastructures.Entry;
import net.datastructures.SortedMap;
import net.datastructures.*;


public class BinarySearchTree<K extends Comparable<K>,V> extends AbstractMap<K,V> implements SortedMap<K,V> {
	
	/* all the data will be stored in the tree*/
	LinkedBinaryTree<Entry<K,V>> tree; 
	int size;  																//the number of entries (mappings)
	
	/* 
	 * default constructor
	 */
	public BinarySearchTree() { 
		tree = (LinkedBinaryTree<Entry<K, V>>) new LinkedBinaryTree();			//create LinkedBinaryTree with cast to LinkedBinaryTree full of type Entry
	}
	
	/* 
	 * Return the tree. The purpose of this method is purely for testing. 
	 * You don't need to make any change. Just make sure to use variable tree to store your entries. 
	 */
	public LinkedBinaryTree<Entry<K,V>> getTree() {
		return tree;									//just return the tree
	}
	
	@Override
	@TimeComplexity("O(1)")
	public int size(){
		return (tree.size() - 1) / 2;			//return height of tree
	}
	
	//helper method to insert new entry at the leaf nodes
	private void expandExternal(Position<Entry<K,V>> p, Entry<K,V> entry) {
		tree.setElement(p, entry);				//store new element at p
		tree.addLeft(p,  null);					//add empty children nodes
		tree.addRight(p,  null);
	}
	
	@TimeComplexity("O(n)")
	public Position<Entry<K, V>> successor(Position<Entry<K, V>> p) {
		/*
		 * TCJ loop over many elements in tree
		 */
        Position<Entry<K, V>> walk = p;				//create new position from parameter p
        walk = right(walk);				//get right child from position
        while (isInternal(walk))
            walk = left(walk);				//go left if it is internal
        return parent(walk); // we want the parent of the leaf
    }
	
	//methods that provide shorthands to aid readability
	  protected Position<Entry<K,V>> root() { return tree.root(); }
	  protected Position<Entry<K,V>> parent(Position<Entry<K,V>> p) { return tree.parent(p); }
	  protected Position<Entry<K,V>> left(Position<Entry<K,V>> p) { return tree.left(p); }
	  protected Position<Entry<K,V>> right(Position<Entry<K,V>> p) { return tree.right(p); }
	  protected Position<Entry<K,V>> sibling(Position<Entry<K,V>> p) { return tree.sibling(p); }
	  protected boolean isRoot(Position<Entry<K,V>> p) { return tree.isRoot(p); }
	  protected boolean isExternal(Position<Entry<K,V>> p) { return tree.isExternal(p); }
	  protected boolean isInternal(Position<Entry<K,V>> p) { return tree.isInternal(p); }
	  protected Entry<K,V> remove(Position<Entry<K,V>> p) { return tree.remove(p); }
	  
	@TimeComplexity("O(n)")
	private Position<Entry<K,V>> treeSearch(Position<Entry<K,V>> p, K key){
		/*
		 * TCJ
		 * recursive calls traverse all elements
		 */
		if(isExternal(p)) {
			return p;
		}
		int comp = key.compareTo(p.getElement().getKey());			//use compareTo for comparator
		if(comp == 0) {				//equal
			return p;
		}
		else if(comp < 0) {						//key smaller than p's key
			return treeSearch(left(p), key);			//we need smaller keys so go left
		}
		else {
			return treeSearch(right(p), key);			//go right otherwise
		}
	}
	
	@Override
	@TimeComplexity("O(n)")
	public V get(K key) {
		/*
		 * TCJ
		 * iterate over all elements
		 */
		Position<Entry<K,V>> p = treeSearch(root(), key);
		if(isExternal(p)) {
			return null;						//nothing if the position is a leaf node
		}
		return p.getElement().getValue();			//return the value at the found position
	}

	@Override
	@TimeComplexity("O(n)")
	public V put(K key, V value) {
		/*
		 * TCJ
		 * create new entry and iterate over elements to find position means n
		 */
		Entry<K,V> newEntry = new mapEntry<>(key, value);			//create new entry with key value pair
		if(root() == null) {						//there is no elements in the tree yet
			tree.addRoot(newEntry);
			expandExternal(tree.root(), newEntry);
			size++;
			return null;
		}
		Position<Entry<K,V>> p = treeSearch(root(), key);
		if(isExternal(p)) {							//key is new and doesn't exist
			expandExternal(p, newEntry);
			size++;							//increment size
			return null;
		}		
		else {										//key already exists
			V old = p.getElement().getValue();
			tree.setElement(p, newEntry);
			return old;						//return the old value associated with this key
		}
	}

	@Override
	@TimeComplexity("O(n)")
	public V remove(K key) {
		/*
		 * TCJ
		 * iterate over all elements
		 */
		Position<Entry<K,V>> p = treeSearch(root(), key);			//find the position of the the given key
	    if (isExternal(p)) {          					//no value to return                           
	      return null;
	    } 
	    else {
	      V old = p.getElement().getValue();
	      if (isInternal(left(p)) && isInternal(right(p))) { 
	        Position<Entry<K,V>> replacement = isExternal(right(p)) ? treeMax(left(p)) : successor(p);				//ternary operation
	        tree.setElement(p, replacement.getElement());
	        p = replacement;
	     } 
	      Position<Entry<K,V>> leaf = (isExternal(left(p)) ? left(p) : right(p));					//ternary operator
	      remove(leaf);
	      remove(p);   
	      size--;					//decrement size
	      return old;					//return the old value associated with the given key
	    }
	}


	@Override
	@TimeComplexity("O(n)")
	public Iterable<Entry<K, V>> entrySet() {
		/*
		 * TCJ
		 * iterate over all elements and return iterable arraylist
		 */
		ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());				//create new arraylist (iterable object)
	    for (Position<Entry<K,V>> p : tree.inorder())					//foreach loop
	      if (isInternal(p)) {
	    	  buffer.addLast(p.getElement());			//add to end of array
	      }
	    return buffer;
	}

	@Override
	@TimeComplexity("O(n)")
	public Entry<K, V> firstEntry() {
		/*
		 * TCJ
		 * call to treeMin
		 */
		if (isEmpty()) {
			return null;		//no first entry if tree is empty
		}
	    return treeMin(root()).getElement();			//otherwise return the entry that root() returns
	}

	@TimeComplexity("O(n)")
	protected Position<Entry<K,V>> treeMax(Position<Entry<K,V>> p) {
		/*
		 * TCJ
		 * iterate over all elements
		 */
	    Position<Entry<K,V>> walk = p;			//create new position object
	    while (isInternal(walk)) {
	      walk = right(walk);
	    }
	    return parent(walk);         //return the position of the parent node from the right walkthrough    
	}
	
	@TimeComplexity("O(n)")
	protected Position<Entry<K,V>> treeMin(Position<Entry<K,V>> p) {
		/*
		 * TCJ
		 * iterate over all elements
		 */
	    Position<Entry<K,V>> walk = p;				//create new position object
	    while (isInternal(walk))
	      walk = left(walk);
	    return parent(walk);         //return the position of the parent node from the right walkthrough      
	}
	
	@Override
	@TimeComplexity("O(n)")
	public Entry<K, V> lastEntry() {
		/*
		 * TCJ
		 * call to treeMin
		 */
		if (isEmpty()) {
			return null;				//no last element if tree is empty
		}
	    return treeMax(root()).getElement();		//otherwise return the last element that the path from root returns
	}

	@Override
	@TimeComplexity("O(n)")
	public Entry<K, V> ceilingEntry(K key) {
		/*
		 * TCJ
		 * while loop over elements with nested if-else
		 */
		Position<Entry<K,V>> p = treeSearch(root(), key);		//get position
	    if (isInternal(p)) {
	    	return p.getElement();   // exact match
	    }
	    while (!isRoot(p)) {
	      if (p == left(parent(p))) {
	        return parent(p).getElement();   	//go left to find parent
	      }
	      else {
	        p = parent(p);				//is its own parent
	      }
	    }
	    return null; 			//return nothing if if-else cases not met
	}

	@Override
	@TimeComplexity("O(n)")
	public Entry<K, V> floorEntry(K key)  {
		/*
		 * TCJ
		 * while loop over elements with nested if-else
		 */
		Position<Entry<K,V>> p = treeSearch(root(), key);		//get position
	    if (isInternal(p)) {
	    	return p.getElement();   // exact match
	    }
	    while (!isRoot(p)) {
	      if (p == right(parent(p))) {
	        return parent(p).getElement();  		//go right to find parent
	      }
	      else {
	        p = parent(p);			//is its own parent
	      }
	    }
	    return null;
	}

	@Override
	@TimeComplexity("O(n^2)")
	public Entry<K, V> lowerEntry(K key) {
		/*
		 * TCJ
		 * call to treeMax and while loop make n^2
		 */
		Position<Entry<K,V>> p = treeSearch(root(), key);		//get position
	    if (isInternal(p) && isInternal(left(p))) {
	      return treeMax(left(p)).getElement(); 
	    }
	    while (!isRoot(p)) {
	      if (p == right(parent(p))) {							//go right for parent node
	        return parent(p).getElement();				//return parent node
	      }
	      else {
	        p = parent(p);					//is its own parent
	      }
	    }
	    return null;  				//otherwise return null if no lower Entry
	}

	@Override
	@TimeComplexity("O(n^2)")
	public Entry<K, V> higherEntry(K key){
		/*
		 * TCJ
		 * call to treeMin and while loop make n^2
		 */
		Position<Entry<K,V>> p = treeSearch(root(), key);			//get position
	    if (isInternal(p) && isInternal(right(p))) {
	      return treeMin(right(p)).getElement();  
	    }
	    while (!isRoot(p)) {
	      if (p == left(parent(p))) {
	        return parent(p).getElement();  			//look left for parent
	      }
	      else {
	        p = parent(p);									//is its own parent
	      }
	    }
	    return null; 			//return nothing if no higher entry
	}

	@Override
	@TimeComplexity("O(n)")
	public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
		/*
		 * TCJ
		 * call to subMapRecurse
		 */
		ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());			//create secondary arraylist
	    if (toKey.compareTo(fromKey) < 0) {                  // ensure that fromKey < toKey
	      subMapRecurse(fromKey, toKey, root(), buffer);					//give recursive call two keys to map from and buffer iterable arraylist
	    }
	    return buffer;				//return secondary arraylist
	}

	@TimeComplexity("O(n)")
	private void subMapRecurse(K fromKey, K toKey, Position<Entry<K,V>> p, ArrayList<Entry<K,V>> buffer) {
		if (isInternal(p)) {
			if (fromKey.compareTo(p.getElement().getKey()) < 0) {		//check keys are not already equal
				subMapRecurse(fromKey, toKey, right(p), buffer);			//recursive call to map
			}
			else {
				subMapRecurse(fromKey, toKey, left(p), buffer); // first consider left subtree
				if (toKey.compareTo(p.getElement().getKey()) < 0) {       // p is within range
					buffer.addLast(p.getElement());                      // so add it to buffer, and consider
					subMapRecurse(fromKey, toKey, right(p), buffer); // right subtree as well
				}
			}
		}
	}
	
	@Override
	@TimeComplexity("O(1)")
	public boolean isEmpty() {
		return tree.size() == 0;			//boolean checker calls size function and compares
	}

	

}
