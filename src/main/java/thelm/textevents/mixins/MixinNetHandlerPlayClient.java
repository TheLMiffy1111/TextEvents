package thelm.textevents.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketTeams;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.common.MinecraftForge;
import thelm.textevents.events.TeamDisplayNamesEvent;
import thelm.textevents.events.TitleEvent;
import thelm.textevents.events.WindowTitleEvent;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

	@ModifyVariable(method = "handleOpenWindow", at = @At(value = "INVOKE", shift = At.Shift.AFTER,
			target = "net/minecraft/network/PacketThreadUtil.checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V"),
			ordinal = 0, index = 1, name = "packetIn")
	protected SPacketOpenWindow onHandleOpenWindow(SPacketOpenWindow packetIn) {
		WindowTitleEvent event = new WindowTitleEvent(packetIn.getWindowTitle(), packetIn.getGuiId(), packetIn.getSlotCount());
		MinecraftForge.EVENT_BUS.post(event);
		return new SPacketOpenWindow(packetIn.getWindowId(), packetIn.getGuiId(), event.getTitle(), packetIn.getSlotCount(), packetIn.getEntityId());
	}

	@ModifyVariable(method = "handleTitle", at = @At(value = "INVOKE", shift = At.Shift.AFTER,
			target = "net/minecraft/network/PacketThreadUtil.checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V"),
			ordinal = 0, index = 1, name = "packetIn")
	protected SPacketTitle onHandleTitle(SPacketTitle packetIn) {
		switch(packetIn.getType()) {
		default:
			return packetIn;
		case TITLE:
		case SUBTITLE:
		case ACTIONBAR:
			TitleEvent event = new TitleEvent(packetIn.getMessage(), packetIn.getType());
			MinecraftForge.EVENT_BUS.post(event);
			return new SPacketTitle(packetIn.getType(), event.getMessage(), packetIn.getFadeInTime(), packetIn.getDisplayTime(), packetIn.getFadeOutTime());
		}
	}

	@Inject(method = "handleTeams", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
	protected void onHandleTeams(SPacketTeams packetIn, CallbackInfo info, Scoreboard scoreboard, ScorePlayerTeam scoreplayerteam) {
		if(packetIn.getAction() == 0 || packetIn.getAction() == 2) {
			TeamDisplayNamesEvent event = new TeamDisplayNamesEvent(packetIn.getDisplayName(), packetIn.getPrefix(), packetIn.getSuffix(), packetIn.getName(), scoreplayerteam);
			MinecraftForge.EVENT_BUS.post(event);
			scoreplayerteam.setDisplayName(event.getDisplayName());
			scoreplayerteam.setPrefix(event.getPrefix());
			scoreplayerteam.setSuffix(event.getSuffix());
		}
	}
}
