package got.common.block.table;

import cpw.mods.fml.relauncher.*;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class GOTBlockTableSummer extends GOTBlockCraftingTable {
	@SideOnly(value = Side.CLIENT)
	public IIcon[] tableIcons;

	public GOTBlockTableSummer() {
		super(Material.rock, GOTFaction.SUMMER_ISLANDS, 25);
		setStepSound(Block.soundTypeStone);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 1) {
			return tableIcons[1];
		}
		if (i == 0) {
			return Blocks.sandstone.getIcon(0, 0);
		}
		return tableIcons[0];
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconregister) {
		tableIcons = new IIcon[2];
		tableIcons[0] = iconregister.registerIcon(getTextureName() + "_side");
		tableIcons[1] = iconregister.registerIcon(getTextureName() + "_top");
	}
}
