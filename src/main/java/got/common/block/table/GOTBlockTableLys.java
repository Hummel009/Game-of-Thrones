package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableLys extends GOTBlockCraftingTable {
	public GOTBlockTableLys() {
		super(Material.wood, GOTFaction.LYS, 71);
		setStepSound(Block.soundTypeWood);
	}
}