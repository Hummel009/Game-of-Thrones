package got.common.item.other;

import cpw.mods.fml.relauncher.*;
import got.common.database.GOTCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class GOTItemRing extends Item {
	@SideOnly(value = Side.CLIENT)
	public static IIcon saxIcon;

	public GOTItemRing() {
		setCreativeTab(GOTCreativeTabs.tabMisc);
	}

	@SideOnly(value = Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconregister) {
		super.registerIcons(iconregister);
		saxIcon = iconregister.registerIcon("got:sax");
	}
}
