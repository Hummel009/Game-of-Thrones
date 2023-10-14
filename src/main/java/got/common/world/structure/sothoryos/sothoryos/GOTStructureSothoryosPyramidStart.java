package got.common.world.structure.sothoryos.sothoryos;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.Random;

public class GOTStructureSothoryosPyramidStart extends StructureStart {
	public GOTStructureSothoryosPyramidStart() {
	}

	public GOTStructureSothoryosPyramidStart(World world, Random random, int i, int j) {
		GOTStructureSothoryosPyramidComponent startComponent = new GOTStructureSothoryosPyramidComponent(world, 0, random, (i << 4) + 8, (j << 4) + 8);
		components.add(startComponent);
		startComponent.buildComponent(startComponent, components, random);
		updateBoundingBox();
	}
}
