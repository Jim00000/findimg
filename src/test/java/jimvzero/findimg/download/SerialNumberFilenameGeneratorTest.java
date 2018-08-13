package jimvzero.findimg.download;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class SerialNumberFilenameGeneratorTest {

	private final static Logger log = LogManager.getLogger(SerialNumberFilenameGeneratorTest.class);
	
	@Test
	public void testcase1() {
		try {
			URL url = new URL("http://i.imgur.com/8RKWlnK.jpg");
			SerialNumberFilenameGenerator generator = new SerialNumberFilenameGenerator();
			for(int i = 0 ; i <= 10; i++) {
				final String filename = generator.generateFilename(url);
				final String expectedFilename = String.format("%d.jpg", i);
				Assertions.assertEquals(expectedFilename, filename);
			}
		} catch (MalformedURLException e) {
			log.catching(e);
		}
	}
	
	@Test
	public void testcase2() {
		try {
			URL url = new URL("http://i.imgur.com/8RKWlnK.jpg");
			Random rand = new Random();
			final int start = rand.nextInt(100);
			SerialNumberFilenameGenerator generator = new SerialNumberFilenameGenerator(start);
			for(int i = start ; i <= start + 10; i++) {
				final String filename = generator.generateFilename(url);
				final String expectedFilename = String.format("%d.jpg", i);
				Assertions.assertEquals(expectedFilename, filename);
			}
		} catch (MalformedURLException e) {
			log.catching(e);
		}
	}
	
}
