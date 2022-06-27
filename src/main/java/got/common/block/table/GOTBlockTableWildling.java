package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableWildling extends GOTBlockCraftingTable {
	public GOTBlockTableWildling() {
		super(Material.wood, GOTFaction.WILDLING, 23);
		setStepSound(Block.soundTypeWood);
	}
}
