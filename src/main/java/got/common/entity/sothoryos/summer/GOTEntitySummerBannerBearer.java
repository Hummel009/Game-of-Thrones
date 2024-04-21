package got.common.entity.sothoryos.summer;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntitySummerBannerBearer extends GOTEntitySummerWarrior implements GOTBannerBearer {
	public GOTEntitySummerBannerBearer(World world) {
		super(world);
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.SUMMER;
	}
}