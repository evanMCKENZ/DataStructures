package cs2321;

import java.util.Comparator;


public class MaxComparator<Double> implements Comparator<Double> {
	
	// This compare method simply calls the compareTo method of the argument. 
	// If the argument is not a Comparable object, and therefore there is no compareTo method,
	// it will throw ClassCastException. 
	public int compare(Double a, Double b) throws ClassCastException {
		return ((Comparable <Double>) a).compareTo(b) * (-1);
	}
}
