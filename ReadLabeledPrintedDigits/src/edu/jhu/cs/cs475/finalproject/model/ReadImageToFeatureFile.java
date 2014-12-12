package edu.jhu.cs.cs475.finalproject.model;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class read the dataset to a feature data file
 * 
 * @author Yunlong Liu
 * @author Yijie Li
 */
public class ReadImageToFeatureFile {

	/* the data file we want to write */
	private String dataFileName;
	private File dataFile;

	/*
	 * the data folder in format: Data/Label/example.png Note that the subfolder
	 * name is the label for each example.
	 */
	private String dataFolderName;
	private File dataFolder;
	private int size = 100;

	/**
	 * Constructor for testing
	 */
	public ReadImageToFeatureFile() {
		super();
	}

	/**
	 * Constructor using data folder name and the data file name you would like
	 * to write.
	 * 
	 * @param dataFileName
	 *            data file name
	 * @param dataFolder
	 *            data folder
	 */
	public ReadImageToFeatureFile(String dataFileName, String dataFolder) {
		super();
		this.dataFileName = dataFileName;
		this.dataFolderName = dataFolder;
		this.dataFolder = new File(this.dataFolderName);
		this.dataFile = new File(this.dataFileName);
	}
	
	public ReadImageToFeatureFile(String dataFileName, String dataFolder, int size){
		this.dataFileName = dataFileName;
		this.dataFolderName = dataFolder;
		this.dataFolder = new File(this.dataFolderName);
		this.dataFile = new File(this.dataFileName);
		this.size = size;
	}

	public void writeDataFile() throws IOException {

		String[] filename = this.dataFolder.list();
		BufferedWriter output = new BufferedWriter(
				new FileWriter(this.dataFile));
		for (String label : filename) {

			if (label.contains("Sample")) {

				String path = this.dataFolderName + File.separator + label;
				String realLabel = label.substring(7);
				if (realLabel.charAt(0) == '0') {
					realLabel = realLabel.substring(1);
				}
				String[] exampleName = (new File(path)).list();

				for (int i = 0; i < size; i++) {

					if (exampleName[i].contains("img")) {
						/* Here we deal with each example */
						String examplePath = path + File.separator
								+ exampleName[i];
						System.out.println("Reading File: " + examplePath);
						BufferedImage exampleImage = ImageIO.read(new File(
								examplePath));

						// For our data set the png image is 128 * 128;
						String featureString = this
								.featureStringConstruction(ExtractFeature
										.extractIntBinary(ImageReader
												.readGreyImage(exampleImage)));
						
						output.write( realLabel + " " + featureString );
					}

				}
			}
		}

		output.flush();
		output.close();
	}

	// TODO refactor this function to other class
	/**
	 * This function parse the char label to its ASCII
	 * 
	 * @param originLabel
	 * @return ASCII of the origin label Say 0 - 9 -> 0 - 9 A - Z -> 65 - ..
	 */
	public int labelToInt(String originLabel) {

		int l = Integer.parseInt(originLabel);
		if (1 <= l && l <= 10) {
			return l - 1;
		} else if (11 <= l && 10 <= 36) {
			return (l + 54);
		} else if (37 <= l && l <= 62) {
			return (l + 60);
		} else {
			return -1;
		}

	}

	public String featureStringConstruction(int[] featureVector) {

		String vector = "";
		for (int i = 0; i < featureVector.length; i++) {
			if (featureVector[i] != 0) {
				vector += (i + 1);
				vector += ":" + featureVector[i] + " ";
			}
		}
		int lastIndex = vector.length() - 1;
		return vector.substring(0, lastIndex) + "\n";

	}
}
