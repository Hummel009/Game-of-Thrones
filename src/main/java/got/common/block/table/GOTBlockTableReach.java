package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableReach extends GOTBlockCraftingTable {
	public GOTBlockTableReach() {
		super(Material.wood, GOTFaction.REACH, 77);
		setStepSound(Block.soundTypeWood);
	}
}