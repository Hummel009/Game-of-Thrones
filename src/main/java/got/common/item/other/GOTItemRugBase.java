package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTCreativeTabs;
import got.common.entity.other.GOTEntityRugBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public abstract class GOTItemRugBase extends Item {
	@SideOnly(Side.CLIENT)
	public IIcon[] rugIcons;
	public String[] rugNames;

	protected GOTItemRugBase(String... names) {
		rugNames = names;
		setCreativeTab(GOTCreativeTabs.tabDeco);
		setMaxStackSize(1);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	public abstract GOTEntityRugBase createRug(World var1, ItemStack var2);

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int i) {
		int i1 = i;
		if (i1 >= rugIcons.length) {
			i1 = 0;
		}
		return rugIcons[i1];
	}

	@SideOnly(Side.CLIENT)
	@Override
	@SuppressWarnings("rawtypes")
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < rugNames.length; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2) {
		int l1 = l;
		int j1 = j;
		int k1 = k;
		int i1 = i;
		Block block = world.getBlock(i1, j1, k1);
		if (block == Blocks.snow_layer) {
			l1 = 1;
		} else if (!block.isReplaceable(world, i1, j1, k1)) {
			if (l1 == 0) {
				--j1;
			}
			if (l1 == 1) {
				++j1;
			}
			if (l1 == 2) {
				--k1;
			}
			if (l1 == 3) {
				++k1;
			}
			if (l1 == 4) {
				--i1;
			}
			if (l1 == 5) {
				++i1;
			}
		}
		if (!entityplayer.canPlayerEdit(i1, j1, k1, l1, itemstack)) {
			return false;
		}
		if (world.getBlock(i1, j1 - 1, k1).isSideSolid(world, i1, j1 - 1, k1, ForgeDirection.UP) && !world.isRemote) {
			GOTEntityRugBase rug = createRug(world, itemstack);
			rug.setLocationAndAngles(i1 + f, j1, k1 + f2, 180.0f - entityplayer.rotationYaw % 360.0f, 0.0f);
			if (world.checkNoEntityCollision(rug.boundingBox) && world.getCollidingBoundingBoxes(rug, rug.boundingBox).isEmpty() && !world.isAnyLiquid(rug.boundingBox)) {
				world.spawnEntityInWorld(rug);
				world.playSoundAtEntity(rug, Blocks.wool.stepSound.func_150496_b(), (Blocks.wool.stepSound.getVolume() + 1.0f) / 2.0f, Blocks.wool.stepSound.getPitch() * 0.8f);
				--itemstack.stackSize;
				return true;
			}
			rug.setDead();
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		rugIcons = new IIcon[rugNames.length];
		for (int i = 0; i < rugIcons.length; ++i) {
			rugIcons[i] = iconregister.registerIcon(getIconString() + '_' + rugNames[i]);
		}
	}
}
