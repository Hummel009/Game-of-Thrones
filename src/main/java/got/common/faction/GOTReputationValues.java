package got.common.faction;

import got.common.util.GOTLog;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.*;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class GOTReputationValues {
	public static final ReputationBonus MARRIAGE_BONUS = new ReputationBonus(5.0f, "got.reputation.marriage");
	public static final ReputationBonus VINEYARD_STEAL_PENALTY = new ReputationBonus(-1.0f, "got.reputation.vineyardSteal");
	public static final ReputationBonus PICKPOCKET_PENALTY = new ReputationBonus(-1.0f, "got.reputation.pickpocket");

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(",##0.0");
	private static final DecimalFormat CONQ_FORMAT = new DecimalFormat(",##0.00");
	private static final DecimalFormatSymbols ALIGN_FORMAT_SYMBOLS = new DecimalFormatSymbols();

	private GOTReputationValues() {
	}

	public static ReputationBonus createMiniquestBonus(float reputation) {
		return new ReputationBonus(reputation, "got.reputation.miniQuest");
	}

	public static ReputationBonus createPledgePenalty(float reputation) {
		return new ReputationBonus(reputation, "got.reputation.breakPledge");
	}

	public static String formatAlignForDisplay(float reputation) {
		return formatAlignForDisplay(reputation, DECIMAL_FORMAT, true);
	}

	private static String formatAlignForDisplay(float reputation, DecimalFormat dFormat, boolean prefixPlus) {
		setupDecimalFormat(dFormat);
		String s = dFormat.format(reputation);
		if (prefixPlus && (s.isEmpty() || s.charAt(0) != '-')) {
			return '+' + s;
		}
		return s;
	}

	public static String formatConqForDisplay(float conq, boolean prefixPlus) {
		return formatAlignForDisplay(conq, CONQ_FORMAT, prefixPlus);
	}

	public static void notifyReputationNotHighEnough(ICommandSender entityplayer, float reputationRequired, GOTFaction faction) {
		ChatComponentText componentAlignReq = new ChatComponentText(formatAlignForDisplay(reputationRequired));
		componentAlignReq.getChatStyle().setColor(EnumChatFormatting.YELLOW);
		entityplayer.addChatMessage(new ChatComponentTranslation("got.chat.insufficientReputation", componentAlignReq, faction.factionName()));
	}

	public static float parseDisplayedAlign(String reputationText) {
		String reputationText1 = reputationText;
		DecimalFormat dFormat = DECIMAL_FORMAT;
		setupDecimalFormat(dFormat);
		if (!reputationText1.isEmpty() && reputationText1.charAt(0) == '+') {
			reputationText1 = reputationText1.substring("+".length());
		}
		try {
			return dFormat.parse(reputationText1).floatValue();
		} catch (ParseException e) {
			GOTLog.getLogger().error("Could not parse reputation value from display string {}", reputationText1);
			e.printStackTrace();
			return 0.0f;
		}
	}

	private static void setupDecimalFormat(DecimalFormat dFormat) {
		String groupSeparator;
		char decimalSeparatorChar = '.';
		char groupSeparatorChar = ',';
		String decimalSeparator = StatCollector.translateToLocal("got.reputation.decimal_separator_char");
		if (decimalSeparator.length() == 1) {
			decimalSeparatorChar = decimalSeparator.charAt(0);
		}
		if ((groupSeparator = StatCollector.translateToLocal("got.reputation.group_separator_char")).length() == 1) {
			groupSeparatorChar = groupSeparator.charAt(0);
		}
		ALIGN_FORMAT_SYMBOLS.setDecimalSeparator(decimalSeparatorChar);
		ALIGN_FORMAT_SYMBOLS.setGroupingSeparator(groupSeparatorChar);
		dFormat.setDecimalFormatSymbols(ALIGN_FORMAT_SYMBOLS);
	}

	public static class ReputationBonus {
		private final float bonus;
		private final String name;

		private boolean needsTranslation = true;
		private boolean isKill;
		private boolean killByHiredUnit;
		private boolean isCivilianKill;

		public ReputationBonus(float f, String s) {
			bonus = f;
			name = s;
		}

		public static float scalePenalty(float penalty, float reputation) {
			float penalty1 = penalty;
			if (reputation > 0.0f && penalty1 < 0.0f) {
				float factor = reputation / 50.0f;
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