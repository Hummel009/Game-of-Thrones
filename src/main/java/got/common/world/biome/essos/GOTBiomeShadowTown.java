package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint;
import got.common.world.structure.essos.asshai.GOTStructureAsshaiCity;

public class GOTBiomeShadowTown extends GOTBiomeShadowLand {
	public GOTBiomeShadowTown(int i, boolean major) {
		super(i, major);
		decorator.flowersPerChunk = 0;
		GOTStructureAsshaiCity town = new GOTStructureAsshaiCity(this, 0.0f);
		town.affix(GOTWaypoint.Asshai, 2);
		decorator.affix(town);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_ASSHAI;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("shadowTown");
	}

}
