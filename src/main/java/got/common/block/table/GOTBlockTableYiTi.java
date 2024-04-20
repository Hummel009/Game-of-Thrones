package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableYiTi extends GOTBlockCraftingTable {
	public GOTBlockTableYiTi() {
		super(Material.wood, GOTFaction.YI_TI, GOTGuiId.TABLE_YI_TI);
		setStepSound(soundTypeWood);
	}
}