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

	@DisplayName("Test on Linux Platform (Use user defined regex pattern)")
	@Test
	public void testcase1() {
		if (SystemUtils.IS_OS_LINUX) {
			log.debug("Simulate App usage on Linux using user defined regex pattern");
			final String tempDir = System.getProperty("java.io.tmpdir");
			final Path outputDirPath = Paths.get(tempDir, "imgs");
			final String srcpath = DownloadManagerTest.class.getClassLoader().getResource("testcases/parse-case-1.html")
					.getFile();
			final String[] args = { "-i", srcpath, "-o", outputDirPath.toString(), "-reg",
					"https?://i.imgur.com/[0-9a-zA-Z]*.jpg", "-sn" };

			App.main(args);
			// Remove images directory
			try {
				FileUtils.deleteDirectory(outputDirPath.toFile());
				log.info("Remove directory " + outputDirPath);
			} catch (IOException e) {
				log.catching(e);
				Assertions.fail(e);
			}
		}
	}

	@DisplayName("Test on Linux Platform (Use default regex pattern file)")
	@Test
	public void testcase2() {
		if (SystemUtils.IS_OS_LINUX) {
			log.debug("Simulate App usage on Linux using default regex pattern file");
			final String tempDir = System.getProperty("java.io.tmpdir");
			final Path outputDirPath = Paths.get(tempDir, "imgs");
			final String srcpath = DownloadManagerTest.class.getClassLoader().getResource("testcases/parse-case-1.html")
					.getFile();
			final String[] args = { "-i", srcpath, "-o", outputDirPath.toString() };

			App.main(args);
			// Remove images directory
			try {
				FileUtils.deleteDirectory(outputDirPath.toFile());
				log.info("Remove directory " + outputDirPath);
			} catch (IOException e) {
				log.catching(e);
				Assertions.fail(e);
			}
		}
	}
	
	@DisplayName("Test on Linux Platform (Use regex pattern file provided from users)")
	@Test
	public void testcase3() {
		if (SystemUtils.IS_OS_LINUX) {
			log.debug("Simulate App usage on Linux using default regex pattern file");
			final String tempDir = System.getProperty("java.io.tmpdir");
			final Path outputDirPath = Paths.get(tempDir, "imgs");
			final String srcpath = DownloadManagerTest.class.getClassLoader().getResource("testcases/parse-case-1.html")
					.getFile();
			final String regfpath = DownloadManagerTest.class.getClassLoader().getResource("testcases/regex/dummy")
					.getFile();
			final String[] args = { "-i", srcpath, "-o", outputDirPath.toString(), "-regf", regfpath };

			App.main(args);
			// Remove images directory
			try {
				FileUtils.deleteDirectory(outputDirPath.toFile());
				log.info("Remove directory " + outputDirPath);
			} catch (IOException e) {
				log.catching(e);
				Assertions.fail(e);
			}
		}
	}

	/// TODO: Add Windows platform Application simulation

}
