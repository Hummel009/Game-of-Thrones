package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableIronborn extends GOTBlockCraftingTable {
	public GOTBlockTableIronborn() {
		super(Material.wood, GOTFaction.IRONBORN, GOTGuiId.TABLE_IRONBORN);
		setStepSound(soundTypeWood);
	}
}