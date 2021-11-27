package got.common.item.other;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class GOTItemHangingFruit extends GOTItemFood {
	public Block fruitBlock;

	public GOTItemHangingFruit(int i, float f, boolean flag, Block block) {
		super(i, f, flag);
		fruitBlock = block;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ) {
		if (world.getBlock(i, j, k).isWood(world, i, j, k)) {
			if (side == 0 || side == 1) {
				return false;
			}
			if (side == 2) {
				--k;
			}
			if (side == 3) {
				++k;
			}
			if (side == 4) {
				--i;
			}
			if (side == 5) {
				++i;
			}
			if (world.isAirBlock(i, j, k)) {
				int meta = ForgeDirection.getOrientation(side - 2).getOpposite().ordinal();
				world.setBlock(i, j, k, fruitBlock, meta, 3);
				if (!entityplayer.capabilities.isCreativeMode) {
					--itemstack.stackSize;
				}
			}
			return true;
		}
		return false;
	}
}
