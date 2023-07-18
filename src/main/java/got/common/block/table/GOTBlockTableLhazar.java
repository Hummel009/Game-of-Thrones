package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableLhazar extends GOTBlockCraftingTable {
	public GOTBlockTableLhazar() {
		super(Material.wood, GOTFaction.LHAZAR, 37);
		setStepSound(Block.soundTypeWood);
	}
}
