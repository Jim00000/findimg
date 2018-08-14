package jimvzero.findimg.download;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.methods.AsyncByteConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DownloadManager implements Downloadable {

	private final static Logger log = LogManager.getLogger(DownloadManager.class);
	static DownloadManager instance = null;
	private CloseableHttpAsyncClient httpclient;
	private Semaphore semaphore;
	private BlockingQueue<Future<HttpResponse>> queue;

	protected DownloadManager() {
		httpclient = HttpAsyncClients.createDefault();
		final int cores = Runtime.getRuntime().availableProcessors();
		semaphore = new Semaphore(cores);
		queue = new ArrayBlockingQueue<>(cores);
	}

	@Override
	public void download(URL src, String dstname) throws IOException {

		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			log.catching(e);
		}

		final HttpGet request = new HttpGet(src.toExternalForm());
		HttpAsyncRequestProducer producer = HttpAsyncMethods.create(request);
		AsyncByteConsumer<HttpResponse> consumer = DownloadConsumer.create(dstname);
		FutureCallback<HttpResponse> callback = DownloadFutureCallback.create(request, semaphore, queue);

		if (httpclient.isRunning() == false)
			httpclient.start();

		Future<HttpResponse> response = httpclient.execute(producer, consumer, callback);
		try {
			queue.put(response);
		} catch (InterruptedException e) {
			log.catching(e);
		}
	}

	@Override
	public synchronized void awaitDownloadComplete() {
		// Busy waiting
		while (queue.isEmpty() == false)
			;
	}

	public synchronized static DownloadManager getInstance() {
		if (instance == null)
			instance = new DownloadManager();
		return instance;
	}

}
