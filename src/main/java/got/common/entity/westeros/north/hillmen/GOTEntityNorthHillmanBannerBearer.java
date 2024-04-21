package got.common.entity.westeros.north.hillmen;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityNorthHillmanBannerBearer extends GOTEntityNorthHillmanWarrior implements GOTBannerBearer {
	public GOTEntityNorthHillmanBannerBearer(World world) {
		super(world);
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.EDDARD;
	}
}