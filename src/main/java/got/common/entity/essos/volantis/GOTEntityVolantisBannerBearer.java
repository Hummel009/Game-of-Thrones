package got.common.entity.essos.volantis;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityVolantisBannerBearer extends GOTEntityVolantisSoldier implements GOTBannerBearer {
	public GOTEntityVolantisBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.VOLANTIS;
	}
}
