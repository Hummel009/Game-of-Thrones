package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableDragonstone extends GOTBlockCraftingTable {
	public GOTBlockTableDragonstone() {
		super(Material.wood, GOTFaction.DRAGONSTONE, GOTGuiID.TABLE_DRAGONSTONE);
		setStepSound(soundTypeWood);
	}
}