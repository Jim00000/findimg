package jimvzero.findimg.regex;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegexPatternManager implements RegexPatternAPI {

	private final static Logger log = LogManager.getLogger(RegexPatternManager.class);
	private static RegexPatternManager instance;
	private List<String> patterns;
	
	protected RegexPatternManager() {
		patterns = new LinkedList<>();
	}
	
	public synchronized final List<String> getPatterns() {
		return patterns;
	}
	
	public synchronized final String[] getPatternArray() {
		String[] patternArray = new String[patterns.size()];
		patternArray = patterns.toArray(patternArray);
		return patternArray;
	}
	
	@Override
	public void defaultRegPattern() {
		try {
			RegexPatternFileReader fileReader = new RegexPatternFileReader();
			patterns = fileReader.getPatterns();
		} catch (IOException e) {
			log.catching(e);
			assert false : "Default Regex Pattern must work well, Check whether the file is missing";
		}
	};
	
	@Override
	public void addPattern(String pattern) {
		patterns.add(pattern);
	}
	
	@Override
	public void readPatternFile(File file) throws IOException {
		RegexPatternFileReader reader = new RegexPatternFileReader(file);
		patterns = reader.getPatterns();
	}
	
	@Override
	public void readPatternFile(String filepath) throws IOException {
		readPatternFile(new File(filepath));
	}
	
	@Override
	public void clearAllPattern() {
		patterns.clear();
	}
	
	public synchronized static RegexPatternManager getInstance() {
		if(instance == null) 
			instance = new RegexPatternManager();
		return instance;
	}
	
}
