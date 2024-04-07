package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableLhazar extends GOTBlockCraftingTable {
	public GOTBlockTableLhazar() {
		super(Material.wood, GOTFaction.LHAZAR, GOTGuiID.TABLE_LHAZAR);
		setStepSound(soundTypeWood);
	}
}
