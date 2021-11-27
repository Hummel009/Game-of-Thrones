package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.biome.GOTBiome;
import got.common.world.feature.GOTWorldGenBoulder;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTBiomeSpawnList.SpawnListContainer;
import got.common.world.structure.other.GOTStructureRuins;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GOTBiomeIronborn extends GOTBiomeWesteros {
	public WorldGenerator boulderGen = new GOTWorldGenBoulder(GOTRegistry.rock, 1, 1, 4);

	public GOTBiomeIronborn(int i, boolean major) {
		super(i, major);
		SpawnListContainer[] container1 = new SpawnListContainer[1];
		container1[0] = GOTBiomeSpawnList.entry(GOTSpawnList.NORTH_CONQUEST, 10).setSpawnChance(GOTBiome.CONQUEST_SPAWN);
		npcSpawnList.newFactionList(0).add(container1);
		GOTStructureRuins ruinsGen = new GOTStructureRuins(this, 0.0f);
		ruinsGen.affix(GOTWaypoint.GreyironCastle);
		ruinsGen.affix(GOTWaypoint.HoareKeep);
		ruinsGen.affix(GOTWaypoint.HoareCastle);
		decorator.addVillage(ruinsGen);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_IRONBORN;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("ironborn");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.IRONBORN;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}
