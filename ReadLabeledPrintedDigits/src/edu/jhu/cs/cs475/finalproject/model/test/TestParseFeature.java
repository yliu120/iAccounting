package edu.jhu.cs.cs475.finalproject.model.test;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.jhu.cs.cs475.finalproject.model.FeatureVector;
import edu.jhu.cs.cs475.finalproject.model.RandomFerns;

/**
 * This class tests the parseFeature function in the Random Fern Classifier
 * @author Yunlong Liu
 * @author Yijie   Li
 */
public class TestParseFeature extends TestCase{
	
	private FeatureVector vector;
	private RandomFerns classifer;
	
	@Before
	public void setUp() {
		vector = new FeatureVector();
		vector.add(3, 1);
		vector.add(40, 1);
		vector.add(50, 1);
		vector.add(90, 1);
		vector.add(182, 1);
		
		classifer = new RandomFerns();
	}
	
	@Test
	public void testParseFeature() {
		String[] tests = classifer.parseFeatureVector(vector);
		assertEquals(tests[0], "00100000" );
		assertEquals(tests[3], "0000000100000000");
		assertEquals(tests[4], "01000000" );
		assertEquals(tests[15], "0000010000000000" );
	}
	

}
