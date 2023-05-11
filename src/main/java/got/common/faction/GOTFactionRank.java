package got.common.faction;

import got.common.GOTAchievementRank;
import got.common.GOTPlayerData;
import got.common.database.GOTTitle;
import net.minecraft.util.StatCollector;

public class GOTFactionRank implements Comparable<GOTFactionRank> {
	public static final GOTFactionRank RANK_NEUTRAL = new Dummy("got.rank.neutral");
	public static final GOTFactionRank RANK_ENEMY = new Dummy("got.rank.enemy");
	public final GOTFaction fac;
	public final float alignment;
	public final String name;
	public GOTAchievementRank rankAchievement;
	public GOTTitle rankTitle;
	public GOTTitle rankTitleMasc;
	public GOTTitle rankTitleFem;
	public boolean addFacName = true;

	public GOTFactionRank(GOTFaction f, float al, String s) {
		fac = f;
		alignment = al;
		name = s;
	}

	public GOTFactionRank(GOTFaction f, float al, String s, Boolean add) {
		fac = f;
		alignment = al;
		name = s;
		addFacName = add;
	}

	@Override
	public int compareTo(GOTFactionRank other) {
		if (fac != other.fac) {
			throw new IllegalArgumentException("Cannot compare two ranks from different factions!");
		}
		float al1 = alignment;
		float al2 = other.alignment;
		if (al1 == al2) {
			throw new IllegalArgumentException("Two ranks cannot have the same alignment value!");
		}
		return -Float.compare(al1, al2);
	}

	public String getCodeFullNameWithGender(GOTPlayerData pd) {
		if (pd.useFeminineRanks()) {
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
			return StatCollector.translateToLocal(getCodeName()) + " " + getFacName();
		}
		return StatCollector.translateToLocal(getCodeName());
	}

	public String getDisplayFullNameFem() {
		if (addFacName) {
			return StatCollector.translateToLocal(getCodeNameFem()) + " " + getFacName();
		}
		return StatCollector.translateToLocal(getCodeNameFem());
	}

	public String getDisplayName() {
		return StatCollector.translateToLocal(getCodeName());
	}

	public String getDisplayNameFem() {
		return StatCollector.translateToLocal(getCodeNameFem());
	}

	public String getFacName() {
		if (fac != null) {
			return StatCollector.translateToLocal("got.rank." + fac.codeName());
		}
		return "";
	}

	public String getFullNameWithGender(GOTPlayerData pd) {
		if (pd.useFeminineRanks()) {
			return getDisplayFullNameFem();
		}
		return getDisplayFullName();
	}

	public GOTAchievementRank getRankAchievement() {
		return rankAchievement;
	}

	public String getShortNameWithGender(GOTPlayerData pd) {
		if (pd.useFeminineRanks()) {
			return getDisplayNameFem();
		}
		return getDisplayName();
	}

	public boolean isAbovePledgeRank() {
		return alignment > fac.getPledgeAlignment();
	}

	public boolean isDummyRank() {
		return false;
	}

	public boolean isPledgeRank() {
		return this == fac.getPledgeRank();
	}

	public void makeAchievement() {
		rankAchievement = new GOTAchievementRank(this);
	}

	public GOTFactionRank makeTitle() {
		rankTitleMasc = new GOTTitle(this, false);
		rankTitleFem = new GOTTitle(this, true);
		return this;
	}

	public GOTFactionRank setPledgeRank() {
		fac.setPledgeRank(this);
		return this;
	}

	public static final class Dummy extends GOTFactionRank {
		public Dummy(String s) {
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