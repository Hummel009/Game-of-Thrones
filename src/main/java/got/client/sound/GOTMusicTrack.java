package got.client.sound;

import net.minecraft.client.audio.*;
import net.minecraft.util.ResourceLocation;

import java.io.InputStream;
import java.util.*;

public class GOTMusicTrack extends PositionedSound {
	public String filename;
	public String title;
	public Map<GOTBiomeMusic, GOTTrackRegionInfo> regions = new EnumMap<>(GOTBiomeMusic.class);
	public List<String> authors = new ArrayList<>();

	public GOTMusicTrack(String s) {
		super(getMusicResource(s));
		volume = 1.0f;
		field_147663_c = 1.0f;
		xPosF = 0.0f;
		yPosF = 0.0f;
		zPosF = 0.0f;
		repeat = false;
		field_147665_h = 0;
		field_147666_i = ISound.AttenuationType.NONE;
		filename = s;
	}

	public static ResourceLocation getMusicResource(String s) {
		return new ResourceLocation("gotmusic", s);
	}

	public void addAuthor(String s) {
		authors.add(s);
	}

	public GOTTrackRegionInfo createRegionInfo(GOTBiomeMusic reg) {
		return regions.computeIfAbsent(reg, GOTTrackRegionInfo::new);
	}

	public Set<GOTBiomeMusic> getAllRegions() {
		return regions.keySet();
	}

	public List<String> getAuthors() {
		return authors;
	}

	public GOTTrackRegionInfo getRegionInfo(GOTBiomeMusic reg) {
		if (regions.containsKey(reg)) {
			return regions.get(reg);
		}
		return null;
	}

	public String getTitle() {
		if (title != null) {
			return title;
		}
		return filename;
	}

	public void setTitle(String s) {
		title = s;
	}

	public void loadSoundResource() {
		SoundEventAccessorComposite soundAccessorComp;
		ResourceLocation resource = getPositionedSoundLocation();
		SoundList soundList = new SoundList();
		soundList.setReplaceExisting(true);
		soundList.setSoundCategory(SoundCategory.MUSIC);
		SoundList.SoundEntry soundEntry = new SoundList.SoundEntry();
		soundEntry.setSoundEntryName(filename);
		soundEntry.setSoundEntryVolume(getVolume());
		soundEntry.setSoundEntryPitch(getPitch());
		soundEntry.setSoundEntryWeight(1);
		soundEntry.setSoundEntryType(SoundList.SoundEntry.Type.SOUND_EVENT);
		soundEntry.setStreaming(true);
		soundList.getSoundList().add(soundEntry);
		SoundRegistry sndRegistry = GOTMusic.Reflect.getSoundRegistry();
		assert sndRegistry != null;
		if (sndRegistry.containsKey(resource) && !soundList.canReplaceExisting()) {
			soundAccessorComp = (SoundEventAccessorComposite) sndRegistry.getObject(resource);
		} else {
			soundAccessorComp = new SoundEventAccessorComposite(resource, 1.0, 1.0, soundList.getSoundCategory());
			sndRegistry.registerSound(soundAccessorComp);
		}
		SoundPoolEntry soundPoolEntry = new SoundPoolEntry(resource, soundEntry.getSoundEntryPitch(), soundEntry.getSoundEntryVolume(), soundEntry.isStreaming());
		TrackSoundAccessor soundAccessor = new TrackSoundAccessor(soundPoolEntry, soundEntry.getSoundEntryWeight());
		soundAccessorComp.addSoundToEventPool(soundAccessor);
	}

	public void loadTrack(InputStream in) {
		loadSoundResource();
		GOTMusic.addTrackToRegions(this);
	}

	public static class TrackSoundAccessor implements ISoundEventAccessor {
		public SoundPoolEntry soundEntry;
		public int weight;

		public TrackSoundAccessor(SoundPoolEntry e, int i) {
			soundEntry = e;
			weight = i;
		}

		@Override
		public SoundPoolEntry func_148720_g() {
			return new SoundPoolEntry(soundEntry);
		}

		@Override
		public int func_148721_a() {
			return weight;
		}
	}

}
