package got.client.sound;

import java.util.ArrayList;
import java.util.List;

public class GOTTrackRegionInfo {
	private final GOTMusicRegion region;
	private final List<String> subregions = new ArrayList<>();
	private final List<GOTMusicCategory> categories = new ArrayList<>();

	private double weight;

	public GOTTrackRegionInfo(GOTMusicRegion r) {
		region = r;
		weight = 1.0;
	}

	public void addAllCategories() {
		for (GOTMusicCategory cat : GOTMusicCategory.values()) {
			addCategory(cat);
		}
	}

	public void addAllSubregions() {
		List<String> allSubs = region.getAllSubregions();
		if (!allSubs.isEmpty()) {
			for (String sub : allSubs) {
				addSubregion(sub);
			}
		}
	}

	public void addCategory(GOTMusicCategory cat) {
		if (!categories.contains(cat)) {
			categories.add(cat);
		}
	}

	public void addSubregion(String sub) {
		if (!subregions.contains(sub)) {
			subregions.add(sub);
		}
	}

	public List<GOTMusicCategory> getCategories() {
		return categories;
	}

	public List<String> getSubregions() {
		return subregions;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double d) {
		weight = d;
	}
}