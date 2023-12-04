package got.common;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.faction.GOTAlignmentValues;
import got.common.faction.GOTFaction;
import got.common.faction.GOTFactionRank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.*;

public class GOTAchievementRank extends GOTAchievement {
	public GOTFactionRank theRank;
	public GOTFaction theFac;

	public GOTAchievementRank(GOTFactionRank rank) {
		super(GOTAchievement.Category.TITLES, GOTAchievement.Category.TITLES.getNextRankAchID(), GOTItems.gregorCleganeSword, "alignment_" + rank.fac.codeName() + '_' + rank.alignment);
		theRank = rank;
		theFac = theRank.fac;
		setRequiresAlly(theFac);
		setSpecial();
	}

	@Override
	public boolean canPlayerEarn(EntityPlayer entityplayer) {
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		float align = pd.getAlignment(theFac);
		return !(align < 0.0f) && (!requiresPledge() || pd.isPledgedTo(theFac));
	}

	@Override
	public IChatComponent getAchievementChatComponent(EntityPlayer entityplayer) {
		GOTPlayerData pd = GOTLevelData.getData(entityplayer);
		IChatComponent component = new ChatComponentTranslation(theRank.getCodeFullNameWithGender(pd)).appendText(" ").appendSibling(new ChatComponentTranslation(theRank.getAffiliationCodeName())).createCopy();
		component.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		component.getChatStyle().setChatHoverEvent(new HoverEvent(GOTChatEvents.SHOW_GOT_ACHIEVEMENT, new ChatComponentText(category.name() + '$' + ID)));
		return component;
	}

	@Override
	public String getDescription(EntityPlayer entityplayer) {
		return StatCollector.translateToLocalFormatted("got.faction.achieveRank", GOTAlignmentValues.formatAlignForDisplay(theRank.alignment));
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
		float rankAlign = theRank.alignment;
		return (!requiresPledge() || pd.isPledgedTo(theFac)) && align >= rankAlign;
	}

	public boolean requiresPledge() {
		return false;
	}
}
