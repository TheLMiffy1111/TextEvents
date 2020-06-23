package thelm.textevents.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import thelm.textevents.events.EntityDisplayNameEvent;

@Mixin(Render.class)
public class MixinRender {

	@ModifyArg(method = "renderEntityName", at = @At(value = "INVOKE",
			target = "net/minecraft/client/renderer/entity/Render.renderLivingLabel(Lnet/minecraft/entity/Entity;Ljava/lang/String;DDDI)V"),
			index = 1)
	protected String onRenderEntityName(Entity entityIn, String name, double x, double y, double z, int maxDistance) {
		EntityDisplayNameEvent event = new EntityDisplayNameEvent(name, entityIn);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getDisplayName();
	}
}
