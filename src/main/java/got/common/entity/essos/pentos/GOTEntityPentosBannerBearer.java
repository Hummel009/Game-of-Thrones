package got.common.entity.essos.pentos;

import got.common.entity.other.iface.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityPentosBannerBearer extends GOTEntityPentosGuard implements GOTBannerBearer {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityPentosBannerBearer(World world) {
		super(world);
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.PENTOS;
	}
}