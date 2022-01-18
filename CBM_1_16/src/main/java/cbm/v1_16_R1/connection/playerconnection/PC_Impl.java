package cbm.v1_16_R1.connection.playerconnection;

import org.bukkit.craftbukkit.v1_16_R1.CraftServer;

import cbm.connection.eventmanager.ConnectionEvent;
import cbm.connection.eventmanager.ConnectionEventManager;
import net.minecraft.server.v1_16_R1.EntityPlayer;
import net.minecraft.server.v1_16_R1.MinecraftServer;
import net.minecraft.server.v1_16_R1.NetworkManager;
import net.minecraft.server.v1_16_R1.Packet;
import net.minecraft.server.v1_16_R1.PacketPlayInAbilities;
import net.minecraft.server.v1_16_R1.PacketPlayInAdvancements;
import net.minecraft.server.v1_16_R1.PacketPlayInArmAnimation;
import net.minecraft.server.v1_16_R1.PacketPlayInAutoRecipe;
import net.minecraft.server.v1_16_R1.PacketPlayInBEdit;
import net.minecraft.server.v1_16_R1.PacketPlayInBeacon;
import net.minecraft.server.v1_16_R1.PacketPlayInBlockDig;
import net.minecraft.server.v1_16_R1.PacketPlayInBlockPlace;
import net.minecraft.server.v1_16_R1.PacketPlayInBoatMove;
import net.minecraft.server.v1_16_R1.PacketPlayInChat;
import net.minecraft.server.v1_16_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_16_R1.PacketPlayInCloseWindow;
import net.minecraft.server.v1_16_R1.PacketPlayInCustomPayload;
import net.minecraft.server.v1_16_R1.PacketPlayInDifficultyChange;
import net.minecraft.server.v1_16_R1.PacketPlayInDifficultyLock;
import net.minecraft.server.v1_16_R1.PacketPlayInEnchantItem;
import net.minecraft.server.v1_16_R1.PacketPlayInEntityAction;
import net.minecraft.server.v1_16_R1.PacketPlayInEntityNBTQuery;
import net.minecraft.server.v1_16_R1.PacketPlayInFlying;
import net.minecraft.server.v1_16_R1.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_16_R1.PacketPlayInItemName;
import net.minecraft.server.v1_16_R1.PacketPlayInKeepAlive;
import net.minecraft.server.v1_16_R1.PacketPlayInPickItem;
import net.minecraft.server.v1_16_R1.PacketPlayInRecipeDisplayed;
import net.minecraft.server.v1_16_R1.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_16_R1.PacketPlayInSetCommandBlock;
import net.minecraft.server.v1_16_R1.PacketPlayInSetCommandMinecart;
import net.minecraft.server.v1_16_R1.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_16_R1.PacketPlayInSetJigsaw;
import net.minecraft.server.v1_16_R1.PacketPlayInSettings;
import net.minecraft.server.v1_16_R1.PacketPlayInSpectate;
import net.minecraft.server.v1_16_R1.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_16_R1.PacketPlayInStruct;
import net.minecraft.server.v1_16_R1.PacketPlayInTabComplete;
import net.minecraft.server.v1_16_R1.PacketPlayInTeleportAccept;
import net.minecraft.server.v1_16_R1.PacketPlayInTileNBTQuery;
import net.minecraft.server.v1_16_R1.PacketPlayInTrSel;
import net.minecraft.server.v1_16_R1.PacketPlayInTransaction;
import net.minecraft.server.v1_16_R1.PacketPlayInUpdateSign;
import net.minecraft.server.v1_16_R1.PacketPlayInUseEntity;
import net.minecraft.server.v1_16_R1.PacketPlayInUseItem;
import net.minecraft.server.v1_16_R1.PacketPlayInVehicleMove;
import net.minecraft.server.v1_16_R1.PacketPlayInWindowClick;
import net.minecraft.server.v1_16_R1.PlayerConnection;

public class PC_Impl extends PlayerConnection {

	public final MinecraftServer minecraftServer;
	public final CraftServer server;

	public PC_Impl(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
		super(minecraftserver, networkmanager, entityplayer);

		this.minecraftServer = minecraftserver;
		this.server = minecraftserver.server;
		entityplayer.playerConnection = this;
	}

	@Override
	public void sendPacket(Packet<?> packet) {
		callPacketEvent(packet);
		super.sendPacket(packet);
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
	public void a(PacketPlayInTransaction packetplayintransaction) {
		if (callPacketEvent(packetplayintransaction))
			return;
		super.a(packetplayintransaction);
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
