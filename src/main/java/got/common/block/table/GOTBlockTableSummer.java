package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableSummer extends GOTBlockCraftingTable {
	public GOTBlockTableSummer() {
		super(Material.wood, GOTFaction.SUMMER_ISLANDS, GOTGuiId.TABLE_SUMMER);
		setStepSound(soundTypeWood);
	}
}
