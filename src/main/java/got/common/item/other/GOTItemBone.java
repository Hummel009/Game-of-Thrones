package got.common.item.other;

import got.common.database.GOTCreativeTabs;
import net.minecraft.item.Item;

public class GOTItemBone extends Item {
	public GOTItemBone() {
		setCreativeTab(GOTCreativeTabs.tabMaterials);
		setFull3D();
	}
}
