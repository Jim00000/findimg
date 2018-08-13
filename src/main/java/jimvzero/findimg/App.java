package jimvzero.findimg;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Paths;
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
import jimvzero.findimg.web.ImageParser;

public class App {

	private final static Logger log = LogManager.getLogger(App.class);

	public static void main(String[] args) {

		Options options = new Options();
		Option inputOption = new Option("i", "input", true, "input HTML webpage file path");
		inputOption.setRequired(true);
		inputOption.setType(String.class);
		options.addOption(inputOption);

		Option outputDirOption = new Option("o", "output-dir", true, "output image directory");
		outputDirOption.setRequired(true);
		outputDirOption.setType(String.class);
		options.addOption(outputDirOption);

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
			final String outputDirPath = cmd.getOptionValue("output-dir");
			final boolean useSerialNumber = cmd.hasOption("use-serial-number");
			
			log.debug("inputFilePath : " + inputFilePath);
			log.debug("outputDirPath : " + outputDirPath);
			log.debug("useSerialNumber : " + useSerialNumber);

			String html = FileUtils.readFileToString(new File(inputFilePath), Charset.defaultCharset());
			try {
				FileUtils.forceMkdir(new File(outputDirPath));
			} catch (IOException e) {
				log.catching(e);
			}

			ImageParser imgparser = new ImageParser();
			imgparser.parse(html);
			List<URL> urls = imgparser.getImage("https?:\\/\\/i\\.imgur\\.com\\/[0-9a-zA-Z]*\\.(jpg|jpeg|png)");

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

			downloadManager.close();

		} catch (ParseException e) {
			log.error(e.getMessage());
			formatter.printHelp("findimg", options);
			System.exit(1);
		} catch (IOException e) {
			log.catching(e);
		}

	}
}
