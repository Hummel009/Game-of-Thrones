package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableWesterlands extends GOTBlockCraftingTable {
	public GOTBlockTableWesterlands() {
		super(Material.wood, GOTFaction.WESTERLANDS, GOTGuiId.TABLE_WESTERLANDS);
		setStepSound(soundTypeWood);
	}
}