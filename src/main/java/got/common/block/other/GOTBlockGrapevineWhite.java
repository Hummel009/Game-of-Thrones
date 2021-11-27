package got.common.block.other;

import got.common.database.GOTRegistry;
import net.minecraft.item.Item;

public class GOTBlockGrapevineWhite extends GOTBlockGrapevine {
	public GOTBlockGrapevineWhite() {
		super(true);
	}

	@Override
	public Item getGrapeItem() {
		return GOTRegistry.grapeWhite;
	}

	@Override
	public Item getGrapeSeedsItem() {
		return GOTRegistry.seedsGrapeWhite;
	}
}
