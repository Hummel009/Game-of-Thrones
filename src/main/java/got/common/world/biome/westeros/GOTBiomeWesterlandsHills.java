package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.database.GOTRegistry;
import got.common.entity.animal.GOTEntityLion;
import got.common.entity.animal.GOTEntityLioness;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomeWesterlandsHills extends GOTBiomeWesterlands {
	public GOTBiomeWesterlandsHills(int i, boolean major) {
		super(i, major);
		decorator.biomeOreFactor = 2.0f;
		decorator.biomeGemFactor = 2.0f;
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreGlowstone, 4), 8.0f, 0, 48);
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreCobalt, 5), 5.0f, 0, 32);
		decorator.clearVillages();
		npcSpawnList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLion.class, 10, 1, 1));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLioness.class, 10, 1, 1));
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterWesterlandsHills;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("westerlandsHills");
	}
}