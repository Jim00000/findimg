package jimvzero.findimg.download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncByteConsumer;
import org.apache.http.protocol.HttpContext;

public class DownloadConsumer extends AsyncByteConsumer<HttpResponse> {

	private HttpResponse response;
	private File target;
	private WritableByteChannel channel;

	protected DownloadConsumer(String filename) throws FileNotFoundException {
		target = new File(filename);
		channel = Channels.newChannel(new FileOutputStream(target));
	}

	@Override
	protected void onByteReceived(ByteBuffer buf, IOControl ioc) throws IOException {
		channel.write(buf);
	}

	@Override
	protected void onResponseReceived(HttpResponse response) throws HttpException, IOException {
		this.response = response;
	}

	@Override
	protected HttpResponse buildResult(HttpContext context) throws Exception {
		channel.close();
		return this.response;
	}
	
	public static DownloadConsumer create(String filename) throws FileNotFoundException {
		return new DownloadConsumer(filename);
	}

}
