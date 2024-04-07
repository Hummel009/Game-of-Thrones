package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableVolantis extends GOTBlockCraftingTable {
	public GOTBlockTableVolantis() {
		super(Material.wood, GOTFaction.VOLANTIS, GOTGuiId.TABLE_VOLANTIS);
		setStepSound(soundTypeWood);
	}
}