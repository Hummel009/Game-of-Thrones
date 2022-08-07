package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.biome.GOTBiome;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;

public class GOTBiomeRedSea extends GOTBiome {
	public GOTBiomeRedSea(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableGOTAmbientList.clear();
		npcSpawnList.clear();
		biomeColors.setWater(6556170);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterRedSea;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return null;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	public GOTBiomeRedSea setLakeBlock(Block block) {
		topBlock = block;
		fillerBlock = block;
		return this;
	}
}
