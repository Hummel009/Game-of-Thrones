package got.common.entity.essos.gold;

import got.common.entity.other.iface.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityGoldenBannerBearer extends GOTEntityGoldenWarrior implements GOTBannerBearer {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGoldenBannerBearer(World world) {
		super(world);
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.GOLDENCOMPANY;
	}
}