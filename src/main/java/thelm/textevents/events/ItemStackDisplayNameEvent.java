package thelm.textevents.events;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ItemStackDisplayNameEvent extends Event {

	private String displayName;
	private final ItemStack stack;

	public ItemStackDisplayNameEvent(String displayName, ItemStack stack) {
		this.displayName = displayName;
		this.stack = stack;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public ItemStack getStack() {
		return stack;
	}
}
