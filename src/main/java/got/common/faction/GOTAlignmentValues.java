package got.common.faction;

import got.common.util.GOTLog;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.*;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class GOTAlignmentValues {
	public static AlignmentBonus MARRIAGE_BONUS = new AlignmentBonus(5.0f, "got.alignment.marriage");
	public static AlignmentBonus VINEYARD_STEAL_PENALTY = new AlignmentBonus(-1.0f, "got.alignment.vineyardSteal");
	public static AlignmentBonus PICKPOCKET_PENALTY = new AlignmentBonus(-1.0f, "got.alignment.pickpocket");
	public static DecimalFormat alignFormat = new DecimalFormat(",##0.0");
	public static DecimalFormat conqFormat = new DecimalFormat(",##0.00");
	public static DecimalFormatSymbols alignFormatSymbols = new DecimalFormatSymbols();

	public static AlignmentBonus createMiniquestBonus(float alignment) {
		return new AlignmentBonus(alignment, "got.alignment.miniQuest");
	}

	public static AlignmentBonus createPledgePenalty(float alignment) {
		return new AlignmentBonus(alignment, "got.alignment.breakPledge");
	}

	public static String formatAlignForDisplay(float alignment) {
		return formatAlignForDisplay(alignment, alignFormat, true);
	}

	public static String formatAlignForDisplay(float alignment, DecimalFormat dFormat, boolean prefixPlus) {
		setupDecimalFormat(dFormat);
		String s = dFormat.format(alignment);
		if (prefixPlus && (s.isEmpty() || s.charAt(0) != '-')) {
			return '+' + s;
		}
		return s;
	}

	public static String formatConqForDisplay(float conq, boolean prefixPlus) {
		return formatAlignForDisplay(conq, conqFormat, prefixPlus);
	}

	public static void notifyAlignmentNotHighEnough(ICommandSender entityplayer, float alignmentRequired, GOTFaction faction) {
		ChatComponentText componentAlignReq = new ChatComponentText(formatAlignForDisplay(alignmentRequired));
		componentAlignReq.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.insufficientAlignment", componentAlignReq, faction.factionName()));
	}

	public static float parseDisplayedAlign(String alignmentText) {
		DecimalFormat dFormat = alignFormat;
		setupDecimalFormat(dFormat);
		if (!alignmentText.isEmpty() && alignmentText.charAt(0) == '+') {
			alignmentText = alignmentText.substring("+".length());
		}
		try {
			return dFormat.parse(alignmentText).floatValue();
		} catch (ParseException e) {
			GOTLog.logger.error("Could not parse alignment value from display string {}", alignmentText);
			e.printStackTrace();
			return 0.0f;
		}
	}

	public static void setupDecimalFormat(DecimalFormat dFormat) {
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
	}

	public static class AlignmentBonus {
		public float bonus;
		public String name;
		public boolean needsTranslation = true;
		public boolean isKill;
		public boolean killByHiredUnit;
		public boolean isCivilianKill;

		public AlignmentBonus(float f, String s) {
			bonus = f;
			name = s;
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
