package got.common.entity.westeros.reach;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityReachBannerBearer extends GOTEntityReachSoldier implements GOTBannerBearer {
	public GOTEntityReachBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.TYRELL;
	}
}