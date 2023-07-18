package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableStormlands extends GOTBlockCraftingTable {
	public GOTBlockTableStormlands() {
		super(Material.wood, GOTFaction.STORMLANDS, 79);
		setStepSound(Block.soundTypeWood);
	}
}