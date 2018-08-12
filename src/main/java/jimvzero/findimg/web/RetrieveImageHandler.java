package jimvzero.findimg.web;

import java.net.URL;
import java.util.List;

import org.jsoup.nodes.Element;

public interface RetrieveImageHandler {

	public void run(Element element, String[] regexs, List<URL> list);
	
}
