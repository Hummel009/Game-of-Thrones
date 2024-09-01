package got.common.faction;

import got.common.GOTAchievementRank;
import got.common.GOTPlayerData;
import got.common.database.GOTTitle;
import net.minecraft.util.StatCollector;

/**
 * Note: this class has a natural ordering that is inconsistent with equals.
 */
public class GOTFactionRank implements Comparable<GOTFactionRank> {
	public static final GOTFactionRank RANK_NEUTRAL = new Dummy("got.rank.neutral");
	public static final GOTFactionRank RANK_ENEMY = new Dummy("got.rank.enemy");

	protected final String name;

	private final GOTFaction faction;
	private final float reputation;

	private GOTAchievementRank rankAchievement;
	private boolean addFacName = true;

	public GOTFactionRank(GOTFaction f, float al, String s) {
		faction = f;
		reputation = al;
		name = s;
	}

	public GOTFactionRank(GOTFaction f, float al, String s, Boolean add) {
		faction = f;
		reputation = al;
		name = s;
		addFacName = add;
	}

	@Override
	public int compareTo(GOTFactionRank other) {
		if (faction != other.faction) {
			throw new IllegalArgumentException("Cannot compare two ranks from different factions!");
		}
		float al1 = reputation;
		float al2 = other.reputation;
		if (al1 == al2) {
			throw new IllegalArgumentException("Two ranks cannot have the same reputation value!");
		}
		return -Float.compare(al1, al2);
	}

	public String getAffiliationCodeName() {
		if (faction != null) {
			return "got.rank." + faction.codeName();
		}
		return "";
	}

	public String getCodeFullNameWithGender(GOTPlayerData pd) {
		if (pd.getFeminineRanks()) {
			return getCodeNameFem();
		}
		return getCodeName();
	}

	public String getCodeName() {
		return "got.rank." + name;
	}

	public String getCodeNameFem() {
		return getCodeName() + "_fm";
	}

	public String getDisplayFullName() {
		if (addFacName) {
			return StatCollector.translateToLocal(getCodeName()) + ' ' + StatCollector.translateToLocal(getAffiliationCodeName());
		}
		return StatCollector.translateToLocal(getCodeName());
	}

	public String getDisplayFullNameFem() {
		if (addFacName) {
			return StatCollector.translateToLocal(getCodeNameFem()) + ' ' + StatCollector.translateToLocal(getAffiliationCodeName());
		}
		return StatCollector.translateToLocal(getCodeNameFem());
	}

	public String getDisplayName() {
		return StatCollector.translateToLocal(getCodeName());
	}

	@SuppressWarnings("WeakerAccess")
	public String getDisplayNameFem() {
		return StatCollector.translateToLocal(getCodeNameFem());
	}

	public String getFullNameWithGender(GOTPlayerData pd) {
		if (pd.getFeminineRanks()) {
			return getDisplayFullNameFem();
		}
		return getDisplayFullName();
	}

	public GOTAchievementRank getRankAchievement() {
		return rankAchievement;
	}

	public String getShortNameWithGender(GOTPlayerData pd) {
		if (pd.getFeminineRanks()) {
			return getDisplayNameFem();
		}
		return getDisplayName();
	}

	public boolean isAbovePledgeRank() {
		return reputation > faction.getPledgeReputation();
	}

	public boolean isDummyRank() {
		return false;
	}

	public boolean isPledgeRank() {
		return this == faction.getPledgeRank();
	}

	public void makeAchievement() {
		rankAchievement = new GOTAchievementRank(this);
	}

	@SuppressWarnings("ResultOfObjectAllocationIgnored")
	public GOTFactionRank makeTitle() {
		new GOTTitle(this, false);
		new GOTTitle(this, true);
		return this;
	}

	public GOTFactionRank setPledgeRank() {
		faction.setPledgeRank(this);
		return this;
	}

	public boolean isAddFacName() {
		return addFacName;
	}

	public float getReputation() {
		return reputation;
	}

	public GOTFaction getFaction() {
		return faction;
	}

	public static class Dummy extends GOTFactionRank {
		private Dummy(String s) {
			super(null, 0.0f, s);
		}

		@Override
		public String getCodeName() {
			return name;
		}

		@Override
		public String getDisplayFullName() {
			return getDisplayName();
		}

		@Override
		public String getDisplayName() {
			return StatCollector.translateToLocal(name);
		}

		@Override
		public boolean isDummyRank() {
			return true;
		}
	}
}