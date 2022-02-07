package got.common.faction;

import java.text.*;

import got.common.util.GOTLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

public class GOTAlignmentValues {
	private static AlignmentBonus MARRIAGE_BONUS = new AlignmentBonus(5.0f, "got.alignment.marriage");
	private static AlignmentBonus VINEYARD_STEAL_PENALTY = new AlignmentBonus(-1.0f, "got.alignment.vineyardSteal");
	private static AlignmentBonus PICKPOCKET_PENALTY = new AlignmentBonus(-1.0f, "got.alignment.pickpocket");
	private static DecimalFormat alignFormat = new DecimalFormat(",##0.0");
	private static DecimalFormat conqFormat = new DecimalFormat(",##0.00");
	private static DecimalFormatSymbols alignFormatSymbols = new DecimalFormatSymbols();

	public static AlignmentBonus createMiniquestBonus(float alignment) {
		return new AlignmentBonus(alignment, "got.alignment.miniQuest");
	}

	public static AlignmentBonus createPledgePenalty(float alignment) {
		return new AlignmentBonus(alignment, "got.alignment.breakPledge");
	}

	public static String formatAlignForDisplay(float alignment) {
		return GOTAlignmentValues.formatAlignForDisplay(alignment, alignFormat, true);
	}

	private static String formatAlignForDisplay(float alignment, DecimalFormat dFormat, boolean prefixPlus) {
		GOTAlignmentValues.setupDecimalFormat(dFormat);
		String s = dFormat.format(alignment);
		if (prefixPlus && !s.startsWith("-")) {
			s = "+" + s;
		}
		return s;
	}

	public static String formatConqForDisplay(float conq, boolean prefixPlus) {
		return GOTAlignmentValues.formatAlignForDisplay(conq, conqFormat, prefixPlus);
	}

	public static AlignmentBonus getMarriageBonus() {
		return MARRIAGE_BONUS;
	}

	public static AlignmentBonus getPickpocket() {
		return PICKPOCKET_PENALTY;
	}

	public static AlignmentBonus getVineyardSteal() {
		return VINEYARD_STEAL_PENALTY;
	}

	public static void notifyAlignmentNotHighEnough(EntityPlayer entityplayer, float alignmentRequired, GOTFaction faction) {
		ChatComponentText componentAlignReq = new ChatComponentText(GOTAlignmentValues.formatAlignForDisplay(alignmentRequired));
		componentAlignReq.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.insufficientAlignment", componentAlignReq, faction.factionName()));
	}

	public static float parseDisplayedAlign(String alignmentText) {
		DecimalFormat dFormat = alignFormat;
		GOTAlignmentValues.setupDecimalFormat(dFormat);
		if (alignmentText.startsWith("+")) {
			alignmentText = alignmentText.substring("+".length());
		}
		try {
			return dFormat.parse(alignmentText).floatValue();
		} catch (ParseException e) {
			GOTLog.getLogger().error("Could not parse alignment value from display string " + alignmentText);
			e.printStackTrace();
			return 0.0f;
		}
	}

	public static void setMarriageBonus(AlignmentBonus mARRIAGE_BONUS) {
		MARRIAGE_BONUS = mARRIAGE_BONUS;
	}

	public static void setPickpocket(AlignmentBonus pICKPOCKET_PENALTY) {
		PICKPOCKET_PENALTY = pICKPOCKET_PENALTY;
	}

	private static DecimalFormat setupDecimalFormat(DecimalFormat dFormat) {
		String groupSeparator;
		char decimalSeparatorChar = '.';
		char groupSeparatorChar = ',';
		String decimalSeparator = StatCollector.translateToLocal("got.alignment.decimal_separator_char");
		if (decimalSeparator.length() == 1) {
			decimalSeparatorChar = decimalSeparator.charAt(0);
		}
		if ((groupSeparator = StatCollector.translateToLocal("got.alignment.group_separator_char")).length() == 1) {
			groupSeparatorChar = groupSeparator.charAt(0);
		}
		alignFormatSymbols.setDecimalSeparator(decimalSeparatorChar);
		alignFormatSymbols.setGroupingSeparator(groupSeparatorChar);
		dFormat.setDecimalFormatSymbols(alignFormatSymbols);
		return dFormat;
	}

	public static void setVineyardSteal(AlignmentBonus vINEYARD_STEAL_PENALTY) {
		VINEYARD_STEAL_PENALTY = vINEYARD_STEAL_PENALTY;
	}

	public static class AlignmentBonus {
		private float bonus;
		private String name;
		private boolean needsTranslation = true;
		private boolean isKill = false;
		private boolean killByHiredUnit = false;
		private boolean isCivilianKill = false;
		private boolean isRoyalOrder = false;
		private GOTFaction faction;

		public AlignmentBonus(float f, String s) {
			setBonus(f);
			setName(s);
		}

		public float getBonus() {
			return bonus;
		}

		public GOTFaction getFaction() {
			return faction;
		}

		public String getName() {
			return name;
		}

		public boolean isCivilianKill() {
			return isCivilianKill;
		}

		public boolean isKill() {
			return isKill;
		}

		public boolean isKillByHiredUnit() {
			return killByHiredUnit;
		}

		public boolean isNeedsTranslation() {
			return needsTranslation;
		}

		public boolean isRoyalOrder() {
			return isRoyalOrder;
		}

		public void setBonus(float bonus) {
			this.bonus = bonus;
		}

		public void setCivilianKill(boolean isCivilianKill) {
			this.isCivilianKill = isCivilianKill;
		}

		public void setFaction(GOTFaction faction) {
			this.faction = faction;
		}

		public void setKill(boolean isKill) {
			this.isKill = isKill;
		}

		public void setKillByHiredUnit(boolean killByHiredUnit) {
			this.killByHiredUnit = killByHiredUnit;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setNeedsTranslation(boolean needsTranslation) {
			this.needsTranslation = needsTranslation;
		}

		public void setRoyalOrder(boolean isRoyalOrder) {
			this.isRoyalOrder = isRoyalOrder;
		}

		public static float scalePenalty(float penalty, float alignment) {
			if (alignment > 0.0f && penalty < 0.0f) {
				float factor = alignment / 50.0f;
				factor = MathHelper.clamp_float(factor, 1.0f, 20.0f);
				penalty *= factor;
			}
			return penalty;
		}
	}

}
