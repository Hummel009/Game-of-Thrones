package got.common.entity.sothoryos.sothoryos;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntitySothoryosBannerBearer extends GOTEntitySothoryosWarrior implements GOTBannerBearer {
	public GOTEntitySothoryosBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.SOTHORYOS;
	}
}