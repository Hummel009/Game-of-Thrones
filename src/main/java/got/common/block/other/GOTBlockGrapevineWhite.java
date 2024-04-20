package got.common.block.other;

import got.common.database.GOTItems;
import net.minecraft.item.Item;

public class GOTBlockGrapevineWhite extends GOTBlockGrapevine {
	public GOTBlockGrapevineWhite() {
		super(true);
	}

	@Override
	public Item getGrapeItem() {
		return GOTItems.grapeWhite;
	}

	@Override
	public Item getGrapeSeedsItem() {
		return GOTItems.seedsGrapeWhite;
	}
}