package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableNorth extends GOTBlockCraftingTable {
	public GOTBlockTableNorth() {
		super(Material.wood, GOTFaction.NORTH, GOTGuiId.TABLE_NORTH);
		setStepSound(soundTypeWood);
	}
}