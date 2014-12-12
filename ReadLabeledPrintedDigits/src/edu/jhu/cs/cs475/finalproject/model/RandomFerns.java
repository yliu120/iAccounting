/**
 * 
 */
package edu.jhu.cs.cs475.finalproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Here we implements a RandomFerns classifier
 * 
 * @author Yunlong Liu
 * @author Yijie Li
 */
public class RandomFerns extends Predictor implements Serializable {

	private List<ProbChar> probs;

	public RandomFerns() {
		super();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see edu.jhu.cs.cs475.finalproject.model.Predictor#train(java.util.List)
	 */
	@Override
	public void train(List<Instance> instances) {

		probs = new ArrayList<>();
		for ( int i = 0 ; i < 62 ; i ++ ) {
			ProbChar prob = new ProbChar();
			probs.add(prob);
		}
		
		for ( Instance instance : instances ) {
			
			ClassificationLabel label = (ClassificationLabel) instance.getLabel();
			int charClass = label.getLabel() - 1;
			FeatureVector vector = instance.getFeatureVector();
			
			probs.get(charClass).add( this.parseFeatureVector(vector) );
			
		}
		
		for ( int i = 0; i < 62; i ++ ) {
			probs.get(i).finish();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.jhu.cs.cs475.finalproject.model.Predictor#predict(edu.jhu.cs.cs475
	 * .finalproject.model.Instance)
	 */
	@Override
	public Label predict(Instance instance) {
		
		FeatureVector vector = instance.getFeatureVector();
		String[] keys = this.parseFeatureVector(vector);
		
		int label = 0;
		double max = -10000000;
		for ( int i = 0; i < 62 ; i ++ ) {
			double prob = this.probs.get(i).getProb(keys);
			if ( prob > max ) {
				max = prob;
				label = i;
			}
		}
		
		return new ClassificationLabel( label + 1 );
		
	}

	public String[] parseFeatureVector(FeatureVector vector) {

		// Initialize call back string array
		String[] groupedFeatures = new String[16];
		String grouping = "";

		for (int i = 1; i <= 4 * 48; i++) {
			Integer key = new Integer(i);
			if (vector.getVector().keySet().contains(key)) {
				grouping += "1";
			} else {
				grouping += "0";
			}
		}

		for (int i = 0; i < 16; i++) {

			int bigGroup = i / 4;
			int smallGroup = i % 4;

			if (smallGroup == 0) {
				groupedFeatures[bigGroup * 4] = grouping.substring(
						48 * bigGroup, 48 * bigGroup + 8);
			} else if (smallGroup == 1) {
				groupedFeatures[bigGroup * 4 + 1] = grouping.substring(
						48 * bigGroup + 8, 48 * bigGroup + 16);
			} else if (smallGroup == 2) {
				groupedFeatures[bigGroup * 4 + 2] = grouping.substring(
						48 * bigGroup + 16, 48 * bigGroup + 32);
			} else {
				groupedFeatures[bigGroup * 4 + 3] = grouping.substring(
						48 * bigGroup + 32, 48 * bigGroup + 48);
			}

		}
		return groupedFeatures;
	}

	private class ProbChar implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private List<Map<String, Integer>> features = new ArrayList<>();
		private int count = 0;
		private boolean finished = false;

		public ProbChar() {
			for (int i = 0; i < 16; i++) {
				Map<String, Integer> featureGroup = new HashMap<>();
				features.add(featureGroup);
			}
		}

		/**
		 * Add a new key to this char features
		 * 
		 * @param keys
		 *            a string array parsed
		 */
		public void add(String[] keys) {

			this.count++;
			for (int i = 0; i < 16; i++) {
				if (features.get(i).containsKey(keys[i])) {
					int countOut = features.get(i).get(keys[i]) + 1;
					features.get(i).put(keys[i], countOut);
				} else {
					features.get(i).put(keys[i], 1);
				}
			}

		}

		/**
		 * Call this function to finish storage data
		 */
		public void finish() {
			finished = true;
		}

		/**
		 * return the log( p(x_(i) | c ) )
		 * 
		 * @param keys
		 *            a string array parsed by the feature vector
		 * @return the log prob.
		 */
		public double getProb(String[] keys) {

			if (this.finished) {

				double logProb = 0.0;
				for (int i = 0; i < 16; i++) {
					double ratio;
					if (features.get(i).containsKey(keys[i])) {
						ratio = (double) features.get(i).get(keys[i]) / count;
					} else {
						ratio = 0.0001;
					}
					logProb += Math.log(ratio);
				}

				return logProb;
			} else {
				throw new RuntimeException("RandomFerns: please call finish() first and then get probs");
			}
		}

	}

}
