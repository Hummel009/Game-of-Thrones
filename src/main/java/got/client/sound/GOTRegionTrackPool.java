package got.client.sound;

import java.util.*;

public class GOTRegionTrackPool {
	private final GOTMusicRegion region;
	private final Collection<GOTMusicTrack> trackList = new ArrayList<>();

	public GOTRegionTrackPool(GOTMusicRegion r) {
		region = r;
	}

	public void addTrack(GOTMusicTrack track) {
		trackList.add(track);
	}

	public boolean isEmpty() {
		return trackList.isEmpty();
	}

	public GOTMusicTrack getRandomTrack(Random rand, GOTTrackSorter.Filter filter) {
		List<GOTMusicTrack> sortedTracks = GOTTrackSorter.sortTracks(trackList, filter);
		double totalWeight = 0.0;
		for (GOTMusicTrack gotMusicTrack : sortedTracks) {
			double weight = gotMusicTrack.getRegionInfo(region).getWeight();
			totalWeight += weight;
		}
		double randWeight = rand.nextDouble();
		randWeight *= totalWeight;
		Iterator<GOTMusicTrack> it = sortedTracks.iterator();
		GOTMusicTrack track = null;
		do {
			if (!it.hasNext()) {
				return track;
			}
			track = it.next();
			double weight = track.getRegionInfo(region).getWeight();
			randWeight -= weight;
		} while (randWeight >= 0.0D);
		return track;
	}
}

