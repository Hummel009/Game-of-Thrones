package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableLys extends GOTBlockCraftingTable {
	public GOTBlockTableLys() {
		super(Material.wood, GOTFaction.LYS, GOTGuiId.TABLE_LYS);
		setStepSound(soundTypeWood);
	}
}