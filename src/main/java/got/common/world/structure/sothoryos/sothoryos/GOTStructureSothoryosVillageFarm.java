package got.common.world.structure.sothoryos.sothoryos;

import got.common.database.GOTRegistry;
import got.common.entity.sothoryos.sothoryos.GOTEntitySothoryosFarmer;
import got.common.entity.sothoryos.sothoryos.GOTEntitySothoryosFarmhand;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.Random;

public class GOTStructureSothoryosVillageFarm extends GOTStructureSothoryosHouse {
	public Block cropBlock;
	public int cropMeta;
	public Item seedItem;
	public boolean melon;

	public GOTStructureSothoryosVillageFarm(boolean flag) {
		super(flag);
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k, int rotation) {
		int i1;
		int k1;
		if (!super.generate(world, random, i, j, k, rotation)) {
			return false;
		}
		int randomCrop = random.nextInt(8);
		switch (randomCrop) {
			case 0:
			case 1:
				cropBlock = Blocks.potatoes;
				cropMeta = 7;
				seedItem = Items.potato;
				melon = false;
				break;
			case 2:
			case 3:
				cropBlock = GOTRegistry.cornStalk;
				cropMeta = 0;
				seedItem = Item.getItemFromBlock(GOTRegistry.cornStalk);
				melon = false;
				break;
			case 4:
				cropBlock = Blocks.wheat;
				cropMeta = 7;
				seedItem = Items.wheat_seeds;
				melon = false;
				break;
			case 5:
				cropBlock = Blocks.carrots;
				cropMeta = 7;
				seedItem = Items.carrot;
				melon = false;
				break;
			case 6:
			case 7:
				cropBlock = Blocks.melon_stem;
				cropMeta = 7;
				seedItem = Items.melon_seeds;
				melon = true;
				break;
			default:
				break;
		}
		for (i1 = -4; i1 <= 4; ++i1) {
			for (k1 = -3; k1 <= 3; ++k1) {
				for (int j1 = 1; j1 <= 4; ++j1) {
					setAir(world, i1, j1, k1);
				}
			}
		}
		loadStrScan("sothoryos_farm");
		associateBlockAlias("BRICK_STAIR", brickStairBlock);
		associateBlockMetaAlias("BRICK_WALL", brickWallBlock, brickWallMeta);
		associateBlockMetaAlias("FENCE", fenceBlock, fenceMeta);
		associateBlockAlias("FENCE_GATE", fenceGateBlock);
		associateBlockMetaAlias("CROP", cropBlock, cropMeta);
		generateStrScan(world, random, 0, 0, 0);
		if (melon) {
			for (k1 = -2; k1 <= 2; ++k1) {
				setBlockAndMetadata(world, 0, 1, k1, brickBlock, brickMeta);
			}
			for (i1 = -1; i1 <= 1; ++i1) {
				setBlockAndMetadata(world, i1, 0, 0, Blocks.stained_hardened_clay, 12);
				setBlockAndMetadata(world, i1, 1, 0, Blocks.water, 0);
				setAir(world, i1, 2, 0);
			}
			for (int k12 : new int[]{-1, 1}) {
				for (int i12 = -3; i12 <= 3; ++i12) {
					if (i12 == 0) {
						continue;
					}
					setBlockAndMetadata(world, i12, 0, k12, Blocks.sand, 0);
					setBlockAndMetadata(world, i12, 1, k12, GOTRegistry.mudGrass, 0);
				}
			}
		}
		if (random.nextInt(3) == 0) {
			GOTEntitySothoryosFarmer farmer = new GOTEntitySothoryosFarmer(world);
			spawnNPCAndSetHome(farmer, world, 0, 2, 1, 4);
		}
		GOTEntitySothoryosFarmhand farmhand1 = new GOTEntitySothoryosFarmhand(world);
		farmhand1.seedsItem = seedItem;
		spawnNPCAndSetHome(farmhand1, world, -2, 2, 0, 6);
		GOTEntitySothoryosFarmhand farmhand2 = new GOTEntitySothoryosFarmhand(world);
		farmhand2.seedsItem = seedItem;
		spawnNPCAndSetHome(farmhand2, world, 2, 2, 0, 6);
		return true;
	}

	@Override
	public int getOffset() {
		return 4;
	}
}
