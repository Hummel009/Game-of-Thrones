package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableQohor extends GOTBlockCraftingTable {
	public GOTBlockTableQohor() {
		super(Material.wood, GOTFaction.QOHOR, GOTGuiID.TABLE_QOHOR);
		setStepSound(soundTypeWood);
	}
}