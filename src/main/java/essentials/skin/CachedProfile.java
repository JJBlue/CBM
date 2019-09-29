package essentials.skin;

import com.mojang.authlib.GameProfile;

public class CachedProfile {
	
	long timestamp = System.currentTimeMillis();
	GameProfile profile;
	
	public CachedProfile(GameProfile profile) {
		this.profile = profile;
	}
	
	public boolean isValid() {
		return GameProfileBuilder.cacheTime < 0 ? true : (System.currentTimeMillis() - timestamp) < GameProfileBuilder.cacheTime;
	}
}