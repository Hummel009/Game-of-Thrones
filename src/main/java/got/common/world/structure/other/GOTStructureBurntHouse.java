package got.common.world.structure.other;

import got.common.database.GOTRegistry;

public class GOTStructureBurntHouse extends GOTStructureRuinedHouse {
	public GOTStructureBurntHouse(boolean flag) {
		super(flag);
		woodBlock = GOTRegistry.wood1;
		woodMeta = 3;
		plankBlock = GOTRegistry.planks1;
		plankMeta = 3;
		fenceBlock = GOTRegistry.fence;
		fenceMeta = 3;
		stairBlock = GOTRegistry.stairsCharred;
		stoneBlock = GOTRegistry.scorchedStone;
		stoneMeta = 0;
		stoneVariantBlock = GOTRegistry.scorchedStone;
		stoneVariantMeta = 0;
	}
}
