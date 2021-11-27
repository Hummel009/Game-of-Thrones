package got.common.faction;

import got.common.*;
import got.common.database.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class GOTFactionRank implements Comparable<GOTFactionRank> {
	public static GOTFactionRank RANK_NEUTRAL = new Dummy("got.faction.rank.neutral");
	public static GOTFactionRank RANK_ENEMY = new Dummy("got.faction.rank.enemy");
	public GOTFaction fac;
	public float alignment;
	public String name;
	public GOTAchievementRank rankAchievement;
	public GOTTitle rankTitle;

	public GOTFactionRank(GOTFaction f, float al, String s) {
		fac = f;
		alignment = al;
		name = s;
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

	public String getCodeFullName() {
		return getCodeName() + ".f";
	}

	public String getCodeFullNameFem() {
		return getCodeNameFem() + ".f";
	}

	public String getCodeFullNameWithGender(GOTPlayerData pd) {
		return getCodeFullName();
	}

	public String getCodeName() {
		return "got.faction." + fac.codeName() + ".rank." + name;
	}

	public String getCodeNameFem() {
		return getCodeName() + "_fm";
	}

	public String getDisplayFullName() {
		return StatCollector.translateToLocal(getCodeFullName());
	}

	public String getDisplayFullNameFem() {
		return StatCollector.translateToLocal(getCodeFullNameFem());
	}

	public String getDisplayName() {
		return StatCollector.translateToLocal(getCodeName());
	}

	public String getDisplayNameFem() {
		return StatCollector.translateToLocal(getCodeNameFem());
	}

	public String getFullNameWithGender(GOTPlayerData pd) {
		return getDisplayFullName();
	}

	public GOTAchievementRank getRankAchievement() {
		return rankAchievement;
	}

	public String getShortNameWithGender(GOTPlayerData pd) {
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

	public GOTFactionRank makeAchievement() {
		rankAchievement = new GOTAchievementRank(this);
		return this;
	}

	public GOTFactionRank makeTitle() {
		rankTitle = new GOTTitle(this);
		return this;
	}

	public GOTFactionRank setPledgeRank() {
		fac.setPledgeRank(this);
		return this;
	}

	public static class Dummy extends GOTFactionRank {
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
			return StatCollector.translateToLocal(getCodeName());
		}

		@Override
		public boolean isDummyRank() {
			return true;
		}
	}

	public static class GOTAchievementRank extends GOTAchievement {
		public GOTFactionRank theRank;
		public GOTFaction theFac;

		public GOTAchievementRank(GOTFactionRank rank) {
			super(GOTAchievement.Category.TITLES, GOTAchievement.Category.TITLES.getNextRankAchID(), GOTRegistry.gregorCleganeSword, "alignment_" + rank.fac.codeName() + "_" + rank.alignment);
			theRank = rank;
			theFac = theRank.fac;
			this.setRequiresAlly(theFac);
			setSpecial();
		}

		@Override
		public boolean canPlayerEarn(EntityPlayer entityplayer) {
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			float align = pd.getAlignment(theFac);
			if (align < 0.0f) {
				return false;
			}
			return !requiresPledge() || pd.isPledgedTo(theFac);
		}

		@Override
		public String getDescription(EntityPlayer entityplayer) {
			return StatCollector.translateToLocalFormatted("got.faction.achieveRank", GOTAlignmentValues.formatAlignForDisplay(theRank.alignment));
		}

		@Override
		public String getTitle(EntityPlayer entityplayer) {
			return theRank.getFullNameWithGender(GOTLevelData.getData(entityplayer));
		}

		public boolean isPlayerRequiredRank(EntityPlayer entityplayer) {
			GOTPlayerData pd = GOTLevelData.getData(entityplayer);
			float align = pd.getAlignment(theFac);
			float rankAlign = theRank.alignment;
			if (requiresPledge() && !pd.isPledgedTo(theFac)) {
				return false;
			}
			return align >= rankAlign;
		}

		public boolean requiresPledge() {
			return theRank.isAbovePledgeRank();
		}
	}

}
