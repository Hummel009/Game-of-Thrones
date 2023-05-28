package got.common.world.structure.other;

import got.common.database.GOTBlocks;

public class GOTStructureBurntHouse extends GOTStructureRuinedHouse {
	public GOTStructureBurntHouse(boolean flag) {
		super(flag);
		woodBlock = GOTBlocks.wood1;
		woodMeta = 3;
		plankBlock = GOTBlocks.planks1;
		plankMeta = 3;
		fenceBlock = GOTBlocks.fence;
		fenceMeta = 3;
		stairBlock = GOTBlocks.stairsCharred;
		stoneBlock = GOTBlocks.scorchedStone;
		stoneMeta = 0;
		stoneVariantBlock = GOTBlocks.scorchedStone;
		stoneVariantMeta = 0;
	}
}
