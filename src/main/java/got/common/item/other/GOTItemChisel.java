package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import got.common.tileentity.GOTTileEntitySign;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
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
		Block block = world.getBlock(i, j, k);
		Material mt = block.getMaterial();
		if (block.isOpaqueCube() && (mt == Material.rock || mt == Material.wood || mt == Material.iron)) {
			if (!entityplayer.canPlayerEdit(i += Facing.offsetsXForSide[side], j += Facing.offsetsYForSide[side], k += Facing.offsetsZForSide[side], side, itemstack) || !signBlock.canPlaceBlockAt(world, i, j, k)) {
				return false;
			}
			if (!world.isRemote) {
				world.setBlock(i, j, k, signBlock, side, 3);
				itemstack.damageItem(1, entityplayer);
				GOTTileEntitySign sign = (GOTTileEntitySign) world.getTileEntity(i, j, k);
				if (sign != null) {
					sign.openEditGUI((EntityPlayerMP) entityplayer);
				}
			}
			return true;
		}
		return false;
	}
}
