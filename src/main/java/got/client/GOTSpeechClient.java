package got.client;

import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GOTSpeechClient {
	private static final int DISPLAY_TIME = 200;

	private static Map<UUID, TimedSpeech> npcSpeeches = new HashMap<>();

	private GOTSpeechClient() {
	}

	public static void clearAll() {
		npcSpeeches.clear();
	}

	public static TimedSpeech getSpeechFor(Entity npc) {
		UUID key = npc.getUniqueID();
		if (npcSpeeches.containsKey(key)) {
			return npcSpeeches.get(key);
		}
		return null;
	}

	public static boolean hasSpeech(GOTEntityNPC npc) {
		return getSpeechFor(npc) != null;
	}

	public static void receiveSpeech(Entity npc, String speech) {
		npcSpeeches.put(npc.getUniqueID(), new TimedSpeech(speech, DISPLAY_TIME));
	}

	public static void removeSpeech(Entity npc) {
		npcSpeeches.remove(npc.getUniqueID());
	}

	public static void update() {
		Map<UUID, TimedSpeech> newMap = new HashMap<>();
		for (Map.Entry<UUID, TimedSpeech> e : npcSpeeches.entrySet()) {
			UUID key = e.getKey();
			TimedSpeech speech = e.getValue();
			speech.setTime(speech.getTime() - 1);
			if (speech.getTime() > 0) {
				newMap.put(key, speech);
			}
		}
		npcSpeeches = newMap;
	}

	public static class TimedSpeech {
		private final String speech;

		private int time;

		private TimedSpeech(String s, int i) {
			speech = s;
			time = i;
		}

		public float getAge() {
			return (float) time / DISPLAY_TIME;
		}

		public String getSpeech() {
			return speech;
		}

		private int getTime() {
			return time;
		}

		private void setTime(int time) {
			this.time = time;
		}
	}
}