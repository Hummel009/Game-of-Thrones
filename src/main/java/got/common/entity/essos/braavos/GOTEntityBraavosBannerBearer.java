package got.common.entity.essos.braavos;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityBraavosBannerBearer extends GOTEntityBraavosSoldier implements GOTBannerBearer {
	public GOTEntityBraavosBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.BRAAVOS;
	}
}
