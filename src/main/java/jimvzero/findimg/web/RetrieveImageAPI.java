package jimvzero.findimg.web;

import java.net.URL;
import java.util.List;

public interface RetrieveImageAPI {

	public List<URL> getImage(String regex);
	
	public List<URL> getImage(String... regexs);
	
	public List<URL> getImage(List<String> regexlist);
	
}
