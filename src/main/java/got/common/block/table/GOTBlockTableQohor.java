package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableQohor extends GOTBlockCraftingTable {
	public GOTBlockTableQohor() {
		super(Material.wood, GOTFaction.QOHOR, 76);
		setStepSound(Block.soundTypeWood);
	}
}