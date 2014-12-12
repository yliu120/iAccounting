package edu.jhu.cs.cs475.finalproject.model.test;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.jhu.cs.cs475.finalproject.model.ReadImageToFeatureFile;

public class TestFeatureVectorConstruction extends TestCase {
	
	private ReadImageToFeatureFile object;
	
	@Before
	public void setUp() {
		this.object = new ReadImageToFeatureFile();
	}
	
	@Test
	public void testFeatureString() {
		int[] test = {0, 1, 0, 0, 1, 0};
		String result = this.object.featureStringConstruction(test);
		
	}

}
