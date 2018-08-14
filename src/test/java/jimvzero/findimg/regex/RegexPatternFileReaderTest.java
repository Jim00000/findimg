package jimvzero.findimg.regex;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class RegexPatternFileReaderTest {

	private final static Logger log = LogManager.getLogger(RegexPatternFileReaderTest.class);

	@DisplayName("Test reading default regex pattern file")
	@Test
	public void testcase1() {
		try {
			RegexPatternFileReader reader = new RegexPatternFileReader();
			reader.getPatterns();
		} catch (IOException e) {
			log.catching(e);
			Assertions.fail(e);
		}
	}

	@DisplayName("Test reading dummy regex pattern file (Filename : dummy, Type : String)")
	@Test
	public void testcase2() {
		try {
			final String filepath = RegexPatternFileReaderTest.class.getClassLoader()
					.getResource("testcases/regex/dummy").getPath();
			RegexPatternFileReader reader = new RegexPatternFileReader(filepath);
			reader.getPatterns();
		} catch (IOException e) {
			log.catching(e);
			Assertions.fail(e);
		}
	}

	@DisplayName("Test reading dummy regex pattern file (Filename : dummy, Type : File)")
	@Test
	public void testcase3() {
		try {
			final String filepath = RegexPatternFileReaderTest.class.getClassLoader()
					.getResource("testcases/regex/dummy").getPath();
			RegexPatternFileReader reader = new RegexPatternFileReader(new File(filepath));
			reader.getPatterns();
		} catch (IOException e) {
			log.catching(e);
			Assertions.fail(e);
		}
	}

	@DisplayName("Test reading non-existent file")
	@Test
	public void testcase4() {
		try {
			final String filepath = "testcases/regex/dummy-non-existent-only-for-testcases-usage";
			RegexPatternFileReader reader = new RegexPatternFileReader(new File(filepath));
			reader.getPatterns();
		} catch (IOException e) {
			return; // Success
		}
		
		Assertions.fail("This testcase should not come here");
	}

}
