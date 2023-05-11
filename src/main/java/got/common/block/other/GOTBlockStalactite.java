package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public class GOTBlockStalactite extends Block {
	public Block modelBlock;
	public int modelMeta;

	public GOTBlockStalactite(Block block, int meta) {
		super(block.getMaterial());
		modelBlock = block;
		modelMeta = meta;
		setStepSound(modelBlock.stepSound);
		setCreativeTab(GOTCreativeTabs.tabDeco);
		setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 1.0f, 0.75f);
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		int metadata = world.getBlockMetadata(i, j, k);
		if (metadata == 0) {
			return world.getBlock(i, j + 1, k).isSideSolid(world, i, j + 1, k, ForgeDirection.DOWN);
		}
		if (metadata == 1) {
			return world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
		}
		return false;
	}

	@Override
	public boolean canReplace(World world, int i, int j, int k, int side, ItemStack itemstack) {
		int metadata = itemstack.getItemDamage();
		if (metadata == 0) {
			return world.getBlock(i, j + 1, k).isSideSolid(world, i, j + 1, k, ForgeDirection.DOWN);
		}
		if (metadata == 1) {
			return world.getBlock(i, j - 1, k).isSideSolid(world, i, j - 1, k, ForgeDirection.UP);
		}
		return false;
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer entityplayer, int i, int j, int k, int meta) {
		return true;
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public float getBlockHardness(World world, int i, int j, int k) {
		return modelBlock.getBlockHardness(world, i, j, k);
	}

	@Override
	public float getExplosionResistance(Entity entity) {
		return modelBlock.getExplosionResistance(entity);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		return modelBlock.getIcon(i, modelMeta);
	}

	@Override
	public int getRenderType() {
		return GOT.proxy.getStalactiteRenderID();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int j = 0; j <= 1; ++j) {
			list.add(new ItemStack(item, 1, j));
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onFallenUpon(World world, int i, int j, int k, Entity entity, float fallDistance) {
		if (entity instanceof EntityLivingBase && world.getBlockMetadata(i, j, k) == 1) {
			int damage = (int) (fallDistance * 2.0f) + 1;
			entity.attackEntityFrom(DamageSource.fall, damage);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (!canBlockStay(world, i, j, k)) {
			dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
			world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public int quantityDropped(Random random) {
		return modelBlock.quantityDropped(random);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random) {
		Block above;
		if (random.nextInt(50) == 0 && world.getBlockMetadata(i, j, k) == 0 && (above = world.getBlock(i, j + 1, k)).isOpaqueCube() && above.getMaterial() == Material.rock) {
			world.spawnParticle("dripWater", i + 0.6, j, k + 0.6, 0.0, 0.0, 0.0);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
