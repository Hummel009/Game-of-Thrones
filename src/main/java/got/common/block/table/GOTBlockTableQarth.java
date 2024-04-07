package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableQarth extends GOTBlockCraftingTable {
	public GOTBlockTableQarth() {
		super(Material.wood, GOTFaction.QARTH, GOTGuiID.TABLE_QARTH);
		setStepSound(soundTypeWood);
	}
}
