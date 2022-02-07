package got.client.sound;

import java.util.Random;

import got.client.GOTClientProxy;
import got.common.GOTConfig;
import got.common.world.biome.GOTBiome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;

public class GOTMusicTicker {
	private static GOTMusicTrack currentTrack;
	private static boolean wasPlayingMenu = true;
	private static int timing = 100;

	private static GOTMusicCategory getCurrentCategory(Minecraft mc, Random rand) {
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

	private static GOTBiomeMusic.MusicRegion getCurrentRegion(Minecraft mc, Random rand) {
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

	public static GOTMusicTrack getCurrentTrack() {
		return currentTrack;
	}

	private static GOTMusicTrack getNewTrack(Minecraft mc, Random rand) {
		GOTBiomeMusic.MusicRegion regionSub = GOTMusicTicker.getCurrentRegion(mc, rand);
		GOTMusicCategory category = GOTMusicTicker.getCurrentCategory(mc, rand);
		if (regionSub != null) {
			GOTBiomeMusic region = regionSub.getRegion();
			String sub = regionSub.getSubregion();
			GOTTrackSorter.Filter filter = category != null ? GOTTrackSorter.forRegionAndCategory(region, category) : GOTTrackSorter.forAny();
			GOTRegionTrackPool trackPool = GOTMusic.getTracksForRegion(region, sub);
			return trackPool.getRandomTrack(rand, filter);
		}
		return null;
	}

	public static void resetTiming(Random rand) {
		timing = GOTMusic.isMenuMusic() ? MathHelper.getRandomIntegerInRange(rand, GOTConfig.musicIntervalMenuMin * 20, GOTConfig.musicIntervalMenuMax * 20) : MathHelper.getRandomIntegerInRange(rand, GOTConfig.musicIntervalMin * 20, GOTConfig.musicIntervalMax * 20);
	}

	public static void setCurrentTrack(GOTMusicTrack currentTrack) {
		GOTMusicTicker.currentTrack = currentTrack;
	}

	static void update(Random rand) {
		Minecraft mc = Minecraft.getMinecraft();
		boolean noMusic = mc.gameSettings.getSoundLevel(SoundCategory.MUSIC) <= 0.0f;
		boolean menu = GOTMusic.isMenuMusic();
		if (wasPlayingMenu != menu) {
			if (getCurrentTrack() != null) {
				mc.getSoundHandler().stopSound(getCurrentTrack());
				setCurrentTrack(null);
			}
			wasPlayingMenu = menu;
			timing = 100;
		}
		if (getCurrentTrack() != null) {
			if (noMusic) {
				mc.getSoundHandler().stopSound(getCurrentTrack());
			}
			if (!mc.getSoundHandler().isSoundPlaying(getCurrentTrack())) {
				setCurrentTrack(null);
				GOTMusicTicker.resetTiming(rand);
			}
		}
		if (!noMusic) {
			boolean update = false;
			if (menu) {
				update = true;
			} else {
				update = GOTMusic.isGOTDimension() && !Minecraft.getMinecraft().isGamePaused();
			}
			if (update && getCurrentTrack() == null && --timing <= 0) {
				setCurrentTrack(GOTMusicTicker.getNewTrack(mc, rand));
				if (getCurrentTrack() != null) {
					wasPlayingMenu = menu;
					mc.getSoundHandler().playSound(getCurrentTrack());
					timing = Integer.MAX_VALUE;
				} else {
					timing = 400;
				}
			}
		}
	}
}
