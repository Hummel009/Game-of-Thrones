package got.client.sound;

import got.client.GOTClientProxy;
import got.common.GOTConfig;
import got.common.world.biome.GOTBiome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class GOTMusicTicker {
	public static GOTMusicTrack currentTrack;
	public static boolean wasPlayingMenu = true;
	public static int firstTiming = 100;
	public static int timing = 100;
	public static int nullTrackResetTiming = 400;

	public static GOTMusicCategory getCurrentCategory(Minecraft mc, Random rand) {
		WorldClient world = mc.theWorld;
		EntityClientPlayerMP entityplayer = mc.thePlayer;
		if (world != null && entityplayer != null) {
			int i = MathHelper.floor_double(entityplayer.posX);
			if (GOTMusicCategory.isCave(world, i, MathHelper.floor_double(entityplayer.boundingBox.minY), MathHelper.floor_double(entityplayer.posZ))) {
				return GOTMusicCategory.CAVE;
			}
			if (GOTMusicCategory.isDay(world)) {
				return GOTMusicCategory.DAY;
			}
			return GOTMusicCategory.NIGHT;
		}
		return null;
	}

	public static GOTBiomeMusic.MusicRegion getCurrentRegion(Minecraft mc, Random rand) {
		int k;
		int i;
		BiomeGenBase biome;
		WorldClient world = mc.theWorld;
		EntityClientPlayerMP entityplayer = mc.thePlayer;
		if (GOTMusic.isMenuMusic()) {
			return GOTBiomeMusic.MENU.getWithoutSub();
		}
		if (GOTMusic.isGOTDimension() && GOTClientProxy.doesClientChunkExist(world, i = MathHelper.floor_double(entityplayer.posX), k = MathHelper.floor_double(entityplayer.posZ)) && (biome = world.getBiomeGenForCoords(i, k)) instanceof GOTBiome) {
			GOTBiome gotbiome = (GOTBiome) biome;
			return gotbiome.getBiomeMusic();
		}
		return null;
	}

	public static GOTMusicTrack getNewTrack(Minecraft mc, Random rand) {
		GOTBiomeMusic.MusicRegion regionSub = getCurrentRegion(mc, rand);
		GOTMusicCategory category = getCurrentCategory(mc, rand);
		if (regionSub != null) {
			GOTBiomeMusic region = regionSub.region;
			String sub = regionSub.subregion;
			GOTTrackSorter.Filter filter = category != null ? GOTTrackSorter.forRegionAndCategory(region, category) : GOTTrackSorter.forAny();
			GOTRegionTrackPool trackPool = GOTMusic.getTracksForRegion(region, sub);
			return trackPool.getRandomTrack(rand, filter);
		}
		return null;
	}

	public static void resetTiming(Random rand) {
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
			boolean update = false;
			if (menu) {
				update = true;
			} else {
				update = GOTMusic.isGOTDimension() && !Minecraft.getMinecraft().isGamePaused();
			}
			if (update && currentTrack == null) {
				--timing;
				if (timing <= 0) {
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
	}
}
