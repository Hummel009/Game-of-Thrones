package got.common.entity.essos.jogos;

import got.common.entity.other.GOTBannerBearer;
import got.common.item.other.GOTItemBanner;
import net.minecraft.world.World;

public class GOTEntityJogosBannerBearer extends GOTEntityJogos implements GOTBannerBearer {
	public GOTEntityJogosBannerBearer(World world) {
		super(world);
		canBeMarried = false;
	}

	@Override
	public GOTItemBanner.BannerType getBannerType() {
		return GOTItemBanner.BannerType.JOGOS;
	}
}