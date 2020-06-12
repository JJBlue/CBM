package essentials.versions;

import java.util.HashMap;
import java.util.Map;

public class VersionDependency<T> {
	Map<String, T> versions;
	
	public T getVersion(String version) {
		if(versions == null) return null;
		return versions.get(version);
	}
	
	public void add(String version, T ver) {
		if(versions == null) versions = new HashMap<>();
		versions.put(version, ver);
	}
}
