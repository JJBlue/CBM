package cbm.versions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cbm.versions.minecraft.MinecraftVersions;
import cbm.versions.minecraft.PackageVersion;

public class VersionDependency<T> {
	Map<String, T> versions;
	
	public static void init() {
		try {
			Class<?> classy = Class.forName("cbm." + MinecraftVersions.getMinecraftVersionExact() + ".MainVersion");
			Method init = classy.getMethod("init", classy);
			init.invoke(null, new Object[0]);
			System.out.print("[CBM] Found Dependency for this MC Version (" + MinecraftVersions.getMinecraftVersionExact() + ")");
		} catch (ClassNotFoundException e) {
			System.out.print("[CBM] No Dependency for this MC Version found");
		} catch (NoSuchMethodException e) {
			System.out.print("[CBM] Dependency Method init not found");
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public T get(PackageVersion version) {
		return get(version.name());
	}
	
	public T get(String version) {
		if(versions == null) return null;
		return versions.get(version);
	}
	
	public void add(PackageVersion version, T ver) {
		add(version.name(), ver);
	}
	
	public void add(String version, T ver) {
		if(versions == null) versions = new HashMap<>();
		versions.put(version, ver);
	}
}
