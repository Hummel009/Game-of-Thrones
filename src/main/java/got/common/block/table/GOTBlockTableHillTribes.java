package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableHillTribes extends GOTBlockCraftingTable {
	public GOTBlockTableHillTribes() {
		super(Material.wood, GOTFaction.HILL_TRIBES, 18);
		setStepSound(Block.soundTypeWood);
	}
}
