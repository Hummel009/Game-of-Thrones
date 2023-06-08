package io.gitlab.dwarfyassassin.gotucp.core.patches.base;

import org.objectweb.asm.tree.ClassNode;

import java.util.HashMap;
import java.util.Map;

public abstract class Patcher {
	protected Map<String, ConsumerImplBecauseNoLambdas<ClassNode>> classes = new HashMap<>();
	private String patcherName;

	protected Patcher(String name) {
		patcherName = name;
	}

	public LoadingPhase getLoadPhase() {
		return LoadingPhase.CORE_MOD_LOADING;
	}

	public boolean shouldInit() {
		return true;
	}

	public boolean isDone() {
		return classes.isEmpty();
	}

	public boolean canRun(String className) {
		return classes.containsKey(className);
	}

	public void run(String className, ClassNode classNode) {
		classes.get(className).accept(classNode);
		classes.remove(className);
	}

	public String getName() {
		return patcherName;
	}

	public enum LoadingPhase {
		CORE_MOD_LOADING, FORGE_MOD_LOADING

	}

	public interface ConsumerImplBecauseNoLambdas<T> {
		void accept(T var1);
	}
}

