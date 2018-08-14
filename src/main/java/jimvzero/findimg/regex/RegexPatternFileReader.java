package jimvzero.findimg.regex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegexPatternFileReader {
	
	private final static Logger log = LogManager.getLogger(RegexPatternFileReader.class);
	private final static String DEFAULT_REGEX_PATTERN_FILENAME = "defaultRegexPattern";
	private final static String DEFAULT_REGEX_PATTERN_FILE_PATH_STRING = RegexPatternFileReader.class.getClassLoader()
			.getResource(DEFAULT_REGEX_PATTERN_FILENAME).getFile();
	private List<String> patterns;
	
	public RegexPatternFileReader() throws IOException {
		this(DEFAULT_REGEX_PATTERN_FILE_PATH_STRING);
	}

	public RegexPatternFileReader(String path) throws IOException {
		this(new File(path));
	}

	public RegexPatternFileReader(File file) throws IOException {
		if (file.exists() == false)
			throw new FileNotFoundException();
		handleRegexPatternFile(file);
	}
	
	protected void handleRegexPatternFile(File file) throws IOException {
		log.info("Read " + file);
		patterns = FileUtils.readLines(file, Charset.defaultCharset());
		for(String pattern : patterns) {
			log.info("Read regex pattern " + pattern);
		}
	}

	public synchronized final List<String> getPatterns() {
		return patterns;
	}
	
}
