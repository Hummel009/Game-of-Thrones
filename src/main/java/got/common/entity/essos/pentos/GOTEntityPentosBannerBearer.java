package got.common.entity.essos.pentos;

import got.common.entity.essos.norvos.GOTEntityNorvosGuard;
import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityPentosBannerBearer extends GOTEntityNorvosGuard implements GOTBannerBearer {
	public GOTEntityPentosBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.PENTOS;
	}
}
