package got.common.entity.animal;

import got.common.item.other.GOTItemLionRug;
import got.common.world.biome.GOTBiome;
import net.minecraft.world.World;

public class GOTEntityLioness extends GOTEntityLionBase implements GOTBiome.ImmuneToFrost {
	public GOTEntityLioness(World world) {
		super(world);
	}

	@Override
	public GOTItemLionRug.LionRugType getLionRugType() {
		return GOTItemLionRug.LionRugType.LIONESS;
	}

	@Override
	public boolean isMale() {
		return false;
	}
}
