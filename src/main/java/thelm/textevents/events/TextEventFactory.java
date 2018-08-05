package thelm.textevents.events;

import net.minecraft.client.gui.BossInfoClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;

public class TextEventFactory {

	public static String onRenderPageTextInBook(String text, ItemStack stack) {
		RenderPageTextInBookEvent event = new RenderPageTextInBookEvent(text, stack);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getPage();
	}

	public static String onRenderEntityLabel(String name, Entity entity) {
		RenderEntityLabelEvent event = new RenderEntityLabelEvent(name, entity);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getName();
	}

	public static ITextComponent onRenderSignText(ITextComponent text, TileEntitySign sign) {
		RenderSignTextEvent event = new RenderSignTextEvent(text, sign);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getText();
	}

	public static String onClientReceivedTitle(String text, SPacketTitle.Type type) {
		ClientReceivedTitleEvent event = new ClientReceivedTitleEvent(text, type);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getText();
	}

	public static ITextComponent onClientReceivedBossName(ITextComponent name) {
		ClientReceivedBossNameEvent event = new ClientReceivedBossNameEvent(name);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getName();
	}

	public static NBTTagCompound onGetItemDisplayName(NBTTagCompound nbt, ItemStack stack) {
		GetItemDisplayNameEvent event = new GetItemDisplayNameEvent(nbt == null ? new NBTTagCompound() : nbt.copy(), stack);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getNBT() == null || event.getNBT().hasNoTags() ? null : event.getNBT();
	}

	public static ITextComponent onClientReceivedPlayerName(ITextComponent name, SPacketPlayerListItem.AddPlayerData data) {
		ClientReceivedPlayerNameEvent event = new ClientReceivedPlayerNameEvent(name, data);
		MinecraftForge.EVENT_BUS.post(event);
		return event.getName();
	}
}
