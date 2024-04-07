package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableTyrosh extends GOTBlockCraftingTable {
	public GOTBlockTableTyrosh() {
		super(Material.wood, GOTFaction.TYROSH, GOTGuiId.TABLE_TYROSH);
		setStepSound(soundTypeWood);
	}
}