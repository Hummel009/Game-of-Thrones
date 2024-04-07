package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableDothraki extends GOTBlockCraftingTable {
	public GOTBlockTableDothraki() {
		super(Material.wood, GOTFaction.DOTHRAKI, GOTGuiId.TABLE_DOTHRAKI);
		setStepSound(soundTypeWood);
	}
}