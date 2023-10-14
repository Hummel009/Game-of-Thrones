package got.client;

import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GOTSpeechClient {
	public static Map<UUID, TimedSpeech> npcSpeeches = new HashMap<>();
	public static int DISPLAY_TIME = 200;

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
			speech.time--;
			if (speech.time > 0) {
				newMap.put(key, speech);
			}
		}
		npcSpeeches = newMap;
	}

	public static class TimedSpeech {
		public String speech;
		public int time;

		public TimedSpeech(String s, int i) {
			speech = s;
			time = i;
		}

		public float getAge() {
			return (float) time / DISPLAY_TIME;
		}

		public String getSpeech() {
			return speech;
		}
	}

}
