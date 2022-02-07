package got.client.sound;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

public enum GOTBiomeMusic {
	MENU("menu"), ESSOS("essos"), WESTEROS("westeros"), ULTHOS("ulthos"), SOTHORYOS("sothoryos"), OCEAN("ocean");

	private String regionName;
	private List<String> subregions = new ArrayList<>();

	GOTBiomeMusic(String s) {
		setRegionName(s);
	}

	public List<String> getAllSubregions() {
		return subregions;
	}

	public String getRegionName() {
		return regionName;
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

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public static GOTBiomeMusic forName(String s) {
		for (GOTBiomeMusic r : GOTBiomeMusic.values()) {
			if (!s.equalsIgnoreCase(r.getRegionName())) {
				continue;
			}
			return r;
		}
		return null;
	}

	public static class MusicRegion extends Pair<GOTBiomeMusic, String> {
		private GOTBiomeMusic region;
		private String subregion;

		private MusicRegion(GOTBiomeMusic r, String s) {
			setRegion(r);
			setSubregion(s);
		}

		@Override
		public GOTBiomeMusic getLeft() {
			return getRegion();
		}

		public GOTBiomeMusic getRegion() {
			return region;
		}

		@Override
		public String getRight() {
			return getSubregion();
		}

		public String getSubregion() {
			return subregion;
		}

		public void setRegion(GOTBiomeMusic region) {
			this.region = region;
		}

		public void setSubregion(String subregion) {
			this.subregion = subregion;
		}

		@Override
		public String setValue(String value) {
			throw new IllegalArgumentException("Value is");
		}
	}

}
