package got.common.entity.sothoryos.sothoryos;

import got.common.entity.other.iface.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntitySothoryosBannerBearer extends GOTEntitySothoryosWarrior implements GOTBannerBearer {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySothoryosBannerBearer(World world) {
		super(world);
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.SOTHORYOS;
	}
}