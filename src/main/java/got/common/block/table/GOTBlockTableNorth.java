package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableNorth extends GOTBlockCraftingTable {
	public GOTBlockTableNorth() {
		super(Material.wood, GOTFaction.NORTH, 13);
		setStepSound(Block.soundTypeWood);
	}
}