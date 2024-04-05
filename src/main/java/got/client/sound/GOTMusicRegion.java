package got.client.sound;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public enum GOTMusicRegion {
	MENU("menu"), ESSOS("essos"), WESTEROS("westeros"), ULTHOS("ulthos"), SOTHORYOS("sothoryos"), OCEAN("ocean");

	private final String regionName;
	private final List<String> subregions = new ArrayList<>();

	GOTMusicRegion(String s) {
		regionName = s;
	}

	public static GOTMusicRegion forName(String s) {
		for (GOTMusicRegion r : values()) {
			if (!s.equalsIgnoreCase(r.regionName)) {
				continue;
			}
			return r;
		}
		return null;
	}

	public Sub getSubregion(String s) {
		if (s != null && !subregions.contains(s)) {
			subregions.add(s);
		}
		return new Sub(this, s);
	}

	public Sub getWithoutSub() {
		return new Sub(this, null);
	}

	public boolean hasSubregion(String s) {
		return subregions.contains(s);
	}

	public List<String> getAllSubregions() {
		return subregions;
	}

	public boolean hasNoSubregions() {
		return subregions.isEmpty();
	}

	public String getRegionName() {
		return regionName;
	}

	public static class Sub extends Pair<GOTMusicRegion, String> {
		private final GOTMusicRegion region;
		private final String subregion;

		protected Sub(GOTMusicRegion r, String s) {
			region = r;
			subregion = s;
		}

		@Override
		public String setValue(String value) {
			throw new IllegalArgumentException("Value is final");
		}

		@Override
		public GOTMusicRegion getLeft() {
			return region;
		}

		@Override
		public String getRight() {
			return subregion;
		}

		public GOTMusicRegion getRegion() {
			return region;
		}

		public String getSubregion() {
			return subregion;
		}
	}
}
