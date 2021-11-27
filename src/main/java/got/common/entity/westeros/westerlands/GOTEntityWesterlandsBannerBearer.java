package got.common.entity.westeros.westerlands;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityWesterlandsBannerBearer extends GOTEntityWesterlandsSoldier implements GOTBannerBearer {
	public GOTEntityWesterlandsBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.LANNISTER;
	}
}
