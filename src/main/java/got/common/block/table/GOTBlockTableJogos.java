package got.common.block.table;

import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableJogos extends GOTBlockCraftingTable {
	public GOTBlockTableJogos() {
		super(Material.wood, GOTFaction.JOGOS, 87);
		setStepSound(Block.soundTypeWood);
	}
}
