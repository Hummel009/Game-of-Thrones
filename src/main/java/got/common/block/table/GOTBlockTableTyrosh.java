package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableTyrosh extends GOTBlockCraftingTable {
	public GOTBlockTableTyrosh() {
		super(Material.wood, GOTFaction.TYROSH, GOTGuiID.TABLE_TYROSH);
		setStepSound(Block.soundTypeWood);
	}
}