package got;

import com.google.common.base.CaseFormat;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import got.common.*;
import got.common.command.*;
import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.entity.GOTEntity;
import got.common.entity.essos.gold.GOTEntityGoldenMan;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntityPortal;
import got.common.entity.other.GOTHiredNPCInfo;
import got.common.faction.GOTFaction;
import got.common.fellowship.GOTFellowship;
import got.common.item.other.GOTItemBanner.BannerType;
import got.common.network.GOTPacketHandler;
import got.common.util.GOTLog;
import got.common.util.GOTReflection;
import got.common.world.GOTWorldType;
import got.common.world.biome.GOTBiome;
import got.common.world.map.GOTBeziers;
import got.common.world.map.GOTWaypoint;
import got.common.world.structure.GOTStructure;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandTime;
import net.minecraft.command.IEntitySelector;
import net.minecraft.command.server.CommandMessage;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.oredict.OreDictionary;

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.*;

@Mod(modid = "got", useMetadata = true)
public class GOT {
	@SidedProxy(clientSide = "got.client.GOTClientProxy", serverSide = "got.common.GOTCommonProxy")
	public static GOTCommonProxy proxy;
	@Mod.Instance("got")
	public static GOT instance;
	public static String VERSION = "23.09.01";
	public static List<String> devs = new ArrayList<>();
	public static GOTEventHandler eventHandler;
	public static GOTPacketHandler packetHandler;
	public static GOTTickHandlerServer tickHandler;
	public static WorldType worldTypeGOT;
	public static WorldType worldTypeGOTEmpty;
	public static WorldType worldTypeGOTClassic;
	public static String langsName = "Русский (ru), Українська (uk), English (en), Français (fr), Deutsch (de), Polska (pl), Türkçe (tr), 中文 (zh)";

	static {
		devs.add("76ae4f2f-e70a-4680-b7cd-3100fa8b567b");
		devs.add("40cd453d-4c71-4afe-9ae3-a2b8cb2b6f00");
		devs.add("ce6eec82-0678-4be3-933d-05acb902d558");
		devs.add("ce924ff6-8450-41ad-865e-89c5897837c4");
		devs.add("9aee5b32-8e19-4d4b-a2d6-1318af62733d");
		devs.add("694406b3-10e4-407d-99bb-17218696627a");
		devs.add("1f63e38e-4059-4a4f-b7c4-0fac4a48e744");
		devs.add("72fd4cfd-064e-4cf1-874d-74000c152f48");
		devs.add("a05ba4aa-2cd0-43b1-957c-7971c9af53d4");
		devs.add("22be67c2-ba43-48db-b2ba-32857e78ddad");
		devs.add("c52f6daa-1479-4304-b8de-30b7b1903b23");
		devs.add("56c71aab-8a68-465d-b386-5f721dd68df6");
		devs.add("188e4e9c-8c67-443d-9b6c-a351076a43e3");
		devs.add("f8cc9b45-509a-4034-8740-0b84ce7e4492");
	}

	public static boolean canDropLoot(World world) {
		return world.getGameRules().getGameRuleBooleanValue("doMobLoot");
	}

	public static boolean canGrief(World world) {
		return world.getGameRules().getGameRuleBooleanValue("mobGriefing");
	}

	public static boolean canNPCAttackEntity(EntityCreature attacker, EntityLivingBase target, boolean isPlayerDirected) {
		if (target == null || !target.isEntityAlive()) {
			return false;
		}
		GOTFaction attackerFaction = getNPCFaction(attacker);
		if (attacker instanceof GOTEntityNPC) {
			GOTEntityNPC npc = (GOTEntityNPC) attacker;
			EntityPlayer hiringPlayer = npc.hiredNPCInfo.getHiringPlayer();
			if (hiringPlayer != null) {
				if (target == hiringPlayer || target.riddenByEntity == hiringPlayer) {
					return false;
				}
				GOTEntityNPC targetNPC = null;
				if (target instanceof GOTEntityNPC) {
					targetNPC = (GOTEntityNPC) target;
				} else if (target.riddenByEntity instanceof GOTEntityNPC) {
					targetNPC = (GOTEntityNPC) target.riddenByEntity;
				}
				if (targetNPC != null && targetNPC.hiredNPCInfo.isActive) {
					UUID hiringPlayerUUID = npc.hiredNPCInfo.getHiringPlayerUUID();
					UUID targetHiringPlayerUUID = targetNPC.hiredNPCInfo.getHiringPlayerUUID();
					if (hiringPlayerUUID != null && hiringPlayerUUID.equals(targetHiringPlayerUUID) && !attackerFaction.isBadRelation(getNPCFaction(targetNPC))) {
						return false;
					}
				}
			}
		}
		if (attackerFaction.allowEntityRegistry) {
			if (attackerFaction.isGoodRelation(getNPCFaction(target)) && attacker.getAttackTarget() != target) {
				return false;
			}
			if (target.riddenByEntity != null && attackerFaction.isGoodRelation(getNPCFaction(target.riddenByEntity)) && attacker.getAttackTarget() != target && attacker.getAttackTarget() != target.riddenByEntity) {
				return false;
			}
			if (!isPlayerDirected) {
				if (target instanceof EntityPlayer && GOTLevelData.getData((EntityPlayer) target).getAlignment(attackerFaction) >= 0.0f && attacker.getAttackTarget() != target) {
					return false;
				}
				return !(target.riddenByEntity instanceof EntityPlayer) || GOTLevelData.getData((EntityPlayer) target.riddenByEntity).getAlignment(attackerFaction) < 0.0f || attacker.getAttackTarget() == target || attacker.getAttackTarget() == target.riddenByEntity;
			}
		}
		return true;
	}

	public static boolean canPlayerAttackEntity(EntityPlayer attacker, EntityLivingBase target, boolean warnFriendlyFire) {
		if (target == null || !target.isEntityAlive()) {
			return false;
		}
		GOTPlayerData playerData = GOTLevelData.getData(attacker);
		boolean friendlyFire = false;
		boolean friendlyFireEnabled = playerData.getFriendlyFire();
		if (target instanceof EntityPlayer && target != attacker) {
			EntityPlayer targetPlayer = (EntityPlayer) target;
			if (!playerData.isSiegeActive()) {
				List<GOTFellowship> fellowships = playerData.getFellowships();
				for (GOTFellowship fs : fellowships) {
					if (!fs.getPreventPVP() || !fs.containsPlayer(targetPlayer.getUniqueID())) {
						continue;
					}
					return false;
				}
			}
		}
		Entity targetNPC = null;
		GOTFaction targetNPCFaction;
		if (target instanceof GOTEntityGoldenMan && getNPCFaction(target) == GOTFaction.UNALIGNED || getNPCFaction(target) != GOTFaction.UNALIGNED) {
			targetNPC = target;
		} else if (getNPCFaction(target.riddenByEntity) != GOTFaction.UNALIGNED) {
			targetNPC = target.riddenByEntity;
		}
		if (targetNPC != null) {
			targetNPCFaction = getNPCFaction(targetNPC);
			if (targetNPC instanceof GOTEntityNPC) {
				GOTEntityNPC targetgotNPC = (GOTEntityNPC) targetNPC;
				GOTHiredNPCInfo hiredInfo = targetgotNPC.hiredNPCInfo;
				if (hiredInfo.isActive) {
					if (hiredInfo.getHiringPlayer() == attacker) {
						return false;
					}
					if (targetgotNPC.getAttackTarget() != attacker && !playerData.isSiegeActive()) {
						UUID hiringPlayerID = hiredInfo.getHiringPlayerUUID();
						List<GOTFellowship> fellowships = playerData.getFellowships();
						for (GOTFellowship fs : fellowships) {
							if (!fs.getPreventHiredFriendlyFire() || !fs.containsPlayer(hiringPlayerID)) {
								continue;
							}
							return false;
						}
					}
				}
			}
			if (targetNPC instanceof EntityLiving && ((EntityLiving) targetNPC).getAttackTarget() != attacker && GOTLevelData.getData(attacker).getAlignment(targetNPCFaction) > 0.0f) {
				friendlyFire = true;
			}
		}
		if (!friendlyFireEnabled && friendlyFire) {
			if (warnFriendlyFire) {
				GOTLevelData.getData(attacker).sendMessageIfNotReceived(GOTGuiMessageTypes.FRIENDLY_FIRE);
			}
			return false;
		}
		return true;
	}

	public static boolean canSpawnMobs(World world) {
		return world.getGameRules().getGameRuleBooleanValue("doMobSpawning");
	}

	public static boolean doDayCycle(World world) {
		return world.getGameRules().getGameRuleBooleanValue("doDaylightCycle");
	}

	public static boolean doFireTick(World world) {
		return world.getGameRules().getGameRuleBooleanValue("doFireTick");
	}

	public static void dropContainerItems(IInventory container, World world, int i, int j, int k) {
		for (int l = 0; l < container.getSizeInventory(); ++l) {
			ItemStack item = container.getStackInSlot(l);
			if (item == null) {
				continue;
			}
			float f = world.rand.nextFloat() * 0.8f + 0.1f;
			float f1 = world.rand.nextFloat() * 0.8f + 0.1f;
			float f2 = world.rand.nextFloat() * 0.8f + 0.1f;
			while (item.stackSize > 0) {
				int i1 = world.rand.nextInt(21) + 10;
				if (i1 > item.stackSize) {
					i1 = item.stackSize;
				}
				item.stackSize -= i1;
				EntityItem entityItem = new EntityItem(world, i + f, j + f1, k + f2, new ItemStack(item.getItem(), i1, item.getItemDamage()));
				if (item.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
				}
				entityItem.motionX = world.rand.nextGaussian() * 0.05000000074505806;
				entityItem.motionY = world.rand.nextGaussian() * 0.05000000074505806 + 0.20000000298023224;
				entityItem.motionZ = world.rand.nextGaussian() * 0.05000000074505806;
				world.spawnEntityInWorld(entityItem);
			}
		}
	}

	public static EntityPlayer getDamagingPlayerIncludingUnits(DamageSource damagesource) {
		if (damagesource.getEntity() instanceof EntityPlayer) {
			return (EntityPlayer) damagesource.getEntity();
		}
		if (damagesource.getEntity() instanceof GOTEntityNPC) {
			GOTEntityNPC npc = (GOTEntityNPC) damagesource.getEntity();
			if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() != null) {
				return npc.hiredNPCInfo.getHiringPlayer();
			}
		}
		return null;
	}

	public static ModContainer getModContainer() {
		return FMLCommonHandler.instance().findContainerFor(instance);
	}

	public static Map<String, Integer> getModContentInfo() {
		Map<String, Integer> map = new HashMap<>();
		map.put("achievements", GOTAchievement.CONTENT.size());
		map.put("packets", GOTPacketHandler.id);
		map.put("banners", BannerType.values().length);
		map.put("mobs", GOTEntity.id);
		map.put("structures", GOTStructure.id);
		map.put("biomes", GOTBiome.CONTENT.size());
		map.put("beziers", GOTBeziers.id);
		map.put("waypoints", GOTWaypoint.values().length);
		map.put("factions", GOTFaction.values().length);
		map.put("items", GOTItems.CONTENT.size());
		map.put("blocks", GOTBlocks.CONTENT.size());
		return map;
	}

	public static GOTFaction getNPCFaction(Entity entity) {
		if (entity == null) {
			return GOTFaction.UNALIGNED;
		}
		if (entity instanceof GOTEntityNPC) {
			return ((GOTEntityNPC) entity).getFaction();
		}
		EntityList.getEntityString(entity);
		return GOTFaction.UNALIGNED;
	}

	public static int getTrueTopBlock(World world, int i, int k) {
		Chunk chunk = world.getChunkProvider().provideChunk(i >> 4, k >> 4);
		for (int j = chunk.getTopFilledSegment() + 15; j > 0; --j) {
			Block block = world.getBlock(i, j, k);
			if (!block.getMaterial().blocksMovement() || block.getMaterial() == Material.leaves || block.isFoliage(world, i, j, k)) {
				continue;
			}
			return j + 1;
		}
		return -1;
	}

	public static boolean isAprilFools() {
		LocalDate calendar = LocalDate.now();
		return calendar.getMonth() == Month.APRIL && calendar.getDayOfMonth() == 1;
	}

	public static boolean isGuyFawkes() {
		LocalDate calendar = LocalDate.now();
		return calendar.getMonth() == Month.NOVEMBER && calendar.getDayOfMonth() == 5;
	}

	public static boolean isNewYear() {
		LocalDate calendar = LocalDate.now();
		return calendar.getMonth() == Month.JANUARY && calendar.getDayOfMonth() == 1 || calendar.getMonth() == Month.DECEMBER && calendar.getDayOfMonth() == 31;
	}

	public static boolean isOpaque(IBlockAccess world, int i, int j, int k) {
		return world.getBlock(i, j, k).isOpaqueCube();
	}

	public static boolean isOreNameEqual(ItemStack itemstack, String name) {
		Iterable<ItemStack> list = OreDictionary.getOres(name);
		for (ItemStack obj : list) {
			if (!OreDictionary.itemMatches(obj, itemstack, false)) {
				continue;
			}
			return true;
		}
		return false;
	}

	public static boolean isUkraine() {
		LocalDate calendar = LocalDate.now();
		return calendar.getMonth() == Month.AUGUST && calendar.getDayOfMonth() == 24;
	}

	@SuppressWarnings("Convert2Lambda")
	public static IEntitySelector selectLivingExceptCreativePlayers() {
		return new IEntitySelector() {

			@Override
			public boolean isEntityApplicable(Entity entity) {
				if (entity instanceof EntityLivingBase && entity.isEntityAlive()) {
					if (entity instanceof EntityPlayer) {
						return !((EntityPlayer) entity).capabilities.isCreativeMode;
					}
					return true;
				}
				return false;
			}
		};
	}

	@SuppressWarnings("Convert2Lambda")
	public static IEntitySelector selectNonCreativePlayers() {
		return new IEntitySelector() {

			@Override
			public boolean isEntityApplicable(Entity entity) {
				return entity instanceof EntityPlayer && entity.isEntityAlive() && !((EntityPlayer) entity).capabilities.isCreativeMode;
			}
		};
	}

	public static void transferEntityToDimension(Entity entity, int newDimension, Teleporter teleporter) {
		if (entity instanceof GOTEntityPortal) {
			return;
		}
		if (!entity.worldObj.isRemote && !entity.isDead) {
			MinecraftServer minecraftserver = MinecraftServer.getServer();
			int oldDimension = entity.dimension;
			WorldServer oldWorld = minecraftserver.worldServerForDimension(oldDimension);
			WorldServer newWorld = minecraftserver.worldServerForDimension(newDimension);
			entity.dimension = newDimension;
			entity.worldObj.removeEntity(entity);
			entity.isDead = false;
			minecraftserver.getConfigurationManager().transferEntityToWorld(entity, oldDimension, oldWorld, newWorld, teleporter);
			Entity newEntity = EntityList.createEntityByName(EntityList.getEntityString(entity), newWorld);
			if (newEntity != null) {
				newEntity.copyDataFrom(entity, true);
				newWorld.spawnEntityInWorld(newEntity);
			}
			entity.isDead = true;
			oldWorld.resetUpdateEntityTick();
			newWorld.resetUpdateEntityTick();
			if (newEntity != null) {
				newEntity.timeUntilPortal = newEntity.getPortalCooldown();
			}
		}
	}

	@Mod.EventHandler
	public void onInit(FMLInitializationEvent event) {
		proxy.onInit();
		GOTLoader.onInit();
	}

	@Mod.EventHandler
	public void onMissingMappings(FMLMissingMappingsEvent event) {
		for (FMLMissingMappingsEvent.MissingMapping mapping : event.get()) {
			Item item;
			Block block;
			String newName;
			if (mapping.type == GameRegistry.Type.BLOCK) {
				if (mapping.name.contains("Carnotite")) {
					newName = mapping.name.replace("Carnotite", "Labradorite");
				} else if (mapping.name.contains("carnotite")) {
					newName = mapping.name.replace("carnotite", "labradorite");
				} else {
					newName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, mapping.name);
				}
				block = (Block) Block.blockRegistry.getObject(newName);
				if (block != null) {
					mapping.remap(block);
				}
			}
			if (mapping.type == GameRegistry.Type.ITEM) {
				if (mapping.name.contains("Carnotite")) {
					newName = mapping.name.replace("Carnotite", "Labradorite");
				} else if (mapping.name.contains("ignot")) {
					newName = mapping.name.replace("ignot", "ingot");
				} else if (mapping.name.contains("carnotite")) {
					newName = mapping.name.replace("carnotite", "labradorite");
				} else {
					newName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, mapping.name);
				}
				item = (Item) Item.itemRegistry.getObject(newName);
				if (item != null) {
					mapping.remap(item);
				}
			}
		}
	}

	@Mod.EventHandler
	public void onServerStarting(FMLServerStartingEvent event) {
		WorldServer world = DimensionManager.getWorld(0);
		proxy.testReflection(world);
		GOTReflection.removeCommand(CommandTime.class);
		GOTReflection.removeCommand(CommandMessage.class);
		Collection<CommandBase> command = new ArrayList<>();
		command.add(new GOTCommandTimeVanilla());
		command.add(new GOTCommandMessageFixed());
		command.add(new GOTCommandTime());
		command.add(new GOTCommandAlignment());
		command.add(new GOTCommandSummon());
		command.add(new GOTCommandFastTravelClock());
		command.add(new GOTCommandWaypointCooldown());
		command.add(new GOTCommandDate());
		command.add(new GOTCommandWaypoints());
		command.add(new GOTCommandAlignmentSee());
		command.add(new GOTCommandFellowship());
		command.add(new GOTCommandFellowshipMessage());
		command.add(new GOTCommandEnableAlignmentZones());
		command.add(new GOTCommandEnchant());
		command.add(new GOTCommandSpawnDamping());
		command.add(new GOTCommandFactionRelations());
		command.add(new GOTCommandPledgeCooldown());
		command.add(new GOTCommandConquest());
		command.add(new GOTCommandStrScan());
		command.add(new GOTCommandDragon());
		command.add(new GOTCommandInvasion());
		command.add(new GOTCommandAchievement());
		command.add(new GOTCommandDatabase());
		if (event.getServer().isDedicatedServer()) {
			command.add(new GOTCommandBanStructures());
			command.add(new GOTCommandAllowStructures());
			command.add(new GOTCommandAdminHideMap());
		}
		for (CommandBase element : command) {
			event.registerServerCommand(element);
		}
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
		Color baseWater = new Color(4876527);
		int baseR = baseWater.getRed();
		int baseG = baseWater.getGreen();
		int baseB = baseWater.getBlue();
		for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
			if (biome == null) {
				continue;
			}
			Color water = new Color(biome.waterColorMultiplier);
			float[] rgb = water.getColorComponents(null);
			int r = (int) (baseR * rgb[0]);
			int g = (int) (baseG * rgb[1]);
			int b = (int) (baseB * rgb[2]);
			biome.waterColorMultiplier = new Color(r, g, b).getRGB();
		}
		Map<String, Integer> info = getModContentInfo();

		for (Map.Entry<String, Integer> entry : info.entrySet()) {
			GOTLog.logger.info("Hummel009: Registered {} {}", entry.getValue(), entry.getKey());
		}
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		GOTLog.findLogger();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
		tickHandler = new GOTTickHandlerServer();
		eventHandler = new GOTEventHandler();
		packetHandler = new GOTPacketHandler();
		worldTypeGOT = new GOTWorldType("got");
		worldTypeGOTEmpty = new GOTWorldType("gotEmpty");
		worldTypeGOTClassic = new GOTWorldType("gotClassic");
		GOTLoader.preInit();
		proxy.preInit();
	}
}