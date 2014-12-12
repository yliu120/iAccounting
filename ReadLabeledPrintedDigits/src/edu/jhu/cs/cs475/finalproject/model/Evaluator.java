package edu.jhu.cs.cs475.finalproject.model;

import java.util.List;

public abstract class Evaluator {

	public abstract double evaluate(List<Instance> instances, Predictor predictor);

}
