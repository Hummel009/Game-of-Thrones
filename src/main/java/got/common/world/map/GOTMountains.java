package got.common.world.map;

public enum GOTMountains {
	NORTH(GOTWaypoint.NorthPass.getX(), GOTWaypoint.NorthPass.getY(), 4.5F, 250), 
	SOUTH(GOTWaypoint.SouthPass.getX(), GOTWaypoint.SouthPass.getY(), 4.5F, 250), 
	WEST(GOTWaypoint.WestPass.getX(), GOTWaypoint.WestPass.getY(), 4.5F, 250), 
	EAST(GOTWaypoint.EastPass.getX(), GOTWaypoint.EastPass.getY(), 4.5F, 250), 
	EYRIE(GOTWaypoint.TheEyrie.getX() + 1, GOTWaypoint.TheEyrie.getY() + 1, 4.5F, 250), 
	GOLD(GOTWaypoint.Goldenhill.getX(), GOTWaypoint.Goldenhill.getY(), 1.0f, 70), 
	VICTARION(GOTWaypoint.VictarionLanding.getX(), GOTWaypoint.VictarionLanding.getY(), 1.0f, 70), 
	UMBER(GOTWaypoint.LastHearth.getX(), GOTWaypoint.LastHearth.getY(), 1.0f, 70), 
	BERIC(GOTWaypoint.HollowHill.getX(), GOTWaypoint.HollowHill.getY(), 1.0f, 70), 
	BREAKSTONE(GOTWaypoint.BreakstoneHill.getX(), GOTWaypoint.BreakstoneHill.getY(), 1.0f, 70), 
	IRONRATH(GOTWaypoint.Ironrath.getX(), GOTWaypoint.Ironrath.getY(), 1.0f, 70), 
	HIGHPOINT(GOTWaypoint.Highpoint.getX(), GOTWaypoint.Highpoint.getY(), 1.0f, 70), 
	GLOVER(GOTWaypoint.DeepwoodMotte.getX(), GOTWaypoint.DeepwoodMotte.getY(), 1.0f, 70), 
	BARROWTOWN(GOTWaypoint.Barrowtown.getX(), GOTWaypoint.Barrowtown.getY() + 1, 2.0f, 150), 
	HAMMERHORN(GOTWaypoint.Hammerhorn.getX(), GOTWaypoint.Hammerhorn.getY(), 1.0f, 70), 
	HARRIDAN(GOTWaypoint.HarridanHill.getX(), GOTWaypoint.HarridanHill.getY(), 1.0f, 70), 
	DRAHONSTONE(GOTWaypoint.Dragonstone.getX(), GOTWaypoint.Dragonstone.getY(), 1.0f, 70), 
	GREYWATER(GOTWaypoint.GreywaterWatch.getX(), GOTWaypoint.GreywaterWatch.getY(), 0.5f, 70), 
	TITS1(GOTWaypoint.Pennytree.getX(), GOTWaypoint.Pennytree.getY() + 1, 1.0f, 70), 
	TITS2(GOTWaypoint.Pennytree.getX(), GOTWaypoint.Pennytree.getY() - 1, 1.0f, 70), 
	MOTHER(GOTWaypoint.VaesDothrak.getX(), GOTWaypoint.VaesDothrak.getY() - 2, 5.0f, 250), 
	KAILIN(GOTWaypoint.MoatKailin.getX(), GOTWaypoint.MoatKailin.getY(), 1.0f, 70), 
	NAGGA(GOTWaypoint.NaggaHill.getX(), GOTWaypoint.NaggaHill.getY(), 1.0f, 70), 
	PYKE(GOTWaypoint.Pyke.getX(), GOTWaypoint.Pyke.getY(), 1.0f, 70), 
	FIST(GOTWaypoint.Fist.getX(), GOTWaypoint.Fist.getY(), 5.0f, 250), 
	HIGHGARDEN(GOTWaypoint.Highgarden.getX() - 1, GOTWaypoint.Highgarden.getY() + 1, 1.0f, 120), 
	HORNHILL(GOTWaypoint.HornHill.getX(), GOTWaypoint.HornHill.getY(), 1.5f, 120), 
	UPLDANDS(GOTWaypoint.Uplands.getX(), GOTWaypoint.Uplands.getY(), 1.5f, 120), 
	STORMSEND(GOTWaypoint.StormsEnd.getX(), GOTWaypoint.StormsEnd.getY(), 1.0f, 70), 
	AEGON(GOTWaypoint.Aegon.getX(), GOTWaypoint.Aegon.getY(), 2.5f, 150), 
	VISENYA(GOTWaypoint.Visenya.getX(), GOTWaypoint.Visenya.getY(), 1.5f, 120), 
	RAENYS(GOTWaypoint.Raenys.getX(), GOTWaypoint.Raenys.getY(), 1.5f, 120), 
	OT1(388, 1946, 1f, 70), OT2(389, 1944, 1f, 70), OT3(391, 1944, 1f, 70);

	public int xCoord;
	public int zCoord;
	public float height;
	public int range;
	public int lavaRange;

	GOTMountains(float x, float z, float h, int r) {
		this(x, z, h, r, 0);
	}

	GOTMountains(float x, float z, float h, int r, int l) {
		xCoord = GOTWaypoint.mapToWorldX(x);
		zCoord = GOTWaypoint.mapToWorldZ(z);
		height = h;
		range = r;
		lavaRange = l;
	}

	public float getHeightBoost(int x, int z) {
		double dx = x - xCoord;
		double dz = z - zCoord;
		double distSq = dx * dx + dz * dz;
		double rangeSq = range * range;
		if (distSq < rangeSq) {
			if (lavaRange > 0 && distSq < (lavaRange * lavaRange)) {
				return getLavaCraterHeight();
			}
			double dist = Math.sqrt(distSq);
			float f = (float) (dist / range);
			return (1.0f - f) * height;
		}
		return 0.0f;
	}

	public float getLavaCraterHeight() {
		return (1.0f - (float) lavaRange / (float) range) * height * 0.4f;
	}

	public static int getLavaHeight(int x, int z) {
		for (GOTMountains m : GOTMountains.values()) {
			double dx;
			double dz;
			if (m.lavaRange <= 0 || (((dx = x - m.xCoord) * dx + (dz = z - m.zCoord) * dz) >= ((m.lavaRange + 6) * (m.lavaRange + 6)))) {
				continue;
			}
			return Math.round(m.getLavaCraterHeight() * 110.0f);
		}
		return 0;
	}

	public static float getTotalHeightBoost(int x, int z) {
		float f = 0.0f;
		for (GOTMountains m : GOTMountains.values()) {
			f += m.getHeightBoost(x, z);
		}
		return f;
	}

	public static boolean mountainAt(int x, int z) {
		return GOTMountains.getTotalHeightBoost(x, z) > 0.005f;
	}

	public static boolean mountainNear(int x, int z, int range) {
		for (GOTMountains m : GOTMountains.values()) {
			double dx = x - m.xCoord;
			double dz = z - m.zCoord;
			double distSq = dx * dx + dz * dz;
			double mtnRange = range + m.range;
			double rangeSq = mtnRange * mtnRange;
			if ((distSq > rangeSq)) {
				continue;
			}
			return true;
		}
		return false;
	}
}
