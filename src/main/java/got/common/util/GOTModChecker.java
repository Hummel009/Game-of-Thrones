package got.common.util;

public class GOTModChecker {
	public static int hasNEI = -1;
	public static int isCauldron = -1;
	public static int hasShaders = -1;
	public static int hasLOTR = -1;
	public static int hasGuiContainer = -1;
	public static int hasWeather2 = -1;
	public static boolean hasgot;

	public static boolean hasGuiContainer() {
		if (hasGuiContainer == -1) {
			try {
				if (Class.forName("net.minecraft.client.gui.inventory.GuiContainer") != null) {
					GOTLog.logger.info("Hummel009: I see GuiContainer here");
					hasGuiContainer = 1;
				} else {
					hasGuiContainer = 0;
				}
			} catch (ClassNotFoundException e) {
				hasGuiContainer = 0;
			}
		}
		return hasGuiContainer == 1;
	}

	public static boolean hasLOTR() {
		if (hasLOTR == -1) {
			try {
				if (Class.forName("lotr.common.LOTRMod") != null) {
					GOTLog.logger.info("Hummel009: Found LOTR installed");
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
					GOTLog.logger.info("Hummel009: Found NEI installed");
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
					GOTLog.logger.info("Hummel009: Found shaders installed");
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
					GOTLog.logger.info("Hummel009: Found Weather2 installed");
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
					if (GOTLog.logger != null) {
						GOTLog.logger.info("Hummel009: Found Cauldron installed");
					}
					isCauldron = 1;
					return true;
				}
			} catch (ClassNotFoundException ignored) {
			}
			try {
				if (Class.forName("thermos.ThermosClassTransformer") != null) {
					System.out.println("Hummel009: Found Thermos installed");
					if (GOTLog.logger != null) {
						GOTLog.logger.info("Hummel009: Found Thermos installed");
					}
					isCauldron = 1;
					return true;
				}
			} catch (ClassNotFoundException ignored) {
			}
			isCauldron = 0;
			return false;
		}
		return isCauldron == 1;
	}
}
