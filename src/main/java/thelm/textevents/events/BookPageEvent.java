package thelm.textevents.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

public class BookPageEvent extends Event {

	private String page;
	private final int pageNumber;
	private final ItemStack stack;

	public BookPageEvent(String page, int pageNumber, ItemStack stack) {
		this.page = page;
		this.pageNumber = pageNumber;
		this.stack = stack;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public ItemStack getStack() {
		return stack;
	}
}
