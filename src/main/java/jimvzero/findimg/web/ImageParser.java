package jimvzero.findimg.web;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ImageParser extends WebParser implements RetrieveImageAPI {

	private final static Logger log = LogManager.getLogger(ImageParser.class);

	private List<URL> retrieveImageProcess(String[] regexs, RetrieveImageHandler handler) {
		if (isReady() == false) {
			throw new RuntimeException("The ImageParser is not ready");
		}

		List<URL> result = new LinkedList<>();
		
		Elements elements = document.body().select("*");
		for (Element element : elements) {
			handler.run(element, regexs, result);
		}

	
		if (result.isEmpty() == true) {
			log.info("None of Image urls are found");
		}

		return result;
	}

	@Override
	public List<URL> getImage(String regex) {
		final String[] regexs = { regex };
		return getImage(regexs);
	}

	@Override
	public List<URL> getImage(String... regexs) {
		final RetrieveImageHandler handler = DefaultRetrieveImageHandler.create();
		return retrieveImageProcess(regexs, handler);
	}

	@Override
	public List<URL> getImage(List<String> regexlist) {
		String[] regexs = new String[regexlist.size()];
		regexs = regexlist.toArray(regexs);
		return getImage(regexs);
	}

}
