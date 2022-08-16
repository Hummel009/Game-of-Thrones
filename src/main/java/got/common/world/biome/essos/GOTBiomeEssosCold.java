package got.common.world.biome.essos;

import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.structure.other.*;

public abstract class GOTBiomeEssosCold extends GOTBiome {
	public GOTBiomeEssosCold(int i, boolean major) {
		super(i, major);
		setupTaigaFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.FOREST);
		addBiomeVariant(GOTBiomeVariant.FOREST_LIGHT);
		addBiomeVariant(GOTBiomeVariant.HILLS);
		addBiomeVariant(GOTBiomeVariant.HILLS_FOREST);
		addBiomeVariant(GOTBiomeVariant.FOREST_ASPEN, 0.2f);
		addBiomeVariant(GOTBiomeVariant.FOREST_BEECH, 0.2f);
		addBiomeVariant(GOTBiomeVariant.FOREST_BIRCH, 0.2f);
		addBiomeVariant(GOTBiomeVariant.FOREST_LARCH, 0.2f);
		addBiomeVariant(GOTBiomeVariant.FOREST_MAPLE, 0.2f);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.SPRUCE, 400);
		decorator.addTree(GOTTreeType.SPRUCE_THIN, 400);
		decorator.addTree(GOTTreeType.LARCH, 300);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA, 100);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA_THIN, 20);
		decorator.addTree(GOTTreeType.FIR, 500);
		decorator.addTree(GOTTreeType.PINE, 500);
		decorator.grassPerChunk = 6;
		decorator.doubleGrassPerChunk = 1;
		decorator.flowersPerChunk = 3;
		decorator.doubleFlowersPerChunk = 1;
		decorator.addRandomStructure(new GOTStructureBarrow(false), 250);
		decorator.addRandomStructure(new GOTStructureSmallStoneRuin(false), 500);
		decorator.addRandomStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addRandomStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addRandomStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}