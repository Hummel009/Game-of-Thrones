package got.common.world.structure.essos.gold;

import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.world.structure.other.GOTStructureTentBase;
import net.minecraft.init.Blocks;

import java.util.Random;

public class GOTStructureGoldenCampTent extends GOTStructureTentBase {
	public GOTStructureGoldenCampTent(boolean flag) {
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
				tentMeta = 10;
				break;
			case 2:
				tentBlock = Blocks.wool;
				tentMeta = 7;
				break;
			default:
				break;
		}
		fenceBlock = GOTBlocks.fence;
		fenceMeta = 3;
		tableBlock = Blocks.crafting_table;
		chestContents = GOTChestContents.GOLDEN;
	}
}
