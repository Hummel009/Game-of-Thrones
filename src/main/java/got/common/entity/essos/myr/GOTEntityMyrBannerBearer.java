package got.common.entity.essos.myr;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityMyrBannerBearer extends GOTEntityMyrSoldier implements GOTBannerBearer {
	public GOTEntityMyrBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.MYR;
	}
}