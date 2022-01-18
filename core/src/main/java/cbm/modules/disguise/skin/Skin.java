package cbm.modules.disguise.skin;

import java.io.IOException;
import java.util.Collection;

import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import cbm.language.LanguageConfig;
import cbm.modules.disguise.gameprofile.GameProfileBuilder;
import cbm.utilitiesvr.player.PlayerUtilities;

public class Skin {
	
	private Skin() {}

    public static void changeSkin(Player p, String name) {
        GameProfile skingp = PlayerUtilities.getGameProfile(p);
        GameProfile skinop;
        
        try {
        	skinop = GameProfileBuilder.fetch(PlayerUtilities.getOfflinePlayer(name).getUniqueId());
        } catch (IOException e) {
        	LanguageConfig.sendMessage(p, "skin.error-load");
            return;
        }

        Collection<Property> props = skinop.getProperties().get("textures");

        skingp.getProperties().removeAll("textures");
        skingp.getProperties().putAll("textures", props);
        
        PlayerUtilities.updatePlayer(p);
        LanguageConfig.sendMessage(p, "skin.change", name);
    }
}
