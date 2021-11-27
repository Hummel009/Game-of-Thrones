package got.common.world.biome.westeros;

import java.util.Random;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.*;
import got.common.world.feature.GOTWorldGenBoulder;
import got.common.world.map.GOTWaypoint.Region;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

public class GOTBiomeTarth extends GOTBiomeStormlandsFlat {
	public WorldGenerator chalkBoulder = new GOTWorldGenBoulder(GOTRegistry.rock, 5, 1, 3);

	public GOTBiomeTarth(int i, boolean major) {
		super(i, major);
		fillerBlock = GOTRegistry.rock;
		fillerBlockMeta = 5;
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreGem, 2), 10.0f, 0, 50);
	}

	@Override
	public void decorate(World world, Random random, int i, int k) {
		super.decorate(world, random, i, k);
		if (random.nextInt(80) == 0) {
			for (int l = 0; l < 3; ++l) {
				int i1 = i + random.nextInt(16) + 8;
				int k1 = k + random.nextInt(16) + 8;
				chalkBoulder.generate(world, random, i1, world.getHeightValue(i1, k1), k1);
			}
		}
	}

	@Override
	public GOTAchievement getBiomeAchievement() {
		return GOTAchievement.VISIT_TARTH;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("tarth");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.STORMLANDS;
	}
}