package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.util.IIcon;

public class GOTItemDoubleFlower extends GOTItemBlockMetadata {
	public GOTItemDoubleFlower(Block block) {
		super(block);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int i) {
		return ((BlockDoublePlant) field_150939_a).func_149888_a(true, i);
	}
}
