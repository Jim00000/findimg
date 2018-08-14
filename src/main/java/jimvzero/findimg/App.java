package jimvzero.findimg;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jimvzero.findimg.download.DownloadManager;
import jimvzero.findimg.download.SerialDownloadManager;
import jimvzero.findimg.regex.RegexPatternManager;
import jimvzero.findimg.web.ImageParser;

public class App {

	private final static Logger log = LogManager.getLogger(App.class);

	public static void main(String[] args) {

		Options options = new Options();
		Option inputOption = new Option("i", "input", true, "Set input HTML webpage file path");
		inputOption.setRequired(true);
		inputOption.setType(String.class);
		options.addOption(inputOption);

		Option outputDirOption = new Option("o", "output-dir", true, "Set output image directory");
		outputDirOption.setRequired(true);
		outputDirOption.setType(String.class);
		options.addOption(outputDirOption);

		Option regexPatternOption = new Option("reg", "regex-pattern", true, "Set regular expression pattern");
		regexPatternOption.setRequired(false);
		regexPatternOption.setType(String.class);
		options.addOption(regexPatternOption);

		Option regexPatternFileOption = new Option("regf", "regex-pattern-file", true,
				"Set regular expression pattern file");
		regexPatternFileOption.setRequired(false);
		regexPatternFileOption.setType(String.class);
		options.addOption(regexPatternFileOption);

		Option useSerialNumberOption = new Option("sn", "use-serial-number", false,
				"use serial number as its filename");
		useSerialNumberOption.setRequired(false);
		useSerialNumberOption.setType(Boolean.class);
		options.addOption(useSerialNumberOption);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);

			final String inputFilePath = cmd.getOptionValue("input");
			log.debug("inputFilePath : " + inputFilePath);

			final String outputDirPath = cmd.getOptionValue("output-dir");
			log.debug("outputDirPath : " + outputDirPath);

			List<String> regexlist = null;

			final boolean useSerialNumber = cmd.hasOption("use-serial-number");
			log.debug("useSerialNumber : " + useSerialNumber);

			RegexPatternManager regexmanager = RegexPatternManager.getInstance();

			if (cmd.hasOption("regex-pattern-file") == true) {
				final String regexPatternFilePath = cmd.getOptionValue("regex-pattern-file");
				log.debug("Use regex pattern file " + regexPatternFilePath);
				regexmanager.readPatternFile(regexPatternFilePath);
				regexlist = regexmanager.getPatterns();
			} else {
				if (cmd.hasOption("regex-pattern") == true) {
					log.debug("Use user defined regex pattern(s)");
					regexlist = Arrays.asList(cmd.getOptionValue("regex-pattern").split(","));
				} else {
					log.debug("Use default regex pattern file");
					regexmanager.defaultRegPattern();
					regexlist = regexmanager.getPatterns();
				}
			}
			
			String html = FileUtils.readFileToString(new File(inputFilePath), Charset.defaultCharset());
			try {
				FileUtils.forceMkdir(new File(outputDirPath));
			} catch (IOException e) {
				log.catching(e);
			}

			ImageParser imgparser = new ImageParser();
			imgparser.parse(html);
			List<URL> urls = imgparser.getImage(regexlist);

			DownloadManager downloadManager = null;
			if (useSerialNumber == true) {
				downloadManager = SerialDownloadManager.getInstance(1);
			} else {
				downloadManager = DownloadManager.getInstance();
			}

			for (URL url : urls) {
				if (useSerialNumber == true) {
					downloadManager.download(url, outputDirPath);
				} else {
					final String filename = FilenameUtils.getName(url.getPath());
					downloadManager.download(url, Paths.get(outputDirPath, filename).toString());
				}
			}
			
			downloadManager.awaitDownloadComplete();

		} catch (ParseException e) {
			log.error(e.getMessage());
			formatter.printHelp("findimg", options);
			System.exit(1);
		} catch (IOException e) {
			log.catching(e);
		}

	}
}
