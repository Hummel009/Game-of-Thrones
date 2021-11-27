package got.common.world.biome.essos;

import got.common.world.biome.other.GOTBiomeOcean;
import got.common.world.map.GOTBezierType;
import net.minecraft.block.Block;

public class GOTBiomeIbbenBeach extends GOTBiomeOcean {
	public GOTBiomeIbbenBeach(int i, boolean major) {
		super(i, major);
		setMinMaxHeight(0.1f, 0.0f);
		setTemperatureRainfall(0.8f, 0.4f);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableGOTAmbientList.clear();
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.WOOD;
	}

	public GOTBiomeIbbenBeach setBeachBlock(Block block, int meta) {
		topBlock = block;
		topBlockMeta = meta;
		fillerBlock = block;
		fillerBlockMeta = meta;
		return this;
	}
}
