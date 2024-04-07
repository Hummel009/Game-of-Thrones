package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableDorne extends GOTBlockCraftingTable {
	public GOTBlockTableDorne() {
		super(Material.wood, GOTFaction.DORNE, GOTGuiId.TABLE_DORNE);
		setStepSound(soundTypeWood);
	}
}