package got.common.item.other;

import cpw.mods.fml.relauncher.*;
import got.common.block.other.GOTBlockTallGrass;
import got.common.database.GOTRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GOTItemTallGrass extends GOTItemBlockMetadata {
	public GOTItemTallGrass(Block block) {
		super(block);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		if (pass > 0) {
			return 16777215;
		}
		return super.getColorFromItemStack(itemstack, pass);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		if (pass > 0) {
			return GOTRegistry.tallGrass.getIcon(-1, meta);
		}
		return super.getIconFromDamageForRenderPass(meta, pass);
	}

	@Override
	public int getRenderPasses(int meta) {
		return GOTBlockTallGrass.grassOverlay[meta] ? 2 : 1;
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
}
