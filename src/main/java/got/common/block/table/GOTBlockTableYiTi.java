package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableYiTi extends GOTBlockCraftingTable {
	public GOTBlockTableYiTi() {
		super(Material.wood, GOTFaction.YI_TI, GOTGuiID.TABLE_YI_TI);
		setStepSound(Block.soundTypeWood);
	}
}
