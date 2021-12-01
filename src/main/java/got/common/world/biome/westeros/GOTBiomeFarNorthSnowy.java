package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTAchievement;
import got.common.entity.animal.GOTEntitySnowBear;
import got.common.world.biome.GOTBiome;
import got.common.world.fixed.*;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTBiomeFarNorthSnowy extends GOTBiome {
	public GOTBiomeFarNorthSnowy(int i, boolean major) {
		super(i, major);
		clearBiomeVariants();
		topBlock = Blocks.snow;
		fillerBlock = Blocks.snow;
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableGOTAmbientList.clear();
		spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(GOTEntitySnowBear.class, 8, 1, 1));
		GOTStructureHardhome home = new GOTStructureHardhome(this, 0.0f);
		home.affix(GOTWaypoint.Hardhome);
		decorator.affix(home);
		GOTStructureNightKing altar = new GOTStructureNightKing(this, 0.0f);
		altar.affix(GOTWaypoint.Aboba);
		decorator.affix(altar);
		GOTStructureWallGate wall = new GOTStructureWallGate(this, 0.0f);
		wall.affix(GOTWaypoint.CastleBlack, 0, -1);
		decorator.affix(wall);
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_FAR_NORTH;
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
		return GOTBezierType.PATH_DIRTY;
	}

	@Override
	public int getWallTop() {
		return 150;
	}

	@Override
	public int spawnCountMultiplier() {
		return 3;
	}
}