package cbm.versions.minecraft;

import cbm.main.Main;

public class ReflectionsUtilities {
	private ReflectionsUtilities() {}

	private static String versionPackageName;

	public static String getPackageVersionName() {
		if (versionPackageName != null)
			return versionPackageName;

		String name = Main.getPlugin().getServer().getClass().getPackageName();
		versionPackageName = name.substring(name.lastIndexOf('.') + 1);
		return versionPackageName;
	}

	public static String getMCString(String className) {
		return "net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + "." + className;
	}

	public static Class<?> getMCClass(String className) throws ClassNotFoundException {
		return Class.forName(getMCString(className));
	}
}
