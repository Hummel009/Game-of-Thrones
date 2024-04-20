package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableLhazar extends GOTBlockCraftingTable {
	public GOTBlockTableLhazar() {
		super(Material.wood, GOTFaction.LHAZAR, GOTGuiId.TABLE_LHAZAR);
		setStepSound(soundTypeWood);
	}
}