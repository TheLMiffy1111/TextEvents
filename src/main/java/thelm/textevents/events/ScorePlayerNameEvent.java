package thelm.textevents.events;

import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ScorePlayerNameEvent extends Event {

	private String playerName;
	private final ScoreObjective scoreObjective;

	public ScorePlayerNameEvent(String playerName, ScoreObjective scoreObjective) {
		this.playerName = playerName;
		this.scoreObjective = scoreObjective;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public ScoreObjective getScoreObjective() {
		return scoreObjective;
	}
}
