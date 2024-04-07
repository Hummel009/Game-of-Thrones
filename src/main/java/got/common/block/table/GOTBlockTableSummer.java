package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableSummer extends GOTBlockCraftingTable {
	public GOTBlockTableSummer() {
		super(Material.wood, GOTFaction.SUMMER_ISLANDS, GOTGuiID.TABLE_SUMMER);
		setStepSound(soundTypeWood);
	}
}
