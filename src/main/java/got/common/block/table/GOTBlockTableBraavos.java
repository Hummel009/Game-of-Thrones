package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableBraavos extends GOTBlockCraftingTable {
	public GOTBlockTableBraavos() {
		super(Material.wood, GOTFaction.BRAAVOS, 85);
		setStepSound(Block.soundTypeWood);
	}
}