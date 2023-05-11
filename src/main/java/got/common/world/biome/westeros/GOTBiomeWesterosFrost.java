package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.map.GOTBezierType;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.spawning.GOTEventSpawner;
import net.minecraft.init.Blocks;

public class GOTBiomeWesterosFrost extends GOTBiomeWesteros {
	public GOTBiomeWesterosFrost(int i, boolean major) {
		super(i, major);
		setupFrostFauna();
		clearBiomeVariants();
		addBiomeVariant(GOTBiomeVariant.HILLS);
		setUnreliableChance(GOTEventSpawner.EventChance.NEVER);
		decorator.clearTrees();
		decorator.clearRandomStructures();
		topBlock = Blocks.snow;
		fillerBlock = Blocks.snow;
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.enterFarNorth;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("farNorthSnowy");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.ICE;
	}

	@Override
	public float getChanceToSpawnAnimals() {
		return 0.1f;
	}

	@Override
	public boolean getEnableRiver() {
		return false;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_SNOWY;
	}

	@Override
	public int getWallTop() {
		return 150;
	}

}