package got.client.sound;

import got.client.GOTClientProxy;
import got.common.GOTConfig;
import got.common.util.GOTCrashHandler;
import got.common.world.biome.GOTBiome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class GOTMusicTicker {
	private static GOTMusicTrack currentTrack;

	private static boolean wasPlayingMenu = true;
	private static int timing = 100;

	private GOTMusicTicker() {
	}

	private static GOTMusicCategory getCurrentCategory(Minecraft mc) {
		WorldClient world = mc.theWorld;
		EntityClientPlayerMP entityplayer = mc.thePlayer;
		if (world != null && entityplayer != null) {
			int k = MathHelper.floor_double(entityplayer.posZ);
			int j = MathHelper.floor_double(entityplayer.boundingBox.minY);
			int i = MathHelper.floor_double(entityplayer.posX);
			if (GOTMusicCategory.isCave(world, i, j, k)) {
				return GOTMusicCategory.CAVE;
			}
			if (GOTMusicCategory.isDay(world)) {
				return GOTMusicCategory.DAY;
			}
			return GOTMusicCategory.NIGHT;
		}
		return null;
	}

	private static GOTMusicRegion.Sub getCurrentRegion(Minecraft mc) {
		WorldClient worldClient = mc.theWorld;
		EntityClientPlayerMP entityClientPlayerMP = mc.thePlayer;
		if (GOTMusic.isMenuMusic()) {
			return GOTMusicRegion.MENU.getWithoutSub();
		}
		if (GOTMusic.isGOTDimension()) {
			int i = MathHelper.floor_double(entityClientPlayerMP.posX);
			int k = MathHelper.floor_double(entityClientPlayerMP.posZ);
			if (GOTClientProxy.doesClientChunkExist(worldClient, i, k)) {
				BiomeGenBase biome = GOTCrashHandler.getBiomeGenForCoords(worldClient, i, k);
				if (biome instanceof GOTBiome) {
					GOTBiome gotbiome = (GOTBiome) biome;
					return gotbiome.getBiomeMusic();
				}
			}
		}
		return null;
	}

	private static GOTMusicTrack getNewTrack(Minecraft mc, Random rand) {
		GOTMusicRegion.Sub regionSub = getCurrentRegion(mc);
		GOTMusicCategory category = getCurrentCategory(mc);
		if (regionSub != null) {
			GOTMusicRegion region = regionSub.getRegion();
			String sub = regionSub.getSubregion();
			GOTTrackSorter.Filter filter = category != null ? GOTTrackSorter.forRegionAndCategory(region, category) : GOTTrackSorter.forAny();
			GOTRegionTrackPool trackPool = GOTMusic.getTracksForRegion(region, sub);
			return trackPool.getRandomTrack(rand, filter);
		}
		return null;
	}

	private static void resetTiming(Random rand) {
		timing = GOTMusic.isMenuMusic() ? MathHelper.getRandomIntegerInRange(rand, GOTConfig.musicIntervalMenuMin * 20, GOTConfig.musicIntervalMenuMax * 20) : MathHelper.getRandomIntegerInRange(rand, GOTConfig.musicIntervalMin * 20, GOTConfig.musicIntervalMax * 20);
	}

	public static void update(Random rand) {
		Minecraft mc = Minecraft.getMinecraft();
		boolean noMusic = mc.gameSettings.getSoundLevel(SoundCategory.MUSIC) <= 0.0f;
		boolean menu = GOTMusic.isMenuMusic();
		if (wasPlayingMenu != menu) {
			if (currentTrack != null) {
				mc.getSoundHandler().stopSound(currentTrack);
				currentTrack = null;
			}
			wasPlayingMenu = menu;
			timing = 100;
		}
		if (currentTrack != null) {
			if (noMusic) {
				mc.getSoundHandler().stopSound(currentTrack);
			}
			if (!mc.getSoundHandler().isSoundPlaying(currentTrack)) {
				currentTrack = null;
				resetTiming(rand);
			}
		}
		if (!noMusic) {
			boolean update = menu || GOTMusic.isGOTDimension() && !Minecraft.getMinecraft().isGamePaused();
			if (update && currentTrack == null && --timing <= 0) {
				currentTrack = getNewTrack(mc, rand);
				if (currentTrack != null) {
					wasPlayingMenu = menu;
					mc.getSoundHandler().playSound(currentTrack);
					timing = Integer.MAX_VALUE;
				} else {
					timing = 400;
				}
			}
		}
	}

	public static GOTMusicTrack getCurrentTrack() {
		return currentTrack;
	}
}