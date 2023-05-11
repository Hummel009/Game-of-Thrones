package got.common.block.slab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public abstract class GOTBlockSlabBase extends BlockSlab {
	public Block singleSlab;
	public Block doubleSlab;
	public int subtypes;

	public GOTBlockSlabBase(boolean flag, Material material, int n) {
		super(flag, material);
		subtypes = n;
		setCreativeTab(GOTCreativeTabs.tabBlock);
		useNeighborBrightness = true;
		if (material == Material.wood) {
			setHardness(2.0f);
			setResistance(5.0f);
			setStepSound(Block.soundTypeWood);
		}
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		return (meta & 8) == 8 || isOpaqueCube();
	}

	@Override
	public ItemStack createStackedBlock(int i) {
		return new ItemStack(singleSlab, 2, i & 7);
	}

	@Override
	public String func_150002_b(int i) {
		return super.getUnlocalizedName() + "." + i;
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public Item getItem(World world, int i, int j, int k) {
		return Item.getItemFromBlock(singleSlab);
	}

	@Override
	public Item getItemDropped(int i, Random random, int j) {
		return Item.getItemFromBlock(singleSlab);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		if (item != Item.getItemFromBlock(doubleSlab)) {
			for (int j = 0; j < subtypes; ++j) {
				list.add(new ItemStack(item, 1, j));
			}
		}
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side) {
		return (world.getBlockMetadata(x, y, z) & 8) == 8 && side == 1 || isOpaqueCube();
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int i, int j, int k, int l) {
		boolean flag;
		if (this == doubleSlab) {
			return super.shouldSideBeRendered(world, i, j, k, l);
		}
		if (l != 1 && l != 0 && !super.shouldSideBeRendered(world, i, j, k, l)) {
			return false;
		}
		int i1 = i + Facing.offsetsXForSide[Facing.oppositeSide[l]];
		int j1 = j + Facing.offsetsYForSide[Facing.oppositeSide[l]];
		int k1 = k + Facing.offsetsZForSide[Facing.oppositeSide[l]];
		flag = (world.getBlockMetadata(i1, j1, k1) & 8) != 0;
		return flag ? l == 0 ? true : l == 1 && super.shouldSideBeRendered(world, i, j, k, l) ? true : world.getBlock(i, j, k) != singleSlab || (world.getBlockMetadata(i, j, k) & 8) == 0 : l == 1 ? true : l == 0 && super.shouldSideBeRendered(world, i, j, k, l) ? true : world.getBlock(i, j, k) != singleSlab || (world.getBlockMetadata(i, j, k) & 8) != 0;
	}

	public static void registerSlabs(Block block, Block block1) {
		((GOTBlockSlabBase) block).singleSlab = block;
		((GOTBlockSlabBase) block).doubleSlab = block1;
		((GOTBlockSlabBase) block1).singleSlab = block;
		((GOTBlockSlabBase) block1).doubleSlab = block1;
	}

	public static class SlabItems {

		public static class BoneDouble extends ItemSlab {
			public BoneDouble(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabBoneSingle, (BlockSlab) GOTRegistry.slabBoneDouble, true);
			}
		}

		public static class BoneSingle extends ItemSlab {
			public BoneSingle(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabBoneSingle, (BlockSlab) GOTRegistry.slabBoneDouble, false);
			}
		}

		public static class ClayTileDouble extends ItemSlab {
			public ClayTileDouble(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabClayTileSingle, (BlockSlab) GOTRegistry.slabClayTileDouble, true);
			}
		}

		public static class ClayTileDyed2Double extends ItemSlab {
			public ClayTileDyed2Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabClayTileDyedSingle2, (BlockSlab) GOTRegistry.slabClayTileDyedDouble2, true);
			}
		}

		public static class ClayTileDyed2Single extends ItemSlab {
			public ClayTileDyed2Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabClayTileDyedSingle2, (BlockSlab) GOTRegistry.slabClayTileDyedDouble2, false);
			}
		}

		public static class ClayTileDyedDouble extends ItemSlab {
			public ClayTileDyedDouble(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabClayTileDyedSingle1, (BlockSlab) GOTRegistry.slabClayTileDyedDouble1, true);
			}
		}

		public static class ClayTileDyedSingle extends ItemSlab {
			public ClayTileDyedSingle(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabClayTileDyedSingle1, (BlockSlab) GOTRegistry.slabClayTileDyedDouble1, false);
			}
		}

		public static class ClayTileSingle extends ItemSlab {
			public ClayTileSingle(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabClayTileSingle, (BlockSlab) GOTRegistry.slabClayTileDouble, false);
			}
		}

		public static class DirtDouble extends ItemSlab {
			public DirtDouble(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingleDirt, (BlockSlab) GOTRegistry.slabDoubleDirt, true);
			}
		}

		public static class DirtSingle extends ItemSlab {
			public DirtSingle(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingleDirt, (BlockSlab) GOTRegistry.slabDoubleDirt, false);
			}
		}

		public static class GravelDouble extends ItemSlab {
			public GravelDouble(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingleGravel, (BlockSlab) GOTRegistry.slabDoubleGravel, true);
			}
		}

		public static class GravelSingle extends ItemSlab {
			public GravelSingle(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingleGravel, (BlockSlab) GOTRegistry.slabDoubleGravel, false);
			}
		}

		public static class RottenSlabDouble extends ItemSlab {
			public RottenSlabDouble(Block block) {
				super(block, (BlockSlab) GOTRegistry.rottenSlabSingle, (BlockSlab) GOTRegistry.rottenSlabDouble, true);
			}
		}

		public static class RottenSlabSingle extends ItemSlab {
			public RottenSlabSingle(Block block) {
				super(block, (BlockSlab) GOTRegistry.rottenSlabSingle, (BlockSlab) GOTRegistry.rottenSlabDouble, false);
			}
		}

		public static class SandDouble extends ItemSlab {
			public SandDouble(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingleSand, (BlockSlab) GOTRegistry.slabDoubleSand, true);
			}
		}

		public static class SandSingle extends ItemSlab {
			public SandSingle(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingleSand, (BlockSlab) GOTRegistry.slabDoubleSand, false);
			}
		}

		public static class ScorchedDouble extends ItemSlab {
			public ScorchedDouble(Block block) {
				super(block, (BlockSlab) GOTRegistry.scorchedSlabSingle, (BlockSlab) GOTRegistry.scorchedSlabDouble, true);
			}
		}

		public static class ScorchedSingle extends ItemSlab {
			public ScorchedSingle(Block block) {
				super(block, (BlockSlab) GOTRegistry.scorchedSlabSingle, (BlockSlab) GOTRegistry.scorchedSlabDouble, false);
			}
		}

		public static class Slab10Double extends ItemSlab {
			public Slab10Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle10, (BlockSlab) GOTRegistry.slabDouble10, true);
			}
		}

		public static class Slab10Single extends ItemSlab {
			public Slab10Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle10, (BlockSlab) GOTRegistry.slabDouble10, false);
			}
		}

		public static class Slab11Double extends ItemSlab {
			public Slab11Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle11, (BlockSlab) GOTRegistry.slabDouble11, true);
			}
		}

		public static class Slab11Single extends ItemSlab {
			public Slab11Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle11, (BlockSlab) GOTRegistry.slabDouble11, false);
			}
		}

		public static class Slab12Double extends ItemSlab {
			public Slab12Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle12, (BlockSlab) GOTRegistry.slabDouble12, true);
			}
		}

		public static class Slab12Single extends ItemSlab {
			public Slab12Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle12, (BlockSlab) GOTRegistry.slabDouble12, false);
			}
		}

		public static class Slab13Double extends ItemSlab {
			public Slab13Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle6, (BlockSlab) GOTRegistry.slabDouble6, true);
			}
		}

		public static class Slab13Single extends ItemSlab {
			public Slab13Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle6, (BlockSlab) GOTRegistry.slabDouble6, false);
			}
		}

		public static class Slab1Double extends ItemSlab {
			public Slab1Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle1, (BlockSlab) GOTRegistry.slabDouble1, true);
			}
		}

		public static class Slab1Single extends ItemSlab {
			public Slab1Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle1, (BlockSlab) GOTRegistry.slabDouble1, false);
			}
		}

		public static class Slab2Double extends ItemSlab {
			public Slab2Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle2, (BlockSlab) GOTRegistry.slabDouble2, true);
			}
		}

		public static class Slab2Single extends ItemSlab {
			public Slab2Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle2, (BlockSlab) GOTRegistry.slabDouble2, false);
			}
		}

		public static class Slab3Double extends ItemSlab {
			public Slab3Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle3, (BlockSlab) GOTRegistry.slabDouble3, true);
			}
		}

		public static class Slab3Single extends ItemSlab {
			public Slab3Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle3, (BlockSlab) GOTRegistry.slabDouble3, false);
			}
		}

		public static class Slab4Double extends ItemSlab {
			public Slab4Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle4, (BlockSlab) GOTRegistry.slabDouble4, true);
			}
		}

		public static class Slab4Single extends ItemSlab {
			public Slab4Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle4, (BlockSlab) GOTRegistry.slabDouble4, false);
			}
		}

		public static class Slab5Double extends ItemSlab {
			public Slab5Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle5, (BlockSlab) GOTRegistry.slabDouble5, true);
			}
		}

		public static class Slab5Single extends ItemSlab {
			public Slab5Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle5, (BlockSlab) GOTRegistry.slabDouble5, false);
			}
		}

		public static class Slab7Double extends ItemSlab {
			public Slab7Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle7, (BlockSlab) GOTRegistry.slabDouble7, true);
			}
		}

		public static class Slab7Single extends ItemSlab {
			public Slab7Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle7, (BlockSlab) GOTRegistry.slabDouble7, false);
			}
		}

		public static class Slab8Double extends ItemSlab {
			public Slab8Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle8, (BlockSlab) GOTRegistry.slabDouble8, true);
			}
		}

		public static class Slab8Single extends ItemSlab {
			public Slab8Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle8, (BlockSlab) GOTRegistry.slabDouble8, false);
			}
		}

		public static class Slab9Double extends ItemSlab {
			public Slab9Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle9, (BlockSlab) GOTRegistry.slabDouble9, true);
			}
		}

		public static class Slab9Single extends ItemSlab {
			public Slab9Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingle9, (BlockSlab) GOTRegistry.slabDouble9, false);
			}
		}

		public static class SlabVDouble extends ItemSlab {
			public SlabVDouble(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingleV, (BlockSlab) GOTRegistry.slabDoubleV, true);
			}
		}

		public static class SlabVSingle extends ItemSlab {
			public SlabVSingle(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingleV, (BlockSlab) GOTRegistry.slabDoubleV, false);
			}
		}

		public static class ThatchDouble extends ItemSlab {
			public ThatchDouble(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingleThatch, (BlockSlab) GOTRegistry.slabDoubleThatch, true);
			}
		}

		public static class ThatchSingle extends ItemSlab {
			public ThatchSingle(Block block) {
				super(block, (BlockSlab) GOTRegistry.slabSingleThatch, (BlockSlab) GOTRegistry.slabDoubleThatch, false);
			}
		}

		public static class WoodSlab1Double extends ItemSlab {
			public WoodSlab1Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.woodSlabSingle1, (BlockSlab) GOTRegistry.woodSlabDouble1, true);
			}
		}

		public static class WoodSlab1Single extends ItemSlab {
			public WoodSlab1Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.woodSlabSingle1, (BlockSlab) GOTRegistry.woodSlabDouble1, false);
			}
		}

		public static class WoodSlab2Double extends ItemSlab {
			public WoodSlab2Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.woodSlabSingle2, (BlockSlab) GOTRegistry.woodSlabDouble2, true);
			}
		}

		public static class WoodSlab2Single extends ItemSlab {
			public WoodSlab2Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.woodSlabSingle2, (BlockSlab) GOTRegistry.woodSlabDouble2, false);
			}
		}

		public static class WoodSlab3Double extends ItemSlab {
			public WoodSlab3Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.woodSlabSingle3, (BlockSlab) GOTRegistry.woodSlabDouble3, true);
			}
		}

		public static class WoodSlab3Single extends ItemSlab {
			public WoodSlab3Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.woodSlabSingle3, (BlockSlab) GOTRegistry.woodSlabDouble3, false);
			}
		}

		public static class WoodSlab4Double extends ItemSlab {
			public WoodSlab4Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.woodSlabSingle4, (BlockSlab) GOTRegistry.woodSlabDouble4, true);
			}
		}

		public static class WoodSlab4Single extends ItemSlab {
			public WoodSlab4Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.woodSlabSingle4, (BlockSlab) GOTRegistry.woodSlabDouble4, false);
			}
		}

		public static class WoodSlab5Double extends ItemSlab {
			public WoodSlab5Double(Block block) {
				super(block, (BlockSlab) GOTRegistry.woodSlabSingle5, (BlockSlab) GOTRegistry.woodSlabDouble5, true);
			}
		}

		public static class WoodSlab5Single extends ItemSlab {
			public WoodSlab5Single(Block block) {
				super(block, (BlockSlab) GOTRegistry.woodSlabSingle5, (BlockSlab) GOTRegistry.woodSlabDouble5, false);
			}
		}

	}

}
