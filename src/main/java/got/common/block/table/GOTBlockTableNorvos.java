package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableNorvos extends GOTBlockCraftingTable {
	public GOTBlockTableNorvos() {
		super(Material.wood, GOTFaction.NORVOS, GOTGuiId.TABLE_NORVOS);
		setStepSound(soundTypeWood);
	}
}