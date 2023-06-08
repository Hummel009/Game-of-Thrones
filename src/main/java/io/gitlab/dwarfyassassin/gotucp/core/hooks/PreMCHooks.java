package io.gitlab.dwarfyassassin.gotucp.core.hooks;

import io.gitlab.dwarfyassassin.gotucp.core.UCPCoreMod;

public class PreMCHooks {
	public static void postFMLLoad() {
		UCPCoreMod.loadModPatches();
	}
}
