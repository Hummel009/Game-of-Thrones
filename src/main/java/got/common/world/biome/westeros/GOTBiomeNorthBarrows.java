package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.structure.other.GOTStructureBarrow;
import got.common.world.structure.westeros.north.GOTStructureNorthCity;

public class GOTBiomeNorthBarrows extends GOTBiomeNorth {
	public GOTBiomeNorthBarrows(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		this.addBiomeVariant(GOTBiomeVariant.DENSEFOREST_BIRCH);
		this.addBiomeVariant(GOTBiomeVariant.DENSEFOREST_OAK);
		this.addBiomeVariant(GOTBiomeVariant.FLOWERS);
		this.addBiomeVariant(GOTBiomeVariant.FOREST);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_ASPEN, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_BEECH, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_BIRCH, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_LIGHT);
		this.addBiomeVariant(GOTBiomeVariant.FOREST_MAPLE, 0.2f);
		this.addBiomeVariant(GOTBiomeVariant.HILLS);
		this.addBiomeVariant(GOTBiomeVariant.HILLS_FOREST);
		this.addBiomeVariant(GOTBiomeVariant.HILLS_SCRUBLAND, 1.0f);
		this.addBiomeVariant(GOTBiomeVariant.SCRUBLAND, 1.0f);
		this.addBiomeVariant(GOTBiomeVariant.STEPPE);
		decorator.addRandomStructure(new GOTStructureBarrow(false), 20);
		GOTStructureNorthCity castle = new GOTStructureNorthCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Goldgrass, 0, 1);
		decorator.addVillage(castle);
		GOTStructureNorthCity town = new GOTStructureNorthCity(this, 0.0f).setIsSmallTown();
		town.affix(GOTWaypoint.Barrowtown, 0, 1, 2);
		decorator.addVillage(town);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_NORTH_BARROWS;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("northBarrows");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.NORTH;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}
