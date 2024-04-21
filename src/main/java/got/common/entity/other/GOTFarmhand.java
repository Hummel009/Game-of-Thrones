package got.common.entity.other;

import net.minecraft.item.Item;
import net.minecraftforge.common.IPlantable;

public interface GOTFarmhand {
	IPlantable getSeedsItem();

	void setSeedsItem(Item seed);
}