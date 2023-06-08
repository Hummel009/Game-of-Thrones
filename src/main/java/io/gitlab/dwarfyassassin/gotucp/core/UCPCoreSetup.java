package io.gitlab.dwarfyassassin.gotucp.core;

import cpw.mods.fml.relauncher.IFMLCallHook;
import io.gitlab.dwarfyassassin.gotucp.core.patches.BotaniaPatcher;
import io.gitlab.dwarfyassassin.gotucp.core.patches.FMLPatcher;
import io.gitlab.dwarfyassassin.gotucp.core.patches.ThaumcraftPatcher;
import org.apache.logging.log4j.LogManager;

import java.util.Map;

public class UCPCoreSetup implements IFMLCallHook {
	public Void call() {
		UCPCoreMod.log = LogManager.getLogger("GOT-UCP");
		UCPCoreMod.registerPatcher(new FMLPatcher());
		UCPCoreMod.registerPatcher(new BotaniaPatcher());
		UCPCoreMod.registerPatcher(new ThaumcraftPatcher());
		return null;
	}

	public void injectData(Map<String, Object> data) {
	}
}

