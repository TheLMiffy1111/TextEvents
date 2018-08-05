package thelm.textevents.asm;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;

@MCVersion("1.12.2")
@SortingIndex(Integer.MAX_VALUE)
public class TextEventsLoadingPlugin implements IFMLLoadingPlugin {

	protected static boolean runtimeDeobfEnabled = false;

	@Override
	public String[] getASMTransformerClass() {
		return new String[] {"thelm.textevents.asm.TextEventsClassTransformer"};
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		runtimeDeobfEnabled = (Boolean)data.get("runtimeDeobfuscationEnabled");
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
