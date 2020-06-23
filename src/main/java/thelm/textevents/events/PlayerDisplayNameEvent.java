package thelm.textevents.events;

import com.mojang.authlib.GameProfile;

import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PlayerDisplayNameEvent extends Event {

	private ITextComponent displayName;
	private final GameProfile gameProfile;

	public PlayerDisplayNameEvent(ITextComponent displayName, GameProfile gameProfile) {
		this.displayName = displayName;
		this.gameProfile = gameProfile;
	}

	public ITextComponent getDisplayName() {
		return displayName;
	}

	public void setDisplayName(ITextComponent displayName) {
		this.displayName = displayName;
	}

	public GameProfile getGameProfile() {
		return gameProfile;
	}
}
