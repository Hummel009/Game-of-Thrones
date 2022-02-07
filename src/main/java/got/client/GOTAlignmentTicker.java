package got.client;

import java.util.*;

import got.common.*;
import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;

public class GOTAlignmentTicker {
	private static Map<GOTFaction, GOTAlignmentTicker> allFactionTickers = new HashMap<>();
	private GOTFaction theFac;
	private float oldAlign;
	private float newAlign;
	private int moveTick = 0;
	private int prevMoveTick = 0;
	private int flashTick;
	private int numericalTick;

	private GOTAlignmentTicker(GOTFaction f) {
		theFac = f;
	}

	public int getFlashTick() {
		return flashTick;
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

	public int getNumericalTick() {
		return numericalTick;
	}

	public void setFlashTick(int flashTick) {
		this.flashTick = flashTick;
	}

	public void setNumericalTick(int numericalTick) {
		this.numericalTick = numericalTick;
	}

	private void update(EntityPlayer entityplayer, boolean forceInstant) {
		float curAlign = GOTLevelData.getData(entityplayer).getAlignment(theFac);
		if (forceInstant) {
			oldAlign = newAlign = curAlign;
			moveTick = 0;
			prevMoveTick = 0;
			setFlashTick(0);
			setNumericalTick(0);
		} else {
			if (newAlign != curAlign) {
				oldAlign = newAlign;
				newAlign = curAlign;
				moveTick = 20;
				prevMoveTick = 20;
				setFlashTick(30);
				setNumericalTick(200);
			}
			prevMoveTick = moveTick;
			if (moveTick > 0) {
				--moveTick;
				if (moveTick <= 0) {
					oldAlign = newAlign;
				}
			}
			if (getFlashTick() > 0) {
				setFlashTick(getFlashTick() - 1);
			}
			if (getNumericalTick() > 0) {
				setNumericalTick(getNumericalTick() - 1);
			}
		}
	}

	public static GOTAlignmentTicker forFaction(GOTFaction fac) {
		GOTAlignmentTicker ticker = allFactionTickers.get(fac);
		if (ticker == null) {
			ticker = new GOTAlignmentTicker(fac);
			allFactionTickers.put(fac, ticker);
		}
		return ticker;
	}

	public static void updateAll(EntityPlayer entityplayer, boolean forceInstant) {
		for (GOTDimension dim : GOTDimension.values()) {
			for (GOTFaction fac : dim.factionList) {
				GOTAlignmentTicker.forFaction(fac).update(entityplayer, forceInstant);
			}
		}
	}
}
