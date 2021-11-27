package got.client.sound;

import java.util.*;

public class GOTTrackSorter {
	public static Filter forAny() {
		return new Filter() {

			@Override
			public boolean accept(GOTMusicTrack track) {
				return true;
			}
		};
	}

	public static Filter forRegionAndCategory(GOTBiomeMusic reg, GOTMusicCategory cat) {
		return new Filter() {

			@Override
			public boolean accept(GOTMusicTrack track) {
				return track.getRegionInfo(reg).getCategories().contains(cat);
			}
		};
	}

	public static List<GOTMusicTrack> sortTracks(List<GOTMusicTrack> tracks, Filter filter) {
		ArrayList<GOTMusicTrack> sorted = new ArrayList<>();
		for (GOTMusicTrack track : tracks) {
			if (!filter.accept(track)) {
				continue;
			}
			sorted.add(track);
		}
		return sorted;
	}

	public interface Filter {
		boolean accept(GOTMusicTrack var1);
	}

}
