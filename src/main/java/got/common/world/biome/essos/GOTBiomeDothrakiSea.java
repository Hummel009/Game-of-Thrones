package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.structure.essos.dothraki.GOTStructureDothrakiVillage;

public class GOTBiomeDothrakiSea extends GOTBiomeEssosPlains {
	public GOTBiomeDothrakiSea(int i, boolean major) {
		super(i, major);
		decorator.addVillage(new GOTStructureDothrakiVillage(this, 1.0f));
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterDothrakiSea;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("dothrakiSea");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.DOTHRAKI;
	}
}