/**
 * 
 */
package edu.jhu.cs.cs475.finalproject.model.test;

import org.junit.Test;

import edu.jhu.cs.cs475.finalproject.model.ExtractFeature;
import junit.framework.TestCase;

/**
 * This Junit test encloses feature extraction tests.
 * @author Yunlong Liu
 * @author Yijie   Li
 */
public class TestExtractFeature extends TestCase {
	
	private int[][] example = { { 0, 1, 0},
			{1, 0, 1},
			{0, 1, 1}
	};
	
	private int[][] resultE = { {0, 0, 0, 0},
			{0, 0, 1, 1},
			{0, 1, 2, 3},
			{0, 1, 3, 5}
	};
	
	@Test
	public void testI() {
		
		int[][] test = ExtractFeature.constructI( example );
		
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 3; j++) {

				assertEquals( resultE[i][j], test[i][j] );

			}
		}
		
	}
	

}
