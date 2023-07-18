package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableIronborn extends GOTBlockCraftingTable {
	public GOTBlockTableIronborn() {
		super(Material.wood, GOTFaction.IRONBORN, GOTGuiID.TABLE_IRONBORN);
		setStepSound(Block.soundTypeWood);
	}
}