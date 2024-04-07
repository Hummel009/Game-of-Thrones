package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableVolantis extends GOTBlockCraftingTable {
	public GOTBlockTableVolantis() {
		super(Material.wood, GOTFaction.VOLANTIS, GOTGuiID.TABLE_VOLANTIS);
		setStepSound(soundTypeWood);
	}
}