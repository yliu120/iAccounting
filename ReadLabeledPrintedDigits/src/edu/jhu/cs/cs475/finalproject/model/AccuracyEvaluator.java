package edu.jhu.cs.cs475.finalproject.model;

import java.util.List;

public class AccuracyEvaluator extends Evaluator  {
	
	private int size = 0;
	private int count = 0;

	@Override
	public double evaluate(List<Instance> instances, Predictor predictor) {
		// TODO Auto-generated method stub
		int size = instances.size();
		double count = 0.0;
		
		for ( Instance instance : instances ){
			ClassificationLabel label1 = ( ClassificationLabel ) instance.getLabel();
			ClassificationLabel label2 = ( ClassificationLabel ) predictor.predict(instance);
			if ( label1.equals( label2 ) )
			{
				count ++;
			}
		}
		
		this.size = size;
		this.count = (int) count;
		
		return (double) count/size;
	}

	public int getSize() {
		return size;
	}

	public int getCount() {
		return count;
	}
	
	
}