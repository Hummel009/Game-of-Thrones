package got.common.world.biome.ulthos;

import got.client.sound.GOTMusicRegion;
import got.common.database.GOTBlocks;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.world.gen.feature.WorldGenMinable;

public abstract class GOTBiomeUlthosBase extends GOTBiome {
	protected GOTBiomeUlthosBase(int i, boolean major) {
		super(i, major);
		banditChance = GOTEventSpawner.EventChance.NEVER;
		decorator.setBiomeGemFactor(3.0f);
		decorator.setBiomeOreFactor(3.0f);
		decorator.addSoil(new WorldGenMinable(GOTBlocks.obsidianGravel, 32), 20.0f, 0, 64);
	}

	protected void setupDefaultTrees() {
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.PINE, 400);
		decorator.addTree(GOTTreeType.SPRUCE, 300);
		decorator.addTree(GOTTreeType.FIR, 300);
		decorator.addTree(GOTTreeType.DARK_OAK, 100);
		decorator.addTree(GOTTreeType.DARK_OAK_PARTY, 1);
	}

	@Override
	public GOTMusicRegion.Sub getBiomeMusic() {
		return GOTMusicRegion.ULTHOS.getSubregion(biomeName);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.ULTHOS;
	}

	@Override
	public GOTBezierType getWallBlock() {
		return null;
	}

	@Override
	public int getWallTop() {
		return 0;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.25f;
	}

	@Override
	public boolean getEnableRiver() {
		return true;
	}
}