package got.common.util;

public class GOTModChecker {
	private static int hasNEI = -1;
	private static int isCauldron = -1;
	private static int hasShaders = -1;
	private static int hasLOTR = -1;
	private static int hasWeather2 = -1;

	public static boolean hasLOTR() {
		if (hasLOTR == -1) {
			try {
				if (Class.forName("lotr.common.LOTRMod") != null) {
					GOTLog.getLogger().info("Hummel009: Found LOTR installed");
					hasLOTR = 1;
				} else {
					hasLOTR = 0;
				}
			} catch (ClassNotFoundException e) {
				hasLOTR = 0;
			}
		}
		return hasLOTR == 1;
	}

	public static boolean hasNEI() {
		if (hasNEI == -1) {
			try {
				if (Class.forName("codechicken.nei.api.API") != null) {
					GOTLog.getLogger().info("Hummel009: Found NEI installed");
					hasNEI = 1;
				} else {
					hasNEI = 0;
				}
			} catch (ClassNotFoundException e) {
				hasNEI = 0;
			}
		}
		return hasNEI == 1;
	}

	public static boolean hasShaders() {
		if (hasShaders == -1) {
			try {
				if (Class.forName("shadersmodcore.client.Shaders") != null) {
					GOTLog.getLogger().info("Hummel009: Found shaders installed");
					hasShaders = 1;
				} else {
					hasShaders = 0;
				}
			} catch (ClassNotFoundException e) {
				hasShaders = 0;
			}
		}
		return hasShaders == 1;
	}

	public static boolean hasWeather2() {
		if (hasWeather2 == -1) {
			try {
				if (Class.forName("weather2.Weather") != null) {
					GOTLog.getLogger().info("Hummel009: Found Weather2 installed");
					hasWeather2 = 1;
				} else {
					hasWeather2 = 0;
				}
			} catch (ClassNotFoundException e) {
				hasWeather2 = 0;
			}
		}
		return hasWeather2 == 1;
	}

	public static boolean isCauldronServer() {
		if (isCauldron == -1) {
			try {
				if (Class.forName("kcauldron.KCauldronClassTransformer") != null) {
					System.out.println("Hummel009: Found Cauldron installed");
					if (GOTLog.getLogger() != null) {
						GOTLog.getLogger().info("Hummel009: Found Cauldron installed");
					}
					isCauldron = 1;
					return true;
				}
			} catch (ClassNotFoundException classNotFoundException) {
			}
			try {
				if (Class.forName("thermos.ThermosClassTransformer") != null) {
					System.out.println("Hummel009: Found Thermos installed");
					if (GOTLog.getLogger() != null) {
						GOTLog.getLogger().info("Hummel009: Found Thermos installed");
					}
					isCauldron = 1;
					return true;
				}
			} catch (ClassNotFoundException classNotFoundException) {
			}
			isCauldron = 0;
			return false;
		}
		return isCauldron == 1;
	}
}
