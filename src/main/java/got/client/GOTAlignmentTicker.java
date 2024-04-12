package got.client;

import got.common.GOTDimension;
import got.common.GOTLevelData;
import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;

import java.util.EnumMap;
import java.util.Map;

public class GOTAlignmentTicker {
	private static final Map<GOTFaction, GOTAlignmentTicker> ALL_FACTION_TICKERS = new EnumMap<>(GOTFaction.class);

	private final GOTFaction theFac;

	private float oldAlign;
	private float newAlign;
	private int moveTick;
	private int prevMoveTick;
	private int flashTick;
	private int numericalTick;

	private GOTAlignmentTicker(GOTFaction f) {
		theFac = f;
	}

	public static GOTAlignmentTicker forFaction(GOTFaction fac) {
		return ALL_FACTION_TICKERS.computeIfAbsent(fac, GOTAlignmentTicker::new);
	}

	public static void updateAll(EntityPlayer entityplayer, boolean forceInstant) {
		for (GOTDimension dim : GOTDimension.values()) {
			for (GOTFaction fac : dim.getFactionList()) {
				forFaction(fac).update(entityplayer, forceInstant);
			}
		}
	}

	public float getInterpolatedAlignment(float f) {
		if (moveTick == 0) {
			return oldAlign;
		}
		float tickF = prevMoveTick + (moveTick - prevMoveTick) * f;
		tickF /= 20.0f;
		tickF = 1.0f - tickF;
		return oldAlign + (newAlign - oldAlign) * tickF;
	}

	private void update(EntityPlayer entityplayer, boolean forceInstant) {
		float curAlign = GOTLevelData.getData(entityplayer).getAlignment(theFac);
		if (forceInstant) {
			oldAlign = newAlign = curAlign;
			moveTick = 0;
			prevMoveTick = 0;
			flashTick = 0;
			numericalTick = 0;
		} else {
			if (newAlign != curAlign) {
				oldAlign = newAlign;
				newAlign = curAlign;
				moveTick = 20;
				flashTick = 30;
				numericalTick = 200;
			}
			prevMoveTick = moveTick;
			if (moveTick > 0) {
				--moveTick;
				if (moveTick <= 0) {
					oldAlign = newAlign;
				}
			}
			if (flashTick > 0) {
				--flashTick;
			}
			if (numericalTick > 0) {
				--numericalTick;
			}
		}
	}

	public int getFlashTick() {
		return flashTick;
	}

	public int getNumericalTick() {
		return numericalTick;
	}
}