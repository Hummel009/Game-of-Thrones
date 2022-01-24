package got.common.world.structure.other;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import got.common.faction.GOTFaction;
import got.common.world.structure.GOTStructure;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GOTStructureRegistry {
	public static HashMap<Integer, IStructureProvider> idToClassMapping = new HashMap();
	public static HashMap<Integer, String> idToStringMapping = new HashMap();
	public static HashMap<Integer, StructureColorInfo> structureItemSpawners = new LinkedHashMap<>();
	public static HashMap<Class<? extends WorldGenerator>, String> classToNameMapping = new HashMap();
	public static HashMap<Class<? extends WorldGenerator>, GOTFaction> classToFactionMapping = new HashMap();

	public static String getNameFromID(int ID) {
		return idToStringMapping.get(ID);
	}

	public static int getRotationFromPlayer(EntityPlayer entityplayer) {
		return MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
	}

	public static IStructureProvider getStructureForID(int ID) {
		return idToClassMapping.get(ID);
	}

	public static String getStructureName(Class<? extends WorldGenerator> entityClass) {
		return StatCollector.translateToLocal("got.structure." + GOTStructureRegistry.getStructureNameFromClass(entityClass) + ".name");
	}

	public static String getStructureNameFromClass(Class entityClass) {
		return classToNameMapping.get(entityClass);
	}

	public static void onInit() {
		GOTStructure.onInit();
	}

	public static void register(int id, Class<? extends WorldGenerator> strClass, String name, GOTFaction faction) {
		GOTStructureRegistry.registerStructure(id, strClass, name, faction.eggColor, faction.eggColor, false);
		classToNameMapping.put(strClass, name);
		classToFactionMapping.put(strClass, faction);
	}

	public static void register(int id, Class<? extends WorldGenerator> strClass, String name, int color) {
		GOTStructureRegistry.registerStructure(id, strClass, name, color, color, false);
		classToNameMapping.put(strClass, name);
	}

	public static void register(int id, GOTVillageGen village, String name, GOTFaction faction, IVillageProperties properties) {
		IStructureProvider strProvider = new IStructureProvider() {

			@Override
			public boolean generateStructure(World world, EntityPlayer entityplayer, int i, int j, int k) {
				GOTVillageGen.AbstractInstance<?> instance = village.createAndSetupVillageInstance(world, i, k, world.rand, LocationInfo.SPAWNED_BY_PLAYER);
				instance.setRotation((GOTStructureRegistry.getRotationFromPlayer(entityplayer) + 2) % 4);
				properties.apply(instance);
				village.generateCompleteVillageInstance(instance, world, i, k);
				return true;
			}

			@Override
			public boolean isVillage() {
				return true;
			}
		};
		GOTStructureRegistry.registerStructure(id, strProvider, name, faction.eggColor, faction.eggColor, false);
	}

	public static void registerStructure(int id, Class<? extends WorldGenerator> strClass, String name, int colorBG, int colorFG, boolean hide) {
		IStructureProvider strProvider = new IStructureProvider() {

			@Override
			public boolean generateStructure(World world, EntityPlayer entityplayer, int i, int j, int k) {
				WorldGenerator generator = null;
				try {
					generator = strClass.getConstructor(Boolean.TYPE).newInstance(true);
				} catch (Exception e) {
					FMLLog.warning("Failed to build GOT structure " + strClass.getName());
					e.printStackTrace();
				}
				if ((generator != null) && (generator instanceof GOTStructureBase)) {
					GOTStructureBase strGen = (GOTStructureBase) generator;
					strGen.restrictions = false;
					strGen.usingPlayer = entityplayer;
					return strGen.generate(world, world.rand, i, j, k, strGen.usingPlayerRotation());
				}
				return false;
			}

			@Override
			public boolean isVillage() {
				return false;
			}
		};
		GOTStructureRegistry.registerStructure(id, strProvider, name, colorBG, colorFG, hide);
	}

	public static void registerStructure(int id, IStructureProvider str, String name, int colorBG, int colorFG, boolean hide) {
		idToClassMapping.put(id, str);
		idToStringMapping.put(id, name);
		structureItemSpawners.put(id, new StructureColorInfo(id, colorBG, colorFG, str.isVillage(), hide));
	}

	public static void registerVillage(int id, GOTVillageGen village, String name, int colorBG, int colorFG, IVillageProperties properties) {
		IStructureProvider strProvider = new IStructureProvider() {

			@Override
			public boolean generateStructure(World world, EntityPlayer entityplayer, int i, int j, int k) {
				GOTVillageGen.AbstractInstance<?> instance = village.createAndSetupVillageInstance(world, i, k, world.rand, LocationInfo.SPAWNED_BY_PLAYER);
				instance.setRotation((GOTStructureRegistry.getRotationFromPlayer(entityplayer) + 2) % 4);
				properties.apply(instance);
				village.generateCompleteVillageInstance(instance, world, i, k);
				return true;
			}

			@Override
			public boolean isVillage() {
				return true;
			}
		};
		GOTStructureRegistry.registerStructure(id, strProvider, name, colorBG, colorFG, false);
	}

	public interface IStructureProvider {
		boolean generateStructure(World var1, EntityPlayer var2, int var3, int var4, int var5);

		boolean isVillage();
	}

	public interface IVillageProperties<V> {
		void apply(V var1);
	}

	public static class StructureColorInfo {
		public int spawnedID;
		public int colorBackground;
		public int colorForeground;
		public boolean isVillage;
		public boolean isHidden;

		public StructureColorInfo(int i, int colorBG, int colorFG, boolean vill, boolean hide) {
			spawnedID = i;
			colorBackground = colorBG;
			colorForeground = colorFG;
			isVillage = vill;
			isHidden = hide;
		}
	}

}
