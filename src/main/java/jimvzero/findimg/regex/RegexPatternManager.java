package jimvzero.findimg.regex;

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
		if((patterns.toArray() instanceof String[]) == false)
			assert false : "patterns must be String[] type";
		return (String[]) patterns.toArray();
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
	
	public synchronized static RegexPatternManager getInstance() {
		if(instance == null) 
			instance = new RegexPatternManager();
		return instance;
	}


	
}
