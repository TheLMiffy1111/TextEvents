package thelm.textevents.events;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.Event;

public class GetItemDisplayNameEvent extends Event {

	private NBTTagCompound nbt;
	private final ItemStack stack;

	public GetItemDisplayNameEvent(NBTTagCompound nbt, ItemStack stack) {
		this.nbt = nbt;
		this.stack = stack;
	}

	public NBTTagCompound getNBT() {
		return this.nbt;
	}

	public void setNBT(NBTTagCompound nbt) {
		this.nbt = nbt;
	}

	public ItemStack getStack() {
		return this.stack;
	}
}
