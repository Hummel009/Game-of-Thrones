package got.common.item.other;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.common.database.GOTMaterial;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class GOTItemKaftan extends GOTItemRobes {
	@SideOnly(value = Side.CLIENT)
	public IIcon overlayIcon;

	public GOTItemKaftan(int slot) {
		super(GOTMaterial.KAFTAN, slot);
	}

	@Override
	public int getColor(ItemStack itemstack) {
		return GOTItemRobes.getRobesColor(itemstack);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemstack, int pass) {
		if (pass >= 1) {
			return 16777215;
		}
		return super.getColorFromItemStack(itemstack, pass);
	}

	@Override
	public IIcon getIcon(ItemStack itemstack, int pass) {
		if (pass >= 1) {
			return overlayIcon;
		}
		return super.getIcon(itemstack, pass);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		super.registerIcons(iconregister);
		overlayIcon = iconregister.registerIcon(getIconString() + "_overlay");
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}
}
