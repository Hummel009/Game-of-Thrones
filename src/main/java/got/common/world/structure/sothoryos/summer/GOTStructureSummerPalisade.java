package got.common.world.structure.sothoryos.summer;

import java.util.Random;

import got.common.database.GOTRegistry;
import got.common.item.other.GOTItemBanner;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class GOTStructureSummerPalisade extends GOTStructureSummerBase {
	public boolean isTall = false;

	public GOTStructureSummerPalisade(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		this.setOriginAndRotation(world, i, j, k, rotation, 0);
		setupRandomBlocks(random);
		if (restrictions) {
			int i1 = 0;
			int k1 = 0;
			int j1 = getTopBlock(world, i1, 0) - 1;
			if (!isSurface(world, 0, j1, k1)) {
				return false;
			}
		}
		int height = 3 + random.nextInt(2);
		if (isTall) {
			height += 4;
		}
		if (isRuined()) {
			height = Math.max(1, height - 2);
		}
		for (int j12 = height; (j12 >= 0 || !isOpaque(world, 0, j12, 0)) && getY(j12) >= 0; --j12) {
			setBlockAndMetadata(world, 0, j12, 0, woodBlock, woodMeta);
			setGrassToDirt(world, 0, j12 - 1, 0);
		}
		if (isTall || random.nextInt(5) == 0) {
			setBlockAndMetadata(world, 0, height + 1, 0, fenceBlock, fenceMeta);
			this.placeSkull(world, random, 0, height + 2, 0);
		}
		if (!isRuined() && isTall) {
			placeWallBanner(world, 0, height, 0, GOTItemBanner.BannerType.SUMMER, 2);
		}
		return true;
	}

	public void setTall() {
		isTall = true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		int randomWood = random.nextInt(3);
		switch (randomWood) {
		case 0:
			woodBlock = GOTRegistry.wood4;
			woodMeta = 2;
			break;
		case 1:
			woodBlock = Blocks.log;
			woodMeta = 0;
			break;
		case 2:
			woodBlock = GOTRegistry.wood6;
			woodMeta = 3;
			break;
		default:
			break;
		}
	}
}
