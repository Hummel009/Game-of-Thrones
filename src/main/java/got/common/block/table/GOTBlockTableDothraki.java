package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableDothraki extends GOTBlockCraftingTable {
	public GOTBlockTableDothraki() {
		super(Material.wood, GOTFaction.DOTHRAKI, 86);
		setStepSound(Block.soundTypeWood);
	}
}
