package jimvzero.findimg.download;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import jimvzero.findimg.web.ImageParser;

public class SerialDownloadManagerTest {

	private final static Logger log = LogManager.getLogger(SerialDownloadManagerTest.class);
	
	@Test
	public void testcase1() {
		Queue<Path> tmpfileQueue = new LinkedList<>();
		try {
			String filepath = SerialDownloadManagerTest.class.getClassLoader().getResource("testcases/parse-case-1.html").getFile();
			final String html = FileUtils.readFileToString(new File(filepath), Charset.defaultCharset());
			ImageParser parser = new ImageParser();
			parser.parse(html);
			List<URL> urls = parser.getImage("https?:\\/\\/i\\.imgur\\.com\\/[0-9a-zA-Z]*\\.(jpg|jpeg|png)");
			DownloadManager down = new SerialDownloadManager(1);
			final String tmpDir = System.getProperty("java.io.tmpdir");
			int idx = 1;
			for(URL url : urls) {
				down.download(url, tmpDir);
				final String filename = String.format("%d.jpg", idx++);
				tmpfileQueue.add(Paths.get(tmpDir, filename));
			}
			down.awaitDownloadComplete();
			down.close();
		} catch (IOException e) {
			Assertions.fail(e);
		} finally {
			tmpfileQueue.forEach((Path path)->{
				try {
					Files.delete(path);
					log.info("Delete " + path);
				} catch (IOException e) {
					log.catching(e);
				}
			});
		}
	}
}
