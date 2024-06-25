package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableJogosNhai extends GOTBlockCraftingTable {
	public GOTBlockTableJogosNhai() {
		super(Material.wood, GOTFaction.JOGOS_NHAI, GOTGuiId.TABLE_JOGOS_NHAI);
		setStepSound(soundTypeWood);
	}
}