package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableBraavos extends GOTBlockCraftingTable {
	public GOTBlockTableBraavos() {
		super(Material.wood, GOTFaction.BRAAVOS, GOTGuiId.TABLE_BRAAVOS);
		setStepSound(soundTypeWood);
	}
}