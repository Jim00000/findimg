package jimvzero.findimg.download;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DownloadFutureCallback implements FutureCallback<HttpResponse> {

	private final static Logger log = LogManager.getLogger(DownloadFutureCallback.class);
	private final Semaphore semaphore;
	private final HttpGet request;
	private BlockingQueue<Future<HttpResponse>> queue;

	protected DownloadFutureCallback(HttpGet request, Semaphore semaphore, BlockingQueue<Future<HttpResponse>> queue) {
		this.semaphore = semaphore;
		this.request = request;
		this.queue = queue;
	}

	@Override
	public void cancelled() {
		semaphore.release();
		log.info(request.getRequestLine() + " cancelled");
		try {
			queue.take();
		} catch (InterruptedException e) {
			log.catching(e);
		}
	}

	@Override
	public void completed(HttpResponse response) {
		semaphore.release();
		log.info(request.getRequestLine() + " -> " + response.getStatusLine());
		try {
			queue.take();
		} catch (InterruptedException e) {
			log.catching(e);
		}
	}

	@Override
	public void failed(Exception ex) {
		semaphore.release();
		log.info(request.getRequestLine() + " -> " + ex);
		try {
			queue.take();
		} catch (InterruptedException e) {
			log.catching(e);
		}
	}

	public static DownloadFutureCallback create(HttpGet request, Semaphore semaphore,
			BlockingQueue<Future<HttpResponse>> queue) {
		return new DownloadFutureCallback(request, semaphore, queue);
	}

}
