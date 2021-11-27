package got.common.entity.westeros.stormlands;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityStormlandsBannerBearer extends GOTEntityStormlandsSoldier implements GOTBannerBearer {
	public GOTEntityStormlandsBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.RENLY;
	}
}
