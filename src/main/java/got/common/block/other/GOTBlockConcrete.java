package got.common.block.other;

import got.common.database.GOTCreativeTabs;
import got.common.util.GOTEnumDyeColor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GOTBlockConcrete extends Block {
	public GOTEnumDyeColor color;

	public GOTBlockConcrete(GOTEnumDyeColor color) {
		super(Material.rock);
		this.color = color;
		setBlockName("concrete_" + this.color);
		setCreativeTab(GOTCreativeTabs.tabBlock);
		setHardness(1.5f);
		setHarvestLevel("pickaxe", 0);
		setResistance(10.0f);
		setBlockTextureName("got:concrete_" + this.color.getName());
	}

	public GOTEnumDyeColor getColor() {
		return color;
	}
}
