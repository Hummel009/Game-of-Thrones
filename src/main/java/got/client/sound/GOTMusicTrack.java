package got.client.sound;

import java.io.InputStream;
import java.util.*;

import net.minecraft.client.audio.*;
import net.minecraft.util.ResourceLocation;

public class GOTMusicTrack extends PositionedSound {
	private String filename;
	private String title;
	private Map<GOTBiomeMusic, GOTTrackRegionInfo> regions = new HashMap<>();
	private List<String> authors = new ArrayList<>();

	public GOTMusicTrack(String s) {
		super(GOTMusicTrack.getMusicResource(s));
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

	public void addAuthor(String s) {
		authors.add(s);
	}

	public GOTTrackRegionInfo createRegionInfo(GOTBiomeMusic reg) {
		GOTTrackRegionInfo info = regions.get(reg);
		if (info == null) {
			info = new GOTTrackRegionInfo(reg);
			regions.put(reg, info);
		}
		return info;
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

	public void setTitle(String s) {
		title = s;
	}

	private static ResourceLocation getMusicResource(String s) {
		return new ResourceLocation("gotmusic", s);
	}

	private static class TrackSoundAccessor implements ISoundEventAccessor {
		private SoundPoolEntry soundEntry;
		private int weight;

		private TrackSoundAccessor(SoundPoolEntry e, int i) {
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
