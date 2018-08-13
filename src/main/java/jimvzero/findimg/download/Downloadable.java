package jimvzero.findimg.download;

import java.net.URL;

public interface Downloadable {
	
	public void download(URL src, String dstname) throws Exception;
	
	public void awaitDownloadComplete();
	
}
