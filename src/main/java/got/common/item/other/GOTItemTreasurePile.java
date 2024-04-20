package got.common.item.other;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class GOTItemTreasurePile extends ItemBlock {
	public GOTItemTreasurePile(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int i) {
		return i;
	}
}