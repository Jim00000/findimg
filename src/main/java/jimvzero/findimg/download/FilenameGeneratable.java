package jimvzero.findimg.download;

import java.net.URL;

public interface FilenameGeneratable {

	public String generateFilename(String fileExtension);
	
	public String generateFilename(URL url);
	
}
