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
		int k1 = k;
		int i1 = i;
		if (world.getBlock(i1, j, k1).isWood(world, i1, j, k1)) {
			if (side == 0 || side == 1) {
				return false;
			}
			if (side == 2) {
				--k1;
			}
			if (side == 3) {
				++k1;
			}
			if (side == 4) {
				--i1;
			}
			if (side == 5) {
				++i1;
			}
			if (world.isAirBlock(i1, j, k1)) {
				int meta = ForgeDirection.getOrientation(side - 2).getOpposite().ordinal();
				world.setBlock(i1, j, k1, fruitBlock, meta, 3);
				if (!entityplayer.capabilities.isCreativeMode) {
					--itemstack.stackSize;
				}
			}
			return true;
		}
		return false;
	}
}
