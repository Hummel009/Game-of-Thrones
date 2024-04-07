package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableQarth extends GOTBlockCraftingTable {
	public GOTBlockTableQarth() {
		super(Material.wood, GOTFaction.QARTH, GOTGuiId.TABLE_QARTH);
		setStepSound(soundTypeWood);
	}
}
