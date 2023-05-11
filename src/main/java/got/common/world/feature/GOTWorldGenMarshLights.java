package got.common.world.feature;

import got.common.database.GOTRegistry;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class GOTWorldGenMarshLights extends WorldGenerator {
	@Override
	public boolean generate(World world, Random random, int i, int j, int k) {
		for (int l = 0; l < 4; ++l) {
			int j1;
			int k1;
			int i1 = i + random.nextInt(8) - random.nextInt(8);
			if (!world.isAirBlock(i1, j1 = j, k1 = k + random.nextInt(8) - random.nextInt(8)) || !GOTRegistry.marshLights.canPlaceBlockAt(world, i1, j1, k1)) {
				continue;
			}
			world.setBlock(i1, j1, k1, GOTRegistry.marshLights, 0, 2);
		}
		return true;
	}
}
