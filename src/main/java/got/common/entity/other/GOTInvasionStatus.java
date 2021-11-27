package got.common.entity.other;

public class GOTInvasionStatus {
	public GOTEntityInvasionSpawner watchedInvasion;
	public int ticksSinceRelevance;

	public void clear() {
		watchedInvasion = null;
		ticksSinceRelevance = 0;
	}

	public float getHealth() {
		return watchedInvasion.getInvasionHealthStatus();
	}

	public float[] getRGB() {
		return watchedInvasion.getInvasionType().invasionFaction.getFactionRGB_MinBrightness(0.45f);
	}

	public String getTitle() {
		return watchedInvasion.getInvasionType().invasionName();
	}

	public boolean isActive() {
		return watchedInvasion != null;
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
