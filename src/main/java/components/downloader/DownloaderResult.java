package components.downloader;

import java.io.File;

public class DownloaderResult {
	private File destFile;
	private String type;
	private long length;
	
	public DownloaderResult(File destFile) {
		this.destFile = destFile;
	}
	
	public DownloaderResult(File destFile, String type, long length) {
		this.destFile = destFile;
		this.type = type;
		this.length = length;
	}

	public File getDestFile() {
		return destFile;
	}

	public String getType() {
		return type;
	}

	public long getLength() {
		return length;
	}
}
