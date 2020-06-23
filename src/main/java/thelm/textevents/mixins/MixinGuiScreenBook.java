package thelm.textevents.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.MinecraftForge;
import thelm.textevents.events.BookPageEvent;

@Mixin(GuiScreenBook.class)
public class MixinGuiScreenBook extends GuiScreen {

	@Shadow
	@Final
	private ItemStack book;
	@Shadow
	@Final
	private boolean bookIsUnsigned;
	@Shadow
	private NBTTagList bookPages;
	@Shadow
	private int currPage;

	private NBTTagList originalBookPages;

	@Inject(method = "<init>", at = @At("RETURN"))
	protected void onConstruct(EntityPlayer player, ItemStack book, boolean isUnsigned, CallbackInfo info) {
		if(!isUnsigned) {
			originalBookPages = bookPages.copy();
			BookPageEvent event = new BookPageEvent(originalBookPages.getStringTagAt(0), 0, book);
			MinecraftForge.EVENT_BUS.post(event);
			bookPages.set(0, new NBTTagString(event.getPage()));
		}
	}

	@Inject(method = "actionPerformed", at = @At("RETURN"))
	public void onActionPerformed(GuiButton button, CallbackInfo info) {
		if(!bookIsUnsigned && button.enabled && (button.id == 1 || button.id == 2)) {
			BookPageEvent event = new BookPageEvent(originalBookPages.getStringTagAt(currPage), currPage, book);
			MinecraftForge.EVENT_BUS.post(event);
			bookPages.set(currPage, new NBTTagString(event.getPage()));
		}
	}
}
