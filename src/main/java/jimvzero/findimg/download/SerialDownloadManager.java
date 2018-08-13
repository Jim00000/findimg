package jimvzero.findimg.download;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.core.util.FileUtils;

public class SerialDownloadManager extends DownloadManager {

	private SerialNumberFilenameGenerator filenameGenerator;

	public SerialDownloadManager() {
		this(0);
	}

	public SerialDownloadManager(int start) {
		super();
		filenameGenerator = new SerialNumberFilenameGenerator(start);
	}

	@Override
	public void download(URL src, String dstname) throws IOException {
		FileUtils.mkdir(new File(dstname), true);
		final String filename = filenameGenerator.generateFilename(src);
		final Path path = Paths.get(dstname, filename);
		super.download(src, path.toString());
	}

	public synchronized static DownloadManager getInstance() {
		return SerialDownloadManager.getInstance(0);
	}
	
	public synchronized static DownloadManager getInstance(int start) {
		if (DownloadManager.instance == null || !(DownloadManager.instance instanceof SerialDownloadManager))
			DownloadManager.instance = new SerialDownloadManager(start);
		return DownloadManager.instance;
	}

}
