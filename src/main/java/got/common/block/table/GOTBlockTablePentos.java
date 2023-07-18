package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTablePentos extends GOTBlockCraftingTable {
	public GOTBlockTablePentos() {
		super(Material.wood, GOTFaction.PENTOS, 74);
		setStepSound(Block.soundTypeWood);
	}
}
