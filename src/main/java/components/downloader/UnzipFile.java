package components.downloader;

import java.io.File;

public interface UnzipFile {
	public abstract File getdestFile(String dest, String entry, boolean directory);
}
