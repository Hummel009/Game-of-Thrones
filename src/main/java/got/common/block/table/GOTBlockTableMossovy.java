package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableMossovy extends GOTBlockCraftingTable {
	public GOTBlockTableMossovy() {
		super(Material.wood, GOTFaction.MOSSOVY, GOTGuiID.TABLE_MOSSOVY);
		setStepSound(Block.soundTypeWood);
	}
}
