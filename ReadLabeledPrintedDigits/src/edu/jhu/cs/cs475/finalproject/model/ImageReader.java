/**
 * 
 */
package edu.jhu.cs.cs475.finalproject.model;

import java.awt.image.BufferedImage;

/**
 * This class encloses static method that reads Image
 * @author Yunlong Liu
 * @author Yijie   Li
 */
public final class ImageReader {
	
	public static int[][] readGreyImage( BufferedImage image ) {
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		int[][] exampleData = new int[ width ][ height ];
		for ( int m = 0 ; m < width; m ++ ) {
			for ( int n = 0; n < height; n ++ ) {
				exampleData[m][n] = ( image.getRGB(m, n) < -10 ) ? 1 : 0;
			}
		}
		
		return exampleData;
		
	}

}
