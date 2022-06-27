package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableRiverlands extends GOTBlockCraftingTable {
	public GOTBlockTableRiverlands() {
		super(Material.wood, GOTFaction.RIVERLANDS, 78);
		setStepSound(Block.soundTypeWood);
	}
}