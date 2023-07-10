package got.common.world.structure.other;

import got.common.block.other.*;
import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.entity.other.*;
import got.common.item.other.GOTItemBanner;
import got.common.item.other.GOTItemMug;
import got.common.recipe.GOTRecipeBrewing;
import got.common.tileentity.*;
import got.common.util.GOTLog;
import got.common.world.biome.GOTBiome;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.*;

public abstract class GOTStructureBase extends WorldGenerator {
	public boolean restrictions = true;
	public boolean notifyChanges;
	public EntityPlayer usingPlayer;
	public boolean shouldFindSurface;
	public GOTStructureBaseSettlement.AbstractInstance<? extends GOTStructureBaseSettlement> settlementInstance;
	public int originX;
	public int originY;
	public int originZ;
	public int rotationMode;
	public StructureBoundingBox sbb;
	public GOTStructureScan currentStrScan;
	public Map<String, BlockAliasPool> scanAliases = new HashMap<>();
	public Map<String, Float> scanAliasChances = new HashMap<>();
	public List<EntityCreature> characters = new ArrayList<>();
	public boolean disable;

	protected GOTStructureBase() {
		super(false);
		notifyChanges = false;
	}

	protected GOTStructureBase(boolean flag) {
		super(flag);
		notifyChanges = flag;
	}

	public static boolean isSurfaceStatic(World world, int i, int j, int k) {
		Block block = world.getBlock(i, j, k);
		BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
		if (block instanceof BlockSlab && !block.isOpaqueCube()) {
			return isSurfaceStatic(world, i, j - 1, k);
		}
		Block above = world.getBlock(i, j + 1, k);
		if (above.getMaterial().isLiquid()) {
			return false;
		}
		if (block == biome.topBlock || block == biome.fillerBlock) {
			return true;
		}
		if (block == Blocks.snow || block == Blocks.grass || block == Blocks.dirt || block == Blocks.gravel || block == GOTBlocks.dirtPath) {
			return true;
		}
		if (block == GOTBlocks.mudGrass || block == GOTBlocks.mud || block == Blocks.sand || block == GOTBlocks.redClay || block == GOTBlocks.whiteSand) {
			return true;
		}
		return block == GOTBlocks.asshaiDirt || block == GOTBlocks.basaltGravel;
	}

	public void addBlockAliasOption(String alias, int weight, Block block) {
		addBlockMetaAliasOption(alias, weight, block, -1);
	}

	public void addBlockMetaAliasOption(String alias, int weight, Block block, int meta) {
		scanAliases.computeIfAbsent(alias, k -> new BlockAliasPool()).addEntry(1, block, meta);
	}

	public void associateBlockAlias(String alias, Block block) {
		addBlockAliasOption(alias, 1, block);
	}

	public void associateBlockMetaAlias(String alias, Block block, int meta) {
		addBlockMetaAliasOption(alias, 1, block, meta);
	}

	public void clear() {
		characters.clear();
	}

	public void disable(boolean disable) {
		this.disable = disable;
	}

	public void fillChest(World world, Random random, int i, int j, int k, GOTChestContents contents, int amount) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		j = getY(j);
		if (!isInSBB(i, j, k)) {
			return;
		}
		GOTChestContents.fillChest(world, random, i, j, k, contents, amount);
	}

	public void findSurface(World world, int i, int k) {
		int j = 8;
		while (getY(j) >= 0) {
			if (isSurface(world, i, j, k)) {
				originY = getY(j);
				break;
			}
			--j;
		}
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		return generate(world, random, i, j, k, random.nextInt(4));
	}

	public abstract boolean generate(World var1, Random var2, int var3, int var4, int var5, int var6);

	public void generateStrScan(World world, Random random, int i, int j, int k) {
		for (int pass = 0; pass <= 1; ++pass) {
			for (GOTStructureScan.ScanStepBase step : currentStrScan.scanSteps) {
				int i1 = i - step.x;
				int j1 = j + step.y;
				int k1 = k + step.z;
				Block aliasBlock = null;
				int aliasMeta = -1;
				if (step.hasAlias()) {
					String alias = step.getAlias();
					BlockAliasPool pool = scanAliases.get(alias);
					if (pool == null) {
						throw new IllegalArgumentException("No block associated to alias " + alias + " !");
					}
					BlockAliasPool.BlockMetaEntry e = pool.getEntry(random);
					aliasBlock = e.block;
					aliasMeta = e.meta;
					if (scanAliasChances.containsKey(alias)) {
						float chance = scanAliasChances.get(alias);
						if (random.nextFloat() >= chance) {
							continue;
						}
					}
				}
				Block block = step.getBlock(aliasBlock);
				int meta = step.getMeta(aliasMeta);
				boolean inThisPass;
				if (block.getMaterial().isOpaque() || block == Blocks.air) {
					inThisPass = pass == 0;
				} else {
					inThisPass = pass == 1;
				}
				if (!inThisPass) {
					continue;
				}
				if (step.findLowest) {
					while (getY(j1) > 0 && !getBlock(world, i1, j1 - 1, k1).getMaterial().blocksMovement()) {
						--j1;
					}
				}
				if (step instanceof GOTStructureScan.ScanStepSkull) {
					placeSkull(world, random, i1, j1, k1);
					continue;
				}
				setBlockAndMetadata(world, i1, j1, k1, block, meta);
				if ((step.findLowest || j1 <= 1) && block.isOpaqueCube()) {
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				if (!step.fillDown) {
					continue;
				}
				int j2 = j1 - 1;
				while (!isOpaque(world, i1, j2, k1) && getY(j2) >= 0) {
					setBlockAndMetadata(world, i1, j2, k1, block, meta);
					if (block.isOpaqueCube()) {
						setGrassToDirt(world, i1, j2 - 1, k1);
					}
					--j2;
				}
			}
		}
		currentStrScan = null;
		scanAliases.clear();
	}

	public void generateSubstructure(GOTStructureBase str, World world, Random random, int i, int j, int k, int r) {
		generateSubstructureWithRestrictionFlag(str, world, random, i, j, k, r, restrictions);
	}

	public void generateSubstructureWithRestrictionFlag(GOTStructureBase str, World world, Random random, int i, int j, int k, int r, boolean isRestrict) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		j = getY(j);
		r += rotationMode;
		str.restrictions = isRestrict;
		str.usingPlayer = usingPlayer;
		str.settlementInstance = settlementInstance;
		str.sbb = sbb;
		r %= 4;
		str.generate(world, random, i, j, k, r);
	}

	public BiomeGenBase getBiome(World world, int i, int k) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		if (!isInSBB(i, 0, k)) {
			return null;
		}
		return world.getBiomeGenForCoords(i, k);
	}

	public Block getBlock(World world, int i, int j, int k) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		j = getY(j);
		if (!isInSBB(i, j, k)) {
			return Blocks.air;
		}
		return world.getBlock(i, j, k);
	}

	public ItemStack getRandomFlower(World world, Random random) {
		BiomeGenBase biome = getBiome(world, 0, 0);
		if (biome instanceof GOTBiome) {
			BiomeGenBase.FlowerEntry fe = ((GOTBiome) biome).getRandomFlower(random);
			return new ItemStack(fe.block, 1, fe.metadata);
		}
		if (random.nextBoolean()) {
			return new ItemStack(Blocks.yellow_flower, 0);
		}
		return new ItemStack(Blocks.red_flower, 0);
	}

	public ItemStack getRandomTallGrass(World world, Random random) {
		BiomeGenBase biome = getBiome(world, 0, 0);
		if (biome instanceof GOTBiome) {
			GOTBiome.GrassBlockAndMeta gbm = ((GOTBiome) biome).getRandomGrass(random);
			return new ItemStack(gbm.block, 1, gbm.meta);
		}
		return new ItemStack(Blocks.tallgrass, 1, 1);
	}

	public int getRotationMode() {
		return rotationMode;
	}

	public TileEntity getTileEntity(World world, int i, int j, int k) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		j = getY(j);
		if (!isInSBB(i, j, k)) {
			return null;
		}
		return world.getTileEntity(i, j, k);
	}

	public int getTopBlock(World world, int i, int k) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		if (!isInSBB(i, 0, k)) {
			return 0;
		}
		return world.getTopSolidOrLiquidBlock(i, k) - originY;
	}

	public int getX(int x, int z) {
		switch (rotationMode) {
			case 0: {
				return originX - x;
			}
			case 1: {
				return originX - z;
			}
			case 2: {
				return originX + x;
			}
			case 3: {
				return originX + z;
			}
			default:
				return originX;
		}
	}

	public int getY(int y) {
		return originY + y;
	}

	public int getZ(int x, int z) {
		switch (rotationMode) {
			case 0: {
				return originZ + z;
			}
			case 1: {
				return originZ - x;
			}
			case 2: {
				return originZ - z;
			}
			case 3: {
				return originZ + x;
			}
			default:
				return originZ;
		}
	}

	public boolean isAir(World world, int i, int j, int k) {
		return getBlock(world, i, j, k).getMaterial() == Material.air;
	}

	public boolean isInSBB(int i, int j, int k) {
		return sbb == null || sbb.isVecInside(i, j, k);
	}

	public boolean isOpaque(World world, int i, int j, int k) {
		return getBlock(world, i, j, k).isOpaqueCube();
	}

	public boolean isReplaceable(World world, int i, int j, int k) {
		return getBlock(world, i, j, k).isReplaceable(world, getX(i, k), getY(j), getZ(i, k));
	}

	public boolean isSideSolid(World world, int i, int j, int k, ForgeDirection side) {
		return getBlock(world, i, j, k).isSideSolid(world, getX(i, k), getY(j), getZ(i, k), side);
	}

	public boolean isSurface(World world, int i, int j, int k) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		j = getY(j);
		if (isSurfaceStatic(world, i, j, k)) {
			return true;
		}
		return settlementInstance != null && settlementInstance.isSettlementSpecificSurface(world, i, j, k);
	}

	public void leashEntityTo(EntityCreature entity, World world, int i, int j, int k) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		j = getY(j);
		if (!isInSBB(i, j, k)) {
			return;
		}
		EntityLeashKnot leash = EntityLeashKnot.func_110129_a(world, i, j, k);
		entity.setLeashedToEntity(leash, true);
	}

	public void loadStrScan(String name) {
		currentStrScan = GOTStructureScan.getScanByName(name);
		if (currentStrScan == null) {
			GOTLog.logger.error("Hummel009: Structure Scan for name {} does not exist!!!", name);
		}
		scanAliases.clear();
	}

	public void placeAnimalJar(World world, int i, int j, int k, Block block, int meta, EntityLiving creature) {
		setBlockAndMetadata(world, i, j, k, block, meta);
		TileEntity te = getTileEntity(world, i, j, k);
		if (te instanceof GOTTileEntityAnimalJar) {
			GOTTileEntityAnimalJar jar = (GOTTileEntityAnimalJar) te;
			NBTTagCompound nbt = new NBTTagCompound();
			if (creature != null) {
				int i1 = getX(i, k);
				int j1 = getY(j);
				int k1 = getZ(i, k);
				creature.setPosition(i1 + 0.5, j1, k1 + 0.5);
				creature.onSpawnWithEgg(null);
				if (creature.writeToNBTOptional(nbt)) {
					jar.setEntityData(nbt);
				}
			}
		}
	}

	public void placeArmorStand(World world, int i, int j, int k, int direction, ItemStack[] armor) {
		setBlockAndMetadata(world, i, j, k, GOTBlocks.armorStand, direction);
		setBlockAndMetadata(world, i, j + 1, k, GOTBlocks.armorStand, direction | 4);
		TileEntity tileentity = getTileEntity(world, i, j, k);
		if (tileentity instanceof GOTTileEntityArmorStand) {
			GOTTileEntityArmorStand armorStand = (GOTTileEntityArmorStand) tileentity;
			if (armor != null) {
				for (int l = 0; l < armor.length; ++l) {
					ItemStack armorPart = armor[l];
					if (armorPart == null) {
						armorStand.setInventorySlotContents(l, null);
						continue;
					}
					armorStand.setInventorySlotContents(l, armor[l].copy());
				}
			}
		}
	}

	public void placeBanner(World world, int i, int j, int k, GOTItemBanner.BannerType bt, int direction) {
		placeBanner(world, i, j, k, bt, direction, false, 0);
	}

	public void placeBanner(World world, int i, int j, int k, GOTItemBanner.BannerType bt, int direction, boolean protection, int r) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		j = getY(j);
		if (!isInSBB(i, j, k)) {
			return;
		}
		for (int l = 0; l < rotationMode; ++l) {
			direction = Direction.rotateRight[direction];
		}
		GOTEntityBanner banner = new GOTEntityBanner(world);
		banner.setLocationAndAngles(i + 0.5, j, k + 0.5, direction * 90.0f, 0.0f);
		banner.setBannerType(bt);
		if (protection) {
			banner.setStructureProtection(true);
			banner.setSelfProtection(false);
		}
		if (r > 0) {
			if (r > 64) {
				throw new RuntimeException("WARNING: Banner protection range " + r + " is too large!");
			}
			banner.setCustomRange(r);
		}
		world.spawnEntityInWorld(banner);
	}

	public void placeBarrel(World world, Random random, int i, int j, int k, int meta, GOTFoods foodList) {
		placeBarrel(world, random, i, j, k, meta, foodList.getRandomBrewableDrink(random));
	}

	public void placeBarrel(World world, Random random, int i, int j, int k, int meta, ItemStack drink) {
		setBlockAndMetadata(world, i, j, k, GOTBlocks.barrel, meta);
		TileEntity tileentity = getTileEntity(world, i, j, k);
		if (tileentity instanceof GOTTileEntityBarrel) {
			GOTTileEntityBarrel barrel = (GOTTileEntityBarrel) tileentity;
			barrel.barrelMode = 2;
			drink = drink.copy();
			GOTItemMug.setStrengthMeta(drink, MathHelper.getRandomIntegerInRange(random, 1, 3));
			GOTItemMug.setVessel(drink, GOTItemMug.Vessel.MUG, true);
			drink.stackSize = MathHelper.getRandomIntegerInRange(random, GOTRecipeBrewing.BARREL_CAPACITY / 2, GOTRecipeBrewing.BARREL_CAPACITY);
			barrel.setInventorySlotContents(9, drink);
		}
	}

	public void placeBigTorch(World world, int i, int j, int k) {
		setBlockAndMetadata(world, i, j, k, GOTBlocks.fuse, 0);
		setBlockAndMetadata(world, i, j + 1, k, GOTBlocks.fuse, 1);
	}

	public void placeChest(World world, Random random, int i, int j, int k, Block chest, int meta, GOTChestContents contents) {
		placeChest(world, random, i, j, k, chest, meta, contents, -1);
	}

	public void placeChest(World world, Random random, int i, int j, int k, Block chest, int meta, GOTChestContents contents, int amount) {
		setBlockAndMetadata(world, i, j, k, chest, meta);
		fillChest(world, random, i, j, k, contents, amount);
	}

	public void placeChest(World world, Random random, int i, int j, int k, int meta, GOTChestContents contents) {
		placeChest(world, random, i, j, k, meta, contents, -1);
	}

	public void placeChest(World world, Random random, int i, int j, int k, int meta, GOTChestContents contents, int amount) {
		placeChest(world, random, i, j, k, Blocks.chest, meta, contents, amount);
	}

	public void placeFlowerPot(World world, int i, int j, int k, ItemStack itemstack) {
		boolean vanilla;
		vanilla = itemstack == null || itemstack.getItem() == Item.getItemFromBlock(Blocks.cactus);
		if (vanilla) {
			setBlockAndMetadata(world, i, j, k, Blocks.flower_pot, 0);
		} else {
			setBlockAndMetadata(world, i, j, k, GOTBlocks.flowerPot, 0);
		}
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		j = getY(j);
		if (!isInSBB(i, j, k)) {
			return;
		}
		if (itemstack != null) {
			if (vanilla) {
				TileEntity te = world.getTileEntity(i, j, k);
				if (te instanceof TileEntityFlowerPot) {
					TileEntityFlowerPot pot = (TileEntityFlowerPot) te;
					pot.func_145964_a(itemstack.getItem(), itemstack.getItemDamage());
					pot.markDirty();
				}
			} else {
				GOTBlockFlowerPot.setPlant(world, i, j, k, itemstack);
			}
		}
	}

	public void placeKebabStand(World world, Random random, int i, int j, int k, Block block, int meta) {
		setBlockAndMetadata(world, i, j, k, block, meta);
		TileEntity tileentity = getTileEntity(world, i, j, k);
		if (tileentity instanceof GOTTileEntityKebabStand) {
			GOTTileEntityKebabStand stand = (GOTTileEntityKebabStand) tileentity;
			int kebab = MathHelper.getRandomIntegerInRange(random, 1, 8);
			stand.generateCookedKebab(kebab);
		}
	}

	public void placeMug(World world, Random random, int i, int j, int k, int meta, GOTFoods foodList) {
		placeMug(world, random, i, j, k, meta, foodList.getRandomPlaceableDrink(random), foodList);
	}

	public void placeMug(World world, Random random, int i, int j, int k, int meta, ItemStack drink, GOTFoods foodList) {
		placeMug(world, random, i, j, k, meta, drink, foodList.getPlaceableDrinkVessels());
	}

	public void placeMug(World world, Random random, int i, int j, int k, int meta, ItemStack drink, GOTItemMug.Vessel[] vesselTypes) {
		GOTItemMug.Vessel vessel = vesselTypes[random.nextInt(vesselTypes.length)];
		setBlockAndMetadata(world, i, j, k, vessel.getBlock(), meta);
		if (random.nextInt(3) != 0) {
			int i1 = i;
			int k1 = k;
			i = getX(i1, k1);
			k = getZ(i1, k1);
			j = getY(j);
			if (!isInSBB(i, j, k)) {
				return;
			}
			drink = drink.copy();
			drink.stackSize = 1;
			if (drink.getItem() instanceof GOTItemMug && ((GOTItemMug) drink.getItem()).isBrewable) {
				GOTItemMug.setStrengthMeta(drink, MathHelper.getRandomIntegerInRange(random, 1, 3));
			}
			GOTItemMug.setVessel(drink, vessel, true);
			GOTBlockMug.setMugItem(world, i, j, k, drink, vessel);
		}
	}

	public void placeNPCRespawner(GOTEntityNPCRespawner entity, World world, int i, int j, int k) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		j = getY(j);
		if (!isInSBB(i, j, k)) {
			return;
		}
		entity.setLocationAndAngles(i + 0.5, j, k + 0.5, 0.0f, 0.0f);
		world.spawnEntityInWorld(entity);
	}

	public void placePlate(World world, Random random, int i, int j, int k, Block plateBlock, GOTFoods foodList) {
		placePlateList(world, random, i, j, k, plateBlock, foodList, false);
	}

	public void placePlateDo(World world, Random random, int i, int j, int k, Block plateBlock, ItemStack foodItem, boolean certain) {
		TileEntity tileentity;
		if (!certain && random.nextBoolean()) {
			return;
		}
		setBlockAndMetadata(world, i, j, k, plateBlock, 0);
		tileentity = getTileEntity(world, i, j, k);
		if ((certain || random.nextBoolean()) && tileentity instanceof GOTTileEntityPlate) {
			GOTTileEntityPlate plate = (GOTTileEntityPlate) tileentity;
			plate.setFoodItem(foodItem);
		}
	}

	public void placePlateItem(World world, Random random, int i, int j, int k, Block plateBlock, ItemStack foodItem, boolean certain) {
		placePlateDo(world, random, i, j, k, plateBlock, foodItem, certain);
	}

	public void placePlateList(World world, Random random, int i, int j, int k, Block plateBlock, GOTFoods foodList, boolean certain) {
		ItemStack food = foodList.getRandomFoodForPlate(random);
		if (random.nextInt(4) == 0) {
			food.stackSize += 1 + random.nextInt(3);
		}
		placePlateItem(world, random, i, j, k, plateBlock, food, certain);
	}

	public void placePlateWithCertainty(World world, Random random, int i, int j, int k, Block plateBlock, GOTFoods foodList) {
		placePlateList(world, random, i, j, k, plateBlock, foodList, true);
	}

	public void placeRandomFlowerPot(World world, Random random, int i, int j, int k) {
		placeFlowerPot(world, i, j, k, getRandomFlower(world, random));
	}

	public void placeRug(GOTEntityRugBase rug, World world, int i, int j, int k, float rotation) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		j = getY(j);
		if (!isInSBB(i, j, k)) {
			return;
		}
		float f = rotation;
		switch (rotationMode) {
			case 0: {
				f += 0.0f;
				break;
			}
			case 1: {
				f += 270.0f;
				break;
			}
			case 2: {
				f += 180.0f;
				break;
			}
			case 3: {
				f += 90.0f;
				break;
			}
			default:
				f %= 360.0f;
				break;
		}
		rug.setLocationAndAngles(i + 0.5, j, k + 0.5, f, 0.0f);
		world.spawnEntityInWorld(rug);
	}

	public void placeSign(World world, int i, int j, int k, Block block, int meta, String[] text) {
		setBlockAndMetadata(world, i, j, k, block, meta);
		TileEntity te = getTileEntity(world, i, j, k);
		if (te instanceof TileEntitySign) {
			TileEntitySign sign = (TileEntitySign) te;
			sign.signText = Arrays.copyOf(text, sign.signText.length);
		}
	}

	public void placeSkull(World world, int i, int j, int k, int dir) {
		setBlockAndMetadata(world, i, j, k, Blocks.skull, 1);
		TileEntity tileentity = getTileEntity(world, i, j, k);
		if (tileentity instanceof TileEntitySkull) {
			TileEntitySkull skull = (TileEntitySkull) tileentity;
			dir += rotationMode * 4;
			dir %= 16;
			skull.func_145903_a(dir);
		}
	}

	public void placeSkull(World world, Random random, int i, int j, int k) {
		placeSkull(world, i, j, k, random.nextInt(16));
	}

	public void placeSpawnerChest(World world, Random random, int i, int j, int k, Block block, int meta, Class<? extends Entity> entityClass, GOTChestContents contents) {
		placeSpawnerChest(world, random, i, j, k, block, meta, entityClass, contents, -1);
	}

	public void placeSpawnerChest(World world, Random random, int i, int j, int k, Block block, int meta, Class<? extends Entity> entityClass, GOTChestContents contents, int amount) {
		setBlockAndMetadata(world, i, j, k, block, meta);
		TileEntity tileentity = getTileEntity(world, i, j, k);
		if (tileentity instanceof GOTTileEntitySpawnerChest) {
			((GOTTileEntitySpawnerChest) tileentity).setMobID(entityClass);
		}
		if (contents != null) {
			fillChest(world, random, i, j, k, contents, amount);
		}
	}

	public void placeWallBanner(World world, int i, int j, int k, GOTItemBanner.BannerType bt, int direction) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		j = getY(j);
		if (!isInSBB(i, j, k)) {
			return;
		}
		for (int l = 0; l < rotationMode; ++l) {
			direction = Direction.rotateRight[direction];
		}
		GOTEntityBannerWall banner = new GOTEntityBannerWall(world, i, j, k, direction);
		banner.setBannerType(bt);
		world.spawnEntityInWorld(banner);
	}

	public void placeWeaponRack(World world, int i, int j, int k, int meta, ItemStack weapon) {
		setBlockAndMetadata(world, i, j, k, GOTBlocks.weaponRack, meta);
		TileEntity tileentity = getTileEntity(world, i, j, k);
		if (tileentity instanceof GOTTileEntityWeaponRack) {
			GOTTileEntityWeaponRack weaponRack = (GOTTileEntityWeaponRack) tileentity;
			if (weapon != null) {
				weaponRack.setWeaponItem(weapon.copy());
			}
		}
	}

	public void plantFlower(World world, Random random, int i, int j, int k) {
		ItemStack itemstack = getRandomFlower(world, random);
		setBlockAndMetadata(world, i, j, k, Block.getBlockFromItem(itemstack.getItem()), itemstack.getItemDamage());
	}

	public void plantTallGrass(World world, Random random, int i, int j, int k) {
		ItemStack itemstack = getRandomTallGrass(world, random);
		setBlockAndMetadata(world, i, j, k, Block.getBlockFromItem(itemstack.getItem()), itemstack.getItemDamage());
	}

	public int rotateMeta(Block block, int meta) {
		if (block instanceof BlockRotatedPillar) {
			int i = meta & 3;
			int j = meta & 0xC;
			if (j == 0 || rotationMode == 0 || rotationMode == 2) {
				return meta;
			}
			if (j == 4) {
				j = 8;
			} else if (j == 8) {
				j = 4;
			}
			return j | i;
		}
		if (block instanceof BlockStairs) {
			int i = meta & 3;
			int j = meta & 4;
			for (int l = 0; l < rotationMode; ++l) {
				switch (i) {
					case 2:
						i = 1;
						continue;
					case 1:
						i = 3;
						continue;
					case 3:
						i = 0;
						continue;
					default:
						break;
				}
				i = 2;
			}
			return j | i;
		}
		if (block instanceof GOTBlockMug || block instanceof BlockTripWireHook || block instanceof BlockAnvil) {
			int i = meta;
			for (int l = 0; l < rotationMode; ++l) {
				i = Direction.rotateRight[i];
			}
			return i;
		}
		if (block instanceof GOTBlockArmorStand || block instanceof GOTBlockWeaponRack) {
			int i = meta & 3;
			int j = meta & 4;
			for (int l = 0; l < rotationMode; ++l) {
				i = Direction.rotateRight[i];
			}
			return j | i;
		}
		if (block == Blocks.wall_sign || block instanceof BlockLadder || block instanceof BlockFurnace || block instanceof BlockChest || block instanceof GOTBlockChest || block instanceof GOTBlockBarrel || block instanceof GOTBlockOven || block instanceof GOTBlockForgeBase || block instanceof GOTBlockKebabStand) {
			if (meta == 0 && (block instanceof BlockFurnace || block instanceof BlockChest || block instanceof GOTBlockChest || block instanceof GOTBlockOven || block instanceof GOTBlockForgeBase)) {
				return 0;
			}
			int i = meta;
			for (int l = 0; l < rotationMode; ++l) {
				i = Direction.facingToDirection[i];
				i = Direction.rotateRight[i];
				i = Direction.directionToFacing[i];
			}
			return i;
		}
		if (block == Blocks.standing_sign) {
			int i = meta;
			i += rotationMode * 4;
			i &= 0xF;
			return i;
		}
		if (block instanceof BlockBed) {
			boolean flag;
			int i = meta;
			flag = meta >= 8;
			if (flag) {
				i -= 8;
			}
			for (int l = 0; l < rotationMode; ++l) {
				i = Direction.rotateRight[i];
			}
			if (flag) {
				i += 8;
			}
			return i;
		}
		if (block instanceof BlockTorch) {
			if (meta == 5) {
				return 5;
			}
			int i = meta;
			for (int l = 0; l < rotationMode; ++l) {
				switch (i) {
					case 4:
						i = 1;
						continue;
					case 1:
						i = 3;
						continue;
					case 3:
						i = 2;
						continue;
					default:
						break;
				}
				if (i != 2) {
					continue;
				}
				i = 4;
			}
			return i;
		}
		if (block instanceof BlockDoor) {
			if ((meta & 8) != 0) {
				return meta;
			}
			int i = meta & 3;
			int j = meta & 4;
			for (int l = 0; l < rotationMode; ++l) {
				i = Direction.rotateRight[i];
			}
			return j | i;
		}
		if (block instanceof BlockTrapDoor) {
			int i = meta & 3;
			int j = meta & 4;
			int k = meta & 8;
			for (int l = 0; l < rotationMode; ++l) {
				switch (i) {
					case 0:
						i = 3;
						continue;
					case 1:
						i = 2;
						continue;
					case 2:
						i = 0;
						continue;
					default:
						break;
				}
				i = 1;
			}
			return k | j | i;
		}
		if (block instanceof BlockFenceGate) {
			int i = meta & 3;
			int j = meta & 4;
			for (int l = 0; l < rotationMode; ++l) {
				i = Direction.rotateRight[i];
			}
			return j | i;
		}
		if (block instanceof BlockPumpkin) {
			int i = meta;
			for (int l = 0; l < rotationMode; ++l) {
				i = Direction.rotateRight[i];
			}
			return i;
		}
		if (block instanceof BlockSkull) {
			if (meta < 2) {
				return meta;
			}
			int i = Direction.facingToDirection[meta];
			for (int l = 0; l < rotationMode; ++l) {
				i = Direction.rotateRight[i];
			}
			return Direction.directionToFacing[i];
		}
		if (block instanceof GOTBlockGate) {
			int i = meta & 7;
			int j = meta & 8;
			if (i != 0 && i != 1) {
				for (int l = 0; l < rotationMode; ++l) {
					i = Direction.facingToDirection[i];
					i = Direction.rotateRight[i];
					i = Direction.directionToFacing[i];
				}
			}
			return j | i;
		}
		if (block instanceof BlockLever) {
			int i = meta & 7;
			int j = meta & 8;
			if (i == 0 || i == 7) {
				for (int l = 0; l < rotationMode; ++l) {
					i = i == 0 ? 7 : 0;
				}
			} else if (i == 5 || i == 6) {
				for (int l = 0; l < rotationMode; ++l) {
					i = i == 5 ? 6 : 5;
				}
			} else {
				for (int l = 0; l < rotationMode; ++l) {
					switch (i) {
						case 4:
							i = 1;
							continue;
						case 1:
							i = 3;
							continue;
						case 3:
							i = 2;
							continue;
						default:
							break;
					}
					i = 4;
				}
			}
			return j | i;
		}
		if (block instanceof BlockButton) {
			int i = meta;
			int j = meta & 8;
			for (int l = 0; l < rotationMode; ++l) {
				switch (i) {
					case 4:
						i = 1;
						continue;
					case 1:
						i = 3;
						continue;
					case 3:
						i = 2;
						continue;
					default:
						break;
				}
				if (i != 2) {
					continue;
				}
				i = 4;
			}
			return j | i;
		}
		return meta;
	}

	public void setAir(World world, int i, int j, int k) {
		setBlockAndMetadata(world, i, j, k, Blocks.air, 0);
	}

	public void setBiomeFiller(World world, int i, int j, int k) {
		BiomeGenBase biome = getBiome(world, i, k);
		Block fillerBlock = biome.fillerBlock;
		int fillerMeta = 0;
		if (biome instanceof GOTBiome) {
			fillerMeta = ((GOTBiome) biome).getFillerBlockMeta();
		}
		setBlockAndMetadata(world, i, j, k, fillerBlock, fillerMeta);
	}

	public void setBiomeTop(World world, int i, int j, int k) {
		BiomeGenBase biome = getBiome(world, i, k);
		Block topBlock = biome.topBlock;
		int topMeta = 0;
		if (biome instanceof GOTBiome) {
			topMeta = ((GOTBiome) biome).getTopBlockMeta();
		}
		setBlockAndMetadata(world, i, j, k, topBlock, topMeta);
	}

	public void setBlockAliasChance(String alias, float chance) {
		scanAliasChances.put(alias, chance);
	}

	public void setBlockAndMetadata(World world, int i, int j, int k, Block block, int meta) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		j = getY(j);
		if (!isInSBB(i, j, k)) {
			return;
		}
		meta = rotateMeta(block, meta);
		setBlockAndNotifyAdequately(world, i, j, k, block, meta);
		if (meta != 0 && (block instanceof BlockChest || block instanceof GOTBlockChest || block instanceof BlockFurnace || block instanceof GOTBlockOven || block instanceof GOTBlockForgeBase)) {
			world.setBlockMetadataWithNotify(i, j, k, meta, notifyChanges ? 3 : 2);
		}
	}

	public void setGrassToDirt(World world, int i, int j, int k) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		j = getY(j);
		if (!isInSBB(i, j, k)) {
			return;
		}
		world.getBlock(i, j, k).onPlantGrow(world, i, j, k, i, j, k);
	}

	public void setOriginAndRotation(World world, int i, int j, int k, int rotation, int shift) {
		setOriginAndRotation(world, i, j, k, rotation, shift, 0);
	}

	public void setOriginAndRotation(World world, int i, int j, int k, int rotation, int shift, int shiftX) {
		--j;
		rotationMode = rotation;
		switch (rotationMode) {
			case 0: {
				k += shift;
				i += shiftX;
				break;
			}
			case 1: {
				i -= shift;
				k += shiftX;
				break;
			}
			case 2: {
				k -= shift;
				i -= shiftX;
				break;
			}
			case 3: {
				i += shift;
				k -= shiftX;
				break;
			}
			default:
				break;
		}
		originX = i;
		originY = j;
		originZ = k;
		if (shouldFindSurface) {
			shouldFindSurface = false;
			findSurface(world, -shiftX, -shift);
		}
	}

	public GOTStructureBase setRestrictions(boolean b) {
		restrictions = b;
		return this;
	}

	public void setStructureBB(StructureBoundingBox box) {
		sbb = box;
	}

	public void setupRandomBlocks(Random random) {
	}

	public void spawnItemFrame(World world, int i, int j, int k, int direction, ItemStack itemstack) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		j = getY(j);
		if (!isInSBB(i, j, k)) {
			return;
		}
		for (int l = 0; l < rotationMode; ++l) {
			direction = Direction.rotateRight[direction];
		}
		EntityItemFrame frame = new EntityItemFrame(world, i, j, k, direction);
		frame.setDisplayedItem(itemstack);
		world.spawnEntityInWorld(frame);
	}

	public void spawnLegendaryNPC(EntityCreature entity, World world, int i, int j, int k) {
		if (!disable) {
			int i1 = i;
			int k1 = k;
			i = getX(i1, k1);
			k = getZ(i1, k1);
			j = getY(j);
			if (!isInSBB(i, j, k)) {
				return;
			}
			entity.setLocationAndAngles(i + 0.5, j, k + 0.5, 0.0f, 0.0f);
			entity.onSpawnWithEgg(null);
			if (entity instanceof GOTEntityNPC) {
				((GOTEntityNPC) entity).isNPCPersistent = true;
			}
			world.spawnEntityInWorld(entity);
		} else if (entity instanceof GOTEntityNPC) {
			((GOTEntityNPC) entity).spawnRidingHorse = false;
			characters.add(entity);
		}
	}

	public void spawnNPCAndSetHome(EntityCreature entity, World world, int i, int j, int k, int homeDistance) {
		int i1 = i;
		int k1 = k;
		i = getX(i1, k1);
		k = getZ(i1, k1);
		j = getY(j);
		if (!isInSBB(i, j, k)) {
			return;
		}
		entity.setLocationAndAngles(i + 0.5, j, k + 0.5, 0.0f, 0.0f);
		entity.onSpawnWithEgg(null);
		if (entity instanceof GOTEntityNPC) {
			((GOTEntityNPC) entity).isNPCPersistent = true;
		}
		world.spawnEntityInWorld(entity);
		entity.setHomeArea(i, j, k, homeDistance);
	}

	public int usingPlayerRotation() {
		return GOTStructureRegistry.getRotationFromPlayer(usingPlayer);
	}

	public static class BlockAliasPool {
		public List<BlockMetaEntry> entries = new ArrayList<>();
		public int totalWeight;

		public BlockAliasPool() {
		}

		public void addEntry(int w, Block b, int m) {
			entries.add(new BlockMetaEntry(w, b, m));
			totalWeight = WeightedRandom.getTotalWeight(entries);
		}

		public BlockMetaEntry getEntry(Random random) {
			return (BlockMetaEntry) WeightedRandom.getRandomItem(random, entries, totalWeight);
		}

		public static class BlockMetaEntry extends WeightedRandom.Item {
			public Block block;
			public int meta;

			public BlockMetaEntry(int w, Block b, int m) {
				super(w);
				block = b;
				meta = m;
			}
		}

	}
}