package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableLorath extends GOTBlockCraftingTable {
	public GOTBlockTableLorath() {
		super(Material.wood, GOTFaction.LORATH, GOTGuiId.TABLE_LORATH);
		setStepSound(soundTypeWood);
	}
}