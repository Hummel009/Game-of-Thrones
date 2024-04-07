package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableMyr extends GOTBlockCraftingTable {
	public GOTBlockTableMyr() {
		super(Material.wood, GOTFaction.MYR, GOTGuiID.TABLE_MYR);
		setStepSound(soundTypeWood);
	}
}