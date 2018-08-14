package jimvzero.findimg.regex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegexPatternFileReader {
	
	private final static Logger log = LogManager.getLogger(RegexPatternFileReader.class);
	private List<String> patterns;
	
	public RegexPatternFileReader() throws IOException {
		patterns = new LinkedList<>();
		addToPatterns(DefaultRegexPattern.getPatterns());
	}

	public RegexPatternFileReader(String path) throws IOException {
		this(new File(path));
	}

	public RegexPatternFileReader(File file) throws IOException {
		patterns = new LinkedList<>();
		if (file.exists() == false)
			throw new FileNotFoundException(file.toString());
		handleRegexPatternFile(file);
	}
	
	protected void handleRegexPatternFile(File file) throws IOException {
		log.info("Read " + file);
		List<String> patternlist = FileUtils.readLines(file, Charset.defaultCharset());
		addToPatterns(patternlist);
	}
	
	private void addToPatterns(List<String> patternlist) {
		for(String pattern : patternlist) {
			this.patterns.add(pattern);
			log.info("Read regex pattern " + pattern);
		}
	}

	public synchronized final List<String> getPatterns() {
		return patterns;
	}
	
}
