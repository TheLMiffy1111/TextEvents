package thelm.textevents.events;

import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.Event;

public class TitleEvent extends Event {

	private ITextComponent message;
	private final SPacketTitle.Type type;

	public TitleEvent(ITextComponent text, SPacketTitle.Type type) {
		this.message = text;
		this.type = type;
	}

	public ITextComponent getMessage() {
		return message;
	}

	public void setMessage(ITextComponent text) {
		this.message = text;
	}

	public SPacketTitle.Type getType() {
		return type;
	}
}
