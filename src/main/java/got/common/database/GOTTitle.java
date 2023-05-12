package got.common.database;

import got.GOT;
import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.GOTPlayerData;
import got.common.faction.GOTAlignmentValues;
import got.common.faction.GOTFaction;
import got.common.faction.GOTFactionRank;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

import java.util.*;

public class GOTTitle {
	public static List<GOTTitle> allTitles = new ArrayList<>();
	public static GOTTitle targaryenF;
	public static GOTTitle targaryenM;
	public static int nextTitleID;
	public int titleID;
	public String name;
	public boolean isHidden;
	public TitleType titleType = TitleType.STARTER;
	public UUID[] uuids;
	public List<GOTFaction> alignmentFactions = new ArrayList<>();
	public float alignmentRequired;
	public boolean anyAlignment;
	public GOTAchievement titleAchievement;
	public boolean useAchievementName;
	public GOTFactionRank titleRank;

	public boolean isFeminineRank;

	public GOTTitle(GOTFactionRank rank, boolean fem) {
		this(fem ? rank.getCodeNameFem() : rank.getCodeName());
		titleType = TitleType.RANK;
		titleRank = rank;
		isFeminineRank = fem;
	}

	public GOTTitle(String s) {
		titleID = nextTitleID;
		nextTitleID++;
		name = s;
		allTitles.add(this);
	}

	public GOTTitle(String s, GOTAchievement ach) {
		this(s == null ? ach.getCodeName() : s);
		titleType = TitleType.ACHIEVEMENT;
		titleAchievement = ach;
		if (s == null) {
			useAchievementName = true;
		}
	}

	public static Comparator<GOTTitle> createTitleSorter(EntityPlayer entityplayer) {
		return Comparator.comparing(title -> title.getDisplayName(entityplayer));
	}

	public static GOTTitle forID(int ID) {
		for (GOTTitle title : allTitles) {
			if (title.titleID != ID) {
				continue;
			}
			return title;
		}
		return null;
	}

	public static GOTTitle forName(String name) {
		for (GOTTitle title : allTitles) {
			if (!title.name.equals(name)) {
				continue;
			}
			return title;
		}
		return null;
	}

	public static void onInit() {
		targaryenF = new GOTTitle("targaryenF").setPlayerExclusive(GOT.devs);
		targaryenM = new GOTTitle("targaryenM").setPlayerExclusive(GOT.devs);
	}

	public boolean canDisplay(EntityPlayer entityplayer) {
		return !isHidden || canPlayerUse(entityplayer);
	}

	public boolean canPlayerUse(EntityPlayer entityplayer) {
		switch (titleType) {
			case STARTER: {
				return true;
			}
			case ACHIEVEMENT: {
				return GOTLevelData.getData(entityplayer).hasAchievement(titleAchievement);
			}
			case PLAYER_EXCLUSIVE: {
				for (UUID player : uuids) {
					if (!entityplayer.getUniqueID().equals(player)) {
						continue;
					}
					return true;
				}
				return false;
			}
			case ALIGNMENT: {
				GOTPlayerData pd = GOTLevelData.getData(entityplayer);
				boolean requirePledge = isAlignmentGreaterThanOrEqualToAllFactionPledges() && GOTConfig.areStrictFactionTitleRequirementsEnabled(entityplayer.worldObj);
				for (GOTFaction f : alignmentFactions) {
					if (pd.getAlignment(f) < alignmentRequired || requirePledge && !pd.isPledgedTo(f)) {
						continue;
					}
					return true;
				}
				return false;
			}
			case RANK: {
				GOTPlayerData pd = GOTLevelData.getData(entityplayer);
				GOTFaction fac = titleRank.fac;
				float align = pd.getAlignment(fac);
				if (align >= titleRank.alignment) {
					boolean requirePledge;
					requirePledge = titleRank.isAbovePledgeRank() || titleRank.isPledgeRank() && GOTConfig.areStrictFactionTitleRequirementsEnabled(entityplayer.worldObj);
					return !requirePledge || pd.isPledgedTo(fac);
				}
				return false;
			}
		}
		return true;
	}

	public String getDescription(EntityPlayer entityplayer) {
		switch (titleType) {
			case STARTER: {
				return StatCollector.translateToLocal("got.titles.unlock.starter");
			}
			case PLAYER_EXCLUSIVE: {
				return StatCollector.translateToLocal("got.titles.unlock.exclusive");
			}
			case ACHIEVEMENT: {
				return titleAchievement.getDescription(entityplayer);
			}
			case ALIGNMENT: {
				boolean requirePledge;
				String alignLevel = GOTAlignmentValues.formatAlignForDisplay(alignmentRequired);
				if (anyAlignment) {
					return StatCollector.translateToLocalFormatted("got.titles.unlock.alignment.any", alignLevel);
				}
				StringBuilder s = new StringBuilder();
				if (alignmentFactions.size() > 1) {
					for (int i = 0; i < alignmentFactions.size(); ++i) {
						GOTFaction f = alignmentFactions.get(i);
						if (i > 0) {
							s.append(" / ");
						}
						s.append(f.factionName());
					}
				} else {
					GOTFaction f = alignmentFactions.get(0);
					s = new StringBuilder(f.factionName());
				}
				requirePledge = isAlignmentGreaterThanOrEqualToAllFactionPledges() && GOTConfig.areStrictFactionTitleRequirementsEnabled(entityplayer.worldObj);
				if (requirePledge) {
					return StatCollector.translateToLocalFormatted("got.titles.unlock.alignment.pledge", s.toString(), alignLevel);
				}
				return StatCollector.translateToLocalFormatted("got.titles.unlock.alignment", s.toString(), alignLevel);
			}
			case RANK: {
				boolean requirePledge;
				String alignS = GOTAlignmentValues.formatAlignForDisplay(titleRank.alignment);
				requirePledge = titleRank.isAbovePledgeRank() || titleRank.isPledgeRank() && GOTConfig.areStrictFactionTitleRequirementsEnabled(entityplayer.worldObj);
				if (requirePledge) {
					return StatCollector.translateToLocalFormatted("got.titles.unlock.alignment.pledge", titleRank.fac.factionName(), alignS);
				}
				return StatCollector.translateToLocalFormatted("got.titles.unlock.alignment", titleRank.fac.factionName(), alignS);
			}
		}
		return "If you can read this, something has gone hideously wrong";
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

	public String getTitleName() {
		return name;
	}

	public String getUntranslatedName(EntityPlayer entityplayer) {
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

	public boolean isAlignmentGreaterThanOrEqualToAllFactionPledges() {
		if (titleType == TitleType.ALIGNMENT && !anyAlignment) {
			for (GOTFaction fac : alignmentFactions) {
				if (alignmentRequired >= fac.getPledgeAlignment()) {
					continue;
				}
				return false;
			}
			return true;
		}
		return false;
	}

	public boolean isFeminineRank() {
		return titleType == TitleType.RANK && isFeminineRank;
	}

	public GOTTitle setAlignment(GOTFaction faction) {
		return setAlignment(faction, faction.getPledgeAlignment());
	}

	public GOTTitle setAlignment(GOTFaction faction, float alignment) {
		return setMultiAlignment(alignment, faction);
	}

	public GOTTitle setMultiAlignment(float alignment, GOTFaction... factions) {
		return setMultiAlignment(alignment, Arrays.asList(factions));
	}

	public GOTTitle setMultiAlignment(float alignment, List<GOTFaction> factions) {
		titleType = TitleType.ALIGNMENT;
		alignmentFactions.addAll(factions);
		alignmentRequired = alignment;
		return this;
	}

	public GOTTitle setPlayerExclusive(List<String> devs) {
		UUID[] us = new UUID[devs.size()];
		for (int i = 0; i < devs.size(); ++i) {
			us[i] = UUID.fromString(devs.get(i));
		}
		return setPlayerExclusive(us);
	}

	public GOTTitle setPlayerExclusive(UUID... players) {
		titleType = TitleType.PLAYER_EXCLUSIVE;
		uuids = players;
		isHidden = true;
		return this;
	}

	public enum TitleType {
		STARTER, PLAYER_EXCLUSIVE, ALIGNMENT, ACHIEVEMENT, RANK

	}

	public static class PlayerTitle {
		public GOTTitle theTitle;
		public EnumChatFormatting theColor;

		public PlayerTitle(GOTTitle title) {
			this(title, null);
		}

		public PlayerTitle(GOTTitle title, EnumChatFormatting color) {
			theTitle = title;
			if (color == null || !color.isColor()) {
				color = EnumChatFormatting.WHITE;
			}
			theColor = color;
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
				data.writeShort(title.theTitle.titleID);
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
			if (theTitle.titleType != null && theTitle.titleType == TitleType.RANK && theTitle.titleRank.addFacName) {
				component = new ChatComponentText("[").appendSibling(new ChatComponentTranslation(theTitle.getUntranslatedName(entityplayer))).appendText(" ").appendSibling(new ChatComponentTranslation(theTitle.titleRank.getAffiliationCodeName())).appendText("]").appendText(" ");
			} else {
				component = new ChatComponentText("[").appendSibling(new ChatComponentTranslation(theTitle.getUntranslatedName(entityplayer))).appendText("]").appendText(" ");
			}
			component.getChatStyle().setColor(theColor);
			return component;
		}

		public GOTTitle getTitle() {
			return theTitle;
		}
	}

}