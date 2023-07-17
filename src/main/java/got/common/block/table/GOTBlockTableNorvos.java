package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableNorvos extends GOTBlockCraftingTable {
	public GOTBlockTableNorvos() {
		super(Material.wood, GOTFaction.NORVOS, GOTGuiID.TABLE_NORVOS);
		setStepSound(Block.soundTypeWood);
	}
}