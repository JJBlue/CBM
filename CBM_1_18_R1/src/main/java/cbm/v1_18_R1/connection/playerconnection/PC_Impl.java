package cbm.v1_18_R1.connection.playerconnection;

import org.bukkit.craftbukkit.v1_18_R1.CraftServer;

import cbm.connection.eventmanager.ConnectionEvent;
import cbm.connection.eventmanager.ConnectionEventManager;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayInAbilities;
import net.minecraft.network.protocol.game.PacketPlayInAdvancements;
import net.minecraft.network.protocol.game.PacketPlayInArmAnimation;
import net.minecraft.network.protocol.game.PacketPlayInAutoRecipe;
import net.minecraft.network.protocol.game.PacketPlayInBEdit;
import net.minecraft.network.protocol.game.PacketPlayInBeacon;
import net.minecraft.network.protocol.game.PacketPlayInBlockDig;
import net.minecraft.network.protocol.game.PacketPlayInBlockPlace;
import net.minecraft.network.protocol.game.PacketPlayInBoatMove;
import net.minecraft.network.protocol.game.PacketPlayInChat;
import net.minecraft.network.protocol.game.PacketPlayInClientCommand;
import net.minecraft.network.protocol.game.PacketPlayInCloseWindow;
import net.minecraft.network.protocol.game.PacketPlayInCustomPayload;
import net.minecraft.network.protocol.game.PacketPlayInDifficultyChange;
import net.minecraft.network.protocol.game.PacketPlayInDifficultyLock;
import net.minecraft.network.protocol.game.PacketPlayInEnchantItem;
import net.minecraft.network.protocol.game.PacketPlayInEntityAction;
import net.minecraft.network.protocol.game.PacketPlayInEntityNBTQuery;
import net.minecraft.network.protocol.game.PacketPlayInFlying;
import net.minecraft.network.protocol.game.PacketPlayInHeldItemSlot;
import net.minecraft.network.protocol.game.PacketPlayInItemName;
import net.minecraft.network.protocol.game.PacketPlayInKeepAlive;
import net.minecraft.network.protocol.game.PacketPlayInPickItem;
import net.minecraft.network.protocol.game.PacketPlayInRecipeDisplayed;
import net.minecraft.network.protocol.game.PacketPlayInResourcePackStatus;
import net.minecraft.network.protocol.game.PacketPlayInSetCommandBlock;
import net.minecraft.network.protocol.game.PacketPlayInSetCommandMinecart;
import net.minecraft.network.protocol.game.PacketPlayInSetCreativeSlot;
import net.minecraft.network.protocol.game.PacketPlayInSetJigsaw;
import net.minecraft.network.protocol.game.PacketPlayInSettings;
import net.minecraft.network.protocol.game.PacketPlayInSpectate;
import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;
import net.minecraft.network.protocol.game.PacketPlayInStruct;
import net.minecraft.network.protocol.game.PacketPlayInTabComplete;
import net.minecraft.network.protocol.game.PacketPlayInTeleportAccept;
import net.minecraft.network.protocol.game.PacketPlayInTileNBTQuery;
import net.minecraft.network.protocol.game.PacketPlayInTrSel;
import net.minecraft.network.protocol.game.PacketPlayInUpdateSign;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.minecraft.network.protocol.game.PacketPlayInUseItem;
import net.minecraft.network.protocol.game.PacketPlayInVehicleMove;
import net.minecraft.network.protocol.game.PacketPlayInWindowClick;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;

public class PC_Impl extends PlayerConnection {

	public final MinecraftServer minecraftServer;
	public final CraftServer server;

	public PC_Impl(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
		super(minecraftserver, networkmanager, entityplayer);

		this.minecraftServer = minecraftserver;
		this.server = minecraftserver.server;
		entityplayer.b = this;
	}

	@Override
	public void a(Packet<?> packet) {
		callPacketEvent(packet);
		super.a(packet);
	}

	public boolean callPacketEvent(Packet<?> packet) {
		ConnectionEvent connectionEvent = new ConnectionEvent(packet);
		ConnectionEventManager.call(connectionEvent);
		return connectionEvent.cancel;
	}

	@Override
	public void a(PacketPlayInArmAnimation packetplayinarmanimation) {
		if (callPacketEvent(packetplayinarmanimation))
			return;
		super.a(packetplayinarmanimation);
	}

	@Override
	public void a(PacketPlayInChat packetplayinchat) {
		if (callPacketEvent(packetplayinchat))
			return;
		super.a(packetplayinchat);
	}

	@Override
	public void a(PacketPlayInClientCommand packetplayinclientcommand) {
		if (callPacketEvent(packetplayinclientcommand))
			return;
		super.a(packetplayinclientcommand);
	}

	@Override
	public void a(PacketPlayInSettings packetplayinsettings) {
		if (callPacketEvent(packetplayinsettings))
			return;
		super.a(packetplayinsettings);
	}

	@Override
	public void a(PacketPlayInEnchantItem packetplayinenchantitem) {
		if (callPacketEvent(packetplayinenchantitem))
			return;
		super.a(packetplayinenchantitem);
	}

	@Override
	public void a(PacketPlayInWindowClick packetplayinwindowclick) {
		if (callPacketEvent(packetplayinwindowclick))
			return;
		super.a(packetplayinwindowclick);
	}

	@Override
	public void a(PacketPlayInAutoRecipe packetplayinautorecipe) {
		if (callPacketEvent(packetplayinautorecipe))
			return;
		super.a(packetplayinautorecipe);
	}

	@Override
	public void a(PacketPlayInCloseWindow packetplayinclosewindow) {
		if (callPacketEvent(packetplayinclosewindow))
			return;
		super.a(packetplayinclosewindow);
	}

	@Override
	public void a(PacketPlayInCustomPayload packetplayincustompayload) {
		if (callPacketEvent(packetplayincustompayload))
			return;
		super.a(packetplayincustompayload);
	}

	@Override
	public void a(PacketPlayInUseEntity packetplayinuseentity) {
		if (callPacketEvent(packetplayinuseentity))
			return;
		super.a(packetplayinuseentity);
	}

	@Override
	public void a(PacketPlayInKeepAlive packetplayinkeepalive) {
		if (callPacketEvent(packetplayinkeepalive))
			return;
		super.a(packetplayinkeepalive);
	}

	@Override
	public void a(PacketPlayInFlying packetplayinflying) {
		if (callPacketEvent(packetplayinflying))
			return;
		super.a(packetplayinflying);
	}

	@Override
	public void a(PacketPlayInAbilities packetplayinabilities) {
		if (callPacketEvent(packetplayinabilities))
			return;
		super.a(packetplayinabilities);
	}

	@Override
	public void a(PacketPlayInBlockDig packetplayinblockdig) {
		if (callPacketEvent(packetplayinblockdig))
			return;
		super.a(packetplayinblockdig);
	}

	@Override
	public void a(PacketPlayInEntityAction packetplayinentityaction) {
		if (callPacketEvent(packetplayinentityaction))
			return;
		super.a(packetplayinentityaction);
	}

	@Override
	public void a(PacketPlayInSteerVehicle packetplayinsteervehicle) {
		if (callPacketEvent(packetplayinsteervehicle))
			return;
		super.a(packetplayinsteervehicle);
	}

	@Override
	public void a(PacketPlayInHeldItemSlot packetplayinhelditemslot) {
		if (callPacketEvent(packetplayinhelditemslot))
			return;
		super.a(packetplayinhelditemslot);
	}

	@Override
	public void a(PacketPlayInSetCreativeSlot packetplayinsetcreativeslot) {
		if (callPacketEvent(packetplayinsetcreativeslot))
			return;
		super.a(packetplayinsetcreativeslot);
	}

	@Override
	public void a(PacketPlayInUpdateSign packetplayinupdatesign) {
		if (callPacketEvent(packetplayinupdatesign))
			return;
		super.a(packetplayinupdatesign);
	}

	@Override
	public void a(PacketPlayInUseItem packetplayinuseitem) {
		if (callPacketEvent(packetplayinuseitem))
			return;
		super.a(packetplayinuseitem);
	}

	@Override
	public void a(PacketPlayInBlockPlace packetplayinblockplace) {
		if (callPacketEvent(packetplayinblockplace))
			return;
		super.a(packetplayinblockplace);
	}

	@Override
	public void a(PacketPlayInSpectate packetplayinspectate) {
		if (callPacketEvent(packetplayinspectate))
			return;
		super.a(packetplayinspectate);
	}

	@Override
	public void a(PacketPlayInResourcePackStatus packetplayinresourcepackstatus) {
		if (callPacketEvent(packetplayinresourcepackstatus))
			return;
		super.a(packetplayinresourcepackstatus);
	}

	@Override
	public void a(PacketPlayInBoatMove packetplayinboatmove) {
		if (callPacketEvent(packetplayinboatmove))
			return;
		super.a(packetplayinboatmove);
	}

	@Override
	public void a(PacketPlayInVehicleMove packetplayinvehiclemove) {
		if (callPacketEvent(packetplayinvehiclemove))
			return;
		super.a(packetplayinvehiclemove);
	}

	@Override
	public void a(PacketPlayInTeleportAccept packetplayinteleportaccept) {
		if (callPacketEvent(packetplayinteleportaccept))
			return;
		super.a(packetplayinteleportaccept);
	}

	@Override
	public void a(PacketPlayInRecipeDisplayed packetplayinrecipedisplayed) {
		if (callPacketEvent(packetplayinrecipedisplayed))
			return;
		super.a(packetplayinrecipedisplayed);
	}

	@Override
	public void a(PacketPlayInAdvancements packetplayinadvancements) {
		if (callPacketEvent(packetplayinadvancements))
			return;
		super.a(packetplayinadvancements);
	}

	@Override
	public void a(PacketPlayInTabComplete packetplayintabcomplete) {
		if (callPacketEvent(packetplayintabcomplete))
			return;
		super.a(packetplayintabcomplete);
	}

	@Override
	public void a(PacketPlayInSetCommandBlock packetplayinsetcommandblock) {
		if (callPacketEvent(packetplayinsetcommandblock))
			return;
		super.a(packetplayinsetcommandblock);
	}

	@Override
	public void a(PacketPlayInSetCommandMinecart packetplayinsetcommandminecart) {
		if (callPacketEvent(packetplayinsetcommandminecart))
			return;
		super.a(packetplayinsetcommandminecart);
	}

	@Override
	public void a(PacketPlayInPickItem packetplayinpickitem) {
		if (callPacketEvent(packetplayinpickitem))
			return;
		super.a(packetplayinpickitem);
	}

	@Override
	public void a(PacketPlayInItemName packetplayinitemname) {
		if (callPacketEvent(packetplayinitemname))
			return;
		super.a(packetplayinitemname);
	}

	@Override
	public void a(PacketPlayInBeacon packetplayinbeacon) {
		if (callPacketEvent(packetplayinbeacon))
			return;
		super.a(packetplayinbeacon);
	}

	@Override
	public void a(PacketPlayInStruct packetplayinstruct) {
		if (callPacketEvent(packetplayinstruct))
			return;
		super.a(packetplayinstruct);
	}

	@Override
	public void a(PacketPlayInTrSel packetplayintrsel) {
		if (callPacketEvent(packetplayintrsel))
			return;
		super.a(packetplayintrsel);
	}

	@Override
	public void a(PacketPlayInBEdit packetplayinbedit) {
		if (callPacketEvent(packetplayinbedit))
			return;
		super.a(packetplayinbedit);
	}

	@Override
	public void a(PacketPlayInEntityNBTQuery packetplayinentitynbtquery) {
		if (callPacketEvent(packetplayinentitynbtquery))
			return;
		super.a(packetplayinentitynbtquery);
	}

	@Override
	public void a(PacketPlayInTileNBTQuery packetplayintilenbtquery) {
		if (callPacketEvent(packetplayintilenbtquery))
			return;
		super.a(packetplayintilenbtquery);
	}

	@Override
	public void a(PacketPlayInSetJigsaw packetplayinsetjigsaw) {
		if (callPacketEvent(packetplayinsetjigsaw))
			return;
		super.a(packetplayinsetjigsaw);
	}

	@Override
	public void a(PacketPlayInDifficultyChange packetplayindifficultychange) {
		if (callPacketEvent(packetplayindifficultychange))
			return;
		super.a(packetplayindifficultychange);
	}

	@Override
	public void a(PacketPlayInDifficultyLock packetplayindifficultylock) {
		if (callPacketEvent(packetplayindifficultylock))
			return;
		super.a(packetplayindifficultylock);
	}
}
