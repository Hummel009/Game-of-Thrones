package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableWildling extends GOTBlockCraftingTable {
	public GOTBlockTableWildling() {
		super(Material.wood, GOTFaction.WILDLING, GOTGuiID.TABLE_WILDLING);
		setStepSound(soundTypeWood);
	}
}
