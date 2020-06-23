package thelm.textevents.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import thelm.textevents.events.PlayerDisplayNameEvent;

@Mixin(NetworkPlayerInfo.class)
public class MixinNetworkPlayerInfo {

	@Shadow
	@Final
	private GameProfile gameProfile;
	@Shadow
	private ITextComponent displayName;

	@Inject(method = "<init>(Lnet/minecraft/network/play/server/SPacketPlayerListItem$AddPlayerData;)V", at = @At("RETURN"))
	protected void onConstruct(SPacketPlayerListItem.AddPlayerData entry, CallbackInfo info) {
		PlayerDisplayNameEvent event = new PlayerDisplayNameEvent(displayName, gameProfile);
		MinecraftForge.EVENT_BUS.post(event);
		displayName = event.getDisplayName();
	}

	@Inject(method = "setDisplayName", at = @At("RETURN"))
	protected void onSetDisplayName(ITextComponent displayNameIn, CallbackInfo info) {
		PlayerDisplayNameEvent event = new PlayerDisplayNameEvent(displayName, gameProfile);
		MinecraftForge.EVENT_BUS.post(event);
		displayName = event.getDisplayName();
	}
}
