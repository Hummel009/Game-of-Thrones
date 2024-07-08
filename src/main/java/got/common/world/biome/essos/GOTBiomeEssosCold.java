package got.common.world.biome.essos;

import got.client.sound.GOTMusicRegion;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.feature.GOTTreeType;
import got.common.world.map.GOTBezierType;
import got.common.world.structure.other.*;

public abstract class GOTBiomeEssosCold extends GOTBiome {
	protected GOTBiomeEssosCold(int i, boolean major) {
		super(i, major);
		setupTaigaFauna();
		biomeVariants.clear();
		biomeVariants.add(GOTBiomeVariant.FOREST, 1.0f);
		biomeVariants.add(GOTBiomeVariant.FOREST_LIGHT, 1.0f);
		biomeVariants.add(GOTBiomeVariant.HILLS, 1.0f);
		biomeVariants.add(GOTBiomeVariant.HILLS_FOREST, 1.0f);
		biomeVariants.add(GOTBiomeVariant.FOREST_ASPEN, 0.2f);
		biomeVariants.add(GOTBiomeVariant.FOREST_BEECH, 0.2f);
		biomeVariants.add(GOTBiomeVariant.FOREST_BIRCH, 0.2f);
		biomeVariants.add(GOTBiomeVariant.FOREST_LARCH, 0.2f);
		biomeVariants.add(GOTBiomeVariant.FOREST_MAPLE, 0.2f);
		decorator.clearTrees();
		decorator.addTree(GOTTreeType.SPRUCE, 400);
		decorator.addTree(GOTTreeType.SPRUCE_THIN, 400);
		decorator.addTree(GOTTreeType.LARCH, 300);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA, 100);
		decorator.addTree(GOTTreeType.SPRUCE_MEGA_THIN, 20);
		decorator.addTree(GOTTreeType.FIR, 500);
		decorator.addTree(GOTTreeType.PINE, 500);
		decorator.setGrassPerChunk(6);
		decorator.setDoubleGrassPerChunk(1);
		decorator.setFlowersPerChunk(3);
		decorator.setDoubleFlowersPerChunk(1);
		decorator.addStructure(new GOTStructureBarrow(false), 150);
		decorator.addStructure(new GOTStructureSmallStoneRuin(false), 500);
		decorator.addStructure(new GOTStructureRuinedHouse(false), 2000);
		decorator.addStructure(new GOTStructureBurntHouse(false), 2000);
		decorator.addStructure(new GOTStructureRottenHouse(false), 4000);
		decorator.addStructure(new GOTStructureStoneRuin.RuinStone(1, 4), 400);
	}

	@Override
	public GOTMusicRegion.Sub getBiomeMusic() {
		return GOTMusicRegion.ESSOS.getSubregion(biomeName);
	}

	@Override
	public GOTBezierType getWallBlock() {
		return null;
	}

	@Override
	public int getWallTop() {
		return 0;
	}
}