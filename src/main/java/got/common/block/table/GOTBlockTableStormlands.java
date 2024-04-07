package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableStormlands extends GOTBlockCraftingTable {
	public GOTBlockTableStormlands() {
		super(Material.wood, GOTFaction.STORMLANDS, GOTGuiId.TABLE_STORMLANDS);
		setStepSound(soundTypeWood);
	}
}