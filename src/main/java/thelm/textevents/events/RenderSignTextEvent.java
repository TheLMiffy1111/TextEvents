package thelm.textevents.events;

import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderSignTextEvent extends Event {

	private ITextComponent text;
	private final TileEntitySign sign;

	public RenderSignTextEvent(ITextComponent text, TileEntitySign sign) {
		this.text = text;
		this.sign = sign;
	}

	public ITextComponent getText() {
		return this.text;
	}

	public void setText(ITextComponent text) {
		this.text = text;
	}

	public TileEntitySign getSign() {
		return this.sign;
	}
}
