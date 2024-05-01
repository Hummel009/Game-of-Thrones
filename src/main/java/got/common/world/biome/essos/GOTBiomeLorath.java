package got.common.world.biome.essos;

import got.common.database.GOTAchievement;
import got.common.database.GOTInvasions;
import got.common.database.GOTSpawnList;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.feature.GOTWorldGenBoulder;
import got.common.world.map.GOTWaypoint;
import got.common.world.spawning.GOTBiomeSpawnList;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnListContainer;
import got.common.world.structure.essos.lorath.GOTStructureLorathFortress;
import got.common.world.structure.essos.lorath.GOTStructureLorathSettlement;
import got.common.world.structure.other.GOTStructureSmallStoneRuin;
import got.common.world.structure.other.GOTStructureStoneRuin;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GOTBiomeLorath extends GOTBiomeEssos {
	private static final WorldGenerator BOULDER_GEN = new GOTWorldGenBoulder(Blocks.stone, 0, 2, 4);

	public GOTBiomeLorath(int i, boolean major) {
		super(i, major);
		setupStandardPlainsFauna();
		biomeVariants.add(GOTBiomeVariant.ORCHARD_ORANGE, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_LEMON, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_LIME, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_OLIVE, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_ALMOND, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_PLUM, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_DATE, 0.2f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_APPLE_PEAR, 0.1f);
		biomeVariants.add(GOTBiomeVariant.ORCHARD_POMEGRANATE, 0.3f);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.BEECH_PARTY, 2);
		decorator.addTree(GOTTreeType.BEECH, 50);
		decorator.addTree(GOTTreeType.BEECH_LARGE, 20);
		decorator.addTree(GOTTreeType.SPRUCE, 400);
		decorator.addTree(GOTTreeType.SPRUCE_THIN, 200);
		decorator.addTree(GOTTreeType.OAK, 200);
		decorator.addTree(GOTTreeType.OAK_TALLER, 5);
		decorator.addTree(GOTTreeType.OAK_LARGE, 30);
		decorator.addTree(GOTTreeType.LARCH, 300);
		decorator.addTree(GOTTreeType.MAPLE, 100);
		decorator.addTree(GOTTreeType.MAPLE_LARGE, 10);
		decorator.addTree(GOTTreeType.MAPLE_PARTY, 2);
		decorator.addSettlement(new GOTStructureLorathSettlement(this, 1.0f));
		decorator.addStructure(new GOTStructureLorathFortress(false), 800);
		invasionSpawns.addInvasion(GOTInvasions.IBBEN, GOTEventSpawner.EventChance.UNCOMMON);
		Collection<GOTSpawnListContainer> c0 = new ArrayList<>();
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.LORATH_CONQUEST, 4).setSpawnChance(SPAWN));
		c0.add(GOTBiomeSpawnList.entry(GOTSpawnList.LORATH_MILITARY, 10).setSpawnChance(SPAWN));
		npcSpawnList.newFactionList(10).add(c0);
		Collection<GOTSpawnListContainer> c1 = new ArrayList<>();
		c1.add(GOTBiomeSpawnList.entry(GOTSpawnList.IBBEN_MILITARY, 10).setSpawnChance(CONQUEST_SPAWN));
		npcSpawnList.newFactionList(0).add(c1);
		decorator.addStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
		decorator.addStructure(new GOTStructureSmallStoneRuin(false), 500);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		super.decorate(world, random, i, k);
		if (random.nextInt(32) == 0) {
			for (int l = 0; l < 3; ++l) {
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				BOULDER_GEN.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterLorath;
	}

	@Override
	public GOTWaypoint.Region getBiomeWaypoints() {
		return GOTWaypoint.Region.LORATH;
	}
}