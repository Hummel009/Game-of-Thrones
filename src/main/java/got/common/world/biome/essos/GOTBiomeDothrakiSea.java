package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.entity.animal.*;
import got.common.world.biome.GOTBiome;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.structure.essos.dothraki.GOTStructureDothrakiVillage;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeDothrakiSea extends GOTBiome {
	public GOTBiomeDothrakiSea(int i, boolean major) {
		super(i, major);
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLion.class, 4, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityLioness.class, 4, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGiraffe.class, 4, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityHorse.class, 8, 1, 2));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityRhino.class, 2, 1, 1));
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntityGemsbok.class, 8, 1, 2));
		spawnableGOTAmbientList.clear();
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityButterfly.class, 5, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityBird.class, 8, 4, 4));
		spawnableGOTAmbientList.add(new BiomeGenBase.SpawnListEntry(GOTEntityDikDik.class, 8, 1, 2));
		decorator.doubleGrassPerChunk = 256;
		decorator.clearTrees();
		GOTStructureDothrakiVillage camp = new GOTStructureDothrakiVillage(this, 1.0f);
		camp.affix(GOTWaypoint.Sathar);
		camp.affix(GOTWaypoint.Kyth);
		camp.affix(GOTWaypoint.Hornoth);
		camp.affix(GOTWaypoint.Rathylar);
		camp.affix(GOTWaypoint.VaesAthjikhari);
		camp.affix(GOTWaypoint.VaesDiaf);
		camp.affix(GOTWaypoint.VaesDothrak);
		camp.affix(GOTWaypoint.VaesEfe);
		camp.affix(GOTWaypoint.VaesGorqoyi);
		camp.affix(GOTWaypoint.VaesGraddakh);
		camp.affix(GOTWaypoint.VaesJini);
		camp.affix(GOTWaypoint.VaesKhadokh);
		camp.affix(GOTWaypoint.VaesKhewo);
		camp.affix(GOTWaypoint.VaesLeisi);
		camp.affix(GOTWaypoint.VaesLeqse);
		camp.affix(GOTWaypoint.VaesMejhah);
		decorator.affix(camp);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_DOTHRAKI_SEA;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("dothrakiSea");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.DOTHRAKI;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.2f;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}