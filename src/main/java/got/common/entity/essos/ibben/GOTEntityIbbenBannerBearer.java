package got.common.entity.essos.ibben;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityIbbenBannerBearer extends GOTEntityIbbenWarrior implements GOTBannerBearer {
	public GOTEntityIbbenBannerBearer(World world) {
		super(world);
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.IBBEN;
	}
}