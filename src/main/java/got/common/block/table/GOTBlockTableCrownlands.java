package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableCrownlands extends GOTBlockCraftingTable {
	public GOTBlockTableCrownlands() {
		super(Material.wood, GOTFaction.CROWNLANDS, 65);
		setStepSound(Block.soundTypeWood);
	}
}