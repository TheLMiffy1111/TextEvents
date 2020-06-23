package thelm.textevents.events;

import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ScoreObjectiveDisplayNameEvent extends Event {

	private String displayName;
	private final ScoreObjective scoreObjective;

	public ScoreObjectiveDisplayNameEvent(String displayName, ScoreObjective scoreObjective) {
		this.displayName = displayName;
		this.scoreObjective = scoreObjective;
		System.out.println(displayName);
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public ScoreObjective getScoreObjective() {
		return scoreObjective;
	}
}
