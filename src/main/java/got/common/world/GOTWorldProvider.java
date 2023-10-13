package got.common.world;

import com.google.common.math.IntMath;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import got.GOT;
import got.client.render.other.GOTCloudRenderer;
import got.client.render.other.GOTRenderSky;
import got.client.render.other.GOTRenderWeather;
import got.common.*;
import got.common.util.GOTModChecker;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.GOTClimateType;
import got.common.world.biome.other.GOTBiomeOcean;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.common.ForgeModContainer;

public class GOTWorldProvider extends WorldProvider {
	public static int MOON_PHASES = 8;
	@SideOnly(Side.CLIENT)
	public IRenderHandler gotSkyRenderer;
	@SideOnly(Side.CLIENT)
	public IRenderHandler gotCloudRenderer;
	@SideOnly(Side.CLIENT)
	public IRenderHandler gotWeatherRenderer;
	public boolean spawnHostiles = true;
	public boolean spawnPeacefuls = true;
	public double cloudsR;
	public double cloudsG;
	public double cloudsB;
	public double fogR;
	public double fogG;
	public double fogB;

	@Override
	public float calculateCelestialAngle(long time, float partialTick) {
		float daytime = ((int) (time % GOTTime.DAY_LENGTH) + partialTick) / GOTTime.DAY_LENGTH - 0.25f;
		if (daytime < 0.0f) {
			daytime += 1.0f;
		}
		if (daytime > 1.0f) {
			daytime -= 1.0f;
		}
		float angle = 1.0f - (float) ((Math.cos(daytime * 3.141592653589793) + 1.0) / 2.0);
		return daytime + (angle - daytime) / 3.0f;
	}

	@Override
	public boolean canBlockFreeze(int i, int j, int k, boolean isBlockUpdate) {
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof GOTBiomeOcean) {
			return GOTBiomeOcean.isFrozen(i, k) && canFreezeIgnoreTemp(i, j, k, isBlockUpdate);
		}
		boolean standardColdBiome = biome instanceof GOTBiome && ((GOTBiome) biome).getClimateType() == GOTClimateType.WINTER;
		boolean altitudeColdBiome = biome instanceof GOTBiome && ((GOTBiome) biome).getClimateType() != null && ((GOTBiome) biome).getClimateType().isAltitudeZone() && k >= 140;
		if (standardColdBiome || altitudeColdBiome) {
			return worldObj.canBlockFreezeBody(i, j, k, isBlockUpdate);
		}
		return false;
	}

	public boolean canFreezeIgnoreTemp(int i, int j, int k, boolean isBlockUpdate) {
		Block block;
		if (j >= 0 && j < worldObj.getHeight() && worldObj.getSavedLightValue(EnumSkyBlock.Block, i, j, k) < 10 && ((block = worldObj.getBlock(i, j, k)) == Blocks.water || block == Blocks.flowing_water) && worldObj.getBlockMetadata(i, j, k) == 0) {
			if (!isBlockUpdate) {
				return true;
			}
			boolean surroundWater = worldObj.getBlock(i - 1, j, k).getMaterial() == Material.water;
			if (surroundWater && worldObj.getBlock(i + 1, j, k).getMaterial() != Material.water) {
				surroundWater = false;
			}
			if (surroundWater && worldObj.getBlock(i, j, k - 1).getMaterial() != Material.water) {
				surroundWater = false;
			}
			if (surroundWater && worldObj.getBlock(i, j, k + 1).getMaterial() != Material.water) {
				surroundWater = false;
			}
			return !surroundWater;
		}
		return false;
	}

	@Override
	public boolean canSnowAt(int i, int j, int k, boolean checkLight) {
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof GOTBiomeOcean) {
			return GOTBiomeOcean.isFrozen(i, k) && canSnowIgnoreTemp(i, j, k, checkLight);
		}
		boolean standardColdBiome = biome instanceof GOTBiome && ((GOTBiome) biome).getClimateType() == GOTClimateType.WINTER;
		boolean altitudeColdBiome = biome instanceof GOTBiome && ((GOTBiome) biome).getClimateType() != null && ((GOTBiome) biome).getClimateType().isAltitudeZone() && k >= 140;
		if (standardColdBiome || altitudeColdBiome) {
			return worldObj.canSnowAtBody(i, j, k, checkLight);
		}
		return false;
	}

	public boolean canSnowIgnoreTemp(int i, int j, int k, boolean checkLight) {
		if (!checkLight) {
			return true;
		}
		return j >= 0 && j < worldObj.getHeight() && worldObj.getSavedLightValue(EnumSkyBlock.Block, i, j, k) < 10 && worldObj.getBlock(i, j, k).getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(worldObj, i, j, k);
	}

	@Override
	public IChunkProvider createChunkGenerator() {
		return new GOTChunkProvider(worldObj, worldObj.getSeed());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean doesXZShowFog(int i, int k) {
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, k);
		if (biome instanceof GOTBiome) {
			return ((GOTBiome) biome).hasFog();
		}
		return super.doesXZShowFog(i, k);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 drawClouds(float f) {
		Minecraft mc = Minecraft.getMinecraft();
		int i = (int) mc.renderViewEntity.posX;
		int k = (int) mc.renderViewEntity.posZ;
		Vec3 clouds = super.drawClouds(f);
		cloudsB = 0.0;
		cloudsG = 0.0;
		cloudsR = 0.0;
		GameSettings settings = mc.gameSettings;
		int[] ranges = ForgeModContainer.blendRanges;
		int distance = 0;
		if (settings.fancyGraphics && settings.renderDistanceChunks >= 0 && settings.renderDistanceChunks < ranges.length) {
			distance = ranges[settings.renderDistanceChunks];
		}
		int l = 0;
		for (int i1 = -distance; i1 <= distance; ++i1) {
			for (int k1 = -distance; k1 <= distance; ++k1) {
				Vec3 tempClouds = Vec3.createVectorHelper(clouds.xCoord, clouds.yCoord, clouds.zCoord);
				BiomeGenBase biome = worldObj.getBiomeGenForCoords(i + i1, k + k1);
				if (biome instanceof GOTBiome) {
					((GOTBiome) biome).getCloudColor(tempClouds);
				}
				cloudsR += tempClouds.xCoord;
				cloudsG += tempClouds.yCoord;
				cloudsB += tempClouds.zCoord;
				++l;
			}
		}
		cloudsR /= l;
		cloudsG /= l;
		cloudsB /= l;
		return Vec3.createVectorHelper(cloudsR, cloudsG, cloudsB);
	}

	@Override
	public BiomeGenBase getBiomeGenForCoords(int i, int k) {
		Chunk chunk;
		if (worldObj.blockExists(i, 0, k) && (chunk = worldObj.getChunkFromBlockCoords(i, k)) != null) {
			int chunkX = i & 0xF;
			int chunkZ = k & 0xF;
			int biomeID = chunk.getBiomeArray()[chunkZ << 4 | chunkX] & 0xFF;
			if (biomeID == 255) {
				BiomeGenBase biomegenbase = worldChunkMgr.getBiomeGenAt((chunk.xPosition << 4) + chunkX, (chunk.zPosition << 4) + chunkZ);
				biomeID = biomegenbase.biomeID;
				chunk.getBiomeArray()[chunkZ << 4 | chunkX] = (byte) (biomeID & 0xFF);
			}
			GOTDimension dim = getGOTDimension();
			return dim.biomeList[biomeID] == null ? dim.biomeList[0] : dim.biomeList[biomeID];
		}
		return worldChunkMgr.getBiomeGenAt(i, k);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getCloudHeight() {
		return 192.0f;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getCloudRenderer() {
		if (!GOTModChecker.hasShaders() && GOTConfig.cloudRange > 0) {
			if (gotCloudRenderer == null) {
				gotCloudRenderer = new GOTCloudRenderer();
			}
			return gotCloudRenderer;
		}
		return super.getCloudRenderer();
	}

	@Override
	public String getDepartMessage() {
		return StatCollector.translateToLocalFormatted("got.dimension.exit", getGOTDimension().getDimensionName());
	}

	@Override
	public String getDimensionName() {
		return getGOTDimension().dimensionName;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float f, float f1) {
		Minecraft mc = Minecraft.getMinecraft();
		int i = (int) mc.renderViewEntity.posX;
		int k = (int) mc.renderViewEntity.posZ;
		Vec3 fog = super.getFogColor(f, f1);
		fogB = 0.0;
		fogG = 0.0;
		fogR = 0.0;
		GameSettings settings = mc.gameSettings;
		int[] ranges = ForgeModContainer.blendRanges;
		int distance = 0;
		if (settings.fancyGraphics && settings.renderDistanceChunks >= 0 && settings.renderDistanceChunks < ranges.length) {
			distance = ranges[settings.renderDistanceChunks];
		}
		int l = 0;
		for (int i1 = -distance; i1 <= distance; ++i1) {
			for (int k1 = -distance; k1 <= distance; ++k1) {
				Vec3 tempFog = Vec3.createVectorHelper(fog.xCoord, fog.yCoord, fog.zCoord);
				BiomeGenBase biome = worldObj.getBiomeGenForCoords(i + i1, k + k1);
				if (biome instanceof GOTBiome) {
					((GOTBiome) biome).getFogColor(tempFog);
				}
				fogR += tempFog.xCoord;
				fogG += tempFog.yCoord;
				fogB += tempFog.zCoord;
				++l;
			}
		}
		fogR /= l;
		fogG /= l;
		fogB /= l;
		return Vec3.createVectorHelper(fogR, fogG, fogB);
	}

	public GOTDimension getGOTDimension() {
		return GOTDimension.GAME_OF_THRONES;
	}

	@Override
	public int getMoonPhase(long time) {
		return getGOTMoonPhase();
	}

	@Override
	public String getSaveFolder() {
		return getGOTDimension().dimensionName;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		if (!GOTModChecker.hasShaders() && GOTConfig.enableGOTSky) {
			if (gotSkyRenderer == null) {
				gotSkyRenderer = new GOTRenderSky(this);
			}
			return gotSkyRenderer;
		}
		return super.getSkyRenderer();
	}

	@Override
	public ChunkCoordinates getSpawnPoint() {
		return new ChunkCoordinates(GOTLevelData.gameOfThronesPortalX, GOTLevelData.gameOfThronesPortalY, GOTLevelData.gameOfThronesPortalZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getWeatherRenderer() {
		if (gotWeatherRenderer == null) {
			gotWeatherRenderer = new GOTRenderWeather();
		}
		return gotWeatherRenderer;
	}

	@Override
	public String getWelcomeMessage() {
		return StatCollector.translateToLocalFormatted("got.dimension.enter", getGOTDimension().getDimensionName());
	}

	public float[] handleFinalFogColors(EntityLivingBase viewer, double tick, float[] rgb) {
		return rgb;
	}

	public float[] modifyFogIntensity(float farPlane, int fogMode) {
		Minecraft mc = Minecraft.getMinecraft();
		int i = (int) mc.renderViewEntity.posX;
		int k = (int) mc.renderViewEntity.posZ;
		float fogStart = 0.0f;
		float fogEnd = 0.0f;
		GameSettings settings = mc.gameSettings;
		int[] ranges = ForgeModContainer.blendRanges;
		int distance = 0;
		if (settings.fancyGraphics && settings.renderDistanceChunks >= 0 && settings.renderDistanceChunks < ranges.length) {
			distance = ranges[settings.renderDistanceChunks];
		}
		int l = 0;
		for (int i1 = -distance; i1 <= distance; ++i1) {
			for (int k1 = -distance; k1 <= distance; ++k1) {
				float thisFogStart;
				float thisFogEnd;
				boolean foggy = doesXZShowFog(i + i1, k + k1);
				if (foggy) {
					thisFogStart = farPlane * 0.05f;
					thisFogEnd = Math.min(farPlane, 192.0f) * 0.5f;
				} else {
					if (fogMode < 0) {
						thisFogStart = 0.0f;
					} else {
						thisFogStart = farPlane * 0.75f;
					}
					thisFogEnd = farPlane;
				}
				fogStart += thisFogStart;
				fogEnd += thisFogEnd;
				++l;
			}
		}
		return new float[] { fogStart / l, fogEnd / l };
	}

	@Override
	public void registerWorldChunkManager() {
		worldChunkMgr = new GOTWorldChunkManager(worldObj, getGOTDimension());
		dimensionId = getGOTDimension().dimensionID;
	}

	@Override
	public void resetRainAndThunder() {
		super.resetRainAndThunder();
		if (GOT.doDayCycle(worldObj)) {
			GOTTime.advanceToMorning();
		}
	}

	public void setRingPortalLocation(int i, int j, int k) {
		GOTLevelData.markGameOfThronesPortalLocation(i, j, k);
	}

	@Override
	public void setSpawnPoint(int i, int j, int k) {
	}

	@Override
	public boolean shouldMapSpin(String entity, double x, double y, double z) {
		return false;
	}

	public static int getGOTMoonPhase() {
		int day = GOTDate.AegonCalendar.currentDay;
		return IntMath.mod(day, MOON_PHASES);
	}

	public static boolean isLunarEclipse() {
		int day = GOTDate.AegonCalendar.currentDay;
		return getGOTMoonPhase() == 0 && IntMath.mod(day / MOON_PHASES, 4) == 3;
	}
}