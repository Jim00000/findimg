package jimvzero.findimg.regex;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class RegexPatternManagerTest {

	private final static Logger log = LogManager.getLogger(RegexPatternManagerTest.class);
	
	@DisplayName("Test reading default regex pattern file")
	@Test
	public void testcase1() {
		RegexPatternManager manager = RegexPatternManager.getInstance();
		manager.defaultRegPattern();
		manager.clearAllPattern();
	}
	
	@DisplayName("Test adding pattern")
	@Test
	public void testcase2() {
		RegexPatternManager manager = RegexPatternManager.getInstance();
		manager.addPattern("https?:\\/\\/i\\.imgur\\.com\\/[0-9a-zA-Z-_]*\\.([Jj][Pp][Ee]?[Gg]|[Pp][Nn][Gg])");
		manager.clearAllPattern();
	}
	
	@DisplayName("Test readinf regex pattern file (File : dummy, Type : String)")
	@Test
	public void testcase3() {
		try {
			final String filepath = RegexPatternFileReaderTest.class.getClassLoader().getResource("testcases/regex/dummy").getPath();
			RegexPatternManager manager = RegexPatternManager.getInstance();
			manager.readPatternFile(filepath);
			manager.clearAllPattern();
		} catch (IOException e) {
			log.catching(e);
			Assertions.fail(e);
		}
	}
	
	@DisplayName("Test readinf regex pattern file (File : dummy, Type : File)")
	@Test
	public void testcase4() {
		try {
			final String filepath = RegexPatternFileReaderTest.class.getClassLoader().getResource("testcases/regex/dummy").getPath();
			RegexPatternManager manager = RegexPatternManager.getInstance();
			manager.readPatternFile(new File(filepath));
			manager.clearAllPattern();
		} catch (IOException e) {
			log.catching(e);
			Assertions.fail(e);
		}
	}
	
}
