package got.common.entity.other.utils;

import got.common.entity.other.inanimate.GOTEntityInvasionSpawner;

public class GOTInvasionStatus {
	private GOTEntityInvasionSpawner watchedInvasion;
	private int ticksSinceRelevance;

	public void clear() {
		watchedInvasion = null;
		ticksSinceRelevance = 0;
	}

	public float getHealth() {
		return watchedInvasion.getInvasionHealthStatus();
	}

	public float[] getRGB() {
		return watchedInvasion.getInvasionType().getInvasionFaction().getFactionRGB_MinBrightness(0.45f);
	}

	public String getTitle() {
		return watchedInvasion.getInvasionType().invasionName();
	}

	public boolean isActive() {
		return watchedInvasion != null;
	}

	@SuppressWarnings("unused")
	public GOTEntityInvasionSpawner getWatchedInvasion() {
		return watchedInvasion;
	}

	public void setWatchedInvasion(GOTEntityInvasionSpawner invasion) {
		watchedInvasion = invasion;
		ticksSinceRelevance = 0;
	}

	public void tick() {
		if (watchedInvasion != null) {
			if (watchedInvasion.isDead) {
				clear();
			} else {
				++ticksSinceRelevance;
				if (ticksSinceRelevance >= 600) {
					ticksSinceRelevance = 0;
					clear();
				}
			}
		}
	}
}