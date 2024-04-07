package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableArryn extends GOTBlockCraftingTable {
	public GOTBlockTableArryn() {
		super(Material.wood, GOTFaction.ARRYN, GOTGuiId.TABLE_ARRYN);
		setStepSound(soundTypeWood);
	}
}