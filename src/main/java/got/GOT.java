package got;

import java.awt.Color;
import java.util.*;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import got.common.*;
import got.common.block.leaves.*;
import got.common.block.other.*;
import got.common.block.planks.GOTBlockPlanksBase;
import got.common.block.sapling.GOTBlockSaplingBase;
import got.common.block.slab.GOTBlockSlabBase;
import got.common.block.wbeam.GOTBlockWoodBeam;
import got.common.block.wood.GOTBlockWoodBase;
import got.common.command.*;
import got.common.database.*;
import got.common.entity.GOTEntity;
import got.common.entity.essos.gold.GOTEntityGoldenMan;
import got.common.entity.other.*;
import got.common.faction.GOTFaction;
import got.common.fellowship.GOTFellowship;
import got.common.item.other.*;
import got.common.network.GOTPacketHandler;
import got.common.util.*;
import got.common.world.GOTWorldType;
import got.common.world.biome.GOTBiome;
import got.common.world.map.*;
import got.common.world.structure.GOTStructure;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.command.*;
import net.minecraft.command.server.CommandMessage;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.*;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = "got", name = GOT.NAME, version = GOT.VERSION, dependencies = "required-after:Forge@[10.13.4.1558,)")
public class GOT {
	@SidedProxy(clientSide = "got.client.GOTClientProxy", serverSide = "got.common.GOTCommonProxy")
	public static GOTCommonProxy proxy;
	@Mod.Instance(value = "got")
	public static GOT instance;
	public static final String NAME = "Game of Thrones";
	public static final String VERSION = "Version 17.5";
	public static String[] DEVELOPERS = { "ce924ff6-8450-41ad-865e-89c5897837c4", "9aee5b32-8e19-4d4b-a2d6-1318af62733d", "1f63e38e-4059-4a4f-b7c4-0fac4a48e744", "72fd4cfd-064e-4cf1-874d-74000c152f48", "a05ba4aa-2cd0-43b1-957c-7971c9af53d4", "22be67c2-ba43-48db-b2ba-32857e78ddad" };
	public static GOTEventHandler eventHandler;
	public static GOTPacketHandler packetHandler;
	public static GOTTickHandlerServer tickHandler;
	public static WorldType worldTypeGOT;
	public static WorldType worldTypeGOTClassic;
	public static Map<ItemStack, Integer> buy = new GOTItemStackMapImpl<>();
	public static Map<ItemStack, Integer> sell = new GOTItemStackMapImpl<>();

	public GOT() {
		instance = this;
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.onLoad();
		for (Object obj : Block.blockRegistry) {
			Block block = (Block) obj;
			if (block instanceof GOTBlockWoodBase) {
				Blocks.fire.setFireInfo(block, 5, 5);
			}
			if (block instanceof GOTBlockLeavesBase || block instanceof GOTBlockFallenLeaves || block instanceof GOTBlockBerryBush) {
				Blocks.fire.setFireInfo(block, 30, 60);
			}
			if (block instanceof GOTBlockPlanksBase || block instanceof GOTBlockFence || block instanceof GOTBlockWoodBars || block instanceof GOTBlockWoodBeam) {
				Blocks.fire.setFireInfo(block, 5, 20);
			}
			if ((block instanceof GOTBlockSlabBase || block instanceof GOTBlockStairs) && block.getMaterial() == Material.wood) {
				Blocks.fire.setFireInfo(block, 5, 20);
			}
			if (block instanceof GOTBlockGrass || block instanceof GOTBlockAsshaiGrass || block instanceof GOTBlockAsshaiMoss) {
				Blocks.fire.setFireInfo(block, 60, 100);
			}
			if (block instanceof GOTBlockFlower || block instanceof GOTBlockDoubleFlower) {
				Blocks.fire.setFireInfo(block, 60, 100);
			}
			if (block instanceof GOTBlockVine) {
				Blocks.fire.setFireInfo(block, 15, 100);
			}
			if (block instanceof BlockSapling || block instanceof GOTBlockSaplingBase) {
				Blocks.fire.setFireInfo(block, 60, 100);
			}
			if (block instanceof GOTBlockThatch || block instanceof GOTBlockThatchFloor || block instanceof GOTBlockReedBars) {
				Blocks.fire.setFireInfo(block, 60, 20);
			}
			if ((block instanceof GOTBlockSlabBase || block instanceof GOTBlockStairs) && block.getMaterial() == Material.grass) {
				Blocks.fire.setFireInfo(block, 60, 20);
			}
			if (!(block instanceof GOTBlockDaub)) {
				continue;
			}
			Blocks.fire.setFireInfo(block, 40, 40);
		}
		Blocks.fire.setFireInfo(Blocks.acacia_stairs, 5, 20);
		Blocks.fire.setFireInfo(Blocks.dark_oak_stairs, 5, 20);
		GOTRegistry.oreCopper.setHarvestLevel("pickaxe", 1);
		GOTRegistry.oreTin.setHarvestLevel("pickaxe", 1);
		GOTRegistry.oreSilver.setHarvestLevel("pickaxe", 2);
		GOTRegistry.oreCobalt.setHarvestLevel("pickaxe", 2);
		GOTRegistry.oreValyrian.setHarvestLevel("pickaxe", 2);
		GOTRegistry.blockMetal1.setHarvestLevel("pickaxe", 1, 0);
		GOTRegistry.blockMetal1.setHarvestLevel("pickaxe", 1, 1);
		GOTRegistry.blockMetal1.setHarvestLevel("pickaxe", 1, 2);
		GOTRegistry.blockMetal1.setHarvestLevel("pickaxe", 2, 3);
		GOTRegistry.blockMetal1.setHarvestLevel("pickaxe", 2, 4);
		GOTRegistry.blockMetal1.setHarvestLevel("pickaxe", 1, 5);
		GOTRegistry.blockMetal1.setHarvestLevel("pickaxe", 2, 6);
		GOTRegistry.blockMetal1.setHarvestLevel("pickaxe", 1, 7);
		GOTRegistry.blockMetal1.setHarvestLevel("pickaxe", 2, 8);
		GOTRegistry.blockMetal1.setHarvestLevel("pickaxe", 1, 9);
		GOTRegistry.blockMetal1.setHarvestLevel("pickaxe", 2, 11);
		GOTRegistry.blockMetal1.setHarvestLevel("pickaxe", 2, 12);
		GOTRegistry.blockMetal1.setHarvestLevel("pickaxe", 1, 15);
		GOTRegistry.oreGlowstone.setHarvestLevel("pickaxe", 1);
		GOTRegistry.quagmire.setHarvestLevel("shovel", 0);
		GOTRegistry.quicksand.setHarvestLevel("shovel", 0);
		GOTRegistry.blockMetal2.setHarvestLevel("pickaxe", 1, 0);
		GOTRegistry.blockMetal2.setHarvestLevel("pickaxe", 1, 4);
		GOTRegistry.asshaiDirt.setHarvestLevel("shovel", 0);
		GOTRegistry.basaltGravel.setHarvestLevel("shovel", 0);
		GOTRegistry.obsidianGravel.setHarvestLevel("shovel", 0);
		GOTRegistry.mud.setHarvestLevel("shovel", 0);
		GOTRegistry.mudGrass.setHarvestLevel("shovel", 0);
		GOTRegistry.mudFarmland.setHarvestLevel("shovel", 0);
		GOTRegistry.dirtPath.setHarvestLevel("shovel", 0);
		GOTRegistry.slabSingleDirt.setHarvestLevel("shovel", 0);
		GOTRegistry.slabDoubleDirt.setHarvestLevel("shovel", 0);
		GOTRegistry.slabSingleSand.setHarvestLevel("shovel", 0);
		GOTRegistry.slabDoubleSand.setHarvestLevel("shovel", 0);
		GOTRegistry.slabSingleGravel.setHarvestLevel("shovel", 0);
		GOTRegistry.slabDoubleGravel.setHarvestLevel("shovel", 0);
		GOTRegistry.whiteSand.setHarvestLevel("shovel", 0);
		GOTRegistry.stalactiteObsidian.setHarvestLevel("pickaxe", 3);
		GOTRegistry.oreGem.setHarvestLevel("pickaxe", 2);
		GOTRegistry.blockGem.setHarvestLevel("pickaxe", 2);
		GOTRegistry.blockGem.setHarvestLevel("pickaxe", 0, 8);
		GOTRegistry.redClay.setHarvestLevel("shovel", 0);
		GOTLoader.onInit();
	}

	@Mod.EventHandler
	public void onServerStarting(FMLServerStartingEvent event) {
		WorldServer world = DimensionManager.getWorld(0);
		proxy.testReflection(world);
		GOTReflection.removeCommand(CommandTime.class);
		GOTReflection.removeCommand(CommandMessage.class);
		event.registerServerCommand(new GOTCommandTimeVanilla());
		event.registerServerCommand(new GOTCommandMessageFixed());
		event.registerServerCommand(new GOTCommandTime());
		event.registerServerCommand(new GOTCommandAlignment());
		event.registerServerCommand(new GOTCommandSummon());
		event.registerServerCommand(new GOTCommandFastTravelClock());
		event.registerServerCommand(new GOTCommandWaypointCooldown());
		event.registerServerCommand(new GOTCommandDate());
		event.registerServerCommand(new GOTCommandWaypoints());
		event.registerServerCommand(new GOTCommandAlignmentSee());
		event.registerServerCommand(new GOTCommandFellowship());
		event.registerServerCommand(new GOTCommandFellowshipMessage());
		event.registerServerCommand(new GOTCommandEnableAlignmentZones());
		event.registerServerCommand(new GOTCommandEnchant());
		event.registerServerCommand(new GOTCommandSpawnDamping());
		event.registerServerCommand(new GOTCommandFactionRelations());
		event.registerServerCommand(new GOTCommandPledgeCooldown());
		event.registerServerCommand(new GOTCommandConquest());
		event.registerServerCommand(new GOTCommandStrScan());
		event.registerServerCommand(new GOTCommandDragon());
		event.registerServerCommand(new GOTCommandInvasion());
		event.registerServerCommand(new GOTCommandAchievement());
		if (event.getServer().isDedicatedServer()) {
			event.registerServerCommand(new GOTCommandBanStructures());
			event.registerServerCommand(new GOTCommandAllowStructures());
			event.registerServerCommand(new GOTCommandAdminHideMap());
		}
	}

	@Mod.EventHandler
	public void postload(FMLPostInitializationEvent event) {
		proxy.onPostload();
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
		int items = 0;
		for (Item item : GOTCommander.getObjectFieldsOfType(GOTRegistry.class, Item.class)) {
			items = items + 1;
		}
		int blocks = 0;
		for (Block block : GOTCommander.getObjectFieldsOfType(GOTRegistry.class, Block.class)) {
			blocks = blocks + 1;
		}
		int waypoints = GOTWaypoint.values().length;
		int factions = GOTFaction.values().length - 2;
		GOTLog.logger.info("Hummel009: Registered " + GOTPacketHandler.id + " packets");
		GOTLog.logger.info("Hummel009: Registered " + GOTItemBanner.id + " banners");
		GOTLog.logger.info("Hummel009: Registered " + GOTEntity.id + " mobs");
		GOTLog.logger.info("Hummel009: Registered " + GOTStructure.id + " structures");
		GOTLog.logger.info("Hummel009: Registered " + GOTBiome.id + " biomes");
		GOTLog.logger.info("Hummel009: Registered " + GOTRoads.id + " roads");
		GOTLog.logger.info("Hummel009: Registered " + waypoints + " waypoints");
		GOTLog.logger.info("Hummel009: Registered " + factions + " factions");
		GOTLog.logger.info("Hummel009: Registered " + items + " items");
		GOTLog.logger.info("Hummel009: Registered " + blocks + " blocks");
	}

	@Mod.EventHandler
	public void preload(FMLPreInitializationEvent event) {
		GOTLog.findLogger();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
		tickHandler = new GOTTickHandlerServer();
		eventHandler = new GOTEventHandler();
		packetHandler = new GOTPacketHandler();
		worldTypeGOT = new GOTWorldType("got");
		worldTypeGOTClassic = new GOTWorldType("gotClassic");
		GOTBlockReplacement.replaceVanillaBlock(Blocks.leaves, new GOTBlockLeavesVanilla1(), ItemLeaves.class);
		GOTBlockReplacement.replaceVanillaBlock(Blocks.leaves2, new GOTBlockLeavesVanilla2(), ItemLeaves.class);
		GOTBlockReplacement.replaceVanillaBlock(Blocks.fence, new GOTBlockFenceVanilla(), GOTItemFenceVanilla.class);
		GOTBlockReplacement.replaceVanillaBlock(Blocks.cake, new GOTBlockPlaceableFood().setBlockTextureName("cake"), null);
		GOTBlockReplacement.replaceVanillaItem(Items.cake, new GOTItemPlaceableFood(Blocks.cake).setTextureName("cake").setCreativeTab(CreativeTabs.tabFood));
		GOTBlockReplacement.replaceVanillaItem(Items.potionitem, new GOTItemPotion().setTextureName("potion"));
		GOTBlockReplacement.replaceVanillaItem(Items.glass_bottle, new GOTItemGlassBottle().setTextureName("potion_bottle_empty"));
		GOTConfig.setupAndLoad(event);
		GOTRegistry.assignContent();
		GOTRegistry.assignMetadata();
		GOTRegistry.registerBlocks();
		GOTRegistry.registerItems();

		buy.put(new ItemStack(GOTRegistry.coin, 1, 0), 1);
		buy.put(new ItemStack(GOTRegistry.coin, 1, 1), 2);
		buy.put(new ItemStack(GOTRegistry.coin, 1, 2), 4);
		buy.put(new ItemStack(GOTRegistry.coin, 1, 3), 8);
		buy.put(new ItemStack(GOTRegistry.coin, 1, 4), 56);
		buy.put(new ItemStack(GOTRegistry.coin, 1, 5), 392);
		buy.put(new ItemStack(GOTRegistry.coin, 1, 6), 11760);
		buy.put(new ItemStack(GOTRegistry.coin, 1, 7), 105840);

		sell.put(new ItemStack(GOTRegistry.coin, 1, 0), 1);
		sell.put(new ItemStack(GOTRegistry.coin, 1, 1), 2);
		sell.put(new ItemStack(GOTRegistry.coin, 1, 2), 4);
		sell.put(new ItemStack(GOTRegistry.coin, 1, 3), 8);
		sell.put(new ItemStack(GOTRegistry.coin, 1, 4), 56);
		sell.put(new ItemStack(GOTRegistry.coin, 1, 5), 392);
		sell.put(new ItemStack(GOTRegistry.coin, 1, 6), 11760);
		sell.put(new ItemStack(GOTRegistry.coin, 1, 7), 105840);

		Blocks.dragon_egg.setCreativeTab(GOTCreativeTabs.tabStory);
		MinecraftForge.EVENT_BUS.register(new GOTTrackingEventHandler());
		GOTLoader.preInit();
		proxy.onPreload();
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
					if (hiringPlayerUUID != null && targetHiringPlayerUUID != null && hiringPlayerUUID.equals(targetHiringPlayerUUID) && !attackerFaction.isBadRelation(getNPCFaction(targetNPC))) {
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
				if (target.riddenByEntity instanceof EntityPlayer && GOTLevelData.getData((EntityPlayer) target.riddenByEntity).getAlignment(attackerFaction) >= 0.0f && attacker.getAttackTarget() != target && attacker.getAttackTarget() != target.riddenByEntity) {
					return false;
				}
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
		GOTFaction targetNPCFaction = null;
		if ((target instanceof GOTEntityGoldenMan && getNPCFaction(target) == GOTFaction.UNALIGNED) || (getNPCFaction(target) != GOTFaction.UNALIGNED)) {
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

	public static boolean isNewYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(2) == 0 && calendar.get(5) == 1;
	}

	public static boolean isOpaque(World world, int i, int j, int k) {
		return world.getBlock(i, j, k).isOpaqueCube();
	}

	public static boolean isOreNameEqual(ItemStack itemstack, String name) {
		ArrayList<ItemStack> list = OreDictionary.getOres(name);
		for (ItemStack obj : list) {
			if (!OreDictionary.itemMatches(obj, itemstack, false)) {
				continue;
			}
			return true;
		}
		return false;
	}

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

	public static boolean isGuyFawkes() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(2) == 10 && calendar.get(5) == 5;
	}
}