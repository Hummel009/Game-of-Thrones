package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableHillTribes extends GOTBlockCraftingTable {
	public GOTBlockTableHillTribes() {
		super(Material.wood, GOTFaction.HILL_TRIBES, GOTGuiId.TABLE_HILLMEN);
		setStepSound(soundTypeWood);
	}
}