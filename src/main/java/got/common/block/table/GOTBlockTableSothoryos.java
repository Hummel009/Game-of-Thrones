package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableSothoryos extends GOTBlockCraftingTable {
	public GOTBlockTableSothoryos() {
		super(Material.wood, GOTFaction.SOTHORYOS, GOTGuiID.TABLE_SOTHORYOS);
		setStepSound(Block.soundTypeWood);
	}
}
