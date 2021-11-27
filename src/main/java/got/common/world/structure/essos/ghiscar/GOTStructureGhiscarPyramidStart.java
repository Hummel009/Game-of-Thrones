package got.common.world.structure.essos.ghiscar;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

public class GOTStructureGhiscarPyramidStart extends StructureStart {
	public GOTStructureGhiscarPyramidStart() {
	}

	public GOTStructureGhiscarPyramidStart(World world, Random random, int i, int j) {
		GOTStructureGhiscarPyramidComponent startComponent = new GOTStructureGhiscarPyramidComponent(world, 0, random, (i << 4) + 8, (j << 4) + 8);
		components.add(startComponent);
		startComponent.buildComponent(startComponent, components, random);
		updateBoundingBox();
	}
}
