package thelm.textevents.events;

import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SignTextEvent extends Event {

	private ITextComponent[] text;
	private final TileEntitySign sign;

	public SignTextEvent(ITextComponent[] text, TileEntitySign sign) {
		this.text = text;
		this.sign = sign;
	}

	public ITextComponent[] getText() {
		return text;
	}

	public ITextComponent getText(int index) {
		return text[MathHelper.clamp(index, 0, 3)];
	}

	public void setText(ITextComponent[] text) {
		this.text = text;
	}

	public void setText(ITextComponent text, int index) {
		this.text[MathHelper.clamp(index, 0, 3)] = text;
	}

	public TileEntitySign getSign() {
		return sign;
	}
}
