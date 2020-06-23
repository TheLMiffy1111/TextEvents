package thelm.textevents.mixins;

import java.util.Arrays;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import thelm.textevents.events.SignTextEvent;

@Mixin(TileEntitySign.class)
public class MixinTileEntitySign extends TileEntity {

	@Shadow
	@Final
	public ITextComponent[] signText;
	public ITextComponent[] originalSignText;

	@Inject(method = "writeToNBT", at = @At(value = "RETURN", shift = At.Shift.BEFORE))
	protected void onWriteToNBT(NBTTagCompound compound, CallbackInfoReturnable<NBTTagCompound> info) {
		if(originalSignText == null) {
			originalSignText = new ITextComponent[] {new TextComponentString(""), new TextComponentString(""), new TextComponentString(""), new TextComponentString("")};
		}
		for(int i = 0; i < 4; ++i) {
			String s = ITextComponent.Serializer.componentToJson(originalSignText[i]);
			compound.setString("Text"+(i+1), s);
		}
	}

	@Inject(method = "readFromNBT", at = @At(value = "RETURN"))
	protected void onReadFromNBT(NBTTagCompound compound, CallbackInfo info) {
		if(originalSignText == null) {
			originalSignText = new ITextComponent[4];
		}
		for(int i = 0; i < 4; ++i) {
			originalSignText[i] = signText[i].createCopy();
		}
		SignTextEvent event = new SignTextEvent(Arrays.copyOf(originalSignText, 4), TileEntitySign.class.cast(this));
		MinecraftForge.EVENT_BUS.post(event);
		for(int i = 0; i < 4; ++i) {
			signText[i] = event.getText(i);
		}
	}
}
