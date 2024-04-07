package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableDorne extends GOTBlockCraftingTable {
	public GOTBlockTableDorne() {
		super(Material.wood, GOTFaction.DORNE, GOTGuiID.TABLE_DORNE);
		setStepSound(soundTypeWood);
	}
}