package got.coremod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import io.gitlab.dwarfyassassin.gotucp.core.UCPCoreMod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.TransformerExclusions({"got.coremod", "io.gitlab.dwarfyassassin.gotucp.core"})
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class GOTLoadingPlugin implements IFMLLoadingPlugin {
	private final UCPCoreMod dwarfyAssassinCompatibilityCoremod = new UCPCoreMod();

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		List<String> classes = new ArrayList<>();
		classes.add(GOTClassTransformer.class.getName());
		classes.addAll(Arrays.asList(dwarfyAssassinCompatibilityCoremod.getASMTransformerClass()));
		return classes.toArray(new String[0]);
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return dwarfyAssassinCompatibilityCoremod.getSetupClass();
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}
}
