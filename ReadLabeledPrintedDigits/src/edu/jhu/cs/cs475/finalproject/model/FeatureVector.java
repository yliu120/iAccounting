package edu.jhu.cs.cs475.finalproject.model;

import java.io.Serializable;
import java.util.HashMap;

public class FeatureVector implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, Double> vector;
	private int largestKey = 0;
	
	public FeatureVector() {
		vector = new HashMap<Integer, Double>();
	}

	public void add(int index, double value) {
		// TODO Auto-generated method stub
		// Filled by Yunlong Liu, Sep 9th
		// Modified by Yunlong Liu, Sep 19th
		// Completed by Yunlong Liu, Sep 9th
		vector.put(index, value);
		if ( index > this.largestKey ) {
			this.largestKey = index;
		}
		
	}
	
	public int getLargestKey() {
		return this.largestKey;
	}
	
	public double get(int index) {
		// TODO Auto-generated method stub
		// Filled by Yunlong Liu, Sep 9th
		// Completed by Yunlong Liu, Sep 9th
		return vector.get( index );
	}
	
	public HashMap<Integer, Double> getVector(){
		return vector;
	}
	
	public Double[] toArray(){
		
		Double[] array = new Double[ this.largestKey ];
		for ( int i = 1 ; i <= this.largestKey; i++  ) {
		//	System.out.println( key + ":" + this.vector.get(key));
			array[i] = this.vector.get( i );
		}
		return array;
		
	}

}
