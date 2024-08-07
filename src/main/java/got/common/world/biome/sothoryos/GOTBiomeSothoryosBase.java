package got.common.world.biome.sothoryos;

import got.client.sound.GOTMusicRegion;
import got.common.database.GOTBlocks;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.world.gen.feature.WorldGenMinable;

public abstract class GOTBiomeSothoryosBase extends GOTBiome {
	protected GOTBiomeSothoryosBase(int i, boolean major) {
		super(i, major);
		decorator.setBiomeGemFactor(2.0f);
		decorator.setBiomeOreFactor(2.0f);
		decorator.addSoil(new WorldGenMinable(GOTBlocks.obsidianGravel, 32), 20.0f, 0, 64);
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.SOTHORYOS;
	}

	@Override
	public GOTMusicRegion.Sub getBiomeMusic() {
		return GOTMusicRegion.SOTHORYOS.getSubregion(biomeName);
	}

	@Override
	public GOTEventSpawner.EventChance getBanditChance() {
		return GOTEventSpawner.EventChance.NEVER;
	}
}