package got.common.world.structure.sothoryos.summer;

import java.util.Random;

import got.common.database.GOTRegistry;

public class GOTStructureSummerPalisadeRuined extends GOTStructureSummerPalisade {
	public GOTStructureSummerPalisadeRuined(boolean flag) {
		super(flag);
	}

	@Override
	public boolean isRuined() {
		return true;
	}

	@Override
	public void setupRandomBlocks(Random random) {
		super.setupRandomBlocks(random);
		if (random.nextBoolean()) {
			woodBlock = GOTRegistry.wood1;
			woodMeta = 3;
		}
	}
}
