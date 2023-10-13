package got.common.world.biome;

import got.common.GOTDate;
import got.common.GOTDimension;

public enum GOTClimateType {
	WINTER(false), COLD(false), COLD_AZ(true), SUMMER(false), SUMMER_AZ(true), NORMAL(false), NORMAL_AZ(true);

	public boolean altitudeZone;

	GOTClimateType(boolean zone) {
		altitudeZone = zone;
	}

	public boolean isAltitudeZone() {
		return altitudeZone;
	}

	public static void performSeasonalChangesClientSide() {
		for (GOTBiome biome : GOTDimension.GAME_OF_THRONES.biomeList) {
			if (biome != null && biome.climateType != null) {
				switch (GOTDate.AegonCalendar.getSeason()) {
				case WINTER:
					switch (biome.climateType) {
					case COLD:
					case COLD_AZ:
					case NORMAL:
					case NORMAL_AZ:
						biome.biomeColors.setGrass(0xffffff);
						biome.biomeColors.setSky(4212300);
						biome.biomeColors.setFog(6188664);
						biome.biomeColors.setFoggy(false);
						break;
					case SUMMER:
					case SUMMER_AZ:
						biome.biomeColors.resetGrass();
						biome.biomeColors.setSky(11653858);
						biome.biomeColors.resetFog();
						biome.biomeColors.setFoggy(false);
						break;
					case WINTER:
						biome.biomeColors.setGrass(0xffffff);
						biome.biomeColors.setSky(4212300);
						biome.biomeColors.setFog(6188664);
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
						biome.biomeColors.resetGrass();
						biome.biomeColors.setSky(11653858);
						biome.biomeColors.resetFog();
						biome.biomeColors.setFoggy(false);
						break;
					case SUMMER:
					case SUMMER_AZ:
						biome.biomeColors.resetGrass();
						biome.biomeColors.resetSky();
						biome.biomeColors.resetFog();
						biome.biomeColors.setFoggy(false);
						break;
					case WINTER:
						biome.biomeColors.setGrass(0xffffff);
						biome.biomeColors.setSky(4212300);
						biome.biomeColors.setFog(6188664);
						biome.biomeColors.setFoggy(true);
						break;
					}
					break;
				case SUMMER:
					switch (biome.climateType) {
					case COLD:
					case COLD_AZ:
						biome.biomeColors.resetGrass();
						biome.biomeColors.setSky(11653858);
						biome.biomeColors.resetFog();
						biome.biomeColors.setFoggy(false);
						break;
					case NORMAL:
					case NORMAL_AZ:
						biome.biomeColors.resetGrass();
						biome.biomeColors.resetSky();
						biome.biomeColors.resetFog();
						biome.biomeColors.setFoggy(false);
						break;
					case SUMMER:
					case SUMMER_AZ:
						biome.biomeColors.setGrass(14538086);
						biome.biomeColors.setSky(15592678);
						biome.biomeColors.resetFog();
						biome.biomeColors.setFoggy(false);
						break;
					case WINTER:
						biome.biomeColors.setGrass(0xffffff);
						biome.biomeColors.setSky(4212300);
						biome.biomeColors.setFog(6188664);
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
						biome.biomeColors.setGrass(0xd09f4d);
						biome.biomeColors.setSky(11653858);
						biome.biomeColors.resetFog();
						biome.biomeColors.setFoggy(true);
						break;
					case SUMMER:
					case SUMMER_AZ:
						biome.biomeColors.resetGrass();
						biome.biomeColors.resetSky();
						biome.biomeColors.resetFog();
						biome.biomeColors.setFoggy(false);
						break;
					case WINTER:
						biome.biomeColors.setGrass(0xffffff);
						biome.biomeColors.setSky(4212300);
						biome.biomeColors.setFog(6188664);
						biome.biomeColors.setFoggy(true);
						break;
					}
					break;
				}
			}
		}
	}

	public static void performSeasonalChangesServerSide() {
		for (GOTBiome biome : GOTDimension.GAME_OF_THRONES.biomeList) {
			if (biome != null && biome.climateType != null) {
				switch (GOTDate.AegonCalendar.getSeason()) {
				case WINTER:
					switch (biome.climateType) {
					case COLD:
					case COLD_AZ:
					case NORMAL:
					case NORMAL_AZ:
					case WINTER:
						biome.setTemperatureRainfall(0.0F, 2.0F);
						break;
					case SUMMER:
					case SUMMER_AZ:
						biome.setTemperatureRainfall(0.28F, 2.0F);
						break;
					}
					break;
				case SPRING:
					switch (biome.climateType) {
					case COLD:
					case COLD_AZ:
					case NORMAL:
					case NORMAL_AZ:
					case SUMMER_AZ:
						biome.setTemperatureRainfall(0.28F, 0.8F);
						break;
					case SUMMER:
						biome.setTemperatureRainfall(0.8F, 0.8F);
						break;
					case WINTER:
						biome.setTemperatureRainfall(0.0F, 2.0F);
						break;
					}
					break;
				case SUMMER:
					switch (biome.climateType) {
					case COLD:
					case COLD_AZ:
					case NORMAL_AZ:
					case SUMMER_AZ:
						biome.setTemperatureRainfall(0.28F, 0.8F);
						break;
					case NORMAL:
						biome.setTemperatureRainfall(0.8F, 0.8F);
						break;
					case SUMMER:
						biome.setTemperatureRainfall(1.2F, 0.4F);
						break;
					case WINTER:
						biome.setTemperatureRainfall(0.0F, 2.0F);
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
						break;
					case SUMMER:
						biome.setTemperatureRainfall(0.8F, 0.8F);
						break;
					case SUMMER_AZ:
						biome.setTemperatureRainfall(0.28F, 0.8F);
						break;
					case WINTER:
						biome.setTemperatureRainfall(0.0F, 2.0F);
						break;
					}
					break;
				}
			}
		}
	}
}