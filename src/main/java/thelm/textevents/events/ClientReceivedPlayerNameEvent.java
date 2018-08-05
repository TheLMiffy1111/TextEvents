package thelm.textevents.events;

import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ClientReceivedPlayerNameEvent extends Event {

	private ITextComponent name;
	private final SPacketPlayerListItem.AddPlayerData data;

	public ClientReceivedPlayerNameEvent(ITextComponent name, SPacketPlayerListItem.AddPlayerData data) {
		this.name = name;
		this.data = data;
	}

	public ITextComponent getName() {
		return this.name;
	}

	public void setName(ITextComponent name) {
		this.name = name;
	}

	public SPacketPlayerListItem.AddPlayerData getData() {
		return this.data;
	}

}
