package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import got.common.tileentity.GOTTileEntitySign;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class GOTItemChisel extends Item {
	public Block signBlock;

	public GOTItemChisel(Block block) {
		signBlock = block;
		setCreativeTab(GOTCreativeTabs.tabTools);
		setMaxStackSize(1);
		setMaxDamage(100);
		setFull3D();
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float f, float f1, float f2) {
		if (side == 0 || side == 1) {
			return false;
		}
		int k1 = k;
		int j1 = j;
		int i1 = i;
		Block block = world.getBlock(i1, j1, k1);
		Material mt = block.getMaterial();
		if (block.isOpaqueCube() && (mt == Material.rock || mt == Material.wood || mt == Material.iron)) {
			if (!entityplayer.canPlayerEdit(i1 += Facing.offsetsXForSide[side], j1 += Facing.offsetsYForSide[side], k1 += Facing.offsetsZForSide[side], side, itemstack) || !signBlock.canPlaceBlockAt(world, i1, j1, k1)) {
				return false;
			}
			if (!world.isRemote) {
				world.setBlock(i1, j1, k1, signBlock, side, 3);
				itemstack.damageItem(1, entityplayer);
				GOTTileEntitySign sign = (GOTTileEntitySign) world.getTileEntity(i1, j1, k1);
				if (sign != null) {
					sign.openEditGUI((EntityPlayerMP) entityplayer);
				}
			}
			return true;
		}
		return false;
	}
}
