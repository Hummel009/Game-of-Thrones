package got.common.block.table;

import got.common.database.GOTGuiID;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockTableJogos extends GOTBlockCraftingTable {
	public GOTBlockTableJogos() {
		super(Material.wood, GOTFaction.JOGOS, GOTGuiID.TABLE_JOGOS);
		setStepSound(soundTypeWood);
	}
}
