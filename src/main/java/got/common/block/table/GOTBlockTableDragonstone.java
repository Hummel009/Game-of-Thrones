package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableDragonstone extends GOTBlockCraftingTable {
	public GOTBlockTableDragonstone() {
		super(Material.wood, GOTFaction.DRAGONSTONE, 67);
		setStepSound(Block.soundTypeWood);
	}
}