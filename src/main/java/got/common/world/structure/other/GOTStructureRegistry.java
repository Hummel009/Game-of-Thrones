package got.common.world.structure.other;

import cpw.mods.fml.common.FMLLog;
import got.common.faction.GOTFaction;
import got.common.world.structure.GOTStructure;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GOTStructureRegistry {
	public static Map<Integer, IStructureProvider> idToClassMapping = new HashMap<>();
	public static Map<Integer, String> idToStringMapping = new HashMap<>();
	public static Map<Integer, StructureColorInfo> structureItemSpawners = new LinkedHashMap<>();
	public static Map<Class<? extends WorldGenerator>, String> classToNameMapping = new HashMap<>();
	public static Map<Class<? extends WorldGenerator>, GOTFaction> classToFactionMapping = new HashMap<>();

	public static String getNameFromID(int ID) {
		return idToStringMapping.get(ID);
	}

	public static int getRotationFromPlayer(EntityPlayer entityplayer) {
		return MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
	}

	public static IStructureProvider getStructureForID(int ID) {
		return idToClassMapping.get(ID);
	}

	public static void onInit() {
		GOTStructure.onInit();
	}

	public static void register(int id, Class<? extends WorldGenerator> strClass, GOTFaction faction) {
		String name = strClass.getSimpleName();
		String cut = name.replace("GOTStructure", "");
		registerStructure(id, strClass, cut, faction.eggColor, faction.eggColor, false);
		classToNameMapping.put(strClass, cut);
		classToFactionMapping.put(strClass, faction);
	}

	public static void register(int id, Class<? extends WorldGenerator> strClass, int color) {
		String name = strClass.getSimpleName();
		String cut = name.replace("GOTStructure", "");
		registerStructure(id, strClass, cut, color, color, false);
		classToNameMapping.put(strClass, cut);
	}

	@SuppressWarnings("all")
	public static void register(int id, GOTStructureBaseSettlement settlement, String name, GOTFaction faction, ISettlementProperties properties) {
		IStructureProvider strProvider = new IStructureProvider() {

			@Override
			public boolean generateStructure(World world, EntityPlayer entityplayer, int i, int j, int k) {
				GOTStructureBaseSettlement.AbstractInstance<?> instance = settlement.createAndSetupSettlementInstance(world, i, k, world.rand, LocationInfo.SPAWNED_BY_PLAYER);
				instance.setRotation((getRotationFromPlayer(entityplayer) + 2) % 4);
				properties.apply(instance);
				settlement.generateCompleteSettlementInstance(instance, world, i, k);
				return true;
			}

			@Override
			public boolean isSettlement() {
				return true;
			}
		};
		registerStructure(id, strProvider, name, faction.eggColor, faction.eggColor, false);
	}

	@SuppressWarnings("all")
	public static void register(int id, GOTStructureBaseSettlement settlement, String name, int color, ISettlementProperties properties) {
		IStructureProvider strProvider = new IStructureProvider() {

			@Override
			public boolean generateStructure(World world, EntityPlayer entityplayer, int i, int j, int k) {
				GOTStructureBaseSettlement.AbstractInstance<?> instance = settlement.createAndSetupSettlementInstance(world, i, k, world.rand, LocationInfo.SPAWNED_BY_PLAYER);
				instance.setRotation((getRotationFromPlayer(entityplayer) + 2) % 4);
				properties.apply(instance);
				settlement.generateCompleteSettlementInstance(instance, world, i, k);
				return true;
			}

			@Override
			public boolean isSettlement() {
				return true;
			}
		};
		registerStructure(id, strProvider, name, color, color, false);
	}

	public static void registerSettlement(int id, GOTStructureBaseSettlement settlement, String name, int colorBG, int colorFG, ISettlementProperties<GOTStructureBaseSettlement.AbstractInstance<?>> properties) {
		IStructureProvider strProvider = new IStructureProvider() {

			@Override
			public boolean generateStructure(World world, EntityPlayer entityplayer, int i, int j, int k) {
				GOTStructureBaseSettlement.AbstractInstance<?> instance = settlement.createAndSetupSettlementInstance(world, i, k, world.rand, LocationInfo.SPAWNED_BY_PLAYER);
				instance.setRotation((getRotationFromPlayer(entityplayer) + 2) % 4);
				properties.apply(instance);
				settlement.generateCompleteSettlementInstance(instance, world, i, k);
				return true;
			}

			@Override
			public boolean isSettlement() {
				return true;
			}
		};
		registerStructure(id, strProvider, name, colorBG, colorFG, false);
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
				if (generator instanceof GOTStructureBase) {
					GOTStructureBase strGen = (GOTStructureBase) generator;
					strGen.restrictions = false;
					strGen.usingPlayer = entityplayer;
					return strGen.generate(world, world.rand, i, j, k, strGen.usingPlayerRotation());
				}
				return false;
			}

			@Override
			public boolean isSettlement() {
				return false;
			}
		};
		registerStructure(id, strProvider, name, colorBG, colorFG, hide);
	}

	public static void registerStructure(int id, IStructureProvider str, String name, int colorBG, int colorFG, boolean hide) {
		idToClassMapping.put(id, str);
		idToStringMapping.put(id, name);
		structureItemSpawners.put(id, new StructureColorInfo(id, colorBG, colorFG, str.isSettlement(), hide));
	}

	public interface ISettlementProperties<V> {
		void apply(V var1);
	}

	public interface IStructureProvider {
		boolean generateStructure(World var1, EntityPlayer var2, int var3, int var4, int var5);

		boolean isSettlement();
	}

	public static class StructureColorInfo {
		public int spawnedID;
		public int colorBackground;
		public int colorForeground;
		public boolean isSettlement;
		public boolean isHidden;

		public StructureColorInfo(int i, int colorBG, int colorFG, boolean vill, boolean hide) {
			spawnedID = i;
			colorBackground = colorBG;
			colorForeground = colorFG;
			isSettlement = vill;
			isHidden = hide;
		}
	}

}
