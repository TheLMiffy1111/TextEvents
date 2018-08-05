package thelm.textevents.events;

import net.minecraft.network.play.server.SPacketTitle;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ClientReceivedTitleEvent extends Event {

	private String text;
	private final SPacketTitle.Type type;

	public ClientReceivedTitleEvent(String text, SPacketTitle.Type type) {
		this.text = text;
		this.type = type;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public SPacketTitle.Type getType() {
		return this.type;
	}
}
