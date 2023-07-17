package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableLorath extends GOTBlockCraftingTable {
	public GOTBlockTableLorath() {
		super(Material.wood, GOTFaction.LORATH, GOTGuiID.TABLE_LORATH);
		setStepSound(Block.soundTypeWood);
	}
}