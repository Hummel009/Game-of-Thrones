package got.client;

import java.util.*;

import got.common.*;
import got.common.faction.GOTFaction;
import net.minecraft.entity.player.EntityPlayer;

public class GOTAlignmentTicker {
	public static Map<GOTFaction, GOTAlignmentTicker> allFactionTickers = new EnumMap<>(GOTFaction.class);
	public static int moveTime = 20;
	public static int flashTime = 30;
	public static int numericalTime = 200;
	public GOTFaction theFac;
	public float oldAlign;
	public float newAlign;
	public int moveTick;
	public int prevMoveTick;
	public int flashTick;
	public int numericalTick;

	public GOTAlignmentTicker(GOTFaction f) {
		theFac = f;
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

	public void update(EntityPlayer entityplayer, boolean forceInstant) {
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
				prevMoveTick = 20;
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

	public static GOTAlignmentTicker forFaction(GOTFaction fac) {
		return allFactionTickers.computeIfAbsent(fac, GOTAlignmentTicker::new);
	}

	public static void updateAll(EntityPlayer entityplayer, boolean forceInstant) {
		for (GOTDimension dim : GOTDimension.values()) {
			for (GOTFaction fac : dim.factionList) {
				GOTAlignmentTicker.forFaction(fac).update(entityplayer, forceInstant);
			}
		}
	}
}
