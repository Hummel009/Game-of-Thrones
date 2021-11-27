package got.common.entity.essos.lhazar;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityLhazarBannerBearer extends GOTEntityLhazarWarrior implements GOTBannerBearer {
	public GOTEntityLhazarBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.LHAZAR;
	}
}
