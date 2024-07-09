package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.essos.pentos.GOTStructurePentosFortress;
import got.common.world.structure.essos.pentos.GOTStructurePentosSettlement;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTBiomePentos extends GOTBiomeEssosBase {
	public GOTBiomePentos(int i, boolean major) {
		super(i, major);
		preseter.setupSouthernPlainsView(false);
		preseter.setupSouthernPlainsFlora();
		preseter.setupSouthernPlainsFauna(false);
		preseter.setupSouthernTrees(false);

		setupRuinedStructures(true);

		decorator.addSettlement(new GOTStructurePentosSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructurePentosFortress(false), 800);

		invasionSpawns.addInvasion(GOTInvasions.BRAAVOS, GOTEventSpawner.EventChance.UNCOMMON);

		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.PENTOS_CONQUEST, 4).setSpawnChance(SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.PENTOS_MILITARY, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.BRAAVOS_CONQUEST, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);

		biomeWaypoints = GOTWaypoint.Region.PENTOS;
		biomeAchievement = GOTAchievement.enterPentos;
		roadBlock = GOTBezierType.PATH_SANDY;
	}

	@Override
	public void generateBiomeTerrain(World world, Random random, Block[] blocks, byte[] meta, int i, int k, double stoneNoise, int height, GOTBiomeVariant variant) {
		generator.generateDirtSandRedSandNoise(world, random, blocks, meta, i, k, stoneNoise, height, variant);
	}
}