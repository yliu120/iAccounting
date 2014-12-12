/**
 * 
 */
package edu.jhu.cs.cs475.finalproject.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

/**
 * This class provide a command line main method for this project
 * 
 * @author Yunlong Liu
 * @author Yijie Li
 */
public class RandomFernTester {

	static public LinkedList<Option> options = new LinkedList<Option>();

	public static void main(String[] args) throws FileNotFoundException {

		String[] manditory_args = {};
		createCommandLineOptions();
		CommandLineUtilities.initCommandLineParameters(args, Classify.options,
				manditory_args);

		String data_file = CommandLineUtilities.getOptionValue("data");
		int size;
		ReadImageToFeatureFile fileReader;
		String prediction = CommandLineUtilities.getOptionValue("prediction");
		String model = CommandLineUtilities.getOptionValue("model");

		String mode = CommandLineUtilities.getOptionValue("mode");

		if (mode.equalsIgnoreCase("train")) {
			
			if (CommandLineUtilities.hasArg("size")) {

				size = CommandLineUtilities.getOptionValueAsInt("size");
				fileReader = new ReadImageToFeatureFile("featuredata.txt",
						data_file, size);
			} else {
				fileReader = new ReadImageToFeatureFile("featuredata.txt",
						data_file);
			}
			
			try {
				fileReader.writeDataFile();
			} catch (IOException e) {
				throw new RuntimeException("Write to data file failed!");
			}
			
			// Load the training data.
			DataReader data_reader = new DataReader("featuredata.txt", true);
			List<Instance> instances = data_reader.readData();
			data_reader.close();

			// Train the model.
			RandomFerns predictor = new RandomFerns();
			predictor.train(instances);
			saveObject(predictor, model);

		} else if (mode.equalsIgnoreCase("test")) {
			// Load the test data.
			DataReader data_reader = new DataReader("testdata.txt", true);
			List<Instance> instances = data_reader.readData();
			data_reader.close();

			// Load the model.
			Predictor predictor = (Predictor) loadObject(model);

			try {
				evaluateAndSavePredictions(predictor, instances, prediction);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Requires mode argument.");
		}

	}

	public static void saveObject(Object object, String file_name) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(new File(
							file_name))));
			oos.writeObject(object);
			oos.close();
		} catch (IOException e) {
			System.err
					.println("Exception writing file " + file_name + ": " + e);
		}
	}

	/**
	 * Load a single object from a filename.
	 * 
	 * @param file_name
	 * @return
	 */
	public static Object loadObject(String file_name) {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream(new File(file_name))));
			Object object = ois.readObject();
			ois.close();
			return object;
		} catch (IOException e) {
			System.err.println("Error loading: " + file_name);
		} catch (ClassNotFoundException e) {
			System.err.println("Error loading: " + file_name);
		}
		return null;
	}

	public static void registerOption(String option_name, String arg_name,
			boolean has_arg, String description) {
		OptionBuilder.withArgName(arg_name);
		OptionBuilder.hasArg(has_arg);
		OptionBuilder.withDescription(description);
		Option option = OptionBuilder.create(option_name);

		Classify.options.add(option);
	}

	private static void createCommandLineOptions() {
		registerOption("data", "String", true, "The data to use.");
		registerOption(
				"size",
				"int",
				true,
				"How many instances you want to use in the database for each character to train.");
		registerOption("prediction", "String", true,
				"Write to a prediction file");
		// Other options will be added here.
		registerOption("mode", "String", true, "The mode: test/train");
		registerOption("model", "String", true, "Model file");
	}

	private static void evaluateAndSavePredictions(Predictor predictor,
			List<Instance> instances, String predictions_file)
			throws IOException {
		PredictionsWriter writer = new PredictionsWriter(predictions_file);
		// TODO Evaluate the model if labels are available.

		Boolean isLabelOn = true;

		for (Instance instance : instances) {
			if (instance.getLabel() == null) {
				isLabelOn = false;
			}
			Label label = predictor.predict(instance);
			writer.writePrediction(label);
		}

		// Print the evaluation label to System.out
		if (isLabelOn) {
			AccuracyEvaluator eval = new AccuracyEvaluator();
			double accuracy = eval.evaluate(instances, predictor);

			// HERE! Print out the accuracy to System.out
			System.out.println("Accuracy: " + accuracy + " (" + eval.getCount()
					+ "/" + eval.getSize() + ")");

		}
		writer.close();

	}
}
