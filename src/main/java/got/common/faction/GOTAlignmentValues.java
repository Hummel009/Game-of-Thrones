package got.common.faction;

import got.common.util.GOTLog;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.*;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class GOTAlignmentValues {
	public static final AlignmentBonus MARRIAGE_BONUS = new AlignmentBonus(5.0f, "got.alignment.marriage");
	public static final AlignmentBonus VINEYARD_STEAL_PENALTY = new AlignmentBonus(-1.0f, "got.alignment.vineyardSteal");
	public static final AlignmentBonus PICKPOCKET_PENALTY = new AlignmentBonus(-1.0f, "got.alignment.pickpocket");

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(",##0.0");
	private static final DecimalFormat CONQ_FORMAT = new DecimalFormat(",##0.00");
	private static final DecimalFormatSymbols ALIGN_FORMAT_SYMBOLS = new DecimalFormatSymbols();

	private GOTAlignmentValues() {
	}

	public static AlignmentBonus createMiniquestBonus(float alignment) {
		return new AlignmentBonus(alignment, "got.alignment.miniQuest");
	}

	public static AlignmentBonus createPledgePenalty(float alignment) {
		return new AlignmentBonus(alignment, "got.alignment.breakPledge");
	}

	public static String formatAlignForDisplay(float alignment) {
		return formatAlignForDisplay(alignment, DECIMAL_FORMAT, true);
	}

	private static String formatAlignForDisplay(float alignment, DecimalFormat dFormat, boolean prefixPlus) {
		setupDecimalFormat(dFormat);
		String s = dFormat.format(alignment);
		if (prefixPlus && (s.isEmpty() || s.charAt(0) != '-')) {
			return '+' + s;
		}
		return s;
	}

	public static String formatConqForDisplay(float conq, boolean prefixPlus) {
		return formatAlignForDisplay(conq, CONQ_FORMAT, prefixPlus);
	}

	public static void notifyAlignmentNotHighEnough(ICommandSender entityplayer, float alignmentRequired, GOTFaction faction) {
		ChatComponentText componentAlignReq = new ChatComponentText(formatAlignForDisplay(alignmentRequired));
		componentAlignReq.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.insufficientAlignment", componentAlignReq, faction.factionName()));
	}

	public static float parseDisplayedAlign(String alignmentText) {
		String alignmentText1 = alignmentText;
		DecimalFormat dFormat = DECIMAL_FORMAT;
		setupDecimalFormat(dFormat);
		if (!alignmentText1.isEmpty() && alignmentText1.charAt(0) == '+') {
			alignmentText1 = alignmentText1.substring("+".length());
		}
		try {
			return dFormat.parse(alignmentText1).floatValue();
		} catch (ParseException e) {
			GOTLog.getLogger().error("Could not parse alignment value from display string {}", alignmentText1);
			e.printStackTrace();
			return 0.0f;
		}
	}

	private static void setupDecimalFormat(DecimalFormat dFormat) {
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
		ALIGN_FORMAT_SYMBOLS.setDecimalSeparator(decimalSeparatorChar);
		ALIGN_FORMAT_SYMBOLS.setGroupingSeparator(groupSeparatorChar);
		dFormat.setDecimalFormatSymbols(ALIGN_FORMAT_SYMBOLS);
	}

	public static class AlignmentBonus {
		private final float bonus;
		private final String name;

		private boolean needsTranslation = true;
		private boolean isKill;
		private boolean killByHiredUnit;
		private boolean isCivilianKill;

		public AlignmentBonus(float f, String s) {
			bonus = f;
			name = s;
		}

		public static float scalePenalty(float penalty, float alignment) {
			float penalty1 = penalty;
			if (alignment > 0.0f && penalty1 < 0.0f) {
				float factor = alignment / 50.0f;
				factor = MathHelper.clamp_float(factor, 1.0f, 20.0f);
				penalty1 *= factor;
			}
			return penalty1;
		}

		public float getBonus() {
			return bonus;
		}

		public String getName() {
			return name;
		}

		public boolean isNeedsTranslation() {
			return needsTranslation;
		}

		public void setNeedsTranslation(boolean needsTranslation) {
			this.needsTranslation = needsTranslation;
		}

		public boolean isKill() {
			return isKill;
		}

		public void setKill(boolean kill) {
			isKill = kill;
		}

		public boolean isKillByHiredUnit() {
			return killByHiredUnit;
		}

		public void setKillByHiredUnit(boolean killByHiredUnit) {
			this.killByHiredUnit = killByHiredUnit;
		}

		public boolean isCivilianKill() {
			return isCivilianKill;
		}

		public void setCivilianKill(boolean civilianKill) {
			isCivilianKill = civilianKill;
		}
	}
}