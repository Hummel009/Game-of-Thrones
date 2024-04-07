package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableReach extends GOTBlockCraftingTable {
	public GOTBlockTableReach() {
		super(Material.wood, GOTFaction.REACH, GOTGuiId.TABLE_REACH);
		setStepSound(soundTypeWood);
	}
}