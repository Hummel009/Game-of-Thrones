package got.common.world.structure.westeros.ironborn;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.world.structure.other.GOTStructureTentBase;
import net.minecraft.init.Blocks;

import java.util.Random;

public class GOTStructureIronbornCampTent extends GOTStructureTentBase {
	public GOTStructureIronbornCampTent(boolean flag) {
		super(flag);
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		int randomWool = random.nextInt(3);
		switch (randomWool) {
			case 0:
				tentBlock = Blocks.wool;
				tentMeta = 4;
				break;
			case 1:
				tentBlock = Blocks.wool;
				tentMeta = 15;
				break;
			default:
				tentBlock = Blocks.wool;
				tentMeta = 7;
				break;
		}
		fenceBlock = Blocks.fence;
		fenceMeta = 0;
		tableBlock = GOTBlocks.tableIronborn;
		chestContents = GOTChestContents.BEYOND_WALL;
	}
}