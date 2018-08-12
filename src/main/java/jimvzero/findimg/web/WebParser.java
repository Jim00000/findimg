package jimvzero.findimg.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebParser implements Parsable {

	private final static Logger log = LogManager.getLogger(WebParser.class);
	private boolean ready;
	protected Document document;
	
	protected WebParser() {
		document = null;
		ready = false;
	}
	
	public synchronized final boolean isReady() {
		return ready;
	}
	
	@Override
	public void parse(String html) throws RuntimeException {
		document = Jsoup.parse(html);
		if (document != null) {
			log.info("Parsing Html successfully");
			ready = true;
		} else {
			throw new RuntimeException("Can not parse webpage");
		}
	}

}
