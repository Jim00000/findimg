package jimvzero.findimg.download;

import java.net.URL;

import org.apache.commons.io.FilenameUtils;

public class SerialNumberFilenameGenerator extends SerialNumberGenerator implements FilenameGeneratable {

	public SerialNumberFilenameGenerator() {
		super();
	}

	public SerialNumberFilenameGenerator(int start) {
		super(start);
	}

	@Override
	public String generateFilename(String fileExtension) {
		return getSerialNumberStrring() + "." + fileExtension;
	}

	@Override
	public String generateFilename(URL url) {
		final String extension = FilenameUtils.getExtension(url.toExternalForm());
		return generateFilename(extension);
	}
	
}
