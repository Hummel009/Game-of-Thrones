package got.common.entity.westeros.north;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityNorthBannerBearer extends GOTEntityNorthSoldier implements GOTBannerBearer {
	public GOTEntityNorthBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.ROBB;
	}
}