package got.common.world.map;

import java.util.*;

import org.apache.commons.lang3.tuple.Pair;

public class GOTWalls {
	public static List<GOTWalls> allWalls = new ArrayList<>();
	public static WallPointDatabase wallPointDatabase = new WallPointDatabase();
	public static int walls = allWalls.size();
	public static int id = 0;
	public WallPoint[] wallPoints;
	public List<WallPoint> endpoints = new ArrayList<>();

	public GOTWalls(WallPoint... ends) {
		Collections.addAll(endpoints, ends);
	}

	public String getDisplayName() {
		return null;
	}

	public static boolean isWallAt(int x, int z) {
		return GOTWalls.isWallNear(x, z, 4) >= 0.0f;
	}

	public static float isWallNear(int x, int z, int width) {
		double widthSq = width * width;
		float leastSqRatio = -1.0f;
		List<WallPoint> points = wallPointDatabase.getPointsForCoords(x, z);
		for (WallPoint point : points) {
			double dx = point.x - x;
			double dz = point.z - z;
			double distSq = dx * dx + dz * dz;
			if (distSq >= widthSq) {
				continue;
			}
			float f = (float) (distSq / widthSq);
			if (leastSqRatio == -1.0f) {
				leastSqRatio = f;
				continue;
			}
			if (f >= leastSqRatio) {
				continue;
			}
			leastSqRatio = f;
		}
		return leastSqRatio;
	}

	public static int[] near(GOTWaypoint wp, int x, int y) {
		return new int[] { wp.imgX + x, wp.imgY + y };
	}

	public static void onInit() {
		allWalls.clear();
		wallPointDatabase = new WallPointDatabase();
		GOTWalls.registerWall(id++, GOTWaypoint.WestWatch, GOTWaypoint.ShadowTower, GOTWaypoint.SentinelStand, GOTWaypoint.Greyguard, GOTWaypoint.Stonedoor, GOTWaypoint.HoarfrostHill, GOTWaypoint.Icemark, GOTWaypoint.Nightfort, GOTWaypoint.DeepLake, GOTWaypoint.Queensgate, GOTWaypoint.CastleBlack, GOTWaypoint.Oakenshield, GOTWaypoint.Woodswatch, GOTWaypoint.SableHall, GOTWaypoint.Rimegate, GOTWaypoint.LongBarrow, GOTWaypoint.Torches, GOTWaypoint.Greenguard, GOTWaypoint.EastWatch);
		GOTWalls.registerWall(id++, new int[] { 2659, 1218 }, new int[] { 2676, 1220 }, new int[] { 2693, 1218 });
		GOTWalls.registerWall(id++, GOTWaypoint.Anbei, GOTWaypoint.Jianmen, GOTWaypoint.Anguo, GOTWaypoint.Anjiang, GOTWaypoint.Dingguo, GOTWaypoint.Pinnu, GOTWaypoint.Pingjiang, GOTWaypoint.Wude, GOTWaypoint.Wusheng, GOTWaypoint.Zhenguo, GOTWaypoint.Lungmen, GOTWaypoint.Pingbei);
	}

	public static void registerWall(int num, Object... waypoints) {
		ArrayList<WallPoint> points = new ArrayList<>();
		for (Object obj : waypoints) {
			if (obj instanceof GOTWaypoint) {
				GOTWaypoint wp = (GOTWaypoint) obj;
				points.add(new WallPoint(wp.getXCoord(), wp.getZCoord(), true));
			} else if (obj instanceof int[]) {
				int[] coords = (int[]) obj;
				points.add(new WallPoint(GOTWaypoint.mapToWorldX(coords[0]), GOTWaypoint.mapToWorldZ(coords[1]), false));
			}
		}
		WallPoint[] array = points.toArray(new WallPoint[0]);
		GOTWalls[] walls = BezierCurves.getSplines(array);
		allWalls.addAll(Arrays.asList(walls));
	}

	public static class BezierCurves {
		public static int wallLengthFactor = 1;

		public static WallPoint bezier(WallPoint a, WallPoint b, WallPoint c, WallPoint d, double t) {
			WallPoint ab = BezierCurves.lerp(a, b, t);
			WallPoint bc = BezierCurves.lerp(b, c, t);
			WallPoint cd = BezierCurves.lerp(c, d, t);
			WallPoint abbc = BezierCurves.lerp(ab, bc, t);
			WallPoint bccd = BezierCurves.lerp(bc, cd, t);
			return BezierCurves.lerp(abbc, bccd, t);
		}

		public static double[][] getControlPoints(double[] src) {
			int i;
			int length = src.length - 1;
			double[] p1 = new double[length];
			double[] p2 = new double[length];
			double[] a = new double[length];
			double[] b = new double[length];
			double[] c = new double[length];
			double[] r = new double[length];
			a[0] = 0.0;
			b[0] = 2.0;
			c[0] = 1.0;
			r[0] = src[0] + 2.0 * src[1];
			for (i = 1; i < length - 1; ++i) {
				a[i] = 1.0;
				b[i] = 4.0;
				c[i] = 1.0;
				r[i] = 4.0 * src[i] + 2.0 * src[i + 1];
			}
			a[length - 1] = 2.0;
			b[length - 1] = 7.0;
			c[length - 1] = 0.0;
			r[length - 1] = 8.0 * src[length - 1] + src[length];
			for (i = 1; i < length; ++i) {
				double m = a[i] / b[i - 1];
				b[i] = b[i] - m * c[i - 1];
				r[i] = r[i] - m * r[i - 1];
			}
			p1[length - 1] = r[length - 1] / b[length - 1];
			for (i = length - 2; i >= 0; --i) {
				p1[i] = (r[i] - c[i] * p1[i + 1]) / b[i];
			}
			for (i = 0; i < length - 1; ++i) {
				p2[i] = 2.0 * src[i + 1] - p1[i + 1];
			}
			p2[length - 1] = 0.5 * (src[length] + p1[length - 1]);
			return new double[][] { p1, p2 };
		}

		public static GOTWalls[] getSplines(WallPoint[] waypoints) {
			if (waypoints.length == 2) {
				WallPoint p1 = waypoints[0];
				WallPoint p2 = waypoints[1];
				GOTWalls wall = new GOTWalls(p1, p2);
				double dx = p2.x - p1.x;
				double dz = p2.z - p1.z;
				int wallLength = (int) Math.round(Math.sqrt(dx * dx + dz * dz));
				int points = wallLength * wallLengthFactor;
				wall.wallPoints = new WallPoint[points];
				for (int l = 0; l < points; ++l) {
					WallPoint point;
					double t = (double) l / (double) points;
					wall.wallPoints[l] = point = new WallPoint(p1.x + dx * t, p1.z + dz * t, false);
					wallPointDatabase.add(point);
				}
				return new GOTWalls[] { wall };
			}
			int length = waypoints.length;
			double[] x = new double[length];
			double[] z = new double[length];
			for (int i = 0; i < length; ++i) {
				x[i] = waypoints[i].x;
				z[i] = waypoints[i].z;
			}
			double[][] controlX = BezierCurves.getControlPoints(x);
			double[][] controlZ = BezierCurves.getControlPoints(z);
			int controlPoints = controlX[0].length;
			WallPoint[] controlPoints1 = new WallPoint[controlPoints];
			WallPoint[] controlPoints2 = new WallPoint[controlPoints];
			for (int i = 0; i < controlPoints; ++i) {
				WallPoint p1 = new WallPoint(controlX[0][i], controlZ[0][i], false);
				WallPoint p2 = new WallPoint(controlX[1][i], controlZ[1][i], false);
				controlPoints1[i] = p1;
				controlPoints2[i] = p2;
			}
			GOTWalls[] walls = new GOTWalls[length - 1];
			for (int i = 0; i < walls.length; ++i) {
				GOTWalls wall;
				WallPoint p1 = waypoints[i];
				WallPoint p2 = waypoints[i + 1];
				WallPoint cp1 = controlPoints1[i];
				WallPoint cp2 = controlPoints2[i];
				walls[i] = wall = new GOTWalls(p1, p2);
				double dx = p2.x - p1.x;
				double dz = p2.z - p1.z;
				int wallLength = (int) Math.round(Math.sqrt(dx * dx + dz * dz));
				int points = wallLength * wallLengthFactor;
				wall.wallPoints = new WallPoint[points];
				for (int l = 0; l < points; ++l) {
					WallPoint point;
					double t = (double) l / (double) points;
					wall.wallPoints[l] = point = BezierCurves.bezier(p1, cp1, cp2, p2, t);
					wallPointDatabase.add(point);
				}
			}
			return walls;
		}

		public static WallPoint lerp(WallPoint a, WallPoint b, double t) {
			double x = a.x + (b.x - a.x) * t;
			double z = a.z + (b.z - a.z) * t;
			return new WallPoint(x, z, false);
		}
	}

	public static class WallPoint {
		public double x;
		public double z;
		public boolean isWaypoint;

		public WallPoint(double i, double j, boolean flag) {
			x = i;
			z = j;
			isWaypoint = flag;
		}
	}

	public static class WallPointDatabase {
		public static int COORD_LOOKUP_SIZE = 1000;
		public Map<Pair<Integer, Integer>, List<WallPoint>> pointMap = new HashMap<>();

		public void add(WallPoint point) {
			int x = (int) Math.round(point.x / 1000.0);
			int z = (int) Math.round(point.z / 1000.0);
			int overlap = 1;
			for (int i = -overlap; i <= overlap; ++i) {
				for (int k = -overlap; k <= overlap; ++k) {
					int xKey = x + i;
					int zKey = z + k;
					getWallList(xKey, zKey, true).add(point);
				}
			}
		}

		public List<WallPoint> getPointsForCoords(int x, int z) {
			int x1 = x / 1000;
			int z1 = z / 1000;
			return getWallList(x1, z1, false);
		}

		public List<WallPoint> getWallList(int xKey, int zKey, boolean addToMap) {
			Pair<Integer, Integer> key = Pair.of(xKey, zKey);
			List<WallPoint> list = pointMap.get(key);
			if (list == null) {
				list = new ArrayList<>();
				if (addToMap) {
					pointMap.put(key, list);
				}
			}
			return list;
		}
	}

}
