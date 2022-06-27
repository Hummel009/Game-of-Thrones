package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableWesterlands extends GOTBlockCraftingTable {
	public GOTBlockTableWesterlands() {
		super(Material.wood, GOTFaction.WESTERLANDS, 82);
		setStepSound(Block.soundTypeWood);
	}
}