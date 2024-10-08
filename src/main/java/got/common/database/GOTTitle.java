package got.common.database;

import got.GOT;
import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.faction.GOTFaction;
import got.common.faction.GOTFactionRank;
import got.common.faction.GOTReputationValues;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

import java.util.*;

public class GOTTitle {
	public static final Collection<GOTTitle> CONTENT = new ArrayList<>();

	private static int nextTitleID;

	private final String name;
	private final int titleID;

	private TitleType titleType = TitleType.STARTER;
	private GOTAchievement titleAchievement;
	private GOTFactionRank titleRank;
	private UUID[] uuids;

	private boolean isHidden;
	private boolean isFeminineRank;
	private boolean useAchievementName;

	@SuppressWarnings("WeakerAccess")
	public GOTTitle(String s, GOTAchievement ach) {
		this(s == null ? ach.getName() : s);
		titleType = TitleType.ACHIEVEMENT;
		titleAchievement = ach;
		if (s == null) {
			useAchievementName = true;
		}
	}

	@SuppressWarnings("WeakerAccess")
	public GOTTitle(GOTFactionRank rank, boolean fem) {
		this(fem ? rank.getCodeNameFem() : rank.getCodeName());
		titleType = TitleType.RANK;
		titleRank = rank;
		isFeminineRank = fem;
	}

	@SuppressWarnings("WeakerAccess")
	public GOTTitle(String s) {
		titleID = nextTitleID;
		nextTitleID++;
		name = s;
		CONTENT.add(this);
	}

	public static Comparator<GOTTitle> createTitleSorter(EntityPlayer entityplayer) {
		return Comparator.comparing(title -> title.getDisplayName(entityplayer));
	}

	public static GOTTitle forID(int ID) {
		for (GOTTitle title : CONTENT) {
			if (title.titleID == ID) {
				return title;
			}
		}
		return null;
	}

	public static void onInit() {
		new GOTTitle("targaryenF").setPlayerExclusive(GOT.DEVS);
		new GOTTitle("targaryenM").setPlayerExclusive(GOT.DEVS);
	}

	public boolean canDisplay(EntityPlayer entityplayer) {
		return !isHidden || canPlayerUse(entityplayer);
	}

	public boolean canPlayerUse(EntityPlayer entityplayer) {
		switch (titleType) {
			case ACHIEVEMENT:
				return GOTLevelData.getData(entityplayer).hasAchievement(titleAchievement);
			case PLAYER_EXCLUSIVE:
				for (UUID player : uuids) {
					if (!entityplayer.getUniqueID().equals(player)) {
						continue;
					}
					return true;
				}
				return false;
			case RANK:
				GOTPlayerData pd = GOTLevelData.getData(entityplayer);
				GOTFaction fac = titleRank.getFaction();
				float rep = pd.getReputation(fac);
				if (rep >= titleRank.getReputation()) {
					boolean requireOath = titleRank.isAboveOathRank() || titleRank.isOathRank() && GOTConfig.areStrictFactionTitleRequirementsEnabled(entityplayer.worldObj);
					return !requireOath || pd.hasTakenOathTo(fac);
				}
				return false;
			default:
				return true;
		}
	}

	public String getDescription(EntityPlayer entityplayer) {
		switch (titleType) {
			case STARTER:
				return StatCollector.translateToLocal("got.titles.unlock.starter");
			case PLAYER_EXCLUSIVE:
				return StatCollector.translateToLocal("got.titles.unlock.exclusive");
			case ACHIEVEMENT:
				return titleAchievement.getDescription();
			case RANK:
				String repS = GOTReputationValues.formatRepForDisplay(titleRank.getReputation());
				boolean requireOath = titleRank.isAboveOathRank() || titleRank.isOathRank() && GOTConfig.areStrictFactionTitleRequirementsEnabled(entityplayer.worldObj);
				if (requireOath) {
					return StatCollector.translateToLocalFormatted("got.titles.unlock.reputation.oath", titleRank.getFaction().factionName(), repS);
				}
				return StatCollector.translateToLocalFormatted("got.titles.unlock.reputation", titleRank.getFaction().factionName(), repS);
			default:
				return "If you can read this, something has gone hideously wrong";
		}
	}

	public String getDisplayName(EntityPlayer entityplayer) {
		if (titleType == TitleType.RANK) {
			if (isFeminineRank) {
				return titleRank.getDisplayFullNameFem();
			}
			return titleRank.getDisplayFullName();
		}
		return StatCollector.translateToLocal(getUntranslatedName(entityplayer));
	}

	private String getUntranslatedName(EntityPlayer entityplayer) {
		if (useAchievementName && titleAchievement != null) {
			return titleAchievement.getUntranslatedTitle(entityplayer);
		}
		if (titleType == TitleType.RANK) {
			if (isFeminineRank) {
				return titleRank.getCodeNameFem();
			}
			return titleRank.getCodeName();
		}
		return "got.title." + name;
	}

	private void setPlayerExclusive(List<String> devs) {
		UUID[] us = new UUID[devs.size()];
		for (int i = 0; i < devs.size(); ++i) {
			us[i] = UUID.fromString(devs.get(i));
		}
		titleType = TitleType.PLAYER_EXCLUSIVE;
		uuids = us;
		isHidden = true;
	}

	public int getTitleID() {
		return titleID;
	}

	public void setHidden(boolean hidden) {
		isHidden = hidden;
	}

	public TitleType getTitleType() {
		return titleType;
	}

	public GOTAchievement getTitleAchievement() {
		return titleAchievement;
	}

	public void setTitleAchievement(GOTAchievement titleAchievement) {
		this.titleAchievement = titleAchievement;
	}

	public enum TitleType {
		STARTER, PLAYER_EXCLUSIVE, ACHIEVEMENT, RANK

	}

	public static class PlayerTitle {
		protected final GOTTitle title;
		protected final EnumChatFormatting theColor;

		public PlayerTitle(GOTTitle title, EnumChatFormatting color) {
			EnumChatFormatting color1 = color;
			this.title = title;
			if (color1 == null || !color1.isColor()) {
				color1 = EnumChatFormatting.WHITE;
			}
			theColor = color1;
		}

		public static EnumChatFormatting colorForID(int ID) {
			for (EnumChatFormatting color : EnumChatFormatting.values()) {
				if (color.getFormattingCode() != ID) {
					continue;
				}
				return color;
			}
			return null;
		}

		public static PlayerTitle readNullableTitle(ByteBuf data) {
			short titleID = data.readShort();
			if (titleID >= 0) {
				byte colorID = data.readByte();
				GOTTitle title = forID(titleID);
				EnumChatFormatting color = colorForID(colorID);
				if (title != null && color != null) {
					return new PlayerTitle(title, color);
				}
			}
			return null;
		}

		public static void writeNullableTitle(ByteBuf data, PlayerTitle title) {
			if (title != null) {
				data.writeShort(title.title.titleID);
				data.writeByte(title.theColor.getFormattingCode());
			} else {
				data.writeShort(-1);
			}
		}

		public EnumChatFormatting getColor() {
			return theColor;
		}

		public String getFormattedTitle(EntityPlayer entityplayer) {
			return getFullTitleComponent(entityplayer).getFormattedText();
		}

		public IChatComponent getFullTitleComponent(EntityPlayer entityplayer) {
			IChatComponent component;
			if (title.titleType != null && title.titleType == TitleType.RANK && title.titleRank.isAddFacName()) {
				component = new ChatComponentText("[").appendSibling(new ChatComponentTranslation(title.getUntranslatedName(entityplayer))).appendText(" ").appendSibling(new ChatComponentTranslation(title.titleRank.getAffiliationCodeName())).appendText("]").appendText(" ");
			} else {
				component = new ChatComponentText("[").appendSibling(new ChatComponentTranslation(title.getUntranslatedName(entityplayer))).appendText("]").appendText(" ");
			}
			component.getChatStyle().setColor(theColor);
			return component;
		}

		public GOTTitle getTitle() {
			return title;
		}
	}
}