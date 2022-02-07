package got.common.faction;

import got.common.*;
import got.common.database.GOTTitle;
import net.minecraft.util.StatCollector;

public class GOTFactionRank implements Comparable<GOTFactionRank> {
	private static GOTFactionRank RANK_NEUTRAL = new Dummy("got.rank.neutral");
	private static GOTFactionRank RANK_ENEMY = new Dummy("got.rank.enemy");
	private final GOTFaction fac;
	private final float alignment;
	public final String name;
	public GOTAchievementRank rankAchievement;
	public GOTTitle rankTitle;
	public GOTTitle rankTitleMasc;
	public GOTTitle rankTitleFem;
	private boolean addFacName = true;

	public GOTFactionRank(GOTFaction f, float al, String s) {
		fac = f;
		alignment = al;
		name = s;
	}

	public GOTFactionRank(GOTFaction f, float al, String s, Boolean add) {
		fac = f;
		alignment = al;
		name = s;
		setAddFacName(add);
	}

	@Override
	public int compareTo(GOTFactionRank other) {
		if (getFac() != other.getFac()) {
			throw new IllegalArgumentException("Cannot compare two ranks from different factions!");
		}
		float al1 = getAlignment();
		float al2 = other.getAlignment();
		if (al1 == al2) {
			throw new IllegalArgumentException("Two ranks cannot have the same alignment value!");
		}
		return -Float.compare(al1, al2);
	}

	public float getAlignment() {
		return alignment;
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
		if (isAddFacName()) {
			return StatCollector.translateToLocal(getCodeName()) + " " + getFacName();
		}
		return StatCollector.translateToLocal(getCodeName());
	}

	public String getDisplayFullNameFem() {
		if (isAddFacName()) {
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

	public GOTFaction getFac() {
		return fac;
	}

	public String getFacName() {
		if (getFac() != null) {
			return StatCollector.translateToLocal("got.rank." + getFac().codeName());
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
		return getAlignment() > getFac().getPledgeAlignment();
	}

	public boolean isAddFacName() {
		return addFacName;
	}

	public boolean isDummyRank() {
		return false;
	}

	public boolean isPledgeRank() {
		return this == getFac().getPledgeRank();
	}

	public GOTFactionRank makeAchievement() {
		rankAchievement = new GOTAchievementRank(this);
		return this;
	}

	public GOTFactionRank makeTitle() {
		rankTitleMasc = new GOTTitle(this, false);
		rankTitleFem = new GOTTitle(this, true);
		return this;
	}

	public void setAddFacName(boolean addFacName) {
		this.addFacName = addFacName;
	}

	public GOTFactionRank setPledgeRank() {
		getFac().setPledgeRank(this);
		return this;
	}

	public static GOTFactionRank getRankEnemy() {
		return RANK_ENEMY;
	}

	public static GOTFactionRank getRankNeutral() {
		return RANK_NEUTRAL;
	}

	public static void setRankEnemy(GOTFactionRank rANK_ENEMY) {
		RANK_ENEMY = rANK_ENEMY;
	}

	public static void setRankNeutral(GOTFactionRank rANK_NEUTRAL) {
		RANK_NEUTRAL = rANK_NEUTRAL;
	}

	private static final class Dummy extends GOTFactionRank {
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
			return StatCollector.translateToLocal(getCodeName());
		}

		@Override
		public boolean isDummyRank() {
			return true;
		}
	}

}