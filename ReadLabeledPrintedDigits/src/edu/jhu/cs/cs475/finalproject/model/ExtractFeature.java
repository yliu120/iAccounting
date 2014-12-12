/**
 * 
 */
package edu.jhu.cs.cs475.finalproject.model;

/**
 * This java file is simply for extract features in each image file
 * 
 * @author Yunlong Liu
 * @author Yijie Li
 */
public final class ExtractFeature {

	public static int[] extractIntBinary(int[][] example) {

		int[][] I = constructI(example);
		int x = I.length - 1;
		int y = I[0].length - 1;

		if (x % 8 != 0 || y % 8 != 0) {
			throw new RuntimeException(
					"ExtractFeature->extractInt: the example should have width and height of 2^(n+3), n>=0");
		}

		if (x < 8 || y < 8) {
			throw new RuntimeException("example should be at least 8*8 ");
		}

		// Use 2-rec, 4-rectangle features of Viola-Jones
		// Here we only construct 32 features in total
		// 2-rec -> 16 features; 4-rec -> 32 coupling features
		int[] binaryFeature = new int[4 * 16 + 4 * 32];

		// Extracting features
		int xIncr = x / 8;
		int yIncr = y / 8;
		int next = 0;

		// 2-rec vertical
		for (int i = 0; i < 8; i++) {

			int part1 = I[x / 2][yIncr * (i + 1)] - I[x / 2][yIncr * i];
			int part2 = I[x][yIncr * (i + 1)] - I[x][yIncr * i];
			int diff = part2 - part1 - part1;
			binaryFeature[next] = (diff >= 0 && diff <= 200) ? 1 : 0;
			binaryFeature[next + 48] = (diff >= -200 && diff <= 0) ? 1 : 0;
			binaryFeature[next + 96] = (diff >= 200 && diff <= 400) ? 1 : 0;
			binaryFeature[next + 144] = (diff >= 400 && diff <= 900) ? 1 : 0;
			
			next++;

		}

		// 2-rec horizontal
		for (int i = 0; i < 8; i++) {

			int part1 = I[xIncr * (i + 1)][y / 2] - I[xIncr * i][y / 2];
			int part2 = I[xIncr * (i + 1)][y] - I[xIncr * i][y];
			int diff = part2 - part1 - part1;
			binaryFeature[next] = (diff >= 0 && diff <= 200) ? 1 : 0;
			binaryFeature[next + 48] = (diff >= -200 && diff <= 0) ? 1 : 0;
			binaryFeature[next + 96] = (diff >= 200 && diff <= 400) ? 1 : 0;
			binaryFeature[next + 144] = (diff >= 400 && diff <= 900) ? 1 : 0;
			next++;

		}

		// 3-rec - coupling horizontal
		xIncr = x / 4;
		yIncr = y / 8;
		for (int i = 1; i <= 8; i++) {

			int part1 = I[xIncr * 1][yIncr * i] - I[xIncr * 1][yIncr * (i - 1)];
			int part2 = I[xIncr * 2][yIncr * i] - I[xIncr * 2][yIncr * (i - 1)]
					- I[xIncr * 1][yIncr * i] + I[xIncr * 1][yIncr * (i - 1)];
			int part3 = I[xIncr * 3][yIncr * i] - I[xIncr * 3][yIncr * (i - 1)]
					- I[xIncr * 2][yIncr * i] + I[xIncr * 2][yIncr * (i - 1)];
			int part4 = I[xIncr * 4][yIncr * i] - I[xIncr * 4][yIncr * (i - 1)]
					- I[xIncr * 3][yIncr * i] + I[xIncr * 3][yIncr * (i - 1)];
			
			int diff1 = part3 + part1 - part2;
			int diff2 = part4 + part2 - part3;
			
			binaryFeature[next] = (diff1 >= 0 && diff1 <= 200) ? 1 : 0;
			binaryFeature[next + 48] = (diff1 >= -200 && diff1 <= 0) ? 1 : 0;
			binaryFeature[next + 96] = (diff1 >= 200 && diff1 <= 400) ? 1 : 0;
			binaryFeature[next + 144] = (diff1 >= 400 && diff1 <= 900) ? 1 : 0;
			next++;
			binaryFeature[next] = (diff2 >= 0 && diff2 <= 200) ? 1 : 0;
			binaryFeature[next + 48] = (diff2 >= -200 && diff2 <= 0) ? 1 : 0;
			binaryFeature[next + 96] = (diff2 >= 200 && diff2 <= 400) ? 1 : 0;
			binaryFeature[next + 144] = (diff2 >= 400 && diff2 <= 900) ? 1 : 0;
			next++;

		}

		// 3-rec - coupling vertical
		xIncr = x / 8;
		yIncr = y / 4;
		for (int i = 1; i <= 8; i++) {

			int part1 = I[xIncr * i][yIncr * 1] - I[xIncr * (i-1)][yIncr * 1];
			int part2 = I[xIncr * i][yIncr * 2] - I[xIncr * (i-1)][yIncr * 2]
					- I[xIncr * i][yIncr * 1] + I[xIncr * (i-1)][yIncr * 1];
			int part3 = I[xIncr * i][yIncr * 3] - I[xIncr * (i-1)][yIncr * 3]
					- I[xIncr * i][yIncr * 2] + I[xIncr * (i-1)][yIncr * 2];			
			int part4 = I[xIncr * i][yIncr * 4] - I[xIncr * (i-1)][yIncr * 4]
							- I[xIncr * i][yIncr * 3] + I[xIncr * (i-1)][yIncr * 3];

			int diff1 = part3 + part1 - part2;
			int diff2 = part4 + part2 - part3;
			
			binaryFeature[next] = (diff1 >= 0 && diff1 <= 200) ? 1 : 0;
			binaryFeature[next + 48] = (diff1 >= -200 && diff1 <= 0) ? 1 : 0;
			binaryFeature[next + 96] = (diff1 >= 200 && diff1 <= 400) ? 1 : 0;
			binaryFeature[next + 144] = (diff1 >= 400 && diff1 <= 900) ? 1 : 0;
			next++;
			binaryFeature[next] = (diff2 >= 0 && diff2 <= 200) ? 1 : 0;
			binaryFeature[next + 48] = (diff2 >= -200 && diff2 <= 0) ? 1 : 0;
			binaryFeature[next + 96] = (diff2 >= 200 && diff2 <= 400) ? 1 : 0;
			binaryFeature[next + 144] = (diff2 >= 400 && diff2 <= 900) ? 1 : 0;
			next++;

		}

		// 4-rec - coupling TODO

		return binaryFeature;

	}
	
	public static int[] extractInt(int[][] example) {

		int[][] I = constructI(example);
		int x = I.length - 1;
		int y = I[0].length - 1;

		if (x % 8 != 0 || y % 8 != 0) {
			throw new RuntimeException(
					"ExtractFeature->extractInt: the example should have width and height of 2^(n+3), n>=0");
		}

		if (x < 8 || y < 8) {
			throw new RuntimeException("example should be at least 8*8 ");
		}

		// Use 2-rec, 4-rectangle features of Viola-Jones
		// Here we only construct 32 features in total
		// 2-rec -> 16 features; 4-rec -> 32 coupling features
		int[] binaryFeature = new int[48];

		// Extracting features
		int xIncr = x / 8;
		int yIncr = y / 8;
		int next = 0;

		// 2-rec vertical
		for (int i = 0; i < 8; i++) {

			int part1 = I[x / 2][yIncr * (i + 1)] - I[x / 2][yIncr * i];
			int part2 = I[x][yIncr * (i + 1)] - I[x][yIncr * i];
			binaryFeature[next] = part2 - part1 - part1;
			next++;

		}

		// 2-rec horizontal
		for (int i = 0; i < 8; i++) {

			int part1 = I[xIncr * (i + 1)][y / 2] - I[xIncr * i][y / 2];
			int part2 = I[xIncr * (i + 1)][y] - I[xIncr * i][y];
			binaryFeature[next] = part2 - part1 - part1;
			next++;

		}

		// 3-rec - coupling horizontal
		xIncr = x / 4;
		yIncr = y / 8;
		for (int i = 1; i <= 8; i++) {

			int part1 = I[xIncr * 1][yIncr * i] - I[xIncr * 1][yIncr * (i - 1)];
			int part2 = I[xIncr * 2][yIncr * i] - I[xIncr * 2][yIncr * (i - 1)]
					- I[xIncr * 1][yIncr * i] + I[xIncr * 1][yIncr * (i - 1)];
			int part3 = I[xIncr * 3][yIncr * i] - I[xIncr * 3][yIncr * (i - 1)]
					- I[xIncr * 2][yIncr * i] + I[xIncr * 2][yIncr * (i - 1)];
			int part4 = I[xIncr * 4][yIncr * i] - I[xIncr * 4][yIncr * (i - 1)]
					- I[xIncr * 3][yIncr * i] + I[xIncr * 3][yIncr * (i - 1)];

			binaryFeature[next] = part3 + part1 - part2;
			next++;
			binaryFeature[next] = part4 + part2 - part3;
			next++;

		}

		// 3-rec - coupling vertical
		xIncr = x / 8;
		yIncr = y / 4;
		for (int i = 1; i <= 8; i++) {

			int part1 = I[xIncr * i][yIncr * 1] - I[xIncr * (i-1)][yIncr * 1];
			int part2 = I[xIncr * i][yIncr * 2] - I[xIncr * (i-1)][yIncr * 2]
					- I[xIncr * i][yIncr * 1] + I[xIncr * (i-1)][yIncr * 1];
			int part3 = I[xIncr * i][yIncr * 3] - I[xIncr * (i-1)][yIncr * 3]
					- I[xIncr * i][yIncr * 2] + I[xIncr * (i-1)][yIncr * 2];			
			int part4 = I[xIncr * i][yIncr * 4] - I[xIncr * (i-1)][yIncr * 4]
							- I[xIncr * i][yIncr * 3] + I[xIncr * (i-1)][yIncr * 3];

			binaryFeature[next] = part3 + part1 - part2;
			next++;
			binaryFeature[next] = part4 + part2 - part3;
			next++;

		}

		// 4-rec - coupling TODO

		return binaryFeature;

	}

	public static int[][] constructI(int[][] example) {

		int x, y;
		x = example.length;
		if (example.length > 0) {
			y = example[0].length;
		} else {
			y = 0;
			throw new RuntimeException(
					"ExtractFeature->constructI(): This is not 2-d array");
		}

		int[][] I = new int[x + 1][y + 1];
		I[0][0] = example[0][0];

		for (int i = 0; i <= x; i++) {
			I[i][0] = 0;
		}

		for (int j = 0; j <= y; j++) {
			I[0][j] = 0;
		}

		for (int i = 1; i <= x; i++) {
			for (int j = 1; j <= y; j++) {

				I[i][j] = example[i - 1][j - 1] + I[i - 1][j] + I[i][j - 1]
						- I[i - 1][j - 1];

			}
		}

		return I;
	}
}
