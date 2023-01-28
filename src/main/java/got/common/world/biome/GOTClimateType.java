package got.common.world.biome;

import got.common.*;

public enum GOTClimateType {
	WINTER("isAlwaysWinter", false), COLD("isLongWinter", false), COLD_AZ("isLongWinterAZ", true), SUMMER("isNeverWinter", false), SUMMER_AZ("isNeverWinterAZ", true), NORMAL("isSeasonalWinter", false), NORMAL_AZ("isSeasonalWinterAZ", true);

	private boolean altitudeZone = false;
	private String climateName;
	
	GOTClimateType(String name, boolean zone) {
		climateName = name;
		altitudeZone = zone;
	}

	public String getClimateName() {
		return climateName;
	}

	public boolean isAltitudeZone() {
		return altitudeZone;
	}

	public static void performSeasonalChanges() {
		for (GOTBiome biome : GOTDimension.GAME_OF_THRONES.biomeList) {
			if (biome != null && biome.climateType != null) {
				switch (GOTDate.AegonCalendar.getSeason()) {
				case WINTER:
					switch (biome.climateType) {
					case COLD:
					case COLD_AZ:
					case NORMAL:
					case NORMAL_AZ:
						biome.setTemperatureRainfall(0.0F, 2.0F);
						biome.biomeColors.setGrass(0xffffff);
						biome.biomeColors.setSky(4212300);
						biome.biomeColors.setFog(6188664);
						biome.biomeColors.setWater(2635588);
						biome.biomeColors.setFoggy(false);
						break;
					case SUMMER:
					case SUMMER_AZ:
						biome.setTemperatureRainfall(0.28F, 2.0F);
						biome.biomeColors.resetGrass();
						biome.biomeColors.setSky(11653858);
						biome.biomeColors.resetFog();
						biome.biomeColors.setWater(2635588);
						biome.biomeColors.setFoggy(false);
						break;
					case WINTER:
						biome.setTemperatureRainfall(0.0F, 2.0F);
						biome.biomeColors.setGrass(0xffffff);
						biome.biomeColors.setSky(4212300);
						biome.biomeColors.setFog(6188664);
						biome.biomeColors.setWater(2635588);
						biome.biomeColors.setFoggy(true);
						break;
					}
					break;
				case SPRING:
					switch (biome.climateType) {
					case COLD:
					case COLD_AZ:
					case NORMAL:
					case NORMAL_AZ:
						biome.setTemperatureRainfall(0.28F, 0.8F);
						biome.biomeColors.resetGrass();
						biome.biomeColors.setSky(11653858);
						biome.biomeColors.resetFog();
						biome.biomeColors.setWater(2635588);
						biome.biomeColors.setFoggy(false);
						break;
					case SUMMER:
						biome.setTemperatureRainfall(0.8F, 0.8F);
						biome.biomeColors.resetGrass();
						biome.biomeColors.resetSky();
						biome.biomeColors.resetFog();
						biome.biomeColors.resetWater();
						biome.biomeColors.setFoggy(false);
						break;
					case SUMMER_AZ:
						biome.setTemperatureRainfall(0.28F, 0.8F);
						biome.biomeColors.resetGrass();
						biome.biomeColors.resetSky();
						biome.biomeColors.resetFog();
						biome.biomeColors.resetWater();
						biome.biomeColors.setFoggy(false);
						break;
					case WINTER:
						biome.setTemperatureRainfall(0.0F, 2.0F);
						biome.biomeColors.setGrass(0xffffff);
						biome.biomeColors.setSky(4212300);
						biome.biomeColors.setFog(6188664);
						biome.biomeColors.setWater(2635588);
						biome.biomeColors.setFoggy(true);
						break;
					}
					break;
				case SUMMER:
					switch (biome.climateType) {
					case COLD:
					case COLD_AZ:
						biome.setTemperatureRainfall(0.28F, 0.8F);
						biome.biomeColors.resetGrass();
						biome.biomeColors.setSky(11653858);
						biome.biomeColors.resetFog();
						biome.biomeColors.setWater(2635588);
						biome.biomeColors.setFoggy(false);
						break;
					case NORMAL:
						biome.setTemperatureRainfall(0.8F, 0.8F);
						biome.biomeColors.resetGrass();
						biome.biomeColors.resetSky();
						biome.biomeColors.resetFog();
						biome.biomeColors.resetWater();
						biome.biomeColors.setFoggy(false);
						break;
					case NORMAL_AZ:
						biome.setTemperatureRainfall(0.28F, 0.8F);
						biome.biomeColors.resetGrass();
						biome.biomeColors.resetSky();
						biome.biomeColors.resetFog();
						biome.biomeColors.resetWater();
						biome.biomeColors.setFoggy(false);
						break;
					case SUMMER:
						biome.setTemperatureRainfall(1.2F, 0.4F);
						biome.biomeColors.setGrass(14538086);
						biome.biomeColors.setSky(15592678);
						biome.biomeColors.resetFog();
						biome.biomeColors.resetWater();
						biome.biomeColors.setFoggy(false);
						break;
					case SUMMER_AZ:
						biome.setTemperatureRainfall(0.28F, 0.8F);
						biome.biomeColors.setGrass(14538086);
						biome.biomeColors.setSky(15592678);
						biome.biomeColors.resetFog();
						biome.biomeColors.resetWater();
						biome.biomeColors.setFoggy(false);
						break;
					case WINTER:
						biome.setTemperatureRainfall(0.0F, 2.0F);
						biome.biomeColors.setGrass(0xffffff);
						biome.biomeColors.setSky(4212300);
						biome.biomeColors.setFog(6188664);
						biome.biomeColors.setWater(2635588);
						biome.biomeColors.setFoggy(true);
						break;
					}
					break;
				case AUTUMN:
					switch (biome.climateType) {
					case COLD:
					case COLD_AZ:
					case NORMAL:
					case NORMAL_AZ:
						biome.setTemperatureRainfall(0.28F, 2.0F);
						biome.biomeColors.setGrass(0xd09f4d);
						biome.biomeColors.setSky(11653858);
						biome.biomeColors.resetFog();
						biome.biomeColors.setWater(2635588);
						biome.biomeColors.setFoggy(true);
						break;
					case SUMMER:
						biome.setTemperatureRainfall(0.8F, 0.8F);
						biome.biomeColors.resetGrass();
						biome.biomeColors.resetSky();
						biome.biomeColors.resetFog();
						biome.biomeColors.resetWater();
						biome.biomeColors.setFoggy(false);
						break;
					case SUMMER_AZ:
						biome.setTemperatureRainfall(0.28F, 0.8F);
						biome.biomeColors.resetGrass();
						biome.biomeColors.resetSky();
						biome.biomeColors.resetFog();
						biome.biomeColors.resetWater();
						biome.biomeColors.setFoggy(false);
						break;
					case WINTER:
						biome.setTemperatureRainfall(0.0F, 2.0F);
						biome.biomeColors.setGrass(0xffffff);
						biome.biomeColors.setSky(4212300);
						biome.biomeColors.setFog(6188664);
						biome.biomeColors.setWater(2635588);
						biome.biomeColors.setFoggy(true);
						break;
					}
					break;
				}
			}
		}
	}
}