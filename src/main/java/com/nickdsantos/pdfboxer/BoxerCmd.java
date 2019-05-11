package com.nickdsantos.pdfboxer;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class BoxerCmd {
	public static void main(String[] args) {
		Option trimBoxOpt = Option.builder("trimBox").hasArg()
				.desc("Sets the TrimBox. Must be passed as x,y,width,height")
				.build();

		Option artBoxOpt = Option.builder("artBox").hasArg()
				.desc("Sets the ArtBox. Must be passed as x,y,width,height")
				.build();

		Option bleedBoxOpt = Option.builder("bleedBox").hasArg()
				.desc("Sets the BleedBox. Must be passed as x,y,width,height")
				.build();

		Option cropBoxOpt = Option.builder("cropBox").hasArg()
				.desc("Sets the CropBox. Must be passed as x,y,width,height")
				.build();

		Option sourceFileOpt = Option.builder("sourceFile").hasArg().required()
				.desc("The source pdf").build();

		Option destFileOpt = Option.builder("destFile").hasArg().required()
				.desc("The destination pdf").build();

		Options options = new Options();
		options.addOption(trimBoxOpt);
		options.addOption(artBoxOpt);
		options.addOption(bleedBoxOpt);
		options.addOption(cropBoxOpt);
		options.addOption(sourceFileOpt);
		options.addOption(destFileOpt);

		CommandLineParser parser = new DefaultParser();
		String sourceFileVal = null;
		String destFileVal = null;
		String[] trimBoxVal = null;
		String[] artBoxVal = null;
		String[] bleedBoxVal = null;
		String[] cropBoxVal = null;
		Boxer boxer = null;

		try {
			CommandLine cmdLine = parser.parse(options, args);
			System.out.println(sourceFileOpt);
			System.out.println(destFileOpt);

			if (cmdLine.hasOption("trimBox")) {
				trimBoxVal = cmdLine.getOptionValue("trimBox").split(",", -1);
			}

			if (cmdLine.hasOption("artBox")) {
				artBoxVal = cmdLine.getOptionValue("artBox").split(",", -1);
			}

			if (cmdLine.hasOption("bleedBox")) {
				bleedBoxVal = cmdLine.getOptionValue("bleedBox").split(",", -1);
			}

			if (cmdLine.hasOption("cropBox")) {
				cropBoxVal = cmdLine.getOptionValue("cropBox").split(",", -1);
			}

			sourceFileVal = cmdLine.getOptionValue("sourceFile");
			destFileVal = cmdLine.getOptionValue("destFile");
		} catch (MissingOptionException me) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.setOptionComparator(null);
			formatter.printHelp("boxer", options);
			return;
		} catch (ParseException e) {
			System.err.printf("Unable to parse command; %s\n", e.toString());
		}

		try {
			boxer = new Boxer(sourceFileVal, destFileVal);
		} catch (IOException e) {
			System.err.printf("Unable to open file %s\n", sourceFileVal);
		}

		if (trimBoxVal != null) {
			boxer.setTrimBox(Float.parseFloat(trimBoxVal[0]),
					Float.parseFloat(trimBoxVal[1]),
					Float.parseFloat(trimBoxVal[2]),
					Float.parseFloat(trimBoxVal[3]));
		}

		if (artBoxVal != null) {
			boxer.setArtBox(Float.parseFloat(artBoxVal[0]),
					Float.parseFloat(artBoxVal[1]),
					Float.parseFloat(artBoxVal[2]),
					Float.parseFloat(artBoxVal[3]));
		}

		if (bleedBoxVal != null) {
			boxer.setBleedBox(Float.parseFloat(bleedBoxVal[0]),
					Float.parseFloat(bleedBoxVal[1]),
					Float.parseFloat(bleedBoxVal[2]),
					Float.parseFloat(bleedBoxVal[3]));
		}

		if (cropBoxVal != null) {
			boxer.setCropBox(Float.parseFloat(cropBoxVal[0]),
					Float.parseFloat(cropBoxVal[1]),
					Float.parseFloat(cropBoxVal[2]),
					Float.parseFloat(cropBoxVal[3]));
		}

		try {
			boxer.save();
		} catch (IOException e) {
			System.err.printf("Unable to save file %s\n", destFileVal);
		}
	}
}
