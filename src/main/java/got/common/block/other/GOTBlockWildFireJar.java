package got.common.block.other;

import java.util.Random;

import cpw.mods.fml.relauncher.*;
import got.GOT;
import got.common.database.*;
import got.common.entity.other.GOTEntityFallingFireJar;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class GOTBlockWildFireJar extends BlockFalling {
	private static int renderingStage = 0;
	private static boolean explodeOnAdded = true;
	private static Material materialFireJar = new MaterialLogic(MapColor.stoneColor);
	@SideOnly(value = Side.CLIENT)
	private IIcon iconBaseSide;
	@SideOnly(value = Side.CLIENT)
	private IIcon iconBaseTop;
	@SideOnly(value = Side.CLIENT)
	private IIcon iconBaseBottom;
	@SideOnly(value = Side.CLIENT)
	private IIcon iconNeckSide;
	@SideOnly(value = Side.CLIENT)
	private IIcon iconLidSide;
	@SideOnly(value = Side.CLIENT)
	private IIcon iconLidTop;
	@SideOnly(value = Side.CLIENT)
	private IIcon iconLidBottom;
	@SideOnly(value = Side.CLIENT)
	private IIcon iconCapSide;
	@SideOnly(value = Side.CLIENT)
	private IIcon iconCapTop;
	@SideOnly(value = Side.CLIENT)
	private IIcon iconCapBottom;
	@SideOnly(value = Side.CLIENT)
	private IIcon iconCrownSide;
	@SideOnly(value = Side.CLIENT)
	private IIcon iconHandleSide;

	public GOTBlockWildFireJar() {
		super(materialFireJar);
		setTickRandomly(true);
		setCreativeTab(GOTCreativeTabs.tabCombat);
		setBlockBounds(0.125f, 0.0f, 0.125f, 0.875f, 1.0f, 0.875f);
		setHardness(0.5f);
		setStepSound(Block.soundTypeStone);
	}

	public void explode(World world, int i, int j, int k) {
		if (!world.isRemote) {
			world.createExplosion(null, i, j, k, 2.0f, false);
			world.setBlockToAir(i, j, k);
			int range = 2;
			for (int l = 0; l < 64; ++l) {
				int j1;
				int k1;
				int i1 = i + MathHelper.getRandomIntegerInRange(world.rand, -range, range);
				Block block = world.getBlock(i1, j1 = j + MathHelper.getRandomIntegerInRange(world.rand, -range, range), k1 = k + MathHelper.getRandomIntegerInRange(world.rand, -range, range));
				if (!block.isAir(world, i1, j1, k1) && !block.isReplaceable(world, i1, j1, k1) || block.getMaterial().isLiquid()) {
					continue;
				}
				world.setBlock(i1, j1, k1, GOTRegistry.wildFire, 0, 3);
			}
		}
	}

	@Override
	public void func_149828_a(World world, int i, int j, int k, int meta) {
		explode(world, i, j, k);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		switch (getRenderingStage()) {
		case 1:
			return i == 0 ? iconBaseBottom : i == 1 ? iconBaseTop : iconBaseSide;
		case 2:
			return iconNeckSide;
		case 3:
			return i == 0 ? iconLidBottom : i == 1 ? iconLidTop : iconLidSide;
		case 4:
			return i == 0 ? iconCapBottom : i == 1 ? iconCapTop : iconCapSide;
		case 5:
			return iconCrownSide;
		case 6:
			return iconHandleSide;
		default:
			break;
		}
		return GOTRegistry.brick5.getIcon(i, 11);
	}

	@Override
	public int getRenderType() {
		return GOT.getProxy().getWildFireJarRenderID();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		ItemStack itemstack = entityplayer.getCurrentEquippedItem();
		if (itemstack != null && itemstack.getItem() instanceof ItemFlintAndSteel) {
			explode(world, i, j, k);
			return true;
		}
		return false;
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		if (world.isBlockIndirectlyGettingPowered(i, j, k)) {
			if (isExplodeOnAdded()) {
				explode(world, i, j, k);
			}
		} else {
			super.onBlockAdded(world, i, j, k);
		}
	}

	@Override
	public void onBlockExploded(World world, int i, int j, int k, Explosion explosion) {
		explode(world, i, j, k);
		super.onBlockExploded(world, i, j, k, explosion);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		double speed = Math.sqrt(entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ);
		if (speed >= MathHelper.getRandomDoubleInRange(world.rand, 0.3, 0.8)) {
			explode(world, i, j, k);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		super.onNeighborBlockChange(world, i, j, k, block);
		if (world.getBlock(i, j, k) == this) {
			if (world.isBlockIndirectlyGettingPowered(i, j, k)) {
				explode(world, i, j, k);
			} else if (!world.isRemote) {
				updateTick(world, i, j, k, world.rand);
			}
		}
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		iconBaseSide = iconregister.registerIcon(getTextureName() + "_base_side");
		iconBaseTop = iconregister.registerIcon(getTextureName() + "_base_top");
		iconBaseBottom = iconregister.registerIcon(getTextureName() + "_base_bottom");
		iconNeckSide = iconregister.registerIcon(getTextureName() + "_neck_side");
		iconLidSide = iconregister.registerIcon(getTextureName() + "_lid_side");
		iconLidTop = iconregister.registerIcon(getTextureName() + "_lid_top");
		iconLidBottom = iconregister.registerIcon(getTextureName() + "_lid_bottom");
		iconCapSide = iconregister.registerIcon(getTextureName() + "_cap_side");
		iconCapTop = iconregister.registerIcon(getTextureName() + "_cap_top");
		iconCapBottom = iconregister.registerIcon(getTextureName() + "_cap_bottom");
		iconCrownSide = iconregister.registerIcon(getTextureName() + "_crown_side");
		iconHandleSide = iconregister.registerIcon(getTextureName() + "_handle_side");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		int b0;
		if (GOT.doFireTick(world)) {
			boolean foundFire = false;
			for (int l = 0; l < 12; ++l) {
				int range = 1 + random.nextInt(4);
				int i1 = i + MathHelper.getRandomIntegerInRange(random, -range, range);
				Block block = world.getBlock(i1, j + MathHelper.getRandomIntegerInRange(random, -range, range), k + MathHelper.getRandomIntegerInRange(random, -range, range));
				Material material = block.getMaterial();
				if (material != Material.fire && material != Material.lava) {
					continue;
				}
				foundFire = true;
				break;
			}
			if (foundFire) {
				explode(world, i, j, k);
			}
		}
		if (world.getBlock(i, j, k) == this && !world.isRemote && BlockFalling.func_149831_e(world, i, j - 1, k) && j >= 0 && world.checkChunksExist(i - (b0 = 32), j - b0, k - b0, i + b0, j + b0, k + b0)) {
			GOTEntityFallingFireJar falling = new GOTEntityFallingFireJar(world, i + 0.5, j + 0.5, k + 0.5, this, world.getBlockMetadata(i, j, k));
			func_149829_a(falling);
			world.spawnEntityInWorld(falling);
		}
	}

	public static int getRenderingStage() {
		return renderingStage;
	}

	public static boolean isExplodeOnAdded() {
		return explodeOnAdded;
	}

	public static void setExplodeOnAdded(boolean explodeOnAdded) {
		GOTBlockWildFireJar.explodeOnAdded = explodeOnAdded;
	}

	public static void setRenderingStage(int renderingStage) {
		GOTBlockWildFireJar.renderingStage = renderingStage;
	}
}
