package got.common.world.structure.other;

import got.common.block.other.*;
import got.common.database.GOTBlocks;
import got.common.database.GOTChestContents;
import got.common.database.GOTFoods;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.inanimate.GOTEntityBanner;
import got.common.entity.other.inanimate.GOTEntityBannerWall;
import got.common.entity.other.inanimate.GOTEntityNPCRespawner;
import got.common.entity.other.inanimate.GOTEntityRugBase;
import got.common.item.other.GOTItemBanner;
import got.common.item.other.GOTItemMug;
import got.common.recipe.GOTRecipeBrewing;
import got.common.tileentity.*;
import got.common.util.GOTCrashHandler;
import got.common.util.GOTLog;
import got.common.world.biome.GOTBiome;
import got.common.world.structure.GOTStructureRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.*;

public abstract class GOTStructureBase extends WorldGenerator {
	protected final boolean notifyChanges;
	private final Map<String, BlockAliasPool> scanAliases = new HashMap<>();
	private final Map<String, Float> scanAliasChances = new HashMap<>();
	private final Set<Class<? extends Entity>> entityClasses = new HashSet<>();
	protected EntityPlayer usingPlayer;
	protected boolean restrictions = true;
	protected int originX;
	protected int originY;
	protected int originZ;

	private GOTStructureBaseSettlement.AbstractInstance settlementInstance;
	private boolean shouldFindSurface;
	private int rotationMode;
	private StructureBoundingBox sbb;
	private GOTStructureScan currentStrScan;
	private boolean wikiGen;

	protected GOTStructureBase() {
		super(false);
		notifyChanges = false;
	}

	protected GOTStructureBase(boolean flag) {
		super(flag);
		notifyChanges = flag;
	}

	public static boolean isSurfaceStatic(World world, int i, int j, int k) {
		int j1 = j;
		while (true) {
			Block block = world.getBlock(i, j1, k);
			BiomeGenBase biome = GOTCrashHandler.getBiomeGenForCoords(world, i, k);
			if (block instanceof BlockSlab && !block.isOpaqueCube()) {
				j1 = j1 - 1;
				continue;
			}
			Block above = world.getBlock(i, j1 + 1, k);
			return !above.getMaterial().isLiquid() && (block == biome.topBlock || block == biome.fillerBlock || block == Blocks.snow || block == Blocks.grass || block == Blocks.dirt || block == Blocks.gravel || block == GOTBlocks.dirtPath || block == GOTBlocks.mudGrass || block == GOTBlocks.mud || block == Blocks.sand || block == GOTBlocks.redClay || block == GOTBlocks.whiteSand || block == GOTBlocks.asshaiDirt || block == GOTBlocks.basaltGravel);
		}
	}

	@SuppressWarnings("unused")
	public EntityPlayer getUsingPlayer() {
		return usingPlayer;
	}

	public void setUsingPlayer(EntityPlayer usingPlayer) {
		this.usingPlayer = usingPlayer;
	}

	protected void addBlockAliasOption(String alias, Block block) {
		if (wikiGen) {
			return;
		}
		addBlockMetaAliasOption(alias, block, -1);
	}

	protected void addBlockMetaAliasOption(String alias, Block block, int meta) {
		if (wikiGen) {
			return;
		}
		scanAliases.computeIfAbsent(alias, k -> new BlockAliasPool()).addEntry(1, block, meta);
	}

	protected void associateBlockAlias(String alias, Block block) {
		if (wikiGen) {
			return;
		}
		addBlockAliasOption(alias, block);
	}

	protected void associateBlockMetaAlias(String alias, Block block, int meta) {
		if (wikiGen) {
			return;
		}
		addBlockMetaAliasOption(alias, block, meta);
	}

	private void fillChest(IBlockAccess world, Random random, int i, int j, int k, GOTChestContents contents, int amount) {
		if (wikiGen) {
			return;
		}
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		if (!isInSBB(i2, j1, k2)) {
			return;
		}
		GOTChestContents.fillChest(world, random, i2, j1, k2, contents, amount);
	}

	protected void findSurface(World world, int i, int k) {
		if (wikiGen) {
			return;
		}
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

	protected void generateStrScan(World world, Random random, int i, int j, int k) {
		if (wikiGen) {
			return;
		}
		for (int pass = 0; pass <= 1; ++pass) {
			for (GOTStructureScan.ScanStepBase step : currentStrScan.getScanSteps()) {
				int i1 = i - step.getX();
				int j1 = j + step.getY();
				int k1 = k + step.getZ();
				Block aliasBlock = null;
				int aliasMeta = -1;
				if (step.hasAlias()) {
					String alias = step.getAlias();
					BlockAliasPool pool = scanAliases.get(alias);
					if (pool == null) {
						throw new IllegalArgumentException("No block associated to alias " + alias + " !");
					}
					BlockAliasPool.BlockMetaEntry e = pool.getEntry(random);
					aliasBlock = e.getBlock();
					aliasMeta = e.getMeta();
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
				if (step.isFindLowest()) {
					while (getY(j1) > 0 && !getBlock(world, i1, j1 - 1, k1).getMaterial().blocksMovement()) {
						--j1;
					}
				}
				if (step instanceof GOTStructureScan.ScanStepSkull) {
					placeSkull(world, random, i1, j1, k1);
					continue;
				}
				setBlockAndMetadata(world, i1, j1, k1, block, meta);
				if ((step.isFindLowest() || j1 <= 1) && block.isOpaqueCube()) {
					setGrassToDirt(world, i1, j1 - 1, k1);
				}
				if (!step.isFillDown()) {
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

	protected void generateSubstructure(GOTStructureBase str, World world, Random random, int i, int j, int k, int r) {
		if (wikiGen) {
			return;
		}
		generateSubstructureWithRestrictionFlag(str, world, random, i, j, k, r, restrictions);
	}

	private void generateSubstructureWithRestrictionFlag(GOTStructureBase str, World world, Random random, int i, int j, int k, int r, boolean isRestrict) {
		if (wikiGen) {
			return;
		}
		int r1 = r;
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		r1 += rotationMode;
		str.restrictions = isRestrict;
		str.usingPlayer = usingPlayer;
		str.settlementInstance = settlementInstance;
		str.sbb = sbb;
		r1 %= 4;
		str.generate(world, random, i2, j1, k2, r1);
	}

	protected BiomeGenBase getBiome(World world, int i, int k) {
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		if (!isInSBB(i2, 0, k2)) {
			return null;
		}
		return GOTCrashHandler.getBiomeGenForCoords(world, i2, k2);
	}

	protected Block getBlock(IBlockAccess world, int i, int j, int k) {
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		if (!isInSBB(i2, j1, k2)) {
			return Blocks.air;
		}
		return world.getBlock(i2, j1, k2);
	}

	protected ItemStack getRandomFlower(World world, Random random) {
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

	private ItemStack getRandomTallGrass(World world, Random random) {
		BiomeGenBase biome = getBiome(world, 0, 0);
		if (biome instanceof GOTBiome) {
			GOTBiome.GrassBlockAndMeta gbm = ((GOTBiome) biome).getRandomGrass(random);
			return new ItemStack(gbm.getBlock(), 1, gbm.getMeta());
		}
		return new ItemStack(Blocks.tallgrass, 1, 1);
	}

	protected int getRotationMode() {
		return rotationMode;
	}

	protected TileEntity getTileEntity(IBlockAccess world, int i, int j, int k) {
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		if (!isInSBB(i2, j1, k2)) {
			return null;
		}
		return world.getTileEntity(i2, j1, k2);
	}

	protected int getTopBlock(World world, int i, int k) {
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		if (!isInSBB(i2, 0, k2)) {
			return 0;
		}
		return world.getTopSolidOrLiquidBlock(i2, k2) - originY;
	}

	protected int getX(int x, int z) {
		switch (rotationMode) {
			case 0:
				return originX - x;
			case 1:
				return originX - z;
			case 2:
				return originX + x;
			case 3:
				return originX + z;
			default:
				return originX;
		}
	}

	protected int getY(int y) {
		return originY + y;
	}

	protected int getZ(int x, int z) {
		switch (rotationMode) {
			case 0:
				return originZ + z;
			case 1:
				return originZ - x;
			case 2:
				return originZ - z;
			case 3:
				return originZ + x;
			default:
				return originZ;
		}
	}

	protected boolean isAir(IBlockAccess world, int i, int j, int k) {
		return getBlock(world, i, j, k).getMaterial() == Material.air;
	}

	private boolean isInSBB(int i, int j, int k) {
		return sbb == null || sbb.isVecInside(i, j, k);
	}

	protected boolean isOpaque(IBlockAccess world, int i, int j, int k) {
		return getBlock(world, i, j, k).isOpaqueCube();
	}

	protected boolean isReplaceable(IBlockAccess world, int i, int j, int k) {
		return getBlock(world, i, j, k).isReplaceable(world, getX(i, k), getY(j), getZ(i, k));
	}

	protected boolean isSideSolid(IBlockAccess world, int i, int j, int k, ForgeDirection side) {
		return getBlock(world, i, j, k).isSideSolid(world, getX(i, k), getY(j), getZ(i, k), side);
	}

	protected boolean isSurface(World world, int i, int j, int k) {
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		return isSurfaceStatic(world, i2, j1, k2) || settlementInstance != null && settlementInstance.isSettlementSpecificSurface(world, i2, j1, k2);
	}

	protected void leashEntityTo(EntityCreature entity, World world, int i, int j, int k) {
		if (wikiGen) {
			return;
		}
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		if (!isInSBB(i2, j1, k2)) {
			return;
		}
		EntityLeashKnot leash = EntityLeashKnot.func_110129_a(world, i2, j1, k2);
		entity.setLeashedToEntity(leash, true);
	}

	protected void loadStrScan(String name) {
		if (wikiGen) {
			return;
		}
		currentStrScan = GOTStructureScan.getScanByName(name);
		if (currentStrScan == null) {
			GOTLog.getLogger().error("Hummel009: Structure Scan for name {} does not exist!!!", name);
		}
		scanAliases.clear();
	}

	protected void placeAnimalJar(World world, int i, int j, int k, Block block, int meta, EntityLiving creature) {
		if (wikiGen) {
			return;
		}
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

	protected void placeArmorStand(World world, int i, int j, int k, int direction, ItemStack[] armor) {
		if (wikiGen) {
			return;
		}
		setBlockAndMetadata(world, i, j, k, GOTBlocks.armorStand, direction);
		setBlockAndMetadata(world, i, j + 1, k, GOTBlocks.armorStand, direction | 4);
		TileEntity tileentity = getTileEntity(world, i, j, k);
		if (tileentity instanceof GOTTileEntityArmorStand) {
			IInventory armorStand = (IInventory) tileentity;
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

	protected void placeBanner(World world, int i, int j, int k, GOTItemBanner.BannerType bt, int direction) {
		if (wikiGen) {
			return;
		}
		placeBanner(world, i, j, k, bt, direction, false, 0);
	}

	protected void placeBanner(World world, int i, int j, int k, GOTItemBanner.BannerType bt, int direction, boolean protection, int r) {
		if (wikiGen) {
			return;
		}
		int direction1 = direction;
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		if (!isInSBB(i2, j1, k2)) {
			return;
		}
		for (int l = 0; l < rotationMode; ++l) {
			direction1 = Direction.rotateRight[direction1];
		}
		GOTEntityBanner banner = new GOTEntityBanner(world);
		banner.setLocationAndAngles(i2 + 0.5, j1, k2 + 0.5, direction1 * 90.0f, 0.0f);
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

	protected void placeBarrel(World world, Random random, int i, int j, int k, int meta, GOTFoods foodList) {
		if (wikiGen) {
			return;
		}
		placeBarrel(world, random, i, j, k, meta, foodList.getRandomBrewableDrink(random));
	}

	protected void placeBarrel(World world, Random random, int i, int j, int k, int meta, ItemStack drink) {
		if (wikiGen) {
			return;
		}
		ItemStack drink1 = drink;
		setBlockAndMetadata(world, i, j, k, GOTBlocks.barrel, meta);
		TileEntity tileentity = getTileEntity(world, i, j, k);
		if (tileentity instanceof GOTTileEntityBarrel) {
			GOTTileEntityBarrel barrel = (GOTTileEntityBarrel) tileentity;
			barrel.setBarrelMode(2);
			drink1 = drink1.copy();
			GOTItemMug.setStrengthMeta(drink1, MathHelper.getRandomIntegerInRange(random, 1, 3));
			GOTItemMug.setVessel(drink1, GOTItemMug.Vessel.MUG, true);
			drink1.stackSize = MathHelper.getRandomIntegerInRange(random, GOTRecipeBrewing.BARREL_CAPACITY / 2, GOTRecipeBrewing.BARREL_CAPACITY);
			barrel.setInventorySlotContents(9, drink1);
		}
	}

	protected void placeBigTorch(World world, int i, int j, int k) {
		if (wikiGen) {
			return;
		}
		setBlockAndMetadata(world, i, j, k, GOTBlocks.fuse, 0);
		setBlockAndMetadata(world, i, j + 1, k, GOTBlocks.fuse, 1);
	}

	protected void placeChest(World world, Random random, int i, int j, int k, Block chest, int meta, GOTChestContents contents) {
		if (wikiGen) {
			return;
		}
		placeChest(world, random, i, j, k, chest, meta, contents, -1);
	}

	protected void placeChest(World world, Random random, int i, int j, int k, Block chest, int meta, GOTChestContents contents, int amount) {
		if (wikiGen) {
			return;
		}
		setBlockAndMetadata(world, i, j, k, chest, meta);
		fillChest(world, random, i, j, k, contents, amount);
	}

	protected void placeChest(World world, Random random, int i, int j, int k, int meta, GOTChestContents contents) {
		if (wikiGen) {
			return;
		}
		placeChest(world, random, i, j, k, meta, contents, -1);
	}

	protected void placeChest(World world, Random random, int i, int j, int k, int meta, GOTChestContents contents, int amount) {
		if (wikiGen) {
			return;
		}
		placeChest(world, random, i, j, k, Blocks.chest, meta, contents, amount);
	}

	protected void placeFlowerPot(World world, int i, int j, int k, ItemStack itemstack) {
		if (wikiGen) {
			return;
		}
		boolean vanilla = itemstack == null || itemstack.getItem() == Item.getItemFromBlock(Blocks.cactus);
		if (vanilla) {
			setBlockAndMetadata(world, i, j, k, Blocks.flower_pot, 0);
		} else {
			setBlockAndMetadata(world, i, j, k, GOTBlocks.flowerPot, 0);
		}
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		if (!isInSBB(i2, j1, k2)) {
			return;
		}
		if (itemstack != null) {
			if (vanilla) {
				TileEntity te = world.getTileEntity(i2, j1, k2);
				if (te instanceof TileEntityFlowerPot) {
					TileEntityFlowerPot pot = (TileEntityFlowerPot) te;
					pot.func_145964_a(itemstack.getItem(), itemstack.getItemDamage());
					pot.markDirty();
				}
			} else {
				GOTBlockFlowerPot.setPlant(world, i2, j1, k2, itemstack);
			}
		}
	}

	protected void placeKebabStand(World world, Random random, int i, int j, int k, Block block, int meta) {
		if (wikiGen) {
			return;
		}
		setBlockAndMetadata(world, i, j, k, block, meta);
		TileEntity tileentity = getTileEntity(world, i, j, k);
		if (tileentity instanceof GOTTileEntityKebabStand) {
			GOTTileEntityKebabStand stand = (GOTTileEntityKebabStand) tileentity;
			int kebab = MathHelper.getRandomIntegerInRange(random, 1, 8);
			stand.generateCookedKebab(kebab);
		}
	}

	protected void placeMug(World world, Random random, int i, int j, int k, int meta, GOTFoods foodList) {
		if (wikiGen) {
			return;
		}
		placeMug(world, random, i, j, k, meta, foodList.getRandomPlaceableDrink(random), foodList);
	}

	protected void placeMug(World world, Random random, int i, int j, int k, int meta, ItemStack drink, GOTFoods foodList) {
		if (wikiGen) {
			return;
		}
		placeMug(world, random, i, j, k, meta, drink, foodList.getPlaceableDrinkVessels());
	}

	private void placeMug(World world, Random random, int i, int j, int k, int meta, ItemStack drink, GOTItemMug.Vessel[] vesselTypes) {
		if (wikiGen) {
			return;
		}
		int i2 = i;
		int k2 = k;
		int j1 = j;
		ItemStack drink1 = drink;
		GOTItemMug.Vessel vessel = vesselTypes[random.nextInt(vesselTypes.length)];
		setBlockAndMetadata(world, i2, j1, k2, vessel.getBlock(), meta);
		if (random.nextInt(3) != 0) {
			int i1 = i2;
			int k1 = k2;
			i2 = getX(i1, k1);
			k2 = getZ(i1, k1);
			j1 = getY(j1);
			if (!isInSBB(i2, j1, k2)) {
				return;
			}
			drink1 = drink1.copy();
			drink1.stackSize = 1;
			if (drink1.getItem() instanceof GOTItemMug && ((GOTItemMug) drink1.getItem()).isBrewable()) {
				GOTItemMug.setStrengthMeta(drink1, MathHelper.getRandomIntegerInRange(random, 1, 3));
			}
			GOTItemMug.setVessel(drink1, vessel, true);
			GOTBlockMug.setMugItem(world, i2, j1, k2, drink1, vessel);
		}
	}

	protected void placeNPCRespawner(GOTEntityNPCRespawner entity, World world, int i, int j, int k) {
		if (wikiGen) {
			if (entity.getSpawnClass1() != null) {
				entityClasses.add(entity.getSpawnClass1());
			}
			if (entity.getSpawnClass2() != null) {
				entityClasses.add(entity.getSpawnClass2());
			}
			return;
		}
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		if (!isInSBB(i2, j1, k2)) {
			return;
		}
		entity.setLocationAndAngles(i2 + 0.5, j1, k2 + 0.5, 0.0f, 0.0f);
		world.spawnEntityInWorld(entity);
	}

	protected void placePlate(World world, Random random, int i, int j, int k, Block plateBlock, GOTFoods foodList) {
		if (wikiGen) {
			return;
		}
		placePlateList(world, random, i, j, k, plateBlock, foodList, false);
	}

	private void placePlateDo(World world, Random random, int i, int j, int k, Block plateBlock, ItemStack foodItem, boolean certain) {
		if (wikiGen) {
			return;
		}
		if (!certain && random.nextBoolean()) {
			return;
		}
		setBlockAndMetadata(world, i, j, k, plateBlock, 0);
		TileEntity tileentity = getTileEntity(world, i, j, k);
		if ((certain || random.nextBoolean()) && tileentity instanceof GOTTileEntityPlate) {
			GOTTileEntityPlate plate = (GOTTileEntityPlate) tileentity;
			plate.setFoodItem(foodItem);
		}
	}

	protected void placePlateItem(World world, Random random, int i, int j, int k, Block plateBlock, ItemStack foodItem, boolean certain) {
		if (wikiGen) {
			return;
		}
		placePlateDo(world, random, i, j, k, plateBlock, foodItem, certain);
	}

	private void placePlateList(World world, Random random, int i, int j, int k, Block plateBlock, GOTFoods foodList, boolean certain) {
		if (wikiGen) {
			return;
		}
		ItemStack food = foodList.getRandomFoodForPlate(random);
		if (random.nextInt(4) == 0) {
			food.stackSize += 1 + random.nextInt(3);
		}
		placePlateItem(world, random, i, j, k, plateBlock, food, certain);
	}

	protected void placePlateWithCertainty(World world, Random random, int i, int j, int k, Block plateBlock, GOTFoods foodList) {
		if (wikiGen) {
			return;
		}
		placePlateList(world, random, i, j, k, plateBlock, foodList, true);
	}

	protected void placeRandomFlowerPot(World world, Random random, int i, int j, int k) {
		if (wikiGen) {
			return;
		}
		placeFlowerPot(world, i, j, k, getRandomFlower(world, random));
	}

	protected void placeRug(GOTEntityRugBase rug, World world, int i, int j, int k, float rotation) {
		if (wikiGen) {
			return;
		}
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		if (!isInSBB(i2, j1, k2)) {
			return;
		}
		float f = rotation;
		switch (rotationMode) {
			case 0:
				f += 0.0f;
				break;
			case 1:
				f += 270.0f;
				break;
			case 2:
				f += 180.0f;
				break;
			case 3:
				f += 90.0f;
				break;
			default:
				f %= 360.0f;
				break;
		}
		rug.setLocationAndAngles(i2 + 0.5, j1, k2 + 0.5, f, 0.0f);
		world.spawnEntityInWorld(rug);
	}

	protected void placeSign(World world, int i, int j, int k, Block block, int meta, String[] text) {
		if (wikiGen) {
			return;
		}
		setBlockAndMetadata(world, i, j, k, block, meta);
		TileEntity te = getTileEntity(world, i, j, k);
		if (te instanceof TileEntitySign) {
			TileEntitySign sign = (TileEntitySign) te;
			sign.signText = Arrays.copyOf(text, sign.signText.length);
		}
	}

	protected void placeSkull(World world, int i, int j, int k, int dir) {
		if (wikiGen) {
			return;
		}
		int dir1 = dir;
		setBlockAndMetadata(world, i, j, k, Blocks.skull, 1);
		TileEntity tileentity = getTileEntity(world, i, j, k);
		if (tileentity instanceof TileEntitySkull) {
			TileEntitySkull skull = (TileEntitySkull) tileentity;
			dir1 += rotationMode * 4;
			dir1 %= 16;
			skull.func_145903_a(dir1);
		}
	}

	protected void placeSkull(World world, Random random, int i, int j, int k) {
		if (wikiGen) {
			return;
		}
		placeSkull(world, i, j, k, random.nextInt(16));
	}

	protected void placeSpawnerChest(World world, Random random, int i, int j, int k, Block block, int meta, Class<? extends Entity> entityClass, GOTChestContents contents) {
		if (wikiGen) {
			return;
		}
		placeSpawnerChest(world, random, i, j, k, block, meta, entityClass, contents, -1);
	}

	private void placeSpawnerChest(World world, Random random, int i, int j, int k, Block block, int meta, Class<? extends Entity> entityClass, GOTChestContents contents, int amount) {
		if (wikiGen) {
			return;
		}
		setBlockAndMetadata(world, i, j, k, block, meta);
		TileEntity tileentity = getTileEntity(world, i, j, k);
		if (tileentity instanceof GOTTileEntitySpawnerChest) {
			((GOTTileEntitySpawnerChest) tileentity).setMobID(entityClass);
		}
		if (contents != null) {
			fillChest(world, random, i, j, k, contents, amount);
		}
	}

	protected void placeWallBanner(World world, int i, int j, int k, GOTItemBanner.BannerType bt, int direction) {
		if (wikiGen) {
			return;
		}
		int direction1 = direction;
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		if (!isInSBB(i2, j1, k2)) {
			return;
		}
		for (int l = 0; l < rotationMode; ++l) {
			direction1 = Direction.rotateRight[direction1];
		}
		GOTEntityBannerWall banner = new GOTEntityBannerWall(world, i2, j1, k2, direction1);
		banner.setBannerType(bt);
		world.spawnEntityInWorld(banner);
	}

	protected void placeWeaponRack(World world, int i, int j, int k, int meta, ItemStack weapon) {
		if (wikiGen) {
			return;
		}
		setBlockAndMetadata(world, i, j, k, GOTBlocks.weaponRack, meta);
		TileEntity tileentity = getTileEntity(world, i, j, k);
		if (tileentity instanceof GOTTileEntityWeaponRack) {
			GOTTileEntityWeaponRack weaponRack = (GOTTileEntityWeaponRack) tileentity;
			if (weapon != null) {
				weaponRack.setWeaponItem(weapon.copy());
			}
		}
	}

	protected void plantFlower(World world, Random random, int i, int j, int k) {
		if (wikiGen) {
			return;
		}
		ItemStack itemstack = getRandomFlower(world, random);
		setBlockAndMetadata(world, i, j, k, Block.getBlockFromItem(itemstack.getItem()), itemstack.getItemDamage());
	}

	protected void plantTallGrass(World world, Random random, int i, int j, int k) {
		if (wikiGen) {
			return;
		}
		ItemStack itemstack = getRandomTallGrass(world, random);
		setBlockAndMetadata(world, i, j, k, Block.getBlockFromItem(itemstack.getItem()), itemstack.getItemDamage());
	}

	protected int rotateMeta(Block block, int meta) {
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
			int i = meta;
			boolean flag = meta >= 8;
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

	protected void setAir(World world, int i, int j, int k) {
		if (wikiGen) {
			return;
		}
		setBlockAndMetadata(world, i, j, k, Blocks.air, 0);
	}

	protected void setBiomeFiller(World world, int i, int j, int k) {
		if (wikiGen) {
			return;
		}
		BiomeGenBase biome = getBiome(world, i, k);
		Block fillerBlock = biome.fillerBlock;
		int fillerMeta = 0;
		if (biome instanceof GOTBiome) {
			fillerMeta = ((GOTBiome) biome).getFillerBlockMeta();
		}
		setBlockAndMetadata(world, i, j, k, fillerBlock, fillerMeta);
	}

	protected void setBiomeTop(World world, int i, int j, int k) {
		if (wikiGen) {
			return;
		}
		BiomeGenBase biome = getBiome(world, i, k);
		Block topBlock = biome.topBlock;
		int topMeta = 0;
		if (biome instanceof GOTBiome) {
			topMeta = ((GOTBiome) biome).getTopBlockMeta();
		}
		setBlockAndMetadata(world, i, j, k, topBlock, topMeta);
	}

	protected void setBlockAliasChance(String alias, float chance) {
		if (wikiGen) {
			return;
		}
		scanAliasChances.put(alias, chance);
	}

	protected void setBlockAndMetadata(World world, int i, int j, int k, Block block, int meta) {
		if (wikiGen) {
			return;
		}
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		if (!isInSBB(i2, j1, k2)) {
			return;
		}
		int meta1 = rotateMeta(block, meta);
		setBlockAndNotifyAdequately(world, i2, j1, k2, block, meta1);
		if (meta1 != 0 && (block instanceof BlockChest || block instanceof GOTBlockChest || block instanceof BlockFurnace || block instanceof GOTBlockOven || block instanceof GOTBlockForgeBase)) {
			world.setBlockMetadataWithNotify(i2, j1, k2, meta1, notifyChanges ? 3 : 2);
		}
	}

	protected void setGrassToDirt(World world, int i, int j, int k) {
		if (wikiGen) {
			return;
		}
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		if (!isInSBB(i2, j1, k2)) {
			return;
		}
		world.getBlock(i2, j1, k2).onPlantGrow(world, i2, j1, k2, i2, j1, k2);
	}

	protected void setOriginAndRotation(World world, int i, int j, int k, int rotation, int shift) {
		if (wikiGen) {
			return;
		}
		setOriginAndRotation(world, i, j, k, rotation, shift, 0);
	}

	protected void setOriginAndRotation(World world, int i, int j, int k, int rotation, int shift, int shiftX) {
		if (wikiGen) {
			return;
		}
		int j1 = j;
		int i1 = i;
		int k1 = k;
		--j1;
		rotationMode = rotation;
		switch (rotationMode) {
			case 0:
				k1 += shift;
				i1 += shiftX;
				break;
			case 1:
				i1 -= shift;
				k1 += shiftX;
				break;
			case 2:
				k1 -= shift;
				i1 -= shiftX;
				break;
			case 3:
				i1 += shift;
				k1 -= shiftX;
				break;
		}
		originX = i1;
		originY = j1;
		originZ = k1;
		if (shouldFindSurface) {
			shouldFindSurface = false;
			findSurface(world, -shiftX, -shift);
		}
	}

	public void setStructureBB(StructureBoundingBox box) {
		sbb = box;
	}

	@SuppressWarnings("NoopMethodInAbstractClass")
	public void setupRandomBlocks(Random random) {
	}

	@SuppressWarnings("unused")
	public boolean isShouldFindSurface() {
		return shouldFindSurface;
	}

	public void setShouldFindSurface(boolean shouldFindSurface) {
		this.shouldFindSurface = shouldFindSurface;
	}

	protected void spawnItemFrame(World world, int i, int j, int k, int direction, ItemStack itemstack) {
		if (wikiGen) {
			return;
		}
		int direction1 = direction;
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		if (!isInSBB(i2, j1, k2)) {
			return;
		}
		for (int l = 0; l < rotationMode; ++l) {
			direction1 = Direction.rotateRight[direction1];
		}
		EntityItemFrame frame = new EntityItemFrame(world, i2, j1, k2, direction1);
		frame.setDisplayedItem(itemstack);
		world.spawnEntityInWorld(frame);
	}

	protected void spawnLegendaryNPC(EntityCreature entity, World world, int i, int j, int k) {
		if (wikiGen) {
			entityClasses.add(entity.getClass());
			return;
		}
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		if (!isInSBB(i2, j1, k2)) {
			return;
		}
		entity.setLocationAndAngles(i2 + 0.5, j1, k2 + 0.5, 0.0f, 0.0f);
		entity.onSpawnWithEgg(null);
		if (entity instanceof GOTEntityNPC) {
			((GOTEntityNPC) entity).setNPCPersistent(true);
		}
		world.spawnEntityInWorld(entity);
	}

	protected void spawnNPCAndSetHome(EntityCreature entity, World world, int i, int j, int k, int homeDistance) {
		if (wikiGen) {
			entityClasses.add(entity.getClass());
			return;
		}
		int i2 = getX(i, k);
		int k2 = getZ(i, k);
		int j1 = getY(j);
		if (!isInSBB(i2, j1, k2)) {
			return;
		}
		entity.setLocationAndAngles(i2 + 0.5, j1, k2 + 0.5, 0.0f, 0.0f);
		entity.onSpawnWithEgg(null);
		if (entity instanceof GOTEntityNPC) {
			((GOTEntityNPC) entity).setNPCPersistent(true);
		}
		world.spawnEntityInWorld(entity);
		entity.setHomeArea(i2, j1, k2, homeDistance);
	}

	public int usingPlayerRotation() {
		return GOTStructureRegistry.getRotationFromPlayer(usingPlayer);
	}

	@SuppressWarnings("unused")
	public boolean isRestrictions() {
		return restrictions;
	}

	public GOTStructureBase setRestrictions(boolean b) {
		restrictions = b;
		return this;
	}

	@SuppressWarnings("unused")
	public GOTStructureBaseSettlement.AbstractInstance getSettlementInstance() {
		return settlementInstance;
	}

	public void setSettlementInstance(GOTStructureBaseSettlement.AbstractInstance settlementInstance) {
		this.settlementInstance = settlementInstance;
	}

	public Set<Class<? extends Entity>> getEntityClasses() {
		return entityClasses;
	}

	@SuppressWarnings("unused")
	public boolean isWikiGen() {
		return wikiGen;
	}

	public void setWikiGen(boolean wikiGen) {
		this.wikiGen = wikiGen;
	}

	private static class BlockAliasPool {
		private final Collection<BlockMetaEntry> entries = new ArrayList<>();
		private int totalWeight;

		private void addEntry(int w, Block b, int m) {
			entries.add(new BlockMetaEntry(w, b, m));
			totalWeight = WeightedRandom.getTotalWeight(entries);
		}

		private BlockMetaEntry getEntry(Random random) {
			return (BlockMetaEntry) WeightedRandom.getRandomItem(random, entries, totalWeight);
		}

		private static class BlockMetaEntry extends WeightedRandom.Item {
			private final Block block;
			private final int meta;

			private BlockMetaEntry(int w, Block b, int m) {
				super(w);
				block = b;
				meta = m;
			}

			private Block getBlock() {
				return block;
			}

			private int getMeta() {
				return meta;
			}
		}
	}
}