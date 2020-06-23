package thelm.textevents.core;

import java.util.Map;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import net.minecraftforge.fml.relauncher.IFMLCallHook;

public class TextEventsFMLCallHook implements IFMLCallHook {

	@Override
	public Void call() throws Exception {
		MixinBootstrap.init();
		Mixins.addConfiguration("mixins.textevents.json");
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}
}
