package got.common.entity.westeros.dragonstone;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityDragonstoneBannerBearer extends GOTEntityDragonstoneSoldier implements GOTBannerBearer {
	public GOTEntityDragonstoneBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.STANNIS;
	}
}
