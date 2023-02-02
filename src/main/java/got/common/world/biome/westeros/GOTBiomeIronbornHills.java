package got.common.world.biome.westeros;

import got.client.sound.GOTBiomeMusic;
import got.client.sound.GOTBiomeMusic.MusicRegion;

public class GOTBiomeIronbornHills extends GOTBiomeIronborn {
	public GOTBiomeIronbornHills(int i, boolean major) {
		super(i, major);
		decorator.biomeOreFactor = 2.0f;
	}

	@Override
	public MusicRegion getBiomeMusic() {
		return GOTBiomeMusic.WESTEROS.getSubregion("ironbornHills");
	}
}