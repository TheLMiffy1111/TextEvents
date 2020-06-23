package thelm.textevents.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.common.MinecraftForge;
import thelm.textevents.events.ScorePlayerNameEvent;

@Mixin(Scoreboard.class)
public class MixinScoreboard {

	@ModifyArg(method = "getOrCreateScore", at = @At(value = "INVOKE",
			target = "net/minecraft/scoreboard/Score.<init>(Lnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreObjective;Ljava/lang/String;)V"),
			index = 2)
	protected String onGetOrCreateScore(Scoreboard scoreboard, ScoreObjective objective, String playerName) {
		ScorePlayerNameEvent event = new ScorePlayerNameEvent(playerName, objective);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getPlayerName();
	}
}
