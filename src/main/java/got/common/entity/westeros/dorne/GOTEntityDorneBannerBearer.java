package got.common.entity.westeros.dorne;

import got.common.entity.other.iface.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityDorneBannerBearer extends GOTEntityDorneSoldier implements GOTBannerBearer {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityDorneBannerBearer(World world) {
		super(world);
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.MARTELL;
	}
}