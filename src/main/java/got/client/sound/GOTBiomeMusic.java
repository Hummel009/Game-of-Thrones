package got.client.sound;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public enum GOTBiomeMusic {
	MENU("menu"), ESSOS("essos"), WESTEROS("westeros"), ULTHOS("ulthos"), SOTHORYOS("sothoryos"), OCEAN("ocean");

	public static String allRegionCode = "all";
	public String regionName;
	public List<String> subregions = new ArrayList<>();

	GOTBiomeMusic(String s) {
		regionName = s;
	}

	public static GOTBiomeMusic forName(String s) {
		for (GOTBiomeMusic r : GOTBiomeMusic.values()) {
			if (s.equalsIgnoreCase(r.regionName)) {
				return r;
			}
		}
		return null;
	}

	public List<String> getAllSubregions() {
		return subregions;
	}

	public MusicRegion getSubregion(String s) {
		if (s != null && !subregions.contains(s)) {
			subregions.add(s);
		}
		return new MusicRegion(this, s);
	}

	public MusicRegion getWithoutSub() {
		return new MusicRegion(this, null);
	}

	public boolean hasNoSubregions() {
		return subregions.isEmpty();
	}

	public boolean hasSubregion(String s) {
		return subregions.contains(s);
	}

	public static class MusicRegion extends Pair<GOTBiomeMusic, String> {
		public GOTBiomeMusic region;
		public String subregion;

		public MusicRegion(GOTBiomeMusic r, String s) {
			region = r;
			subregion = s;
		}

		@Override
		public GOTBiomeMusic getLeft() {
			return region;
		}

		@Override
		public String getRight() {
			return subregion;
		}

		@Override
		public String setValue(String value) {
			throw new IllegalArgumentException("Value is");
		}
	}

}
