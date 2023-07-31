package got.client.sound;

import java.util.ArrayList;
import java.util.List;

public class GOTTrackSorter {
	public static Filter forAny() {
		return track -> true;
	}

	public static Filter forRegionAndCategory(GOTBiomeMusic reg, GOTMusicCategory cat) {
		return track -> track.getRegionInfo(reg).getCategories().contains(cat);
	}

	public static List<GOTMusicTrack> sortTracks(Iterable<GOTMusicTrack> tracks, Filter filter) {
		List<GOTMusicTrack> sorted = new ArrayList<>();
		for (GOTMusicTrack track : tracks) {
			if (filter.accept(track)) {
				sorted.add(track);
			}
		}
		return sorted;
	}

	public interface Filter {
		boolean accept(GOTMusicTrack var1);
	}

}
