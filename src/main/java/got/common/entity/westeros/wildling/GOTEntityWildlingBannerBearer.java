package got.common.entity.westeros.wildling;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityWildlingBannerBearer extends GOTEntityWildling implements GOTBannerBearer {
	public GOTEntityWildlingBannerBearer(World world) {
		super(world);
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.WILDLING;
	}
}