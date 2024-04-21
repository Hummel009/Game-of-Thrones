package got.common.entity.westeros.crownlands;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityCrownlandsBannerBearer extends GOTEntityCrownlandsGuard implements GOTBannerBearer {
	public GOTEntityCrownlandsBannerBearer(World world) {
		super(world);
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.ROBERT;
	}
}