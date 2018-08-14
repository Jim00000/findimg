package jimvzero.findimg.regex;

import java.util.Arrays;
import java.util.List;

public class DefaultRegexPattern {

	private final static List<String> patterns = Arrays.asList(
		"https?://i\\.imgur\\.com/[0-9a-zA-Z]*\\.([Jj][Pp][Ee]?[Gg]|[Pp][Nn][Gg])"	
	);

	public static synchronized final List<String> getPatterns() {
		return patterns;
	}
	
}
