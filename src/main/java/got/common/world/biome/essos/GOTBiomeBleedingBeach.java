package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.other.GOTBiomeOcean;
import got.common.world.map.GOTBezierType;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.block.Block;

import java.awt.*;

public class GOTBiomeBleedingBeach extends GOTBiomeOcean {
	public GOTBiomeBleedingBeach(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableGOTAmbientList.clear();
		npcSpawnList.clear();
		biomeColors.setWater(new Color(0x640a0a));
		banditChance = GOTEventSpawner.EventChance.NEVER;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterBleedingSea;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return GOTBezierType.WALL_YITI;
	}

	@Override
	public int getWallTop() {
		return 90;
	}

	public GOTBiome setBeachBlock(Block block, int meta) {
		topBlock = block;
		topBlockMeta = meta;
		fillerBlock = block;
		fillerBlockMeta = meta;
		return this;
	}
}