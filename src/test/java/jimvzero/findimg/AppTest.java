package jimvzero.findimg;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jimvzero.findimg.web.ImageParser;

public class AppTest {
	
	private ImageParser parser;
	
	@BeforeEach
	void init() {
		parser = new ImageParser();
	}
	
	@DisplayName("testcase - parse-case-1.html")
	@Test
	void testcase1() {
		try {
			String filepath = AppTest.class.getClassLoader().getResource("testcases/parse-case-1.html").getFile();
			final String html = FileUtils.readFileToString(new File(filepath), Charset.defaultCharset());
			parser.parse(html);
			List<URL> urls = parser.getImage("https?:\\/\\/i\\.imgur\\.com\\/[0-9a-zA-Z]*\\.(jpg|jpeg|png)");
			Assertions.assertEquals(9, urls.size());
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}
	
	@DisplayName("testcase - parse-case-2.html")
	@Test
	void testcase2() {
		try {
			String filepath = AppTest.class.getClassLoader().getResource("testcases/parse-case-2.html").getFile();
			final String html = FileUtils.readFileToString(new File(filepath), Charset.defaultCharset());
			parser.parse(html);
			List<URL> urls = parser.getImage("https?:\\/\\/i\\.imgur\\.com\\/[0-9a-zA-Z]*\\.(jpg|jpeg|png)");
			Assertions.assertEquals(11, urls.size());
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}
	
	@DisplayName("testcase - parse-case-3.html")
	@Test
	void testcase3() {
		try {
			String filepath = AppTest.class.getClassLoader().getResource("testcases/parse-case-3.html").getFile();
			final String html = FileUtils.readFileToString(new File(filepath), Charset.defaultCharset());
			parser.parse(html);
			List<URL> urls = parser.getImage("https?://truth\\.bahamut\\.com\\.tw/[0-9a-zA-Z/]*\\.([jJ][pP][gG]|[pP][nN][gG])");
			Assertions.assertEquals(14, urls.size());
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}

}
