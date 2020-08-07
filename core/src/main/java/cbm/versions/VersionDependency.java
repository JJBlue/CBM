package cbm.versions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cbm.main.Main;
import cbm.versions.minecraft.MinecraftVersions;
import cbm.versions.minecraft.PackageVersion;

public class VersionDependency<T> {
	Map<String, T> versions;
	
	public static void init() {
		try {
			Class<?> classy = Class.forName("cbm." + MinecraftVersions.getMinecraftVersionExact() + ".MainVersion");
			Method init = classy.getMethod("init");
			init.invoke(null, new Object[0]);
			Main.getPlugin().getLogger().info("Found Dependency for this MC Version (" + MinecraftVersions.getMinecraftVersionExact() + ")");
		} catch (ClassNotFoundException e) {
			Main.getPlugin().getLogger().warning("No Dependency for this MC Version found");
		} catch (NoSuchMethodException e) {
			Main.getPlugin().getLogger().warning("Dependency Method init not found");
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public T get(PackageVersion version) {
		if(version == null) return null;
		return get(version.name());
	}
	
	public T get(String version) {
		if(versions == null) return null;
		return versions.get(version);
	}
	
	public void add(PackageVersion version, T ver) {
		if(version == null) return;
		add(version.name(), ver);
	}
	
	public void add(String version, T ver) {
		if(versions == null) versions = new HashMap<>();
		versions.put(version, ver);
	}
}
