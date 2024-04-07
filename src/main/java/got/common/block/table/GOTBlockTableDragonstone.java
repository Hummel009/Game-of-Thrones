package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableDragonstone extends GOTBlockCraftingTable {
	public GOTBlockTableDragonstone() {
		super(Material.wood, GOTFaction.DRAGONSTONE, GOTGuiId.TABLE_DRAGONSTONE);
		setStepSound(soundTypeWood);
	}
}