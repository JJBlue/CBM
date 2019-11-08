package essentials.modules.kits;

import java.util.Collections;
import java.util.List;

public class KitManager {
	static List<Kit> kits;
	
	public static void load() {
		KitsConfig.load();
		kits = Collections.synchronizedList(KitsConfig.loadKit());
	}
	
	public static void unload() {
		KitsConfig.unload();
		kits = null;
	}
	
	public static void add(Kit kit) {
		kits.add(kit);
	}
	
	public static void remove(Kit kit) {
		kits.remove(kit);
		//TODO remove from config
	}
}
