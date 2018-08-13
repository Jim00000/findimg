package jimvzero.findimg;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import jimvzero.findimg.download.DownloadManagerTest;

public class AppTest {

	private final static Logger log = LogManager.getLogger(AppTest.class);

	@DisplayName("Test on Linux Platform")
	@Test
	public void testcase1() {
		if (SystemUtils.IS_OS_LINUX) {
			log.info("Simulate App usage on Linux : ");
			log.info("$ java -jar " + "findimg-0.0.1-SNAPSHOT-jar-with-dependencies.jar"
					+ "-i src/test/resources/testcases/parse-case-1.html" + "-o ${tmp}/imgs" + "-reg https?://i\\.imgur\\.com/[0-9a-zA-Z]*\\.jpg" + "-sn");
			final String tempDir = System.getProperty("java.io.tmpdir");
			final Path outputDirPath = Paths.get(tempDir, "imgs");
			final String srcpath = DownloadManagerTest.class.getClassLoader().getResource("testcases/parse-case-1.html")
					.getFile();
			final String[] args = { "-i", srcpath, "-o", outputDirPath.toString(), "-reg", "https?://i.imgur.com/[0-9a-zA-Z]*.jpg", "-sn" };

			App.main(args);
			// Remove images directory
			try {
				FileUtils.deleteDirectory(outputDirPath.toFile());
				log.info("Remove directory " + outputDirPath);
			} catch (IOException e) {
				Assertions.fail(e);
			}
		}
	}
	
	///TODO: Add Windows platform Application simulation 

}
