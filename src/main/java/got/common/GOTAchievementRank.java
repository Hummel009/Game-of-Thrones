package got.common;

import got.common.database.*;
import got.common.faction.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class GOTAchievementRank extends GOTAchievement {
	private GOTFactionRank theRank;
	private GOTFaction theFac;

	public GOTAchievementRank(GOTFactionRank rank) {
		super(GOTAchievement.Category.TITLES, GOTAchievement.Category.TITLES.getNextRankAchID(), GOTRegistry.gregorCleganeSword, "alignment_" + rank.getFac().codeName() + "_" + rank.getAlignment());
		theRank = rank;
		theFac = theRank.getFac();
		setRequiresAlly(theFac);
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
		return StatCollector.translateToLocalFormatted("got.faction.achieveRank", GOTAlignmentValues.formatAlignForDisplay(theRank.getAlignment()));
	}

	@Override
	public String getTitle(EntityPlayer entityplayer) {
		return theRank.getFullNameWithGender(GOTLevelData.getData(entityplayer));
	}

	@Override
	public String getUntranslatedTitle(EntityPlayer entityplayer) {
		return theRank.getCodeFullNameWithGender(GOTLevelData.getData(entityplayer));
	}

	public boolean isPlayerRequiredRank(EntityPlayer entityplayer) {
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		float align = pd.getAlignment(theFac);
		float rankAlign = theRank.getAlignment();
		if (requiresPledge() && !pd.isPledgedTo(theFac)) {
			return false;
		}
		return align >= rankAlign;
	}

	private boolean requiresPledge() {
		return false;
	}
}
