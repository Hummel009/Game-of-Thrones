package got.common.world.biome;

import got.common.GOTDate;
import got.common.GOTDimension;

import java.awt.*;

public enum GOTClimateType {
	WINTER(false), COLD(false), COLD_AZ(true), SUMMER(false), SUMMER_AZ(true), NORMAL(false), NORMAL_AZ(true);

	private final boolean altitudeZone;

	GOTClimateType(boolean zone) {
		altitudeZone = zone;
	}

	public static void performSeasonalChangesClientSide() {
		for (GOTBiome biome : GOTDimension.GAME_OF_THRONES.getBiomeList()) {
			if (biome != null && biome.getClimateType() != null) {
				GOTClimateType climateType = biome.getClimateType();

				switch (GOTDate.AegonCalendar.getSeason()) {
					case WINTER:
						switch (climateType) {
							case COLD:
							case COLD_AZ:
							case NORMAL:
							case NORMAL_AZ:
								biome.getBiomeColors().setGrass(new Color(0xffffff));
								biome.getBiomeColors().setSky(new Color(4212300));
								biome.getBiomeColors().setFog(new Color(6188664));
								biome.getBiomeColors().setFoggy(false);
								break;
							case SUMMER:
							case SUMMER_AZ:
								biome.getBiomeColors().setGrass(null);
								biome.getBiomeColors().setSky(new Color(11653858));
								biome.getBiomeColors().setFog(null);
								biome.getBiomeColors().setFoggy(false);
								break;
							case WINTER:
								biome.getBiomeColors().setGrass(new Color(0xffffff));
								biome.getBiomeColors().setSky(new Color(4212300));
								biome.getBiomeColors().setFog(new Color(6188664));
								biome.getBiomeColors().setFoggy(true);
								break;
						}
						break;
					case SPRING:
						switch (climateType) {
							case COLD:
							case COLD_AZ:
							case NORMAL:
							case NORMAL_AZ:
								biome.getBiomeColors().setGrass(null);
								biome.getBiomeColors().setSky(new Color(11653858));
								biome.getBiomeColors().setFog(null);
								biome.getBiomeColors().setFoggy(false);
								break;
							case SUMMER:
							case SUMMER_AZ:
								biome.getBiomeColors().setGrass(null);
								biome.getBiomeColors().setSky(null);
								biome.getBiomeColors().setFog(null);
								biome.getBiomeColors().setFoggy(false);
								break;
							case WINTER:
								biome.getBiomeColors().setGrass(new Color(0xffffff));
								biome.getBiomeColors().setSky(new Color(4212300));
								biome.getBiomeColors().setFog(new Color(6188664));
								biome.getBiomeColors().setFoggy(true);
								break;
						}
						break;
					case SUMMER:
						switch (climateType) {
							case COLD:
							case COLD_AZ:
								biome.getBiomeColors().setGrass(null);
								biome.getBiomeColors().setSky(new Color(11653858));
								biome.getBiomeColors().setFog(null);
								biome.getBiomeColors().setFoggy(false);
								break;
							case NORMAL:
							case NORMAL_AZ:
								biome.getBiomeColors().setGrass(null);
								biome.getBiomeColors().setSky(null);
								biome.getBiomeColors().setFog(null);
								biome.getBiomeColors().setFoggy(false);
								break;
							case SUMMER:
							case SUMMER_AZ:
								biome.getBiomeColors().setGrass(new Color(14538086));
								biome.getBiomeColors().setSky(new Color(15592678));
								biome.getBiomeColors().setFog(null);
								biome.getBiomeColors().setFoggy(false);
								break;
							case WINTER:
								biome.getBiomeColors().setGrass(new Color(0xffffff));
								biome.getBiomeColors().setSky(new Color(4212300));
								biome.getBiomeColors().setFog(new Color(6188664));
								biome.getBiomeColors().setFoggy(true);
								break;
						}
						break;
					case AUTUMN:
						switch (climateType) {
							case COLD:
							case COLD_AZ:
							case NORMAL:
							case NORMAL_AZ:
								biome.getBiomeColors().setGrass(new Color(0xd09f4d));
								biome.getBiomeColors().setSky(new Color(11653858));
								biome.getBiomeColors().setFog(null);
								biome.getBiomeColors().setFoggy(true);
								break;
							case SUMMER:
							case SUMMER_AZ:
								biome.getBiomeColors().setGrass(null);
								biome.getBiomeColors().setSky(null);
								biome.getBiomeColors().setFog(null);
								biome.getBiomeColors().setFoggy(false);
								break;
							case WINTER:
								biome.getBiomeColors().setGrass(new Color(0xffffff));
								biome.getBiomeColors().setSky(new Color(4212300));
								biome.getBiomeColors().setFog(new Color(6188664));
								biome.getBiomeColors().setFoggy(true);
								break;
						}
						break;
				}
			}
		}
	}

	public static void performSeasonalChangesServerSide() {
		for (GOTBiome biome : GOTDimension.GAME_OF_THRONES.getBiomeList()) {
			if (biome != null && biome.getClimateType() != null) {
				GOTClimateType climateType = biome.getClimateType();

				switch (GOTDate.AegonCalendar.getSeason()) {
					case WINTER:
						switch (climateType) {
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
						switch (climateType) {
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
						switch (climateType) {
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
						switch (climateType) {
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

	public boolean isAltitudeZone() {
		return altitudeZone;
	}
}