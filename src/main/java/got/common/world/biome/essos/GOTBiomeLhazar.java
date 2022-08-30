package got.common.world.biome.essos;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.structure.essos.lhazar.GOTStructureLhazarVillage;

public class GOTBiomeLhazar extends GOTBiomeEssosPlains {
	public GOTBiomeLhazar(int i, boolean major) {
		super(i, major);
		decorator.addVillage(new GOTStructureLhazarVillage(this, 1.0f));
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterLhazar;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.ESSOS.getSubregion("lhazar");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.LHAZAR;
	}
}