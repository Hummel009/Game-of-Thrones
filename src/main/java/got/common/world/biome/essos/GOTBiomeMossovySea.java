package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint.Region;
import net.minecraft.block.Block;

public class GOTBiomeMossovySea extends GOTBiome {
	public GOTBiomeMossovySea(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableGOTAmbientList.clear();
		npcSpawnList.clear();
		decorator.sandPerChunk = 0;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return null;
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.MOSSOVY;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	public GOTBiomeMossovySea setLakeBlock(Block block) {
		topBlock = block;
		fillerBlock = block;
		return this;
	}
}
