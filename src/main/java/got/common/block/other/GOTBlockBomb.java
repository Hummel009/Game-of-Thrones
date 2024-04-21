package got.common.block.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.common.database.GOTCreativeTabs;
import got.common.database.GOTItems;
import got.common.entity.other.GOTEntityBomb;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class GOTBlockBomb extends Block {
	@SideOnly(Side.CLIENT)
	private IIcon[] bombIcons;

	public GOTBlockBomb() {
		super(Material.iron);
		setCreativeTab(GOTCreativeTabs.TAB_COMBAT);
		setHardness(3.0f);
		setResistance(0.0f);
		setStepSound(soundTypeMetal);
	}

	public static int getBombStrengthLevel(int meta) {
		return meta & 7;
	}

	public static boolean isFireBomb(int meta) {
		return (meta & 8) != 0;
	}

	@Override
	public boolean canDropFromExplosion(Explosion explosion) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		int meta = world.getBlockMetadata(i, j, k);
		int strength = getBombStrengthLevel(meta);
		if (strength == 1) {
			return 11974326;
		}
		if (strength == 2) {
			return 7829367;
		}
		return 16777215;
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		boolean isFire = isFireBomb(j);
		if (i == -1) {
			return bombIcons[2];
		}
		if (i == 1) {
			return bombIcons[isFire ? 4 : 1];
		}
		return bombIcons[isFire ? 3 : 0];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(int i) {
		int strength = getBombStrengthLevel(i);
		if (strength == 1) {
			return 11974326;
		}
		if (strength == 2) {
			return 7829367;
		}
		return 16777215;
	}

	@Override
	public int getRenderType() {
		return GOT.proxy.getBombRenderID();
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i <= 1; ++i) {
			for (int j = 0; j <= 2; ++j) {
				list.add(new ItemStack(item, 1, j + i * 8));
			}
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float f, float f1, float f2) {
		if (entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() == GOTItems.fuse) {
			onBlockDestroyedByPlayer(world, i, j, k, -1);
			world.setBlockToAir(i, j, k);
			return true;
		}
		return false;
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		super.onBlockAdded(world, i, j, k);
		if (world.isBlockIndirectlyGettingPowered(i, j, k)) {
			onBlockDestroyedByPlayer(world, i, j, k, -1);
			world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int meta) {
		int meta1 = meta;
		if (!world.isRemote && meta1 == -1) {
			meta1 = world.getBlockMetadata(i, j, k);
			GOTEntityBomb bomb = new GOTEntityBomb(world, i + 0.5f, j + 0.5f, k + 0.5f, null);
			bomb.setBombStrengthLevel(meta1);
			bomb.setDroppedByPlayer(true);
			world.spawnEntityInWorld(bomb);
			world.playSoundAtEntity(bomb, "game.tnt.primed", 1.0f, 1.0f);
		}
	}

	@Override
	public void onBlockExploded(World world, int i, int j, int k, Explosion explosion) {
		if (!world.isRemote) {
			int meta = world.getBlockMetadata(i, j, k);
			GOTEntityBomb bomb = new GOTEntityBomb(world, i + 0.5f, j + 0.5f, k + 0.5f, explosion.getExplosivePlacedBy());
			bomb.setBombStrengthLevel(meta);
			bomb.setFuseFromExplosion();
			bomb.setDroppedByPlayer(true);
			world.spawnEntityInWorld(bomb);
		}
		super.onBlockExploded(world, i, j, k, explosion);
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block) {
		if (block.getMaterial() != Material.air && block.canProvidePower() && world.isBlockIndirectlyGettingPowered(i, j, k)) {
			onBlockDestroyedByPlayer(world, i, j, k, -1);
			world.setBlockToAir(i, j, k);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		bombIcons = new IIcon[5];
		bombIcons[0] = iconregister.registerIcon(getTextureName() + "_side");
		bombIcons[1] = iconregister.registerIcon(getTextureName() + "_top");
		bombIcons[2] = iconregister.registerIcon(getTextureName() + "_handle");
		bombIcons[3] = iconregister.registerIcon(getTextureName() + "_fire_side");
		bombIcons[4] = iconregister.registerIcon(getTextureName() + "_fire_top");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}