package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableArryn extends GOTBlockCraftingTable {
	public GOTBlockTableArryn() {
		super(Material.wood, GOTFaction.ARRYN, 62);
		setStepSound(Block.soundTypeWood);
	}
}