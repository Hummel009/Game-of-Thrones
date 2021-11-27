package got.common.entity.essos.tyrosh;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityTyroshBannerBearer extends GOTEntityTyroshSoldier implements GOTBannerBearer {
	public GOTEntityTyroshBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.TYROSH;
	}
}
