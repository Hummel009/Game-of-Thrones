package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableCrownlands extends GOTBlockCraftingTable {
	public GOTBlockTableCrownlands() {
		super(Material.wood, GOTFaction.CROWNLANDS, GOTGuiId.TABLE_CROWNLANDS);
		setStepSound(soundTypeWood);
	}
}