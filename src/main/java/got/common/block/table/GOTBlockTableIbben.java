package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableIbben extends GOTBlockCraftingTable {
	public GOTBlockTableIbben() {
		super(Material.wood, GOTFaction.ARRYN, GOTGuiID.TABLE_IBBEN);
		setStepSound(soundTypeWood);
	}
}