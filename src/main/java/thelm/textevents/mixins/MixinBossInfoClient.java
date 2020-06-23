package thelm.textevents.mixins;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.BossInfoClient;
import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import net.minecraftforge.common.MinecraftForge;
import thelm.textevents.events.BossNameEvent;

@Mixin(BossInfoClient.class)
public class MixinBossInfoClient extends BossInfo {

	public MixinBossInfoClient(UUID uniqueIdIn, ITextComponent nameIn, Color colorIn, Overlay overlayIn) {
		super(uniqueIdIn, nameIn, colorIn, overlayIn);
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	protected void onConstruct(SPacketUpdateBossInfo packetIn, CallbackInfo info) {
		BossNameEvent event = new BossNameEvent(packetIn.getName());
		MinecraftForge.EVENT_BUS.post(event);
		super.setName(event.getName());
	}
	
	@Override
	public void setName(ITextComponent nameIn) {
		BossNameEvent event = new BossNameEvent(nameIn);
		MinecraftForge.EVENT_BUS.post(event);
		super.setName(event.getName());
	}
}
