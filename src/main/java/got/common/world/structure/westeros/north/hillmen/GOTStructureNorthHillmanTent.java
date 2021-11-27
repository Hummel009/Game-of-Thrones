package got.common.world.structure.westeros.north.hillmen;

import java.util.Random;

import got.common.database.*;
import got.common.world.structure.other.GOTStructureTentBase;
import net.minecraft.init.Blocks;

public class GOTStructureNorthHillmanTent extends GOTStructureTentBase {
	public GOTStructureNorthHillmanTent(boolean flag) {
		super(flag);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		int randomWool = random.nextInt(3);
		switch (randomWool) {
		case 0:
			tentBlock = Blocks.wool;
			tentMeta = 15;
			break;
		case 1:
			tentBlock = Blocks.wool;
			tentMeta = 12;
			break;
		case 2:
			tentBlock = Blocks.wool;
			tentMeta = 7;
			break;
		default:
			break;
		}
		fenceBlock = GOTRegistry.fence;
		fenceMeta = 3;
		tableBlock = GOTRegistry.tableWildling;
		hasTorches = true;
		chestContents = GOTChestContents.BEYOND_WALL;
	}
}
