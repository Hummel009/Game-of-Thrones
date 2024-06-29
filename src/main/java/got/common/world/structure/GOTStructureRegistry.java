package got.common.world.structure;

import cpw.mods.fml.common.FMLLog;
import got.common.faction.GOTFaction;
import got.common.world.structure.other.GOTStructureBase;
import got.common.world.structure.other.GOTStructureBaseSettlement;
import got.common.world.structure.other.LocationInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.*;

public class GOTStructureRegistry {
	public static final Collection<Class<? extends WorldGenerator>> CONTENT = new HashSet<>();

	public static final Map<Integer, StructureColorInfo> STRUCTURE_ITEM_SPAWNERS = new LinkedHashMap<>();
	public static final Map<Class<? extends WorldGenerator>, String> CLASS_TO_NAME_MAPPING = new HashMap<>();
	private static final Map<Class<?>, Set<String>> S_CLASS_TO_NAME_MAPPING = new HashMap<>();

	private static final Map<Integer, IStructureProvider> ID_TO_CLASS_MAPPING = new HashMap<>();
	private static final Map<Integer, String> ID_TO_STRING_MAPPING = new HashMap<>();

	private GOTStructureRegistry() {
	}

	public static String getNameFromID(int ID) {
		return ID_TO_STRING_MAPPING.get(ID);
	}

	public static int getRotationFromPlayer(EntityPlayer entityplayer) {
		return MathHelper.floor_double(entityplayer.rotationYaw * 4.0f / 360.0f + 0.5) & 3;
	}

	public static IStructureProvider getStructureForID(int ID) {
		return ID_TO_CLASS_MAPPING.get(ID);
	}

	public static void register(int id, Class<? extends WorldGenerator> strClass, GOTFaction faction) {
		String name = strClass.getSimpleName();
		String cut = name.replace("GOTStructure", "");
		registerStructure(id, strClass, cut, faction.getEggColor(), faction.getEggColor(), false);
		CLASS_TO_NAME_MAPPING.put(strClass, cut);
		CONTENT.add(strClass);
	}

	public static void register(int id, Class<? extends WorldGenerator> strClass, int color) {
		String name = strClass.getSimpleName();
		String cut = name.replace("GOTStructure", "");
		registerStructure(id, strClass, cut, color, color, false);
		CLASS_TO_NAME_MAPPING.put(strClass, cut);
		CONTENT.add(strClass);
	}

	@SuppressWarnings("rawtypes")
	public static void register(int id, GOTStructureBaseSettlement settlement, String name, GOTFaction faction, ISettlementProperties properties) {
		IStructureProvider strProvider = new IStructureProvider() {

			@Override
			public boolean generateStructure(World world, EntityPlayer entityplayer, int i, int j, int k) {
				GOTStructureBaseSettlement.AbstractInstance instance = settlement.createAndSetupSettlementInstance(world, i, k, world.rand, LocationInfo.SPAWNED_BY_PLAYER);
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
		registerName(settlement, name);
		registerStructure(id, strProvider, name, faction.getEggColor(), faction.getEggColor(), false);
	}

	private static void registerName(GOTStructureBaseSettlement settlement, String name) {
		S_CLASS_TO_NAME_MAPPING.computeIfAbsent(settlement.getClass(), k -> new HashSet<>());
		S_CLASS_TO_NAME_MAPPING.get(settlement.getClass()).add(name);
	}

	@SuppressWarnings("rawtypes")
	public static void register(int id, GOTStructureBaseSettlement settlement, String name, int color, ISettlementProperties properties) {
		registerSettlement(id, settlement, name, color, color, properties);
	}

	private static void registerSettlement(int id, GOTStructureBaseSettlement settlement, String name, int colorBG, int colorFG, ISettlementProperties<GOTStructureBaseSettlement.AbstractInstance> properties) {
		IStructureProvider strProvider = new IStructureProvider() {

			@Override
			public boolean generateStructure(World world, EntityPlayer entityplayer, int i, int j, int k) {
				GOTStructureBaseSettlement.AbstractInstance instance = settlement.createAndSetupSettlementInstance(world, i, k, world.rand, LocationInfo.SPAWNED_BY_PLAYER);
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

	private static void registerStructure(int id, Class<? extends WorldGenerator> strClass, String name, int colorBG, int colorFG, boolean hide) {
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
					strGen.setRestrictions(false);
					strGen.setUsingPlayer(entityplayer);
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

	private static void registerStructure(int id, IStructureProvider str, String name, int colorBG, int colorFG, boolean hide) {
		ID_TO_CLASS_MAPPING.put(id, str);
		ID_TO_STRING_MAPPING.put(id, name);
		STRUCTURE_ITEM_SPAWNERS.put(id, new StructureColorInfo(id, colorBG, colorFG, str.isSettlement(), hide));
	}

	public interface ISettlementProperties<V> {
		void apply(V var1);
	}

	public interface IStructureProvider {
		boolean generateStructure(World var1, EntityPlayer var2, int var3, int var4, int var5);

		boolean isSettlement();
	}

	public static class StructureColorInfo {
		private final int spawnedID;
		private final int colorBackground;
		private final int colorForeground;
		private final boolean isSettlement;
		private final boolean isHidden;

		protected StructureColorInfo(int i, int colorBG, int colorFG, boolean vill, boolean hide) {
			spawnedID = i;
			colorBackground = colorBG;
			colorForeground = colorFG;
			isSettlement = vill;
			isHidden = hide;
		}

		public int getSpawnedID() {
			return spawnedID;
		}

		public int getColorBackground() {
			return colorBackground;
		}

		public int getColorForeground() {
			return colorForeground;
		}

		public boolean isSettlement() {
			return isSettlement;
		}

		public boolean isHidden() {
			return isHidden;
		}
	}
}