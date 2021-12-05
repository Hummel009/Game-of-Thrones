package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeWolfswood extends GOTBiomeNorthForest {
	public GOTBiomeWolfswood(int i, boolean major) {
		super(i, major);
		decorator.treesPerChunk = 6;
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 30, 4, 8));
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("wolfswood");
	}
}