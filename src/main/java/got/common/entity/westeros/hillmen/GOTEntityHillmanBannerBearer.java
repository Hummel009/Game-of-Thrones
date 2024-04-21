package got.common.entity.westeros.hillmen;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityHillmanBannerBearer extends GOTEntityHillmanWarrior implements GOTBannerBearer {
	public GOTEntityHillmanBannerBearer(World world) {
		super(world);
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.HILLMEN;
	}
}