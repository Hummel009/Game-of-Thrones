package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableIronborn extends GOTBlockCraftingTable {
	public GOTBlockTableIronborn() {
		super(Material.wood, GOTFaction.IRONBORN, 69);
		setStepSound(Block.soundTypeWood);
	}
}