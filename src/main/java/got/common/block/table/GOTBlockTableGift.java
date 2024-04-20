package got.common.block.table;

import got.common.database.GOTGuiId;
import got.common.faction.GOTFaction;
import net.minecraft.block.material.Material;

public class GOTBlockTableGift extends GOTBlockCraftingTable {
	public GOTBlockTableGift() {
		super(Material.wood, GOTFaction.NIGHT_WATCH, GOTGuiId.TABLE_GIFT);
		setStepSound(soundTypeWood);
	}
}