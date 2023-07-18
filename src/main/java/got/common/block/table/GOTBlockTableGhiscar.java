package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableGhiscar extends GOTBlockCraftingTable {
	public GOTBlockTableGhiscar() {
		super(Material.wood, GOTFaction.GHISCAR, 68);
		setStepSound(Block.soundTypeWood);
	}
}