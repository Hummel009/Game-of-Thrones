package got.common.world.biome.essos;

import got.common.world.biome.other.GOTBiomeOcean;
import got.common.world.map.GOTBezierType;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;

public class GOTBiomeRedBeach extends GOTBiomeOcean {
	public GOTBiomeRedBeach(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableGOTAmbientList.clear();
		npcSpawnList.clear();
		biomeColors.setWater(6556170);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.COBBLEBRICK;
	}

	@Override
	public int getWallTop() {
		return 90;
	}

	public GOTBiomeRedBeach setBeachBlock(Block block, int meta) {
		topBlock = block;
		topBlockMeta = meta;
		fillerBlock = block;
		fillerBlockMeta = meta;
		return this;
	}
}
