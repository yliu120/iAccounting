/**
 * 
 */
package edu.jhu.cs.cs475.finalproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Here we implements a RandomFerns classifier
 * @author Yunlong Liu
 * @author Yijie   Li
 */
public class RandomFerns extends Predictor implements Serializable {
	
	public RandomFerns() {
		super();
	}

	/** (non-Javadoc)
	 * @see edu.jhu.cs.cs475.finalproject.model.Predictor#train(java.util.List)
	 */
	@Override
	public void train(List<Instance> instances) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see edu.jhu.cs.cs475.finalproject.model.Predictor#predict(edu.jhu.cs.cs475.finalproject.model.Instance)
	 */
	@Override
	public Label predict(Instance instance) {
		return null;
	}
	
	public String[] parseFeatureVector( FeatureVector vector ) {
		
		// Initialize call back string array
		String[] groupedFeatures = new String[16];
		String   grouping = "";
		
		for ( int i = 1; i <= 4 * 48; i ++ ) {
			Integer key = new Integer(i);
			if (vector.getVector().keySet().contains( key )) {
				grouping += "1";
			} else {
				grouping += "0";
			}
		}
		
		for ( int i = 0; i < 16; i ++ ) {
			
			int bigGroup = i / 4;
			int smallGroup = i % 4;
			
			if ( smallGroup == 0  ) {
				groupedFeatures[ bigGroup * 4 ] = grouping.substring(48 * bigGroup, 48 * bigGroup + 8);
			} else if ( smallGroup == 1) {
				groupedFeatures[ bigGroup * 4 + 1 ] = grouping.substring(48 * bigGroup + 8, 48 * bigGroup + 16);
			} else if ( smallGroup == 2) {
				groupedFeatures[ bigGroup * 4 + 2] = grouping.substring(48 * bigGroup + 16, 48 * bigGroup + 32);
			} else {
				groupedFeatures[ bigGroup * 4 + 3] = grouping.substring(48 * bigGroup + 32, 48 * bigGroup + 48);
			}
			
		}
		return groupedFeatures;	
	}

}
