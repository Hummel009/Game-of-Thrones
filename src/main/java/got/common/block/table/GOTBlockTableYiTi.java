package got.common.block.table;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTRegistry;
import got.common.faction.GOTFaction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class GOTBlockTableYiTi extends GOTBlockCraftingTable {
	@SideOnly(value = Side.CLIENT)
	public IIcon[] tableIcons;

	public GOTBlockTableYiTi() {
		super(Material.rock, GOTFaction.YI_TI, 49);
		setStepSound(Block.soundTypeStone);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIcon(int i, int j) {
		if (i == 1) {
			return tableIcons[1];
		}
		if (i == 0) {
			return GOTRegistry.brick5.getIcon(0, 11);
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
