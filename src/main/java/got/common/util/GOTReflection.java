package got.common.util;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper.UnableToAccessFieldException;
import cpw.mods.fml.relauncher.ReflectionHelper.UnableToFindFieldException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockStem;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.event.HoverEvent.Action;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.WorldInfo;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class GOTReflection {
	public static boolean canPistonPushBlock(Block block, World world, int i, int j, int k, boolean flag) {
		try {
			Method method = getPrivateMethod(BlockPistonBase.class, null, new Class[]{Block.class, World.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE}, "canPushBlock", "func_150080_a");
			return (Boolean) method.invoke(null, block, world, i, j, k, flag);
		} catch (Exception e) {
			logFailure(e);
			return false;
		}
	}

	public static Item getCropItem(BlockCrops block) {
		try {
			Method method = getPrivateMethod(BlockCrops.class, block, new Class[0], "func_149865_P");
			return (Item) method.invoke(block);
		} catch (Exception e) {
			logFailure(e);
			return null;
		}
	}

	public static float getDamageAmount(Item item) {
		float f = 0.0f;
		try {
			Field privateField = getPotentiallyObfuscatedPrivateValue(ItemSword.class, "field_150934_a");
			if (privateField != null) {
				privateField.setAccessible(true);
				f = (float) privateField.get(item);
			}
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e2) {
			e2.printStackTrace();
		}
		return f;
	}

	public static int getFishHookBobTime(EntityFishHook fishHook) {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(EntityFishHook.class, fishHook, "field_146045_ax");
		} catch (Exception e) {
			logFailure(e);
			return 0;
		}
	}

	public static String[] getHorseArmorTextures() {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(EntityHorse.class, null, "horseArmorTextures", "field_110270_bw");
		} catch (Exception e) {
			logFailure(e);
			return new String[0];
		}
	}

	public static AnimalChest getHorseInv(EntityHorse horse) {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(EntityHorse.class, horse, "horseChest", "field_110296_bG");
		} catch (Exception e) {
			logFailure(e);
			return null;
		}
	}

	public static IAttribute getHorseJumpStrength() {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(EntityHorse.class, null, "horseJumpStrength", "field_110271_bv");
		} catch (Exception e) {
			logFailure(e);
			return null;
		}
	}

	public static Map<String, Action> getHoverEventMappings() {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(Action.class, null, "nameMapping", "field_150690_d");
		} catch (Exception e) {
			logFailure(e);
			return Collections.emptyMap();
		}
	}

	public static Block getOreBlock(WorldGenMinable ore) {
		Block b = null;
		try {
			Field privateField = getPotentiallyObfuscatedPrivateValue(WorldGenMinable.class, "field_150519_a");
			if (privateField != null) {
				privateField.setAccessible(true);
				b = (Block) privateField.get(ore);
			}
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e2) {
			e2.printStackTrace();
		}
		return b;
	}

	public static int getOreMeta(WorldGenMinable ore) {
		int i = 0;
		try {
			Field privateField = getPotentiallyObfuscatedPrivateValue(WorldGenMinable.class, "mineableBlockMeta");
			if (privateField != null) {
				privateField.setAccessible(true);
				i = (int) privateField.get(ore);
			}
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e2) {
			e2.printStackTrace();
		}
		return i;
	}

	public static <T, E> T getPotentiallyObfuscatedPrivateValue(Class<? super E> classToAccess, String fieldName) {
		try {
			return ReflectionHelper.getPrivateValue(classToAccess, null, ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldName));
		} catch (UnableToFindFieldException | UnableToAccessFieldException | NullPointerException e) {
			try {
				return (T) classToAccess.getDeclaredField(fieldName);
			} catch (NoSuchFieldException | SecurityException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	public static <E> Method getPrivateMethod(Class<? super E> classToAccess, E instance, Class[] methodClasses, String... methodNames) {
		try {
			return ReflectionHelper.findMethod(classToAccess, instance, remapMethodNames(classToAccess.getName(), methodNames), methodClasses);
		} catch (ReflectionHelper.UnableToFindFieldException e) {
			GOTLog.logger.log(Level.ERROR, "Unable to locate any method %s on type %s", Arrays.toString(methodNames), classToAccess.getName());
			throw e;
		} catch (ReflectionHelper.UnableToAccessFieldException e) {
			GOTLog.logger.log(Level.ERROR, "Unable to access any method %s on type %s", Arrays.toString(methodNames), classToAccess.getName());
			throw e;
		}
	}

	public static Block getStemFruitBlock(BlockStem block) {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(BlockStem.class, block, "field_149877_a");
		} catch (Exception e) {
			logFailure(e);
			return null;
		}
	}

	public static ToolMaterial getToolMaterial(Item item) {
		ToolMaterial tm = null;
		try {
			Field privateField = getPotentiallyObfuscatedPrivateValue(ItemSword.class, "field_150933_b");
			if (privateField != null) {
				privateField.setAccessible(true);
				tm = (ToolMaterial) privateField.get(item);
			}
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e2) {
			e2.printStackTrace();
		}
		return tm;
	}

	public static boolean isBadEffect(Potion potion) {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(Potion.class, potion, "isBadEffect", "field_76418_K");
		} catch (Exception e) {
			logFailure(e);
			return false;
		}
	}

	public static boolean isFishHookInGround(EntityFishHook fishHook) {
		try {
			return ObfuscationReflectionHelper.getPrivateValue(EntityFishHook.class, fishHook, "field_146051_au");
		} catch (Exception e) {
			logFailure(e);
			return false;
		}
	}

	public static void logFailure(Exception e) {
		GOTLog.logger.log(Level.ERROR, "GOTReflection failed");
		throw new RuntimeException(e);
	}

	public static Entity newEntity(Class<? extends Entity> entityClass, World world) {
		try {
			Class[] param = new Class[1];
			param[0] = World.class;
			return entityClass.getDeclaredConstructor(param).newInstance(world);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
		         InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static WorldGenerator newStructure(Class<? extends WorldGenerator> structureClass, boolean bool) {
		try {
			Class[] param = new Class[1];
			param[0] = boolean.class;
			return structureClass.getDeclaredConstructor(param).newInstance(bool);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
		         InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] remapMethodNames(String className, String... methodNames) {
		String internalClassName = FMLDeobfuscatingRemapper.INSTANCE.unmap(className.replace('.', '/'));
		String[] mappedNames = new String[methodNames.length];
		int i = 0;
		for (String mName : methodNames) {
			mappedNames[i] = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(internalClassName, mName, null);
			i++;
		}
		return mappedNames;
	}

	public static void removeCommand(Class<? extends ICommand> commandClass) {
		try {
			CommandHandler handler = (CommandHandler) MinecraftServer.getServer().getCommandManager();
			Map<String, ICommand> commandMap = handler.getCommands();
			Set<ICommand> commandSet = ObfuscationReflectionHelper.getPrivateValue(CommandHandler.class, handler, "commandSet", "field_71561_b");
			ArrayList<ICommand> mapremoves = new ArrayList<>();
			for (ICommand command : commandMap.values()) {
				if (command.getClass() == commandClass) {
					mapremoves.add(command);
				}
			}
			commandMap.values().removeAll(mapremoves);
			ArrayList<ICommand> setremoves = new ArrayList<>();
			for (ICommand obj : commandSet) {
				if (obj.getClass() == commandClass) {
					setremoves.add(obj);
				}
			}
			setremoves.forEach(commandSet::remove);
		} catch (Exception e) {
			logFailure(e);
		}
	}

	public static <T, E> void setFinalField(Class<? super T> classToAccess, T instance, E value, Field f) throws Exception {
		try {
			unlockFinalField(f);
			f.set(instance, value);
		} catch (Exception e) {
			GOTLog.logger.log(Level.ERROR, "Unable to access static field");
			throw e;
		}
	}

	public static void setupHorseInv(EntityHorse horse) {
		try {
			Method method = getPrivateMethod(EntityHorse.class, horse, new Class[0], "func_110226_cD");
			method.invoke(horse);
		} catch (Exception e) {
			logFailure(e);
		}
	}

	public static void setWorldInfo(World world, WorldInfo newWorldInfo) {
		try {
			ObfuscationReflectionHelper.setPrivateValue(World.class, world, newWorldInfo, "worldInfo", "field_72986_A");
		} catch (Exception e) {
			logFailure(e);
		}
	}

	public static void testAll(World world) {
		getHorseJumpStrength();
		getHorseArmorTextures();
		getHorseInv(new EntityHorse(world));
		setupHorseInv(new EntityHorse(world));
		getStemFruitBlock((BlockStem) Blocks.melon_stem);
		getCropItem((BlockCrops) Blocks.potatoes);
		isBadEffect(Potion.poison);
		getHoverEventMappings();
		isFishHookInGround(new EntityFishHook(world));
		getFishHookBobTime(new EntityFishHook(world));
		canPistonPushBlock(Blocks.glass, world, 0, 0, 0, false);
	}

	public static void unlockFinalField(Field f) throws Exception {
		f.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
	}
}
