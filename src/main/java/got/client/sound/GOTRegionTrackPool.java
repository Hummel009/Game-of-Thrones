package got.client.sound;

import java.util.*;

public class GOTRegionTrackPool {
	public GOTBiomeMusic region;
	public String subregion;
	public List<GOTMusicTrack> trackList = new ArrayList<>();

	public GOTRegionTrackPool(GOTBiomeMusic r, String s) {
		region = r;
		subregion = s;
	}

	public void addTrack(GOTMusicTrack track) {
		trackList.add(track);
	}

	public GOTMusicTrack getRandomTrack(Random rand, GOTTrackSorter.Filter filter) {
		List<GOTMusicTrack> sortedTracks = GOTTrackSorter.sortTracks(trackList, filter);
		double totalWeight = 0.0;
		for (GOTMusicTrack track : sortedTracks) {
			double weight2 = track.getRegionInfo(region).getWeight();
			totalWeight += weight2;
		}
		double randWeight = rand.nextDouble();
		randWeight *= totalWeight;
		Iterator<GOTMusicTrack> it = sortedTracks.iterator();
		GOTMusicTrack track = null;
		do {
			if (it.hasNext()) {
				continue;
			}
			return track;
		} while ((randWeight -= (track = it.next()).getRegionInfo(region).getWeight()) >= 0.0);
		return track;
	}

	public boolean isEmpty() {
		return trackList.isEmpty();
	}
}
