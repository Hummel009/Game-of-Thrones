package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableDorne extends GOTBlockCraftingTable {
	public GOTBlockTableDorne() {
		super(Material.wood, GOTFaction.DORNE, 66);
		setStepSound(Block.soundTypeWood);
	}
}