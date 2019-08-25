package essentials.utilities;

import essentials.main.Main;

public class ReflectionsUtilities {
	private ReflectionsUtilities() {}
	
	private static String versionPackageName;
	
	public static String getPackageVersionName() {
		if(versionPackageName != null)
			return versionPackageName;
		
		String name = Main.getPlugin().getServer().getClass().getPackageName();
		versionPackageName = name.substring(name.lastIndexOf('.') + 1);
		return versionPackageName;
	}
}
