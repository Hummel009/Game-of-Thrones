package io.gitlab.dwarfyassassin.gotucp.core;

import io.gitlab.dwarfyassassin.gotucp.core.patches.base.Patcher;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UCPCoreMod {
	public static Logger log;
	public static List<Patcher> activePatches = new ArrayList<>();
	private static List<Patcher> modPatches = new ArrayList<>();

	static {
		System.out.println("GOT-UCP: Found core mod.");
	}

	public static void loadModPatches() {
		int i = 0;
		for (Patcher patcher : modPatches) {
			if (!patcher.shouldInit()) {
				continue;
			}
			activePatches.add(patcher);
			++i;
		}
		log.info("Loaded {} mod patches.", i);
		modPatches.clear();
	}

	public static void registerPatcher(Patcher patcher) {
		if (patcher.getLoadPhase() == Patcher.LoadingPhase.CORE_MOD_LOADING && patcher.shouldInit()) {
			activePatches.add(patcher);
		} else if (patcher.getLoadPhase() == Patcher.LoadingPhase.FORGE_MOD_LOADING) {
			modPatches.add(patcher);
		}
	}

	public String getAccessTransformerClass() {
		return null;
	}

	public String[] getASMTransformerClass() {
		return new String[]{UCPClassTransformer.class.getName()};
	}

	public String getModContainerClass() {
		return null;
	}

	public String getSetupClass() {
		return UCPCoreSetup.class.getName();
	}

	public void injectData(Map<String, Object> data) {
	}
}
