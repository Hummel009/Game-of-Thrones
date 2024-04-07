package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableRiverlands extends GOTBlockCraftingTable {
	public GOTBlockTableRiverlands() {
		super(Material.wood, GOTFaction.RIVERLANDS, GOTGuiId.TABLE_RIVERLANDS);
		setStepSound(soundTypeWood);
	}
}