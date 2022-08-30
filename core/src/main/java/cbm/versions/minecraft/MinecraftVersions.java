package cbm.versions.minecraft;

public class MinecraftVersions {
	public static PackageVersion getMinecraftVersionExact() {
		String packagename = ReflectionsUtilities.getPackageVersionName();
		
		try {
			return PackageVersion.valueOf(packagename);
		} catch (Exception e) {
			System.out.print("Minecraft Version unknown: " + packagename);
			return null;
		}
	}
	
	public static MinecraftVersion getMinecraftVersion() {
		PackageVersion exact = getMinecraftVersionExact();
		
		if(exact == null)
			return MinecraftVersion.NOT_FOUND;
		
		switch (exact) {
			case v1_14_R1:
				return MinecraftVersion.v1_14;
			case v1_15_R1:
				return MinecraftVersion.v1_15;
			case v1_16_R1:
			case v1_16_R2:
			case v1_16_R3:
				return MinecraftVersion.v1_16;
			case v1_18_R1:
			case v1_18_R2:
				return MinecraftVersion.v1_18;
			case v1_19_R1:
				return MinecraftVersion.v1_19;
		}
		
		return MinecraftVersion.NOT_FOUND;
	}
}
