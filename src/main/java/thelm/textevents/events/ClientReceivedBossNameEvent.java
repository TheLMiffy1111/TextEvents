package thelm.textevents.events;

import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ClientReceivedBossNameEvent extends Event {

	private ITextComponent name;

	public ClientReceivedBossNameEvent(ITextComponent name) {
		this.name = name;
	}

	public ITextComponent getName() {
		return this.name;
	}

	public void setName(ITextComponent name) {
		this.name = name;
	}
}
