package got.common.entity.essos.asshai;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityAsshaiBannerBearer extends GOTEntityAsshaiWarrior implements GOTBannerBearer {
	public GOTEntityAsshaiBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.ASSHAI;
	}
}
