package got.common.faction;

import got.common.*;
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
		this.fac = f;
		this.alignment = al;
		this.name = s;
	}

	public GOTFactionRank(GOTFaction f, float al, String s, Boolean add) {
		this.fac = f;
		this.alignment = al;
		this.name = s;
		addFacName = add;
	}

	@Override
	public int compareTo(GOTFactionRank other) {
		if (this.fac != other.fac) {
			throw new IllegalArgumentException("Cannot compare two ranks from different factions!");
		}
		float al1 = this.alignment;
		float al2 = other.alignment;
		if (al1 == al2) {
			throw new IllegalArgumentException("Two ranks cannot have the same alignment value!");
		}
		return -Float.valueOf(al1).compareTo(Float.valueOf(al2));
	}

	public String getCodeFullNameWithGender(GOTPlayerData pd) {
		if (pd.useFeminineRanks()) {
			return this.getCodeNameFem();
		}
		return this.getCodeName();
	}

	public String getCodeName() {
		return "got.rank." + this.name;
	}

	public String getCodeNameFem() {
		return this.getCodeName() + "_fm";
	}

	public String getDisplayFullName() {
		if (addFacName) {
			return StatCollector.translateToLocal(this.getCodeName()) + " " + getFacName();
		} else {
			return StatCollector.translateToLocal(this.getCodeName());
		}
	}

	public String getFacName() {
		if (this.fac != null) {
			return StatCollector.translateToLocal("got.rank." + this.fac.codeName());
		}
		return "";
	}

	public String getDisplayFullNameFem() {
		if (addFacName) {
			return StatCollector.translateToLocal(this.getCodeNameFem()) + " " + getFacName();
		} else {
			return StatCollector.translateToLocal(this.getCodeNameFem());
		}
	}

	public String getDisplayName() {
		return StatCollector.translateToLocal(this.getCodeName());
	}

	public String getDisplayNameFem() {
		return StatCollector.translateToLocal((String) this.getCodeNameFem());
	}

	public String getFullNameWithGender(GOTPlayerData pd) {
		if (pd.useFeminineRanks()) {
			return this.getDisplayFullNameFem();
		}
		return this.getDisplayFullName();
	}

	public GOTAchievementRank getRankAchievement() {
		return this.rankAchievement;
	}

	public String getShortNameWithGender(GOTPlayerData pd) {
		if (pd.useFeminineRanks()) {
			return this.getDisplayNameFem();
		}
		return this.getDisplayName();
	}

	public boolean isAbovePledgeRank() {
		return this.alignment > this.fac.getPledgeAlignment();
	}

	public boolean isDummyRank() {
		return false;
	}

	public boolean isPledgeRank() {
		return this == this.fac.getPledgeRank();
	}

	public GOTFactionRank makeAchievement() {
		this.rankAchievement = new GOTAchievementRank(this);
		return this;
	}

	public GOTFactionRank makeTitle() {
		this.rankTitleMasc = new GOTTitle(this, false);
		this.rankTitleFem = new GOTTitle(this, true);
		return this;
	}

	public GOTFactionRank setPledgeRank() {
		this.fac.setPledgeRank(this);
		return this;
	}

	public static final class Dummy extends GOTFactionRank {
		public Dummy(String s) {
			super(null, 0.0f, s);
		}

		@Override
		public String getCodeName() {
			return this.name;
		}

		@Override
		public String getDisplayFullName() {
			return this.getDisplayName();
		}

		@Override
		public String getDisplayName() {
			return StatCollector.translateToLocal((String) this.getCodeName());
		}

		@Override
		public boolean isDummyRank() {
			return true;
		}
	}

}