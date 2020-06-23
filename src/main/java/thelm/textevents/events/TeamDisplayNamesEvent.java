package thelm.textevents.events;

import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraftforge.fml.common.eventhandler.Event;

public class TeamDisplayNamesEvent extends Event {

	private String displayName;
	private String prefix;
	private String suffix;
	private final String name;
	private final ScorePlayerTeam scorePlayerTeam;

	public TeamDisplayNamesEvent(String displayName, String prefix, String suffix, String name, ScorePlayerTeam scorePlayerTeam) {
		this.displayName = displayName;
		this.prefix = prefix;
		this.suffix = suffix;
		this.name = name;
		this.scorePlayerTeam = scorePlayerTeam;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getName() {
		return name;
	}

	public ScorePlayerTeam getScorePlayerTeam() {
		return scorePlayerTeam;
	}
}
