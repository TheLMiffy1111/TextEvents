package thelm.textevents.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderPageTextInBookEvent extends Event {

	private String page;
	private final ItemStack stack;

	public RenderPageTextInBookEvent(String page, ItemStack stack) {
		this.page = page;
		this.stack = stack;
	}

	public String getPage() {
		return this.page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public ItemStack getStack() {
		return this.stack;
	}
}
