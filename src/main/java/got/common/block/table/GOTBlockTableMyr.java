package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableMyr extends GOTBlockCraftingTable {
	public GOTBlockTableMyr() {
		super(Material.wood, GOTFaction.MYR, 72);
		setStepSound(Block.soundTypeWood);
	}
}