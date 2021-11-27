package got.common.entity.essos.lys;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityLysBannerBearer extends GOTEntityLysSoldier implements GOTBannerBearer {
	public GOTEntityLysBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.LYS;
	}
}
