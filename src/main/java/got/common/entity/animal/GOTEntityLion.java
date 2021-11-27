package got.common.entity.animal;

import got.common.item.other.GOTItemLionRug;
import got.common.world.biome.GOTBiome;
import net.minecraft.world.World;

public class GOTEntityLion extends GOTEntityLionBase implements GOTBiome.ImmuneToFrost {
	public GOTEntityLion(World world) {
		super(world);
	}

	@Override
	public GOTItemLionRug.LionRugType getLionRugType() {
		return GOTItemLionRug.LionRugType.LION;
	}

	@Override
	public boolean isMale() {
		return true;
	}
}
