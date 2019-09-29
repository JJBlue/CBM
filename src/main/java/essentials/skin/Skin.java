package essentials.skin;

import java.io.IOException;
import java.util.Collection;

import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import essentials.language.LanguageConfig;
import essentials.utilities.PlayerUtilities;

public class Skin {

    public void changeSkin(Player p, String name) {
        GameProfile skingp;
        CraftPlayer cp = (CraftPlayer) p;

        try {
            skingp = GameProfileBuilder.fetch(PlayerUtilities.getOfflinePlayer(name).getUniqueId());
        } catch (IOException e) {
        	LanguageConfig.sendMessage(p, "skin.error-load");
            e.printStackTrace();
            return;
        }

        Collection<Property> props = skingp.getProperties().get("textures");

        cp.getProfile().getProperties().removeAll("textures");
        cp.getProfile().getProperties().putAll("textures", props);
        
        PlayerUtilities.updatePlayer(p);
    }
}
