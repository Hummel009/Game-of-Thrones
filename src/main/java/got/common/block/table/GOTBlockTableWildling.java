package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableWildling extends GOTBlockCraftingTable {
	public GOTBlockTableWildling() {
		super(Material.wood, GOTFaction.WILDLING, GOTGuiId.TABLE_WILDLING);
		setStepSound(soundTypeWood);
	}
}
