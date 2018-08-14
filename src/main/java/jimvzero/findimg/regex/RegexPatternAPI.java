package jimvzero.findimg.regex;

import java.io.File;
import java.io.IOException;

public interface RegexPatternAPI {

	public void defaultRegPattern();

	public void addPattern(String pattern);

	public void readPatternFile(File file) throws IOException;

	public void readPatternFile(String filepath) throws IOException;
	
	public void clearAllPattern();

}
