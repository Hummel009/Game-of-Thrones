package got.common.block.other;

import java.util.*;

import cpw.mods.fml.relauncher.*;
import got.common.database.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.util.ForgeDirection;

public class GOTBlockBerryBush extends Block implements IPlantable, IGrowable {
	public GOTBlockBerryBush() {
		super(Material.plants);
		setTickRandomly(true);
		setCreativeTab(GOTCreativeTabs.tabDeco);
		setHardness(0.4f);
		setStepSound(Block.soundTypeGrass);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public boolean func_149851_a(World world, int i, int j, int k, boolean isRemote) {
		return !GOTBlockBerryBush.hasBerries(world.getBlockMetadata(i, j, k));
	}

	@Override
	public boolean func_149852_a(World world, Random random, int i, int j, int k) {
		return true;
	}

	@Override
	public void func_149853_b(World world, Random random, int i, int j, int k) {
		if (random.nextInt(3) == 0) {
			growBerries(world, i, j, k);
		}
	}

	private ArrayList<ItemStack> getBerryDrops(World world, int i, int j, int k, int meta) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		if (GOTBlockBerryBush.hasBerries(meta)) {
			int berryType = GOTBlockBerryBush.getBerryType(meta);
			Item berry = null;
			int berries = 1 + world.rand.nextInt(4);
			switch (berryType) {
			case 0: {
				berry = GOTRegistry.blueberry;
				break;
			}
			case 1: {
				berry = GOTRegistry.blackberry;
				break;
			}
			case 2: {
				berry = GOTRegistry.raspberry;
				break;
			}
			case 3: {
				berry = GOTRegistry.cranberry;
				break;
			}
			case 4: {
				berry = GOTRegistry.elderberry;
				break;
			}
			case 5: {
				berry = GOTRegistry.wildberry;
			}
			}
			if (berry != null) {
				for (int l = 0; l < berries; ++l) {
					drops.add(new ItemStack(berry));
				}
			}
		}
		return drops;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int i, int j, int k, int meta, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<>();
		drops.add(new ItemStack(this, 1, GOTBlockBerryBush.setHasBerries(meta, false)));
		drops.addAll(getBerryDrops(world, i, j, k, meta));
		return drops;
	}

	private float getGrowthFactor(World world, int i, int j, int k) {
		float growth;
		Block below = world.getBlock(i, j - 1, k);
		if (below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, this) && world.getBlockLightValue(i, j + 1, k) >= 9) {
			int i1;
			int k1;
			growth = 1.0f;
			boolean bushAdjacent = false;
			block0: for (i1 = i - 1; i1 <= i + 1; ++i1) {
				for (k1 = k - 1; k1 <= k + 1; ++k1) {
					if (i1 == i && k1 == k || !(world.getBlock(i1, j, k1) instanceof GOTBlockBerryBush)) {
						continue;
					}
					bushAdjacent = true;
					break block0;
				}
			}
			for (i1 = i - 1; i1 <= i + 1; ++i1) {
				for (k1 = k - 1; k1 <= k + 1; ++k1) {
					float growthBonus = 0.0f;
					if (world.getBlock(i1, j - 1, k1).canSustainPlant(world, i1, j - 1, k1, ForgeDirection.UP, this)) {
						growthBonus = 1.0f;
						if (world.getBlock(i1, j - 1, k1).isFertile(world, i1, j - 1, k1)) {
							growthBonus = 3.0f;
						}
					}
					if (i1 != i || k1 != k) {
						growthBonus /= 4.0f;
					}
					growth += growthBonus;
				}
			}
			if (growth > 0.0f) {
				if (bushAdjacent) {
					growth /= 2.0f;
				}
				if (world.isRaining()) {
					growth *= 3.0f;
				}
				return growth / 150.0f;
			}
		}
		if (below.canSustainPlant(world, i, j - 1, k, ForgeDirection.UP, (IPlantable) Blocks.sapling)) {
			growth = world.getBlockLightValue(i, j + 1, k) / 2000.0f;
			if (world.isRaining()) {
				growth *= 3.0f;
			}
			return growth;
		}
		return 0.0f;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		int berryType = GOTBlockBerryBush.getBerryType(j);
		BushType type = BushType.forMeta(berryType);
		if (GOTBlockBerryBush.hasBerries(j)) {
			return type.iconGrown;
		}
		return type.iconBare;
	}

	@Override
	public Block getPlant(IBlockAccess world, int i, int j, int k) {
		return this;
	}

	@Override
	public int getPlantMetadata(IBlockAccess world, int i, int j, int k) {
		return world.getBlockMetadata(i, j, k);
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int i, int j, int k) {
		return EnumPlantType.Crop;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (BushType type : BushType.values()) {
			int meta = type.getBushMeta();
			list.add(new ItemStack(item, 1, GOTBlockBerryBush.setHasBerries(meta, true)));
			list.add(new ItemStack(item, 1, GOTBlockBerryBush.setHasBerries(meta, false)));
		}
	}

	private void growBerries(World world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		world.setBlockMetadataWithNotify(i, j, k, GOTBlockBerryBush.setHasBerries(meta, true), 3);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float f, float f1, float f2) {
		int meta = world.getBlockMetadata(i, j, k);
		if (GOTBlockBerryBush.hasBerries(meta)) {
			world.setBlockMetadataWithNotify(i, j, k, GOTBlockBerryBush.setHasBerries(meta, false), 3);
			if (!world.isRemote) {
				ArrayList<ItemStack> drops = getBerryDrops(world, i, j, k, meta);
				for (ItemStack berry : drops) {
					this.dropBlockAsItem(world, i, j, k, berry);
				}
			}
			return true;
		}
		return false;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		for (BushType type : BushType.values()) {
			type.iconBare = iconregister.registerIcon(getTextureName() + "_" + type.bushName + "_bare");
			type.iconGrown = iconregister.registerIcon(getTextureName() + "_" + type.bushName);
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random) {
		int meta = world.getBlockMetadata(i, j, k);
		if (!world.isRemote && !GOTBlockBerryBush.hasBerries(meta)) {
			float growth = getGrowthFactor(world, i, j, k);
			if (random.nextFloat() < growth) {
				growBerries(world, i, j, k);
			}
		}
	}

	private static int getBerryType(int meta) {
		return meta & 7;
	}

	public static boolean hasBerries(int meta) {
		return (meta & 8) != 0;
	}

	public static int setHasBerries(int meta, boolean flag) {
		if (flag) {
			return GOTBlockBerryBush.getBerryType(meta) | 8;
		}
		return GOTBlockBerryBush.getBerryType(meta);
	}

	public enum BushType {
		BLUEBERRY(0, "blueberry", false), BLACKBERRY(1, "blackberry", false), RASPBERRY(2, "raspberry", false), CRANBERRY(3, "cranberry", false), ELDERBERRY(4, "elderberry", false), WILDBERRY(5, "wildberry", true);

		private int bushMeta;
		private String bushName;
		private boolean poisonous;
		@SideOnly(value = Side.CLIENT)
		private IIcon iconBare;
		@SideOnly(value = Side.CLIENT)
		private IIcon iconGrown;

		BushType(int i, String s, boolean flag) {
			setBushMeta(i);
			bushName = s;
			setPoisonous(flag);
		}

		public int getBushMeta() {
			return bushMeta;
		}

		public boolean isPoisonous() {
			return poisonous;
		}

		public void setBushMeta(int bushMeta) {
			this.bushMeta = bushMeta;
		}

		public void setPoisonous(boolean poisonous) {
			this.poisonous = poisonous;
		}

		private static BushType forMeta(int i) {
			for (BushType type : BushType.values()) {
				if (type.getBushMeta() != i) {
					continue;
				}
				return type;
			}
			return BushType.values()[0];
		}

		public static BushType randomType(Random rand) {
			return BushType.values()[rand.nextInt(BushType.values().length)];
		}
	}

}
