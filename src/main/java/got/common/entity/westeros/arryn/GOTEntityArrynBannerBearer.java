package got.common.entity.westeros.arryn;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityArrynBannerBearer extends GOTEntityArrynSoldier implements GOTBannerBearer {
	public GOTEntityArrynBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.ARRYN;
	}
}
