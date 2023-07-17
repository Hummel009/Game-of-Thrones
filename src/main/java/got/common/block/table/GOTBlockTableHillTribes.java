package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableHillTribes extends GOTBlockCraftingTable {
	public GOTBlockTableHillTribes() {
		super(Material.wood, GOTFaction.HILL_TRIBES, GOTGuiID.TABLE_HILLMEN);
		setStepSound(Block.soundTypeWood);
	}
}
