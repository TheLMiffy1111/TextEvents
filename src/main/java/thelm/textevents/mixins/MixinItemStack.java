package thelm.textevents.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import thelm.textevents.events.ItemStackDisplayNameEvent;

@Mixin(ItemStack.class)
public class MixinItemStack {

	@Inject(method = "getDisplayName", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
	protected void onGetDisplayName(CallbackInfoReturnable<String> info) {
		ItemStackDisplayNameEvent event = new ItemStackDisplayNameEvent(info.getReturnValue(), ItemStack.class.cast(this));
		MinecraftForge.EVENT_BUS.post(event);
		info.setReturnValue(event.getDisplayName());
	}
}
