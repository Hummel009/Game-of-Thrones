package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableSothoryos extends GOTBlockCraftingTable {
	public GOTBlockTableSothoryos() {
		super(Material.wood, GOTFaction.SOTHORYOS, GOTGuiId.TABLE_SOTHORYOS);
		setStepSound(soundTypeWood);
	}
}