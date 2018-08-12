package jimvzero.findimg.web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

public class DefaultRetrieveImageHandler implements RetrieveImageHandler {

	private final static Logger log = LogManager.getLogger(DefaultRetrieveImageHandler.class);

	protected DefaultRetrieveImageHandler() {
	}

	protected void handleRegex(Element element, String regex, List<URL> list) {
		final Pattern pattern = Pattern.compile(regex);
		// Check inner text
		check(element.ownText(), pattern, list);
		// Check each attribute
		for (Attribute attr : element.attributes()) {
			if (attr.getValue() != null)
				check(attr.getValue(), pattern, list);
		}
	}

	private void check(String text, Pattern pattern, List<URL> list) {
		assert text != null : "text must not be null";
		Matcher matcher = pattern.matcher(text);
		if (matcher.matches() == true) {
			try {
				final String matchedurl = matcher.group(0);
				final URL imgurl = new URL(matchedurl);
				if (list.contains(imgurl) == false) {
					list.add(imgurl);
					log.info("Find matched image url : " + matchedurl);
				}
			} catch (MalformedURLException e) {
				log.catching(e);
			}
		}
	}

	@Override
	public void run(Element element, String[] regexs, List<URL> list) {
		for (String regex : regexs)
			handleRegex(element, regex, list);
	}

	public static DefaultRetrieveImageHandler create() {
		return new DefaultRetrieveImageHandler();
	}

}
