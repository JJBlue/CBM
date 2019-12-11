package components.downloader;

import java.io.File;
import java.net.URLConnection;

public interface getFileRunnable {
	File getFile(URLConnection urlConnection, String contentType);
}
