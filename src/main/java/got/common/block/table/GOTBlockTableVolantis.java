package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableVolantis extends GOTBlockCraftingTable {
	public GOTBlockTableVolantis() {
		super(Material.wood, GOTFaction.VOLANTIS, 81);
		setStepSound(Block.soundTypeWood);
	}
}