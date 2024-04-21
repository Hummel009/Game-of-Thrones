package got.common.entity.essos.norvos;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityNorvosBannerBearer extends GOTEntityNorvosGuard implements GOTBannerBearer {
	public GOTEntityNorvosBannerBearer(World world) {
		super(world);
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.NORVOS;
	}
}