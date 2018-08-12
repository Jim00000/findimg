package jimvzero.findimg;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import jimvzero.findimg.download.DownloadManager;
import jimvzero.findimg.web.ImageParser;

public class DownloadTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		String filepath = DownloadTest.class.getClassLoader().getResource("testcases/parse-case-1.html").getFile();
		final String html = FileUtils.readFileToString(new File(filepath), Charset.defaultCharset());
		ImageParser parser = new ImageParser();
		parser.parse(html);
		List<URL> urls = parser.getImage("https?:\\/\\/i\\.imgur\\.com\\/[0-9a-zA-Z]*\\.(jpg|jpeg|png)");
		DownloadManager downs = DownloadManager.getInstance();
		
		int idx = 1;
		for(URL url : urls) {
			final String extension = FilenameUtils.getExtension(url.toExternalForm());
			System.out.println(url.toExternalForm());
			downs.download(url, idx + "." + extension);
			idx++;
		}
		
		downs.close();
	}

}
