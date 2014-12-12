/**
 * 
 */
package edu.jhu.cs.cs475.finalproject.model;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;

/**
 * This class provide a command line main method for this project
 * @author Yunlong Liu
 * @author Yijie   Li
 */
public class RandomFernTester {
	
		static public LinkedList<Option> options = new LinkedList<Option>();
		
		public static void main(String[] args) {
			
			String[] manditory_args = {"data"};
			createCommandLineOptions();
			CommandLineUtilities.initCommandLineParameters(args, Classify.options, manditory_args);
			
			String data_file = CommandLineUtilities.getOptionValue("data");
			
			ReadImageToFeatureFile fileReader = new ReadImageToFeatureFile( "featuredata.txt" , data_file );
			try {
				fileReader.writeDataFile();
			} catch (IOException e) {
				throw new RuntimeException("Write to data file failed!");
			}
			
		}

		public static void learning(String data_file, int gd_iterations, double gd_eta) {
		}
		
		public static void registerOption(String option_name, String arg_name, boolean has_arg, String description) {
			OptionBuilder.withArgName(arg_name);
			OptionBuilder.hasArg(has_arg);
			OptionBuilder.withDescription(description);
			Option option = OptionBuilder.create(option_name);
			
			Classify.options.add(option);		
		}
		
		private static void createCommandLineOptions() {
			registerOption("data", "String", true, "The data to use.");
			registerOption("gd_eta", "double", true, "The constant scalar for learning rate.");
			registerOption("gd_iterations", "int", true, "The number of iterations.");
			// Other options will be added here.
		}

}
