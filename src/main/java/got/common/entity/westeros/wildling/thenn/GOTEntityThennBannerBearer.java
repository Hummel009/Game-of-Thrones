package got.common.entity.westeros.wildling.thenn;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityThennBannerBearer extends GOTEntityThenn implements GOTBannerBearer {
	public GOTEntityThennBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.THENN;
	}
}
