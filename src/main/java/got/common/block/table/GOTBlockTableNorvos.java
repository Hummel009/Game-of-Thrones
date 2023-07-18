package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableNorvos extends GOTBlockCraftingTable {
	public GOTBlockTableNorvos() {
		super(Material.wood, GOTFaction.NORVOS, 73);
		setStepSound(Block.soundTypeWood);
	}
}