package got.common.entity.essos.ghiscar;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityGhiscarBannerBearer extends GOTEntityGhiscarCorsair implements GOTBannerBearer {
	public GOTEntityGhiscarBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.GHISCAR;
	}
}