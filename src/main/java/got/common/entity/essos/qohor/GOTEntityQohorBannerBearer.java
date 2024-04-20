package got.common.entity.essos.qohor;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityQohorBannerBearer extends GOTEntityQohorGuard implements GOTBannerBearer {
	public GOTEntityQohorBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.QOHOR;
	}
}