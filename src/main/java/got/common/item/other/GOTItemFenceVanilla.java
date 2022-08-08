package got.common.item.other;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class GOTItemFenceVanilla extends GOTItemBlockMetadata {
	public GOTItemFenceVanilla(Block block) {
		super(block);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return "tile.got.fence_vanilla." + itemstack.getItemDamage();
	}
}
