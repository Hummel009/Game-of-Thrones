package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableGift extends GOTBlockCraftingTable {
	public GOTBlockTableGift() {
		super(Material.wood, GOTFaction.NIGHT_WATCH, 28);
		setStepSound(Block.soundTypeWood);
	}
}
