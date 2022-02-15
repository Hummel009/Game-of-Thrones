package got.common.world.structure.westeros.ironborn;

import java.util.Random;

import got.common.database.*;
import got.common.world.structure.other.GOTStructureTentBase;
import net.minecraft.init.Blocks;

public class GOTStructureIronbornTent extends GOTStructureTentBase {
	public GOTStructureIronbornTent(boolean flag) {
		super(flag);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		int randomWool = random.nextInt(3);
		switch (randomWool) {
		case 0:
			tentBlock = Blocks.wool;
			tentMeta = 13;
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
		fenceBlock = Blocks.fence;
		fenceMeta = 0;
		tableBlock = GOTRegistry.tableIronborn;
		chestContents = GOTChestContents.BEYOND_WALL;
	}
}
