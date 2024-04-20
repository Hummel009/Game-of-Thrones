package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTablePentos extends GOTBlockCraftingTable {
	public GOTBlockTablePentos() {
		super(Material.wood, GOTFaction.PENTOS, GOTGuiId.TABLE_PENTOS);
		setStepSound(soundTypeWood);
	}
}