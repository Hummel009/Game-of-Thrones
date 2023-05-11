package got.common.world;

import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;

public class GOTWorldInfo extends DerivedWorldInfo {
	public long gotTotalTime;
	public long gotWorldTime;

	public GOTWorldInfo(WorldInfo worldinfo) {
		super(worldinfo);
	}

	@Override
	public long getWorldTime() {
		return gotWorldTime;
	}

	@Override
	public void setWorldTime(long time) {
	}

	@Override
	public long getWorldTotalTime() {
		return gotTotalTime;
	}

	public void got_setTotalTime(long time) {
		gotTotalTime = time;
	}

	public void got_setWorldTime(long time) {
		gotWorldTime = time;
	}

	@Override
	public void incrementTotalWorldTime(long time) {
	}
}
