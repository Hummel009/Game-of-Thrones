package io.gitlab.dwarfyassassin.gotucp.core;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import io.gitlab.dwarfyassassin.gotucp.core.patches.base.Patcher;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.HashSet;

public class UCPClassTransformer implements IClassTransformer {
	static {
		FMLLaunchHandler launchHandler = ReflectionHelper.getPrivateValue(FMLLaunchHandler.class, null, "INSTANCE");
		LaunchClassLoader launchClassLoader = ReflectionHelper.getPrivateValue(FMLLaunchHandler.class, launchHandler, "classLoader");
	}

	public byte[] transform(String name, String transformedName, byte[] classBytes) {
		boolean ran = false;
		for (Patcher patcher : UCPCoreMod.activePatches) {
			if (patcher.canRun(name)) {
				ran = true;
				ClassNode classNode = new ClassNode();
				ClassReader classReader = new ClassReader(classBytes);
				classReader.accept(classNode, 0);
				UCPCoreMod.log.info("Running patcher {} for {}", patcher.getName(), name);
				patcher.run(name, classNode);
				ClassWriter writer = new ClassWriter(1);
				classNode.accept(writer);
				classBytes = writer.toByteArray();
			}
		}
		if (ran) {
			HashSet<Patcher> removes = new HashSet<>();
			for (Patcher patcher : UCPCoreMod.activePatches) {
				if (patcher.isDone()) {
					removes.add(patcher);
				}
			}
			UCPCoreMod.activePatches.removeAll(removes);
			if (UCPCoreMod.activePatches.isEmpty()) {
				UCPCoreMod.log.info("Ran all active patches.");
			}
		}
		return classBytes;
	}
}

