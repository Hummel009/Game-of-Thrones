package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;
import got.common.database.GOTRegistry;
import got.common.world.map.*;
import got.common.world.map.GOTWaypoint.Region;
import got.common.world.structure.westeros.ironborn.GOTStructureIronbornCity;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class GOTBiomeIronbornHills extends GOTBiomeIronborn {
	public GOTBiomeIronbornHills(int i, boolean major) {
		super(i, major);
		decorator.clearOres();
		decorator.addOre(new WorldGenMinable(Blocks.iron_ore, 8), 8.0f, 0, 255);
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreTin, 8), 8.0f, 0, 255);
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreCopper, 8), 8.0f, 0, 255);
		decorator.addOre(new WorldGenMinable(GOTRegistry.oreCobalt, 5), 5.0f, 0, 32);
		GOTStructureIronbornCity castle = new GOTStructureIronbornCity(this, 0.0f).setIsCastle();
		castle.affix(GOTWaypoint.Hammerhorn);
		decorator.affix(castle);
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("ironbornHills");
	}

	@Override
	public Region getBiomeWaypoints() {
		return Region.IRONBORN;
	}

	@Override
	public GOTBezierType getRoadBlock() {
		return GOTBezierType.PATH_DIRTY;
	}
}
