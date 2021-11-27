package got.common.block.other;

import got.common.database.GOTRegistry;
import net.minecraft.item.Item;

public class GOTBlockGrapevineRed extends GOTBlockGrapevine {
	public GOTBlockGrapevineRed() {
		super(true);
	}

	@Override
	public Item getGrapeItem() {
		return GOTRegistry.grapeRed;
	}

	@Override
	public Item getGrapeSeedsItem() {
		return GOTRegistry.seedsGrapeRed;
	}
}
