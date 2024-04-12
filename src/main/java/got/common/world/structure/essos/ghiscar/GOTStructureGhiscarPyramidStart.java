package got.common.world.structure.essos.ghiscar;

import net.minecraft.world.gen.structure.StructureStart;

import java.util.Random;

public class GOTStructureGhiscarPyramidStart extends StructureStart {
	@SuppressWarnings("unused")
	public GOTStructureGhiscarPyramidStart() {
	}

	public GOTStructureGhiscarPyramidStart(Random random, int i, int j) {
		GOTStructureGhiscarPyramidComponent startComponent = new GOTStructureGhiscarPyramidComponent(0, random, (i << 4) + 8, (j << 4) + 8);
		components.add(startComponent);
		startComponent.buildComponent(startComponent, components, random);
		updateBoundingBox();
	}
}
