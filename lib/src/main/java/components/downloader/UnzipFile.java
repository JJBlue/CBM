package components.downloader;

import java.io.File;

public interface UnzipFile {
	File getdestFile(String dest, String entry, boolean directory);
}
