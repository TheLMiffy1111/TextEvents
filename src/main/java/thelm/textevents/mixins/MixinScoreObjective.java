package thelm.textevents.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.common.MinecraftForge;
import thelm.textevents.events.ScoreObjectiveDisplayNameEvent;

@Mixin(ScoreObjective.class)
public class MixinScoreObjective {

	@Shadow
	private String displayName;

	@Inject(method = "setDisplayName", at = @At("RETURN"))
	protected void onSetDisplayName(String nameIn, CallbackInfo info) {
		ScoreObjectiveDisplayNameEvent event = new ScoreObjectiveDisplayNameEvent(nameIn, ScoreObjective.class.cast(this));
		MinecraftForge.EVENT_BUS.post(event);
		displayName = event.getDisplayName();
	}
}
