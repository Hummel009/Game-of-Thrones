package got.common.block.table;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTRegistry;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class GOTBlockTableAsshai extends GOTBlockCraftingTable {
	public GOTBlockTableAsshai() {
		super(Material.wood, GOTFaction.ASSHAI, 84);
		setStepSound(Block.soundTypeWood);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 1) {
			return tableIcons[1];
		}
		if (i == 0) {
			return GOTRegistry.planks1.getIcon(0, 3);
		}
		return tableIcons[0];
	}
}