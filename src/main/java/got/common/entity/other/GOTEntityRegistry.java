package got.common.entity.other;

import cpw.mods.fml.common.registry.EntityRegistry;
import got.GOT;
import got.common.entity.GOTEntity;
import got.common.faction.GOTFaction;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.*;

public class GOTEntityRegistry {
	public static Map<Integer, SpawnEggInfo> spawnEggs = new LinkedHashMap<>();
	public static Map<String, Integer> stringToIDMapping = new HashMap<>();
	public static Map<Integer, String> IDToStringMapping = new HashMap<>();
	public static Map<Class, String> classToNameMapping = new HashMap<>();
	public static Map<Class, Integer> classToIDMapping = new HashMap<>();
	public static Map<Class, GOTFaction> classToFactionMapping = new HashMap<>();
	public static Set<Class> entitySet = new HashSet<>();

	public static Entity createEntityByClass(Class entityClass, World world) {
		Entity entity = null;
		try {
			entity = (Entity) entityClass.getConstructor(World.class).newInstance(world);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return entity;
	}

	public static Set<String> getAllEntityNames() {
		return Collections.unmodifiableSet(stringToIDMapping.keySet());
	}

	public static Class<? extends Entity> getClassFromString(String name) {
		return EntityList.stringToClassMapping.get(name);
	}

	public static int getEntityID(Entity entity) {
		return GOTEntityRegistry.getEntityIDFromClass(entity.getClass());
	}

	public static int getEntityIDFromClass(Class entityClass) {
		return classToIDMapping.get(entityClass);
	}

	public static int getIDFromString(String name) {
		if (!stringToIDMapping.containsKey(name)) {
			return 0;
		}
		return stringToIDMapping.get(name);
	}

	public static String getStringFromClass(Class entityClass) {
		return EntityList.classToStringMapping.get(entityClass);
	}

	public static String getStringFromID(int id) {
		return IDToStringMapping.get(id);
	}

	public static void preInit() {
		GOTEntity.preInit();
	}

	public static void register(Class<? extends Entity> entityClass, int id, GOTFaction faction) {
		registerHidden(entityClass, id, 80, 3, true);
		entitySet.add(entityClass);
		spawnEggs.put(id, new SpawnEggInfo(id, faction.eggColor, faction.eggColor));
		classToFactionMapping.put(entityClass, faction);
	}

	public static void register(Class<? extends Entity> entityClass, int id, int color) {
		registerHidden(entityClass, id, 80, 3, true);
		entitySet.add(entityClass);
		spawnEggs.put(id, new SpawnEggInfo(id, color, color));
	}

	public static void registerHidden(Class<? extends Entity> entityClass, int id) {
		registerHidden(entityClass, id, 80, 3, true);
	}

	public static void registerHidden(Class<? extends Entity> entityClass, int id, int updateRange, int updateFreq, boolean sendVelocityUpdates) {
		String name = entityClass.getSimpleName();
		String cut = name.replace("GOTEntity", "");
		EntityRegistry.registerModEntity(entityClass, cut, id, GOT.instance, updateRange, updateFreq, sendVelocityUpdates);
		String fullName = EntityList.classToStringMapping.get(entityClass);
		stringToIDMapping.put(fullName, id);
		IDToStringMapping.put(id, fullName);
		classToIDMapping.put(entityClass, id);
		classToNameMapping.put(entityClass, cut);
	}

	public static void registerLegendaryNPC(Class<? extends Entity> entityClass, int id, GOTFaction faction) {
		registerHidden(entityClass, id, 80, 3, true);
		entitySet.add(entityClass);
		spawnEggs.put(id, new SpawnEggInfo(id, 9605778, faction.eggColor));
		classToFactionMapping.put(entityClass, faction);
	}

	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (GOTEntityRegistry.SpawnEggInfo info : GOTEntityRegistry.spawnEggs.values()) {
			list.add(new ItemStack(item, 1, info.spawnedID));
		}
	}

	public static class SpawnEggInfo {
		public int spawnedID;
		public int primaryColor;
		public int secondaryColor;

		public SpawnEggInfo(int i, int j, int k) {
			spawnedID = i;
			primaryColor = j;
			secondaryColor = k;
		}
	}
}
