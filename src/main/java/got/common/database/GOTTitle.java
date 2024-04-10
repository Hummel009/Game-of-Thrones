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
	public static final Collection<GOTTitle> CONTENT = new ArrayList<>();

	private static int nextTitleID;

	private final List<GOTFaction> alignmentFactions = new ArrayList<>();
	private final String name;
	private final int titleID;

	private boolean isHidden;
	private TitleType titleType = TitleType.STARTER;
	private GOTAchievement titleAchievement;

	private boolean useAchievementName;
	private float alignmentRequired;
	private UUID[] uuids;
	private GOTFactionRank titleRank;

	private boolean isFeminineRank;

	public GOTTitle(String s, GOTAchievement ach) {
		this(s == null ? ach.getCodeName() : s);
		titleType = TitleType.ACHIEVEMENT;
		titleAchievement = ach;
		if (s == null) {
			useAchievementName = true;
		}
	}

	public GOTTitle(GOTFactionRank rank, boolean fem) {
		this(fem ? rank.getCodeNameFem() : rank.getCodeName());
		titleType = TitleType.RANK;
		titleRank = rank;
		isFeminineRank = fem;
	}

	private GOTTitle(String s) {
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
			if (title.titleID != ID) {
				continue;
			}
			return title;
		}
		return null;
	}

	public static GOTTitle forName(String name) {
		for (GOTTitle title : CONTENT) {
			if (!title.name.equals(name)) {
				continue;
			}
			return title;
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
			case STARTER:
				return true;
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
			case RANK:
				GOTPlayerData pd = GOTLevelData.getData(entityplayer);
				GOTFaction fac = titleRank.getFaction();
				float align = pd.getAlignment(fac);
				if (align >= titleRank.getAlignment()) {
					boolean requirePledge = titleRank.isAbovePledgeRank() || titleRank.isPledgeRank() && GOTConfig.areStrictFactionTitleRequirementsEnabled(entityplayer.worldObj);
					return !requirePledge || pd.isPledgedTo(fac);
				}
				return false;
		}
		return true;
	}

	public String getDescription(EntityPlayer entityplayer) {
		switch (titleType) {
			case STARTER:
				return StatCollector.translateToLocal("got.titles.unlock.starter");
			case PLAYER_EXCLUSIVE:
				return StatCollector.translateToLocal("got.titles.unlock.exclusive");
			case ACHIEVEMENT:
				return titleAchievement.getDescription(entityplayer);
			case ALIGNMENT: {
				String alignLevel = GOTAlignmentValues.formatAlignForDisplay(alignmentRequired);
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
				boolean requirePledge = isAlignmentGreaterThanOrEqualToAllFactionPledges() && GOTConfig.areStrictFactionTitleRequirementsEnabled(entityplayer.worldObj);
				if (requirePledge) {
					return StatCollector.translateToLocalFormatted("got.titles.unlock.alignment.pledge", s.toString(), alignLevel);
				}
				return StatCollector.translateToLocalFormatted("got.titles.unlock.alignment", s.toString(), alignLevel);
			}
			case RANK:
				String alignS = GOTAlignmentValues.formatAlignForDisplay(titleRank.getAlignment());
				boolean requirePledge = titleRank.isAbovePledgeRank() || titleRank.isPledgeRank() && GOTConfig.areStrictFactionTitleRequirementsEnabled(entityplayer.worldObj);
				if (requirePledge) {
					return StatCollector.translateToLocalFormatted("got.titles.unlock.alignment.pledge", titleRank.getFaction().factionName(), alignS);
				}
				return StatCollector.translateToLocalFormatted("got.titles.unlock.alignment", titleRank.getFaction().factionName(), alignS);
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

	private boolean isAlignmentGreaterThanOrEqualToAllFactionPledges() {
		if (titleType == TitleType.ALIGNMENT) {
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

	private GOTTitle setAlignment(GOTFaction faction, float alignment) {
		return setMultiAlignment(alignment, faction);
	}

	private GOTTitle setMultiAlignment(float alignment, Collection<GOTFaction> factions) {
		titleType = TitleType.ALIGNMENT;
		alignmentFactions.addAll(factions);
		alignmentRequired = alignment;
		return this;
	}

	private GOTTitle setMultiAlignment(float alignment, GOTFaction... factions) {
		return setMultiAlignment(alignment, Arrays.asList(factions));
	}

	private GOTTitle setPlayerExclusive(List<String> devs) {
		UUID[] us = new UUID[devs.size()];
		for (int i = 0; i < devs.size(); ++i) {
			us[i] = UUID.fromString(devs.get(i));
		}
		return setPlayerExclusive(us);
	}

	private GOTTitle setPlayerExclusive(UUID... players) {
		titleType = TitleType.PLAYER_EXCLUSIVE;
		uuids = players;
		isHidden = true;
		return this;
	}

	public int getTitleID() {
		return titleID;
	}

	public boolean isHidden() {
		return isHidden;
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
		STARTER, PLAYER_EXCLUSIVE, ALIGNMENT, ACHIEVEMENT, RANK

	}

	public static class PlayerTitle {
		protected GOTTitle theTitle;
		protected EnumChatFormatting theColor;

		public PlayerTitle(GOTTitle title) {
			this(title, null);
		}

		public PlayerTitle(GOTTitle title, EnumChatFormatting color) {
			EnumChatFormatting color1 = color;
			theTitle = title;
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
			if (theTitle.titleType != null && theTitle.titleType == TitleType.RANK && theTitle.titleRank.isAddFacName()) {
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