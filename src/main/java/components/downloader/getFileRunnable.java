package components.downloader;

import java.io.File;
import java.net.URLConnection;

public interface getFileRunnable {
	public abstract File getFile(URLConnection urlConnection, String contentType);
}
