package got.common.item.other;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import got.common.block.other.GOTBlockConcrete;
import got.common.block.other.GOTBlockConcretePowder;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class GOTItemBlockConcrete extends ItemBlock {
	public GOTItemBlockConcrete(Block p_i45328_1_) {
		super(p_i45328_1_);
	}

	@Override
	public String getItemStackDisplayName(ItemStack p_77653_1_) {
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			if (field_150939_a instanceof GOTBlockConcretePowder) {
				return I18n.format("tile.concrete_powder.name", I18n.format("color." + ((GOTBlockConcretePowder) field_150939_a).getColor().getUnlocalizedName()));
			}
			if (field_150939_a instanceof GOTBlockConcrete) {
				return I18n.format("tile.concrete.name", I18n.format("color." + ((GOTBlockConcrete) field_150939_a).getColor().getUnlocalizedName()));
			}
			return I18n.format(field_150939_a.getUnlocalizedName());
		}
		return "";
	}
}
