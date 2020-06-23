package thelm.textevents.events;

import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class BossNameEvent extends Event {

	private ITextComponent name;

	public BossNameEvent(ITextComponent name) {
		this.name = name;
	}

	public ITextComponent getName() {
		return name;
	}

	public void setName(ITextComponent name) {
		this.name = name;
	}
}
