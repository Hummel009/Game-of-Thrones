package got.common.entity.essos.gold;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityGoldenBannerBearer extends GOTEntityGoldenWarrior implements GOTBannerBearer {
	public GOTEntityGoldenBannerBearer(World world) {
		super(world);
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.GOLDENCOMPANY;
	}
}