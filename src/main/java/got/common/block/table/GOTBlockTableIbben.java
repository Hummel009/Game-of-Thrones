package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableIbben extends GOTBlockCraftingTable {
	public GOTBlockTableIbben() {
		super(Material.wood, GOTFaction.ARRYN, 2);
		setStepSound(Block.soundTypeWood);
	}
}