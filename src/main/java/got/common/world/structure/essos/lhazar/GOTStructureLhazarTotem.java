package got.common.world.structure.essos.lhazar;

import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureLhazarTotem extends GOTStructureLhazarBase {
	public GOTStructureLhazarTotem(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		setOriginAndRotation(world, i, j, k, rotation, 2);
		setupRandomBlocks(random);
		if (restrictions) {
			for (int i1 = -3; i1 <= 3; ++i1) {
				for (int k1 = -3; k1 <= 3; ++k1) {
					int j1 = getTopBlock(world, i1, k1) - 1;
					if (isSurface(world, i1, j1, k1)) {
						continue;
					}
					return false;
				}
			}
		}
		loadStrScan("lhazar_totem");
		associateBlockMetaAlias("WOOD", woodBlock, woodMeta);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockMetaAlias("FLAG", flagBlock, flagMeta);
		associateBlockMetaAlias("BONE", boneBlock, boneMeta);
		generateStrScan(world, random, 0, 0, 0);
		placeWallBanner(world, 0, 6, -5, GOTItemBanner.BannerType.LHAZAR, 2);
		placeWallBanner(world, 0, 6, 5, GOTItemBanner.BannerType.LHAZAR, 0);
		placeWallBanner(world, -6, 8, 0, GOTItemBanner.BannerType.LHAZAR, 3);
		placeWallBanner(world, 6, 8, 0, GOTItemBanner.BannerType.LHAZAR, 1);
		return true;
	}
}
