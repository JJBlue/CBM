package cbm.modules.MapPaint;

import cbm.config.MainConfig;

public class MPInformation {
	public String path;
	public String filename;
	public int mapID;

	public int startX;
	public int startY;

	public String getConvertedPath() {
		if (path == null || path.isEmpty())
			return MainConfig.getDataFolder() + "picture";
		return path;
	}
}
