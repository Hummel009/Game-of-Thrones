package got.common.entity.essos.qarth;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityQarthBannerBearer extends GOTEntityQarthLevyman implements GOTBannerBearer {
	public GOTEntityQarthBannerBearer(World world) {
		super(world);
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.QARTH;
	}
}