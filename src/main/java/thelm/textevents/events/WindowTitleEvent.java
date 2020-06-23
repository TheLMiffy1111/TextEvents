package thelm.textevents.events;

import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class WindowTitleEvent extends Event {

	private ITextComponent title;
	private final String inventoryType;
	private final int slotCount;

	public WindowTitleEvent(ITextComponent title, String inventoryType, int slotCount) {
		this.title = title;
		this.inventoryType = inventoryType;
		this.slotCount = slotCount;
	}

	public ITextComponent getTitle() {
		return title;
	}

	public void setText(ITextComponent title) {
		this.title = title;
	}

	public String getInventoryType() {
		return inventoryType;
	}

	public int getSlotCount() {
		return slotCount;
	}
}
