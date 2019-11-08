package essentials.modules.kits;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KitManager {
	static Map<String, Kit> kits;
	
	public static void load() {
		KitsConfig.load();
		kits = Collections.synchronizedMap(new HashMap<>());
		
		for(Kit kit : KitsConfig.loadKit()) {
			kits.put(kit.ID, kit);
		}
	}
	
	public static void unload() {
		if(kits != null) {
			synchronized (kits) {
				for(Kit kit : kits.values()) {
					if(!kit.saved) {
						KitsConfig.saveKit(kit);
					}
				}
			}
		}
		
		KitsConfig.unload();
		kits = null;
	}
	
	public static void add(Kit kit) {
		kit.saved = false;
		kits.put(kit.ID, kit);
	}
	
	public static void remove(Kit kit) {
		kits.remove(kit.ID);
		KitsConfig.removeKit(kit);
	}
	
	public static Kit getKit(String ID) {
		return kits.get(ID);
	}
}
