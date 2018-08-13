package jimvzero.findimg.download;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import jimvzero.findimg.web.ImageParser;

public class SerialDownloadManagerTest {

	@Test
	public void testcase1() {
		try {
			String filepath = SerialDownloadManagerTest.class.getClassLoader().getResource("testcases/parse-case-1.html").getFile();
			final String html = FileUtils.readFileToString(new File(filepath), Charset.defaultCharset());
			ImageParser parser = new ImageParser();
			parser.parse(html);
			List<URL> urls = parser.getImage("https?:\\/\\/i\\.imgur\\.com\\/[0-9a-zA-Z]*\\.(jpg|jpeg|png)");
			DownloadManager down = new SerialDownloadManager(1);
			for(URL url : urls) {
				down.download(url, "pics/");
			}
			down.awaitDownloadComplete();
			down.close();
		} catch (IOException e) {
			Assertions.fail(e);
		}
	}
}
