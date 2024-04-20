package got.common.entity.westeros.gift;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityGiftBannerBearer extends GOTEntityGiftGuard implements GOTBannerBearer {
	public GOTEntityGiftBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.NIGHT;
	}
}