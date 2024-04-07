package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableMossovy extends GOTBlockCraftingTable {
	public GOTBlockTableMossovy() {
		super(Material.wood, GOTFaction.MOSSOVY, GOTGuiId.TABLE_MOSSOVY);
		setStepSound(soundTypeWood);
	}
}
