package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableGhiscar extends GOTBlockCraftingTable {
	public GOTBlockTableGhiscar() {
		super(Material.wood, GOTFaction.GHISCAR, GOTGuiId.TABLE_GHISCAR);
		setStepSound(soundTypeWood);
	}
}