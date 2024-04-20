package got.common.entity.westeros.riverlands;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityRiverlandsBannerBearer extends GOTEntityRiverlandsSoldier implements GOTBannerBearer {
	public GOTEntityRiverlandsBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.TULLY;
	}
}