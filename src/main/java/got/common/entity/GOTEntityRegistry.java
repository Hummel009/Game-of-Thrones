package got.common.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import got.GOT;
import got.common.faction.GOTFaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

import java.util.*;

public class GOTEntityRegistry {
	public static final Collection<Class<? extends Entity>> CONTENT = new HashSet<>();

	public static final Map<Integer, SpawnEggInfo> SPAWN_EGGS = new LinkedHashMap<>();
	public static final Map<Class<? extends Entity>, String> ENTITY_CLASS_TO_NAME = new HashMap<>();

	private static final Map<String, Integer> STRING_TO_ID_MAPPING = new HashMap<>();
	private static final Map<Integer, String> ID_TO_STRING_MAPPING = new HashMap<>();
	private static final Map<Class<? extends Entity>, Integer> CLASS_TO_ID_MAPPING = new HashMap<>();

	private GOTEntityRegistry() {
	}

	public static Set<String> getAllEntityNames() {
		return Collections.unmodifiableSet(STRING_TO_ID_MAPPING.keySet());
	}

	public static Class<? extends Entity> getClassFromString(String name) {
		return (Class<? extends Entity>) EntityList.stringToClassMapping.get(name);
	}

	public static int getEntityID(Entity entity) {
		return CLASS_TO_ID_MAPPING.get(entity.getClass());
	}

	public static int getIDFromString(String name) {
		if (!STRING_TO_ID_MAPPING.containsKey(name)) {
			return 0;
		}
		return STRING_TO_ID_MAPPING.get(name);
	}

	public static String getStringFromClass(Class<? extends Entity> entityClass) {
		return (String) EntityList.classToStringMapping.get(entityClass);
	}

	public static String getStringFromID(int id) {
		return ID_TO_STRING_MAPPING.get(id);
	}

	public static void register(Class<? extends Entity> entityClass, int id, GOTFaction faction) {
		registerHidden(entityClass, id, 80, 3, true);
		CONTENT.add(entityClass);
		SPAWN_EGGS.put(id, new SpawnEggInfo(id, faction.getEggColor(), faction.getEggColor()));
	}

	public static void register(Class<? extends Entity> entityClass, int id, int color) {
		registerHidden(entityClass, id, 80, 3, true);
		CONTENT.add(entityClass);
		SPAWN_EGGS.put(id, new SpawnEggInfo(id, color, color));
	}

	public static void registerHidden(Class<? extends Entity> entityClass, int id) {
		registerHidden(entityClass, id, 80, 3, true);
		CONTENT.add(entityClass);
	}

	public static void registerHidden(Class<? extends Entity> entityClass, int id, int updateRange, int updateFreq, boolean sendVelocityUpdates) {
		String name = entityClass.getSimpleName();
		String cut = name.replace("GOTEntity", "");
		EntityRegistry.registerModEntity(entityClass, cut, id, GOT.instance, updateRange, updateFreq, sendVelocityUpdates);
		String fullName = (String) EntityList.classToStringMapping.get(entityClass);
		STRING_TO_ID_MAPPING.put(fullName, id);
		ID_TO_STRING_MAPPING.put(id, fullName);
		CLASS_TO_ID_MAPPING.put(entityClass, id);
		ENTITY_CLASS_TO_NAME.put(entityClass, cut);
	}

	public static void registerLegendaryNPC(Class<? extends Entity> entityClass, int id, GOTFaction faction) {
		registerHidden(entityClass, id, 80, 3, true);
		CONTENT.add(entityClass);
		SPAWN_EGGS.put(id, new SpawnEggInfo(id, 9605778, faction.getEggColor()));
	}

	public static class SpawnEggInfo {
		private final int spawnedID;
		private final int primaryColor;
		private final int secondaryColor;

		protected SpawnEggInfo(int i, int j, int k) {
			spawnedID = i;
			primaryColor = j;
			secondaryColor = k;
		}

		public int getSpawnedID() {
			return spawnedID;
		}

		public int getPrimaryColor() {
			return primaryColor;
		}

		public int getSecondaryColor() {
			return secondaryColor;
		}
	}
}