package got.common;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;

import codechicken.nei.NEIModContainer;
import codechicken.nei.api.*;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.eventhandler.*;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import got.GOT;
import got.common.block.GOTVanillaSaplings;
import got.common.block.other.*;
import got.common.block.sapling.GOTBlockSaplingBase;
import got.common.block.table.GOTBlockCraftingTable;
import got.common.database.*;
import got.common.database.GOTTitle.PlayerTitle;
import got.common.enchant.*;
import got.common.entity.animal.*;
import got.common.entity.essos.GOTEntityStoneman;
import got.common.entity.essos.asshai.GOTEntityAsshaiMan;
import got.common.entity.essos.legendary.warrior.GOTEntityAsshaiArchmag;
import got.common.entity.other.*;
import got.common.entity.sothoryos.sothoryos.GOTEntitySothoryosMan;
import got.common.entity.westeros.GOTEntityProstitute;
import got.common.entity.westeros.reach.GOTEntityReachSoldier;
import got.common.faction.*;
import got.common.item.*;
import got.common.item.other.*;
import got.common.item.weapon.*;
import got.common.network.*;
import got.common.quest.GOTMiniQuest;
import got.common.tileentity.GOTTileEntityPlate;
import got.common.util.*;
import got.common.world.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.essos.*;
import got.common.world.biome.ulthos.GOTBiomeUlthosDesert;
import got.common.world.biome.variant.GOTBiomeVariantStorage;
import got.common.world.biome.westeros.*;
import integrator.NEIGOTIntegratorConfig;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.*;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.player.PlayerEvent.*;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import net.minecraftforge.event.world.*;

public class GOTEventHandler implements IFuelHandler {
	public GOTItemBow proxyBowItemServer;
	public GOTItemBow proxyBowItemClient;

	public GOTEventHandler() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.TERRAIN_GEN_BUS.register(this);
		GameRegistry.registerFuelHandler(this);
	}

	public void cancelAttackEvent(LivingAttackEvent event) {
		event.setCanceled(true);
		DamageSource source = event.source;
		if (source instanceof EntityDamageSourceIndirect) {
			source.getSourceOfDamage();
		}
	}

	@SubscribeEvent
	public void getBlockDrops(BlockEvent.HarvestDropsEvent event) {
		ItemStack itemstack;
		EntityPlayer entityplayer = event.harvester;
		Block block = event.block;
		if (entityplayer != null && (itemstack = entityplayer.getCurrentEquippedItem()) != null && block instanceof BlockWeb && itemstack.getItem() instanceof ItemShears) {
			int meta = 0;
			Item item = Item.getItemFromBlock(block);
			if (item != null && item.getHasSubtypes()) {
				meta = event.blockMetadata;
			}
			ItemStack silkDrop = new ItemStack(item, 1, meta);
			event.drops.clear();
			event.drops.add(silkDrop);
		}
	}

	@Override
	public int getBurnTime(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof ItemBlock && ((ItemBlock) item).field_150939_a instanceof GOTBlockSaplingBase) {
			return 100;
		}
		if (item == Item.getItemFromBlock(GOTRegistry.blockMetal1) && itemstack.getItemDamage() == 10) {
			return 6000;
		}
		if (item == Items.reeds || item == Item.getItemFromBlock(GOTRegistry.reeds) || item == Item.getItemFromBlock(GOTRegistry.kelp) || item == Item.getItemFromBlock(GOTRegistry.driedReeds) || item == Item.getItemFromBlock(GOTRegistry.cornStalk)) {
			return 100;
		}
		return 0;
	}

	@SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent event) {
		if (!GOTConfig.enchantingVanilla && (event.left != null && event.left.getItem() instanceof ItemEnchantedBook || event.right != null && event.right.getItem() instanceof ItemEnchantedBook)) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onArrowNock(ArrowNockEvent event) {
		EntityPlayer entityplayer = event.entityPlayer;
		World world = entityplayer.worldObj;
		ItemStack itemstack = event.result;
		if (itemstack != null && itemstack.getItem() instanceof ItemBow && !(itemstack.getItem() instanceof GOTItemBow) && !(itemstack.getItem() instanceof GOTItemCrossbow)) {
			if (!world.isRemote) {
				if (proxyBowItemServer == null) {
					proxyBowItemServer = new GOTItemBow(Item.ToolMaterial.WOOD);
					event.result = proxyBowItemServer.onItemRightClick(itemstack, world, entityplayer);
					proxyBowItemServer = null;
					event.setCanceled(true);
				}
			} else if (proxyBowItemClient == null) {
				proxyBowItemClient = new GOTItemBow(Item.ToolMaterial.WOOD);
				event.result = proxyBowItemClient.onItemRightClick(itemstack, world, entityplayer);
				proxyBowItemClient = null;
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		EntityPlayer entityplayer = event.getPlayer();
		Block block = event.block;
		int meta = event.blockMetadata;
		World world = event.world;
		int i = event.x;
		int j = event.y;
		int k = event.z;
		if (!world.isRemote && GOTBannerProtection.isProtected(world, i, j, k, GOTBannerProtection.forPlayer(entityplayer, GOTBannerProtection.Permission.FULL), true)) {
			event.setCanceled(true);
			return;
		}
		if (!world.isRemote && entityplayer != null) {
			if (GOTBlockGrapevine.isFullGrownGrapes(block, meta)) {
				GOTEntityReachSoldier.defendGrapevines(entityplayer, world, i, j, k);
			} else {
				boolean grapesAbove = false;
				for (int j1 = 1; j1 <= 3; ++j1) {
					int j2 = j + j1;
					Block above = world.getBlock(i, j2, k);
					if (!GOTBlockGrapevine.isFullGrownGrapes(above, world.getBlockMetadata(i, j2, k))) {
						continue;
					}
					grapesAbove = true;
				}
				if (grapesAbove) {
					GOTEntityReachSoldier.defendGrapevines(entityplayer, world, i, j + 1, k);
				}
			}
		}
	}

	@SubscribeEvent
	public void onBlockInteract(PlayerInteractEvent event) {
		Block block;
		int meta;
		EntityPlayer entityplayer = event.entityPlayer;
		World world = entityplayer.worldObj;
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		int i = event.x;
		int j = event.y;
		int k = event.z;
		int side = event.face;
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			ItemStack plateItem;
			int logFacing;
			GOTTileEntityPlate plate;
			TileEntity tileentity;
			int cauldronMeta;
			boolean mightBeAbleToAlterWorld;
			block = world.getBlock(i, j, k);
			meta = world.getBlockMetadata(i, j, k);
			GOTBannerProtection.Permission perm = GOTBannerProtection.Permission.FULL;
			mightBeAbleToAlterWorld = entityplayer.isSneaking() && itemstack != null;
			if (!mightBeAbleToAlterWorld) {
				if (block instanceof BlockDoor || block instanceof BlockTrapDoor || block instanceof BlockFenceGate || block instanceof GOTBlockGate) {
					perm = GOTBannerProtection.Permission.DOORS;
				} else if (block instanceof BlockWorkbench || block instanceof GOTBlockCraftingTable || block instanceof BlockAnvil || block instanceof GOTBlockCommandTable) {
					perm = GOTBannerProtection.Permission.TABLES;
				} else if (world.getTileEntity(i, j, k) instanceof IInventory) {
					perm = block instanceof GOTBlockBarrel || block instanceof GOTBlockKebabStand ? GOTBannerProtection.Permission.FOOD : GOTBannerProtection.Permission.CONTAINERS;
				} else if (((block instanceof GOTBlockArmorStand) || (block instanceof GOTBlockWeaponRack || block == Blocks.bookshelf)) || (block instanceof BlockJukebox)) {
					perm = GOTBannerProtection.Permission.CONTAINERS;
				} else if (block instanceof BlockEnderChest) {
					perm = GOTBannerProtection.Permission.PERSONAL_CONTAINERS;
				} else if (block instanceof GOTBlockPlate || block instanceof BlockCake || block instanceof GOTBlockPlaceableFood || block instanceof GOTBlockMug) {
					perm = GOTBannerProtection.Permission.FOOD;
				} else if (block instanceof BlockBed) {
					perm = GOTBannerProtection.Permission.BEDS;
				} else if (block instanceof BlockButton || block instanceof BlockLever) {
					perm = GOTBannerProtection.Permission.SWITCHES;
				}
			}
			if (!world.isRemote && GOTBannerProtection.isProtected(world, i, j, k, GOTBannerProtection.forPlayer(entityplayer, perm), true)) {
				event.setCanceled(true);
				if (block instanceof BlockDoor) {
					world.markBlockForUpdate(i, j - 1, k);
					world.markBlockForUpdate(i, j, k);
					world.markBlockForUpdate(i, j + 1, k);
				}
				return;
			}
			if (block == Blocks.flower_pot && meta == 0 && itemstack != null && GOTBlockFlowerPot.canAcceptPlant(itemstack)) {
				GOT.proxy.placeFlowerInPot(world, i, j, k, side, itemstack);
				if (!entityplayer.capabilities.isCreativeMode) {
					--itemstack.stackSize;
				}
				event.setCanceled(true);
				return;
			}
			if (itemstack != null && block == Blocks.cauldron && meta > 0) {
				GOTItemMug.Vessel drinkVessel = null;
				for (GOTItemMug.Vessel v : GOTItemMug.Vessel.values()) {
					if (v.getEmptyVesselItem() != itemstack.getItem()) {
						continue;
					}
					drinkVessel = v;
					break;
				}
				if (drinkVessel != null) {
					GOT.proxy.fillMugFromCauldron(world, i, j, k, side, itemstack);
					--itemstack.stackSize;
					ItemStack mugFill = new ItemStack(GOTRegistry.mugWater);
					GOTItemMug.setVessel(mugFill, drinkVessel, true);
					if (itemstack.stackSize <= 0) {
						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, mugFill);
					} else if (!entityplayer.inventory.addItemStackToInventory(mugFill)) {
						entityplayer.dropPlayerItemWithRandomChoice(mugFill, false);
					}
					event.setCanceled(true);
					return;
				}
			}
			if (!world.isRemote && block instanceof GOTBlockPlate && entityplayer.isSneaking() && (tileentity = world.getTileEntity(i, j, k)) instanceof GOTTileEntityPlate && (plateItem = (plate = (GOTTileEntityPlate) tileentity).getFoodItem()) != null) {
				((GOTBlockPlate) block).dropOnePlateItem(plate);
				--plateItem.stackSize;
				plate.setFoodItem(plateItem);
				event.setCanceled(true);
				return;
			}
			if (!world.isRemote && block instanceof BlockCauldron && itemstack != null && (cauldronMeta = BlockCauldron.func_150027_b(meta)) > 0) {
				boolean undyed = false;
				Item item = itemstack.getItem();
				if (item instanceof GOTItemPouch && GOTItemPouch.isPouchDyed(itemstack)) {
					GOTItemPouch.removePouchDye(itemstack);
					undyed = true;
				} else if (item instanceof GOTItemPipe && GOTItemPipe.isPipeDyed(itemstack)) {
					GOTItemPipe.removePipeDye(itemstack);
					undyed = true;
				} else if (item instanceof GOTItemLeatherHat && (GOTItemLeatherHat.isHatDyed(itemstack) || GOTItemLeatherHat.isFeatherDyed(itemstack))) {
					GOTItemLeatherHat.removeHatAndFeatherDye(itemstack);
					undyed = true;
				} else if (item instanceof GOTItemFeatherDyed && GOTItemFeatherDyed.isFeatherDyed(itemstack)) {
					GOTItemFeatherDyed.removeFeatherDye(itemstack);
					undyed = true;
				} else if (item instanceof GOTItemRobes && GOTItemRobes.areRobesDyed(itemstack)) {
					GOTItemRobes.removeRobeDye(itemstack);
					undyed = true;
				} else if (item instanceof GOTItemPartyHat && GOTItemPartyHat.isHatDyed(itemstack)) {
					GOTItemPartyHat.removeHatDye(itemstack);
					undyed = true;
				}
				if (undyed) {
					((BlockCauldron) block).func_150024_a(world, i, j, k, cauldronMeta - 1);
					event.setCanceled(true);
					return;
				}
			}
			if (!world.isRemote && itemstack != null && itemstack.getItem() == Items.dye && itemstack.getItemDamage() == 15 && block instanceof BlockLog && (logFacing = meta & 0xC) != 12) {
				boolean onInnerFace = false;
				switch (logFacing) {
				case 0:
					onInnerFace = side == 0 || side == 1;
					break;
				case 4:
					onInnerFace = side == 4 || side == 5;
					break;
				case 8:
					onInnerFace = side == 2 || side == 3;
					break;
				default:
					break;
				}
				if (onInnerFace) {
					world.setBlockMetadataWithNotify(i, j, k, meta |= 0xC, 3);
					world.playAuxSFX(2005, i, j, k, 0);
					if (!entityplayer.capabilities.isCreativeMode) {
						--itemstack.stackSize;
					}
					event.setCanceled(true);
					return;
				}
			}
			if (!world.isRemote && GOTBlockGrapevine.isFullGrownGrapes(block, meta)) {
				GOTEntityReachSoldier.defendGrapevines(entityplayer, world, i, j, k);
			}
			if (block == Blocks.bookshelf && !entityplayer.isSneaking() && GOTBlockBookshelfStorage.canOpenBookshelf(world, i, j, k, entityplayer) && !world.isRemote) {
				world.setBlock(i, j, k, GOTRegistry.bookshelfStorage, 0, 3);
				boolean flag = GOTRegistry.bookshelfStorage.onBlockActivated(world, i, j, k, entityplayer, side, 0.5f, 0.5f, 0.5f);
				if (!flag) {
					world.setBlock(i, j, k, Blocks.bookshelf, 0, 3);
				}
				event.setCanceled(true);
				return;
			}
			if (((block == Blocks.anvil) && (GOTConfig.isGOTEnchantingEnabled(world) || !GOTConfig.isEnchantingEnabled(world)) && !world.isRemote)) {
				entityplayer.openGui(GOT.instance, 53, world, i, j, k);
				event.setCanceled(true);
				return;
			}
		}
		if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
			block = world.getBlock(i, j, k);
			meta = world.getBlockMetadata(i, j, k);
			ForgeDirection dir = ForgeDirection.getOrientation(side);
			int i1 = i + dir.offsetX;
			int j1 = j + dir.offsetY;
			int k1 = k + dir.offsetZ;
			Block block1 = world.getBlock(i1, j1, k1);
			if (!world.isRemote && GOTBannerProtection.isProtected(world, i1, j1, k1, GOTBannerProtection.forPlayer(entityplayer, GOTBannerProtection.Permission.FULL), true) && block1 instanceof BlockFire) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onBreakingSpeed(BreakSpeed event) {
		EntityPlayer entityplayer = event.entityPlayer;
		Block block = event.block;
		int meta = event.metadata;
		float speed = event.newSpeed;
		ItemStack itemstack = entityplayer.getCurrentEquippedItem();
		if (itemstack != null && itemstack.getItem().getDigSpeed(itemstack, block, meta) > 1.0f) {
			speed *= GOTEnchantmentHelper.calcToolEfficiency(itemstack);
		}
		event.newSpeed = speed;
	}

	@SubscribeEvent
	public void onChunkDataLoad(ChunkDataEvent.Load event) {
		World world = event.world;
		Chunk chunk = event.getChunk();
		NBTTagCompound data = event.getData();
		if (!world.isRemote && world.provider instanceof GOTWorldProvider) {
			GOTBiomeVariantStorage.loadChunkVariants(world, chunk, data);
		}
	}

	@SubscribeEvent
	public void onChunkDataSave(ChunkDataEvent.Save event) {
		World world = event.world;
		Chunk chunk = event.getChunk();
		NBTTagCompound data = event.getData();
		if (!world.isRemote && world.provider instanceof GOTWorldProvider) {
			GOTBiomeVariantStorage.saveChunkVariants(world, chunk, data);
		}
	}

	@SubscribeEvent
	public void onChunkStartWatching(ChunkWatchEvent.Watch event) {
		EntityPlayerMP entityplayer = event.player;
		World world = entityplayer.worldObj;
		ChunkCoordIntPair chunkCoords = event.chunk;
		Chunk chunk = world.getChunkFromChunkCoords(chunkCoords.chunkXPos, chunkCoords.chunkZPos);
		if (!world.isRemote && world.provider instanceof GOTWorldProvider) {
			GOTBiomeVariantStorage.sendChunkVariantsToPlayer(world, chunk, entityplayer);
		}
	}

	@SubscribeEvent
	public void onChunkStopWatching(ChunkWatchEvent.UnWatch event) {
		EntityPlayerMP entityplayer = event.player;
		World world = entityplayer.worldObj;
		ChunkCoordIntPair chunkCoords = event.chunk;
		Chunk chunk = world.getChunkFromChunkCoords(chunkCoords.chunkXPos, chunkCoords.chunkZPos);
		if (!world.isRemote && world.provider instanceof GOTWorldProvider) {
			GOTBiomeVariantStorage.sendUnwatchToPlayer(world, chunk, entityplayer);
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if ("got".equals(event.modID)) {
			GOTConfig.load();
		}
		if (GOTModChecker.hasNEI()) {
			for (IConfigureNEI element : NEIModContainer.plugins) {
				IConfigureNEI iConfigNEI = element;
				if (!iConfigNEI.getClass().equals(NEIGOTIntegratorConfig.class)) {
					continue;
				}
				NEIGOTIntegratorConfig configNEI = (NEIGOTIntegratorConfig) iConfigNEI;
				for (ItemStack element2 : configNEI.hiddenItems) {
					if (!ItemInfo.hiddenItems.contains(element2)) {
						continue;
					}
					ItemInfo.hiddenItems.remove(element2);
				}
				configNEI.loadConfig();
			}
		}
	}

	@SubscribeEvent
	public void onCrafting(PlayerEvent.ItemCraftedEvent event) {
		EntityPlayer entityplayer = event.player;
		ItemStack itemstack = event.crafting;
		if (!entityplayer.worldObj.isRemote) {
			if (itemstack.getItem() == Item.getItemFromBlock(GOTRegistry.bomb)) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.CRAFT_BOMB);
			}
			if (itemstack.getItem() == Item.getItemFromBlock(GOTRegistry.wildFireJar)) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.CRAFT_WILD_FIRE);
			}
			for (GOTEnumDyeColor color : GOTEnumDyeColor.values()) {
				if (itemstack.getItem() == Item.getItemFromBlock(GOTRegistry.concretePowder.get(color))) {
					GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.GET_CONCRETE);
				}
			}
			if (itemstack.getItem() == GOTRegistry.bronzeIngot) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.GET_BRONZE);
			}
		}
	}

	@SubscribeEvent
	public void onEntityAttackedByPlayer(AttackEntityEvent event) {
		Entity entity = event.target;
		World world = entity.worldObj;
		EntityPlayer entityplayer = event.entityPlayer;
		if (!world.isRemote && (entity instanceof EntityHanging || entity instanceof GOTBannerProtectable) && GOTBannerProtection.isProtected(world, entity, GOTBannerProtection.forPlayer(entityplayer, GOTBannerProtection.Permission.FULL), true)) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onEntityHurt(LivingHurtEvent event) {
		EntityPlayer p;
		if (event.entityLiving instanceof EntityPlayer && (p = (EntityPlayer) event.entityLiving).getHeldItem() != null && p.getHeldItem().getItem() instanceof ItemSword && p.isUsingItem()) {
			ItemStack heldItem = p.getHeldItem();
			int heldDamage = p.getHeldItem().getItemDamage();
			if (event.source != DamageSource.drown && event.source != DamageSource.inFire && event.source != DamageSource.onFire && event.source != DamageSource.outOfWorld && event.source != DamageSource.starve && event.source != DamageSource.onFire && event.source != DamageSource.outOfWorld) {
				event.ammount *= 0.4f;
				heldItem.setItemDamage(heldDamage + 5);
			}
		}
	}

	@SubscribeEvent
	public void onEntityInteract(EntityInteractEvent event) {
		String playerName;
		UUID brandingPlayer;
		EntityPlayer entityplayer = event.entityPlayer;
		World world = entityplayer.worldObj;
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		Entity entity = event.target;
		if (!world.isRemote && (entity instanceof EntityHanging || entity instanceof GOTBannerProtectable) && GOTBannerProtection.isProtected(world, entity, GOTBannerProtection.forPlayer(entityplayer, GOTBannerProtection.Permission.FULL), true)) {
			event.setCanceled(true);
			return;
		}
		if ((entity instanceof EntityCow || entity instanceof GOTEntityZebra) && itemstack != null && GOTItemMug.isItemEmptyDrink(itemstack)) {
			GOTItemMug.Vessel vessel = GOTItemMug.getVessel(itemstack);
			ItemStack milkItem = new ItemStack(GOTRegistry.mugMilk);
			GOTItemMug.setVessel(milkItem, vessel, true);
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}
			if (itemstack.stackSize <= 0 || entityplayer.capabilities.isCreativeMode) {
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, milkItem);
			} else if (!entityplayer.inventory.addItemStackToInventory(milkItem)) {
				entityplayer.dropPlayerItemWithRandomChoice(milkItem, false);
			}
			event.setCanceled(true);
			return;
		}
		if (entity instanceof EntityWolf) {
			int dyeType;
			EntityWolf wolf = (EntityWolf) entity;
			if (itemstack != null && GOT.isOreNameEqual(itemstack, "bone") && itemstack.getItem() != Items.bone) {
				Item prevItem = itemstack.getItem();
				itemstack.func_150996_a(Items.bone);
				boolean flag = wolf.interact(entityplayer);
				itemstack.func_150996_a(prevItem);
				if (flag) {
					event.setCanceled(true);
					return;
				}
			}
			if (itemstack != null && (dyeType = GOTItemDye.isItemDye(itemstack)) >= 0 && itemstack.getItem() != Items.dye) {
				Item prevItem = itemstack.getItem();
				int prevMeta = itemstack.getItemDamage();
				itemstack.func_150996_a(Items.dye);
				itemstack.setItemDamage(dyeType);
				boolean flag = wolf.interact(entityplayer);
				itemstack.func_150996_a(prevItem);
				itemstack.setItemDamage(prevMeta);
				if (flag) {
					event.setCanceled(true);
					return;
				}
			}
		}
		if (entity instanceof GOTTradeable && ((GOTTradeable) entity).canTradeWith(entityplayer)) {
			if (entity instanceof GOTUnitTradeable) {
				entityplayer.openGui(GOT.instance, 24, world, entity.getEntityId(), 0, 0);
			} else {
				entityplayer.openGui(GOT.instance, 19, world, entity.getEntityId(), 0, 0);
			}
			event.setCanceled(true);
			return;
		}
		if (entity instanceof GOTUnitTradeable && ((GOTUnitTradeable) entity).canTradeWith(entityplayer)) {
			entityplayer.openGui(GOT.instance, 20, world, entity.getEntityId(), 0, 0);
			event.setCanceled(true);
			return;
		}
		if (entity instanceof GOTMercenary && ((GOTMercenary) entity).canTradeWith(entityplayer) && ((GOTEntityNPC) entity).hiredNPCInfo.getHiringPlayerUUID() == null) {
			entityplayer.openGui(GOT.instance, 58, world, entity.getEntityId(), 0, 0);
			event.setCanceled(true);
			return;
		}
		if (entity instanceof GOTEntityNPC) {
			GOTEntityNPC npc = (GOTEntityNPC) entity;
			if (npc.hiredNPCInfo.getHiringPlayer() == entityplayer) {
				if (entity instanceof GOTEntityProstitute) {
					entityplayer.openGui(GOT.instance, 1, world, entity.getEntityId(), 0, 0);
				} else {
					entityplayer.openGui(GOT.instance, 21, world, entity.getEntityId(), 0, 0);
				}
				event.setCanceled(true);
				return;
			}
			if (npc.hiredNPCInfo.isActive && entityplayer.capabilities.isCreativeMode && itemstack != null && itemstack.getItem() == Items.clock) {
				UUID hiringUUID;
				String playerName2;
				if (!world.isRemote && MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile()) && (hiringUUID = npc.hiredNPCInfo.getHiringPlayerUUID()) != null && (playerName2 = GOTEventHandler.getUsernameWithoutWebservice(hiringUUID)) != null) {
					ChatComponentText msg = new ChatComponentText("Hired unit belongs to " + playerName2);
					msg.getChatStyle().setColor(EnumChatFormatting.GREEN);
					entityplayer.addChatMessage(msg);
				}
				event.setCanceled(true);
				return;
			}
		}
		if (!world.isRemote && entityplayer.capabilities.isCreativeMode && MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile()) && itemstack != null && itemstack.getItem() == Items.clock && entity instanceof EntityLiving && (brandingPlayer = GOTItemBrandingIron.getBrandingPlayer(entity)) != null && (playerName = GOTEventHandler.getUsernameWithoutWebservice(brandingPlayer)) != null) {
			ChatComponentText msg = new ChatComponentText("Entity was branded by " + playerName);
			msg.getChatStyle().setColor(EnumChatFormatting.GREEN);
			entityplayer.addChatMessage(msg);
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		Entity entity = event.entity;
		World world = entity.worldObj;
		if (!world.isRemote && entity instanceof EntityXPOrb && !GOTConfig.enchantingVanilla && world.provider instanceof GOTWorldProvider) {
			event.setCanceled(true);
			return;
		}
		if (!world.isRemote && entity.getClass() == EntityFishHook.class && world.provider instanceof GOTWorldProvider) {
			EntityFishHook oldFish = (EntityFishHook) entity;
			NBTTagCompound fishData = new NBTTagCompound();
			oldFish.writeToNBT(fishData);
			oldFish.setDead();
			GOTEntityFishHook newFish = new GOTEntityFishHook(world);
			newFish.readFromNBT(fishData);
			newFish.field_146042_b = oldFish.field_146042_b;
			if (newFish.field_146042_b != null) {
				newFish.field_146042_b.fishEntity = newFish;
				newFish.setPlayerID(newFish.field_146042_b.getEntityId());
			}
			world.spawnEntityInWorld(newFish);
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onEntitySpawnAttempt(LivingSpawnEvent.CheckSpawn event) {
		EntityLivingBase entity = event.entityLiving;
		World world = entity.worldObj;
		if (!world.isRemote && entity instanceof EntityMob && GOTBannerProtection.isProtected(world, entity, GOTBannerProtection.anyBanner(), false)) {
			event.setResult(Event.Result.DENY);
		}
	}

	@SubscribeEvent
	public void onExplosionDetonate(ExplosionEvent.Detonate event) {
		Explosion expl = event.explosion;
		World world = event.world;
		Entity exploder = expl.exploder;
		if (!world.isRemote && exploder != null) {
			GOTBannerProtection.IFilter protectFilter = null;
			if (exploder instanceof GOTEntityNPC || exploder instanceof EntityMob) {
				protectFilter = GOTBannerProtection.anyBanner();
			} else if (exploder instanceof EntityThrowable) {
				protectFilter = GOTBannerProtection.forThrown((EntityThrowable) exploder);
			} else if (exploder instanceof EntityTNTPrimed) {
				protectFilter = GOTBannerProtection.forTNT((EntityTNTPrimed) exploder);
			} else if (exploder instanceof EntityMinecartTNT) {
				protectFilter = GOTBannerProtection.forTNTMinecart();
			}
			if (protectFilter != null) {
				List<ChunkPosition> blockList = expl.affectedBlockPositions;
				ArrayList<ChunkPosition> removes = new ArrayList<>();
				for (ChunkPosition blockPos : blockList) {
					int i = blockPos.chunkPosX;
					int j = blockPos.chunkPosY;
					int k = blockPos.chunkPosZ;
					if (!GOTBannerProtection.isProtected(world, i, j, k, protectFilter, false)) {
						continue;
					}
					removes.add(blockPos);
				}
				blockList.removeAll(removes);
			}
		}
	}

	@SubscribeEvent
	public void onFillBucket(FillBucketEvent event) {
		EntityPlayer entityplayer = event.entityPlayer;
		World world = event.world;
		MovingObjectPosition target = event.target;
		if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			int i = target.blockX;
			int j = target.blockY;
			int k = target.blockZ;
			if (!world.isRemote && GOTBannerProtection.isProtected(world, i, j, k, GOTBannerProtection.forPlayer(entityplayer, GOTBannerProtection.Permission.FULL), true)) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onHarvestCheck(HarvestCheck event) {
		EntityPlayer entityplayer = event.entityPlayer;
		Block block = event.block;
		ItemStack itemstack = entityplayer.getCurrentEquippedItem();
		if (itemstack != null && block instanceof BlockWeb && itemstack.getItem() instanceof ItemShears) {
			event.success = true;
		}
	}

	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		EntityPlayer entityplayer = event.entityPlayer;
		ItemStack itemstack = event.item.getEntityItem();
		if (!entityplayer.worldObj.isRemote) {
			if (itemstack.stackSize > 0) {
				for (int i = 0; i < entityplayer.inventory.getSizeInventory(); ++i) {
					ItemStack itemInSlot = entityplayer.inventory.getStackInSlot(i);
					if (itemInSlot == null || itemInSlot.getItem() != GOTRegistry.pouch) {
						continue;
					}
					GOTItemPouch.tryAddItemToPouch(itemInSlot, itemstack, true);
					if (itemstack.stackSize <= 0) {
						break;
					}
				}
				if (itemstack.stackSize <= 0) {
					event.setResult(Event.Result.ALLOW);
				}
			}
			if (itemstack.getItem() == Item.getItemFromBlock(GOTRegistry.clover) && itemstack.getItemDamage() == 1) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.FIND_FOUR_LEAF_CLOVER);
			}
			if (GOTConfig.enchantingAutoRemoveVanilla) {
				GOTEventHandler.dechant(itemstack, entityplayer);
			}
		}
	}

	@SubscribeEvent
	public void onItemUseFinish(PlayerUseItemEvent.Finish event) {
		EntityPlayer entityplayer = event.entityPlayer;
		World world = entityplayer.worldObj;
		ItemStack itemstack = event.item;
		if (!world.isRemote && GOTPoisonedDrinks.isDrinkPoisoned(itemstack)) {
			GOTPoisonedDrinks.addPoisonEffect(entityplayer, itemstack);
		}
	}

	@SubscribeEvent
	public void onItemUseStop(PlayerUseItemEvent.Stop event) {
		EntityPlayer entityplayer = event.entityPlayer;
		World world = entityplayer.worldObj;
		ItemStack itemstack = event.item;
		int usingTick = event.duration;
		if (itemstack != null && itemstack.getItem() instanceof ItemBow && !(itemstack.getItem() instanceof GOTItemBow) && !(itemstack.getItem() instanceof GOTItemCrossbow)) {
			if (!world.isRemote) {
				if (proxyBowItemServer == null) {
					proxyBowItemServer = new GOTItemBow(Item.ToolMaterial.WOOD);
				}
				proxyBowItemServer.onPlayerStoppedUsing(itemstack, world, entityplayer, usingTick);
				proxyBowItemServer = null;
				event.setCanceled(true);
				return;
			}
			if (proxyBowItemClient == null) {
				proxyBowItemClient = new GOTItemBow(Item.ToolMaterial.WOOD);
			}
			proxyBowItemClient.onPlayerStoppedUsing(itemstack, world, entityplayer, usingTick);
			proxyBowItemClient = null;
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onLivingAttacked(LivingAttackEvent event) {
		EntityLivingBase entity = event.entityLiving;
		EntityLivingBase attacker = event.source.getEntity() instanceof EntityLivingBase ? (EntityLivingBase) event.source.getEntity() : null;
		World world = entity.worldObj;
		if (entity instanceof GOTNPCMount && entity.riddenByEntity != null && attacker == entity.riddenByEntity) {
			cancelAttackEvent(event);
		}
		if (attacker instanceof EntityPlayer && !GOT.canPlayerAttackEntity((EntityPlayer) attacker, entity, true)) {
			cancelAttackEvent(event);
		}
		if (attacker instanceof EntityCreature && !GOT.canNPCAttackEntity((EntityCreature) attacker, entity, false)) {
			cancelAttackEvent(event);
		}
		if (event.source instanceof EntityDamageSourceIndirect) {
			Entity projectile = event.source.getSourceOfDamage();
			if (projectile instanceof EntityArrow || projectile instanceof GOTEntityCrossbowBolt || projectile instanceof GOTEntityDart) {
				boolean isLegendaryArmor = true;
				for (int i = 0; i < 4; ++i) {
					ItemStack armour = entity.getEquipmentInSlot(i + 1);
					if (armour != null && armour.getItem() instanceof ItemArmor && ((ItemArmor) armour.getItem()).getArmorMaterial() == GOTMaterial.ROYCE.toArmorMaterial()) {
						continue;
					}
					isLegendaryArmor = false;
					break;
				}
				ItemStack armour = entity.getEquipmentInSlot(3);
				if (armour != null && armour.getItem() instanceof ItemArmor && ((ItemArmor) armour.getItem()).getArmorMaterial() == GOTMaterial.BLACKSKIN.toArmorMaterial()) {
					isLegendaryArmor = true;
				}
				if (isLegendaryArmor) {
					if (!world.isRemote && entity instanceof EntityPlayer) {
						((EntityPlayer) entity).inventory.damageArmor(event.ammount);
					}
					cancelAttackEvent(event);
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {
		EntityPlayer entityplayer;
		int k;
		int j;
		int i;
		EntityLivingBase entity = event.entityLiving;
		World world = entity.worldObj;
		DamageSource source = event.source;
		if (!world.isRemote && entity instanceof EntityPlayer) {
			entityplayer = (EntityPlayer) entity;
			i = MathHelper.floor_double(entityplayer.posX);
			j = MathHelper.floor_double(entityplayer.posY);
			k = MathHelper.floor_double(entityplayer.posZ);
			GOTLevelData.getData(entityplayer).setDeathPoint(i, j, k);
			GOTLevelData.getData(entityplayer).setDeathDimension(entityplayer.dimension);
		}
		if (!world.isRemote) {
			entityplayer = null;
			boolean creditHiredUnit = false;
			boolean byNearbyUnit = false;
			if (source.getEntity() instanceof EntityPlayer) {
				entityplayer = (EntityPlayer) source.getEntity();
			} else if (entity.func_94060_bK() instanceof EntityPlayer) {
				entityplayer = (EntityPlayer) entity.func_94060_bK();
			} else if (source.getEntity() instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) source.getEntity();
				if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() != null) {
					entityplayer = npc.hiredNPCInfo.getHiringPlayer();
					creditHiredUnit = true;
					double nearbyDist = 64.0;
					byNearbyUnit = npc.getDistanceSqToEntity(entityplayer) <= nearbyDist * nearbyDist;
				}
			}
			if (entityplayer != null) {
				GOTPlayerData playerData = GOTLevelData.getData(entityplayer);
				GOTFaction entityFaction = GOT.getNPCFaction(entity);
				float prevAlignment = playerData.getAlignment(entityFaction);
				List<GOTFaction> forcedBonusFactions = null;
				if (entity instanceof GOTEntityNPC) {
					forcedBonusFactions = ((GOTEntityNPC) entity).killBonusFactions;
				}
				boolean wasSelfDefenceAgainstAlliedUnit = false;
				if (!creditHiredUnit && prevAlignment > 0.0f && entity instanceof GOTEntityNPC) {
					GOTEntityNPC npc = (GOTEntityNPC) entity;
					if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.wasAttackCommanded) {
						wasSelfDefenceAgainstAlliedUnit = true;
					}
				}
				GOTAlignmentValues.AlignmentBonus alignmentBonus = null;
				if (!wasSelfDefenceAgainstAlliedUnit && (entity instanceof GOTEntityNPC)) {
					GOTEntityNPC npc = (GOTEntityNPC) entity;
					alignmentBonus = new GOTAlignmentValues.AlignmentBonus(npc.getAlignmentBonus(), npc.getEntityClassName());
					alignmentBonus.needsTranslation = true;
					alignmentBonus.isCivilianKill = npc.isCivilianNPC() && !npc.isLegendaryNPC();
					alignmentBonus.isRoyalOrder = npc.isRoyalOrder;
					alignmentBonus.faction = npc.getFaction();
				}
				if (alignmentBonus != null && alignmentBonus.bonus != 0.0f && (!creditHiredUnit || creditHiredUnit && byNearbyUnit)) {
					alignmentBonus.isKill = true;
					if (creditHiredUnit) {
						alignmentBonus.killByHiredUnit = true;
					}
					playerData.addAlignment(entityplayer, alignmentBonus, entityFaction, forcedBonusFactions, entity);
				}
				if (!creditHiredUnit) {
					if (entityFaction.allowPlayer) {
						GOTFaction pledgeFac;
						playerData.getFactionData(entityFaction).addNPCKill();
						List<GOTFaction> killBonuses = entityFaction.getBonusesForKilling();
						for (GOTFaction enemy : killBonuses) {
							playerData.getFactionData(enemy).addEnemyKill();
						}
						if (!entityplayer.capabilities.isCreativeMode && entityFaction.inDefinedControlZone(entityplayer, Math.max(entityFaction.getControlZoneReducedRange(), 50))) {
							GOTFactionBounties.forFaction(entityFaction).forPlayer(entityplayer).recordNewKill();
						}
						if ((pledgeFac = playerData.getPledgeFaction()) != null && (pledgeFac == entityFaction || pledgeFac.isAlly(entityFaction))) {
							playerData.onPledgeKill(entityplayer);
						}
					}
					float newAlignment = playerData.getAlignment(entityFaction);
					if (!wasSelfDefenceAgainstAlliedUnit && !entityplayer.capabilities.isCreativeMode && entityFaction != GOTFaction.UNALIGNED) {
						int sentSpeeches = 0;
						int maxSpeeches = 5;
						double range = 8.0;
						List nearbyAlliedNPCs = world.selectEntitiesWithinAABB(EntityLiving.class, entity.boundingBox.expand(range, range, range), new IEntitySelector() {

							@Override
							public boolean isEntityApplicable(Entity entitySelect) {
								if (entitySelect.isEntityAlive()) {
									GOTFaction fac = GOT.getNPCFaction(entitySelect);
									return fac.isGoodRelation(entityFaction);
								}
								return false;
							}
						});
						for (Object nearbyAlliedNPC : nearbyAlliedNPCs) {
							String speech;
							GOTEntityNPC gotnpc;
							EntityLiving npc = (EntityLiving) nearbyAlliedNPC;
							if (npc instanceof GOTEntityNPC && ((GOTEntityNPC) npc).hiredNPCInfo.isActive && newAlignment > 0.0f || npc.getAttackTarget() != null) {
								continue;
							}
							npc.setAttackTarget(entityplayer);
							if (!(npc instanceof GOTEntityNPC) || sentSpeeches >= maxSpeeches || (speech = (gotnpc = (GOTEntityNPC) npc).getSpeechBank(entityplayer)) == null || gotnpc.getDistanceSqToEntity(entityplayer) >= range) {
								continue;
							}
							gotnpc.sendSpeechBank(entityplayer, speech);
							++sentSpeeches;
						}
					}
					if (!playerData.isSiegeActive()) {
						List<GOTMiniQuest> miniquests = playerData.getMiniQuests();
						for (GOTMiniQuest quest : miniquests) {
							quest.onKill(entityplayer, entity);
						}
						if (entity instanceof EntityPlayer) {
							EntityPlayer slainPlayer = (EntityPlayer) entity;
							List<GOTMiniQuest> slainMiniquests = GOTLevelData.getData(slainPlayer).getMiniQuests();
							for (GOTMiniQuest quest : slainMiniquests) {
								quest.onKilledByPlayer(slainPlayer, entityplayer);
							}
						}
					}
				}
			}
		}

		if (!world.isRemote && (source.getEntity() instanceof GOTEntityNPC)) {
			GOTEntityNPC npc = (GOTEntityNPC) source.getEntity();
			if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() != null) {
				npc.hiredNPCInfo.getHiringPlayer();
			}
		}

		if (!world.isRemote && entity instanceof EntityPlayer) {
			entityplayer = (EntityPlayer) entity;
			if (GOTEnchantmentHelper.hasMeleeOrRangedEnchant(source, GOTEnchantment.headhunting)) {
				ItemStack playerHead = new ItemStack(Items.skull, 1, 3);
				GameProfile profile = entityplayer.getGameProfile();
				NBTTagCompound profileData = new NBTTagCompound();
				NBTUtil.func_152460_a(profileData, profile);
				playerHead.setTagInfo("SkullOwner", profileData);
				entityplayer.entityDropItem(playerHead, 0.0f);
			}
		}
	}

	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event) {
		EntityLivingBase entity = event.entityLiving;
		Random rand = entity.getRNG();
		int i = event.lootingLevel;
		if (entity instanceof EntitySheep && GOTConfig.dropMutton) {
			int meat = rand.nextInt(3) + rand.nextInt(1 + i);
			for (int l = 0; l < meat; ++l) {
				if (entity.isBurning()) {
					entity.dropItem(GOTRegistry.muttonCooked, 1);
					continue;
				}
				entity.dropItem(GOTRegistry.muttonRaw, 1);
			}
		}
	}

	@SubscribeEvent
	public void onLivingHeal(LivingHealEvent event) {
		EntityLivingBase entity = event.entityLiving;
		World world = entity.worldObj;
		if (!world.isRemote && entity instanceof EntityPlayer && !((EntityPlayer) entity).capabilities.isCreativeMode && entity.isEntityAlive()) {
			int i = MathHelper.floor_double(entity.posX);
			int j = MathHelper.floor_double(entity.boundingBox.minY);
			int k2 = MathHelper.floor_double(entity.posZ);
			BiomeGenBase biomeGenBase = world.getBiomeGenForCoords(i, k2);
			if (biomeGenBase instanceof GOTBiome && shouldApplyWinterOverlay(world, biomeGenBase, entity) && (world.canBlockSeeTheSky(i, j, k2) || entity.isInWater()) && world.getSavedLightValue(EnumSkyBlock.Block, i, j, k2) < 10) {
				event.amount *= 0.3f;
			}
		}
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		EntityLivingBase entity = event.entityLiving;
		EntityLivingBase attacker = event.source.getEntity() instanceof EntityLivingBase ? (EntityLivingBase) event.source.getEntity() : null;
		World world = entity.worldObj;
		if (entity instanceof EntityPlayerMP && event.source == GOTDamage.frost) {
			GOTDamage.doFrostDamage((EntityPlayerMP) entity);
		}
		if (!world.isRemote) {
			ItemStack weapon;
			int preMaxHurtResTime = entity.maxHurtResistantTime;
			int maxHurtResTime = 20;
			if (attacker != null && GOTWeaponStats.isMeleeWeapon(weapon = attacker.getHeldItem())) {
				maxHurtResTime = GOTWeaponStats.getAttackTimeWithBase(weapon, 20);
			}
			entity.maxHurtResistantTime = maxHurtResTime = Math.min(maxHurtResTime, 20);
			if (entity.hurtResistantTime == preMaxHurtResTime) {
				entity.hurtResistantTime = maxHurtResTime;
			}
		}
		if (event.source.getSourceOfDamage() instanceof GOTEntityArrowPoisoned && !world.isRemote) {
			GOTItemDagger.applyStandardPoison(entity);
		}
		if (event.source.getSourceOfDamage() instanceof GOTEntityArrowFire && !world.isRemote) {
			entity.setFire(30);
		}
		if (!world.isRemote) {
			if (GOTEnchantmentHelper.hasMeleeOrRangedEnchant(event.source, GOTEnchantment.fire)) {
				GOTPacketWeaponFX packet = new GOTPacketWeaponFX(GOTPacketWeaponFX.Type.INFERNAL, entity);
				GOTPacketHandler.networkWrapper.sendToAllAround(packet, GOTPacketHandler.nearEntity(entity, 64.0));
			}
			if (GOTEnchantmentHelper.hasMeleeOrRangedEnchant(event.source, GOTEnchantment.chill)) {
				GOTEnchantmentWeaponSpecial.doChillAttack(entity);
			}
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		int k2;
		int i;
		int j;
		EntityLivingBase entity = event.entityLiving;
		World world = entity.worldObj;
		if (!world.isRemote) {
			GOTEnchantmentHelper.onEntityUpdate(entity);
		}
		if (GOTConfig.enchantingAutoRemoveVanilla && !world.isRemote && entity instanceof EntityPlayer && entity.ticksExisted % 60 == 0) {
			EntityPlayer entityplayer = (EntityPlayer) entity;
			for (int l2 = 0; l2 < entityplayer.inventory.getSizeInventory(); ++l2) {
				ItemStack itemstack = entityplayer.inventory.getStackInSlot(l2);
				if (itemstack == null) {
					continue;
				}
				GOTEventHandler.dechant(itemstack, entityplayer);
			}
		}
		if (!world.isRemote && entity.isEntityAlive() && entity.isInWater() && entity.ridingEntity == null && entity.ticksExisted % 10 == 0) {
			boolean flag = true;
			if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode) {
				flag = false;
			}
			if (entity instanceof GOTEntityUlthosSpider || entity instanceof GOTEntityStoneman || entity instanceof GOTEntityAsshaiMan || entity instanceof GOTEntityAsshaiArchmag || entity instanceof GOTEntitySothoryosMan) {
				flag = false;
			}
			if (flag && world.getBiomeGenForCoords(i = MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posZ)) instanceof GOTBiomeShadowLand) {
				entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 600, 1));
				entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 600, 1));
				entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 600));
				entity.addPotionEffect(new PotionEffect(Potion.blindness.id, 600));
			}
			if (flag && world.getBiomeGenForCoords(i = MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posZ)) instanceof GOTBiomeValyria) {
				entity.addPotionEffect(new PotionEffect(Potion.wither.id, 600));
			}
			if (flag && world.getBiomeGenForCoords(i = MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posZ)) instanceof GOTBiomeValyriaSea) {
				entity.addPotionEffect(new PotionEffect(Potion.wither.id, 600));
			}
			if (flag && world.getBiomeGenForCoords(i = MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posZ)) instanceof GOTBiomeValyriaVolcano) {
				entity.addPotionEffect(new PotionEffect(Potion.wither.id, 600));
			}
		}
		if (!world.isRemote && entity.isEntityAlive()) {
			IAttributeInstance speedAttribute;
			ItemStack weapon = entity.getHeldItem();
			boolean lanceOnFoot = false;
			if (weapon != null && weapon.getItem() instanceof GOTItemLance && entity.ridingEntity == null) {
				lanceOnFoot = true;
				if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode) {
					lanceOnFoot = false;
				}
			}
			if ((speedAttribute = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed)).getModifier(GOTItemLance.lanceSpeedBoost_id) != null) {
				speedAttribute.removeModifier(GOTItemLance.lanceSpeedBoost);
			}
			if (lanceOnFoot) {
				speedAttribute.applyModifier(GOTItemLance.lanceSpeedBoost);
			}
		}
		if (!world.isRemote && entity.isEntityAlive() && entity.ticksExisted % 20 == 0) {
			boolean flag = true;
			if (entity instanceof GOTBiome.ImmuneToFrost || entity instanceof EntitySheep || entity instanceof EntityHorse || entity instanceof EntitySquid || entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).isImmuneToFrost) {
				flag = false;
			}
			if (entity instanceof EntityPlayer) {
				flag = !((EntityPlayer) entity).capabilities.isCreativeMode;
			}
			if (flag) {
				i = MathHelper.floor_double(entity.posX);
				j = MathHelper.floor_double(entity.boundingBox.minY);
				k2 = MathHelper.floor_double(entity.posZ);
				BiomeGenBase biome = world.getBiomeGenForCoords(i, k2);
				if ((biome.temperature == 0.0F || biome instanceof GOTBiome && ((GOTBiome) biome).isAltitudeZone && j >= 140) && (world.canBlockSeeTheSky(i, j, k2) || entity.isInWater()) && world.getSavedLightValue(EnumSkyBlock.Block, i, j, k2) < 10) {
					int frostProtection = 50;
					for (int l1 = 1; l1 < 4; ++l1) {
						ItemStack armor = entity.getEquipmentInSlot(l1);
						if (armor == null || !(armor.getItem() instanceof ItemArmor)) {
							continue;
						}
						ItemArmor.ArmorMaterial armorMaterial = ((ItemArmor) armor.getItem()).getArmorMaterial();
						Item material = armorMaterial.func_151685_b();
						if (material == Items.leather) {
							frostProtection += 50;
							continue;
						}
						if ((material == GOTRegistry.fur) || (material == GOTRegistry.iceShard) || (armorMaterial == GOTMaterial.NORTH.toArmorMaterial()) || (armorMaterial == GOTMaterial.REDKING.toArmorMaterial())) {
							frostProtection += 100;
							continue;
						}
						if (armorMaterial != GOTMaterial.GIFT.toArmorMaterial()) {
							continue;
						}
						frostProtection += 50;
					}
					if (world.isRaining()) {
						frostProtection /= 3;
					}
					if (entity.isInWater()) {
						frostProtection /= 20;
					}
					if (biome instanceof GOTBiomeAlwaysWinter) {
						frostProtection /= 5;
					}
					if (world.rand.nextInt(frostProtection = Math.max(frostProtection, 1)) == 0) {
						entity.attackEntityFrom(GOTDamage.frost, 1.0f);
					}
					if (world.rand.nextInt(frostProtection) == 0) {
						entity.attackEntityFrom(GOTDamage.frost, 1.0f);
					}
				}
			}
		}
		if (!world.isRemote && entity.isEntityAlive() && entity.ticksExisted % 20 == 0) {
			boolean flag = true;
			if (entity instanceof GOTBiome.ImmuneToHeat || entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).isImmuneToFire()) {
				flag = false;
			}
			if (entity instanceof EntityPlayer) {
				flag = !((EntityPlayer) entity).capabilities.isCreativeMode;
			}
			if (flag) {
				int i2 = MathHelper.floor_double(entity.posX);
				int j1 = MathHelper.floor_double(entity.boundingBox.minY);
				int k21 = MathHelper.floor_double(entity.posZ);
				BiomeGenBase biome = world.getBiomeGenForCoords(i2, k21);
				if ((biome instanceof GOTBiomeDorneDesert || biome instanceof GOTBiomeJogosNhaiDesert || biome instanceof GOTBiomeQarthDesert || biome instanceof GOTBiomeUlthosDesert) && !entity.isInWater() && world.canBlockSeeTheSky(i2, j1, k21) && world.isDaytime()) {
					int burnChance = 50;
					int burnProtection = 0;
					for (int l1 = 0; l1 < 4; ++l1) {
						ItemStack armour = entity.getEquipmentInSlot(l1 + 1);
						if (armour == null || !(armour.getItem() instanceof ItemArmor)) {
							continue;
						}
						ItemArmor.ArmorMaterial material = ((ItemArmor) armour.getItem()).getArmorMaterial();
						if (material.customCraftingMaterial == Items.leather) {
							burnProtection += 50;
						}
						if (material != GOTMaterial.ROBES.toArmorMaterial() || material != GOTMaterial.DORNE.toArmorMaterial()) {
							continue;
						}
						burnProtection += 400;
					}
					burnChance += burnProtection;
					if (world.rand.nextInt(burnChance = Math.max(burnChance, 1)) == 0 && entity.attackEntityFrom(DamageSource.onFire, 1.0f) && entity instanceof EntityPlayerMP) {
						GOTDamage.doBurnDamage((EntityPlayerMP) entity);
					}
				}
			}
		}
		if (world.isRemote) {
			GOTPlateFallingInfo.getOrCreateFor(entity, true).update();
		}
	}

	@SubscribeEvent
	public void onMinecartInteract(MinecartInteractEvent event) {
		EntityPlayer entityplayer = event.player;
		World world = entityplayer.worldObj;
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		EntityMinecart minecart = event.minecart;
		if (minecart instanceof EntityMinecartChest && itemstack != null && itemstack.getItem() instanceof GOTItemPouch) {
			if (!world.isRemote) {
				int pouchSlot = entityplayer.inventory.currentItem;
				entityplayer.openGui(GOT.instance, GOTCommonProxy.packGuiIDWithSlot(64, pouchSlot), world, minecart.getEntityId(), 0, 0);
			}
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
		EntityPlayer entityplayer = event.player;
		if (!entityplayer.worldObj.isRemote) {
			GOTLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, entityplayer.worldObj);
			GOTLevelData.sendAllAlignmentsInWorldToPlayer(entityplayer, entityplayer.worldObj);
			GOTLevelData.sendShieldToAllPlayersInWorld(entityplayer, entityplayer.worldObj);
			GOTLevelData.sendAllShieldsInWorldToPlayer(entityplayer, entityplayer.worldObj);
			GOTLevelData.sendCapeToAllPlayersInWorld(entityplayer, entityplayer.worldObj);
			GOTLevelData.sendAllCapesInWorldToPlayer(entityplayer, entityplayer.worldObj);
		}
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		EntityPlayer entityplayer = event.player;
		World world = entityplayer.worldObj;
		if (!world.isRemote) {
			EntityPlayerMP entityplayermp = (EntityPlayerMP) entityplayer;
			if (world.provider.terrainType instanceof GOTWorldType && entityplayermp.dimension == 0 && !GOTLevelData.getData(entityplayermp).getTeleportedME()) {
				int dimension = GOTDimension.GAME_OF_THRONES.dimensionID;
				GOTTeleporter teleporter = new GOTTeleporter(DimensionManager.getWorld(dimension), false);
				MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityplayermp, dimension, teleporter);
				GOTLevelData.getData(entityplayermp).setTeleportedME(true);
			}
			GOTLevelData.sendLoginPacket(entityplayermp);
			GOTLevelData.sendPlayerData(entityplayermp);
			GOTLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, world);
			GOTLevelData.sendAllAlignmentsInWorldToPlayer(entityplayer, world);
			GOTLevelData.sendShieldToAllPlayersInWorld(entityplayermp, world);
			GOTLevelData.sendAllShieldsInWorldToPlayer(entityplayermp, world);
			GOTDate.sendUpdatePacket(entityplayermp, false);
			GOTFactionRelations.sendAllRelationsTo(entityplayermp);
			GOTPlayerData pd = GOTLevelData.getData(entityplayermp);
			pd.updateFastTravelClockFromLastOnlineTime(entityplayermp, world);
		}
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		EntityPlayer entityplayer = event.player;
		World world = entityplayer.worldObj;
		if (!world.isRemote && entityplayer instanceof EntityPlayerMP && world instanceof WorldServer) {
			EntityPlayerMP entityplayermp = (EntityPlayerMP) entityplayer;
			WorldServer worldserver = (WorldServer) world;
			ChunkCoordinates deathPoint = GOTLevelData.getData(entityplayermp).getDeathPoint();
			int deathDimension = GOTLevelData.getData(entityplayermp).getDeathDimension();
			if ((deathDimension == GOTDimension.GAME_OF_THRONES.dimensionID) && GOTConfig.GOTRespawning) {
				boolean hasBed;
				double respawnThreshold;
				ChunkCoordinates bedLocation = entityplayermp.getBedLocation(entityplayermp.dimension);
				hasBed = bedLocation != null;
				if (hasBed) {
					hasBed = EntityPlayer.verifyRespawnCoordinates(worldserver, bedLocation, entityplayermp.isSpawnForced(entityplayermp.dimension)) != null;
				}
				ChunkCoordinates spawnLocation = hasBed ? bedLocation : worldserver.getSpawnPoint();
				respawnThreshold = hasBed ? (double) GOTConfig.GOTBedRespawnThreshold : (double) GOTConfig.GOTWorldRespawnThreshold;
				if (deathPoint != null) {
					boolean flag;
					flag = deathPoint.getDistanceSquaredToChunkCoordinates(spawnLocation) > respawnThreshold * respawnThreshold;
					if (flag) {
						double randomDistance = MathHelper.getRandomIntegerInRange(worldserver.rand, GOTConfig.GOTMinRespawn, GOTConfig.GOTMaxRespawn);
						float angle = worldserver.rand.nextFloat() * 3.1415927f * 2.0f;
						int i = deathPoint.posX + (int) (randomDistance * MathHelper.sin(angle));
						int k = deathPoint.posZ + (int) (randomDistance * MathHelper.cos(angle));
						int j = GOT.getTrueTopBlock(worldserver, i, k);
						entityplayermp.setLocationAndAngles(i + 0.5, j, k + 0.5, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
						entityplayermp.playerNetServerHandler.setPlayerLocation(i + 0.5, j, k + 0.5, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
					}
				}
			}

		}
	}

	@SubscribeEvent
	public void onSaplingGrow(SaplingGrowTreeEvent event) {
		World world = event.world;
		int i = event.x;
		int j = event.y;
		int k = event.z;
		Block block = world.getBlock(i, j, k);
		if (block == Blocks.sapling) {
			GOTVanillaSaplings.growTree(world, i, j, k, event.rand);
			event.setResult(Event.Result.DENY);
		}
	}

	@SubscribeEvent
	public void onServerChat(ServerChatEvent event) {
		PotionEffect nausea;
		PlayerTitle playerTitle;
		EntityPlayerMP entityplayer = event.player;
		String message = event.message;
		String username = event.username;
		ChatComponentTranslation chatComponent = event.component;
		if (GOTConfig.drunkMessages && (nausea = entityplayer.getActivePotionEffect(Potion.confusion)) != null) {
			int duration = nausea.getDuration();
			float chance = duration / 4800.0f;
			chance = Math.min(chance, 1.0f);
			chance *= 0.4f;
			entityplayer.getRNG();
			String key = chatComponent.getKey();
			Object[] formatArgs = chatComponent.getFormatArgs();
			for (int a = 0; a < formatArgs.length; ++a) {
				Object arg = formatArgs[a];
				String chatText = null;
				if (arg instanceof ChatComponentText) {
					ChatComponentText componentText = (ChatComponentText) arg;
					chatText = componentText.getUnformattedText();
				} else if (arg instanceof String) {
					chatText = (String) arg;
				}
				if (chatText == null || !chatText.equals(message)) {
					continue;
				}
				String newText = GOTDrunkenSpeech.getDrunkenSpeech(chatText, chance);
				if (arg instanceof String) {
					formatArgs[a] = newText;
					continue;
				}
				if (!(arg instanceof ChatComponentText)) {
					continue;
				}
				formatArgs[a] = new ChatComponentText(newText);
			}
			chatComponent = new ChatComponentTranslation(key, formatArgs);
		}
		if ((playerTitle = GOTLevelData.getData(entityplayer).getPlayerTitle()) != null) {
			ArrayList<Object> newFormatArgs = new ArrayList<>();
			for (Object arg : chatComponent.getFormatArgs()) {
				if (arg instanceof ChatComponentText) {
					ChatComponentText componentText = (ChatComponentText) arg;
					if (componentText.getUnformattedText().contains(username)) {
						ChatComponentText usernameComponent = componentText;
						IChatComponent titleComponent = playerTitle.getFullTitleComponent(entityplayer);
						IChatComponent fullUsernameComponent = new ChatComponentText("").appendSibling(titleComponent).appendSibling(usernameComponent);
						newFormatArgs.add(fullUsernameComponent);
						continue;
					}
					newFormatArgs.add(componentText);
					continue;
				}
				newFormatArgs.add(arg);
			}
			ChatComponentTranslation newChatComponent = new ChatComponentTranslation(chatComponent.getKey(), newFormatArgs.toArray());
			newChatComponent.setChatStyle(chatComponent.getChatStyle().createShallowCopy());
			for (Object sibling : chatComponent.getSiblings()) {
				newChatComponent.appendSibling((IChatComponent) sibling);
			}
			chatComponent = newChatComponent;
		}
		event.component = chatComponent;
	}

	@SubscribeEvent
	public void onSmelting(PlayerEvent.ItemSmeltedEvent event) {
		EntityPlayer entityplayer = event.player;
		ItemStack itemstack = event.smelting;
		if (!entityplayer.worldObj.isRemote) {
			if (itemstack.getItem() == GOTRegistry.bronzeIngot) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.GET_BRONZE);
			}
			if (itemstack.getItem() == GOTRegistry.copperIngot) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.GET_COPPER);
			}
		}
	}

	@SubscribeEvent
	public void onStartTrackingEntity(StartTracking event) {
		Entity entity = event.target;
		EntityPlayer entityplayer = event.entityPlayer;
		if (!entity.worldObj.isRemote && entity instanceof GOTEntityNPC) {
			EntityPlayerMP entityplayermp = (EntityPlayerMP) entityplayer;
			GOTEntityNPC npc = (GOTEntityNPC) entity;
			npc.onPlayerStartTracking(entityplayermp);
		}
		if (!entity.worldObj.isRemote && entity instanceof GOTRandomSkinEntity) {
			GOTPacketEntityUUID packet = new GOTPacketEntityUUID(entity.getEntityId(), entity.getUniqueID());
			GOTPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
		if (!entity.worldObj.isRemote && entity instanceof GOTEntityBanner) {
			((GOTEntityBanner) entity).sendBannerToPlayer(entityplayer, false, false);
		}
	}

	@SubscribeEvent
	public void onUseBonemeal(BonemealEvent event) {
		World world = event.world;
		Random rand = world.rand;
		int i = event.x;
		int j = event.y;
		int k = event.z;
		if (!world.isRemote) {
			BiomeGenBase biomegenbase;
			int meta;
			if (event.block instanceof BlockLog && ((meta = world.getBlockMetadata(i, j, k)) & 0xC) != 12) {
				world.setBlockMetadataWithNotify(i, j, k, meta |= 0xC, 3);
				event.setResult(Event.Result.ALLOW);
				return;
			}
			if (event.block instanceof GOTBlockSaplingBase) {
				GOTBlockSaplingBase sapling = (GOTBlockSaplingBase) event.block;
				world.getBlockMetadata(i, j, k);
				if (rand.nextFloat() < 0.45) {
					sapling.incrementGrowth(world, i, j, k, rand);
				}
				event.setResult(Event.Result.ALLOW);
				return;
			}
			if (event.block.canSustainPlant(world, i, j, k, ForgeDirection.UP, Blocks.tallgrass) && event.block instanceof IGrowable && (biomegenbase = world.getBiomeGenForCoords(i, k)) instanceof GOTBiome) {
				GOTBiome biome = (GOTBiome) biomegenbase;
				block0: for (int attempts = 0; attempts < 128; ++attempts) {
					int i1 = i;
					int j1 = j + 1;
					int k1 = k;
					for (int subAttempts = 0; subAttempts < attempts / 16; ++subAttempts) {
						Block below = world.getBlock(i1 += rand.nextInt(3) - 1, (j1 += (rand.nextInt(3) - 1) * rand.nextInt(3) / 2) - 1, k1 += rand.nextInt(3) - 1);
						if (!(below instanceof IGrowable) || !below.canSustainPlant(world, i1, j1 - 1, k1, ForgeDirection.UP, Blocks.tallgrass) || world.getBlock(i1, j1, k1).isNormalCube()) {
							continue block0;
						}
					}
					if (world.getBlock(i1, j1, k1).getMaterial() != Material.air) {
						continue;
					}
					if (rand.nextInt(8) > 0) {
						GOTBiome.GrassBlockAndMeta obj = biome.getRandomGrass(rand);
						Block block = obj.block;
						int meta3 = obj.meta;
						if (!block.canBlockStay(world, i1, j1, k1)) {
							continue;
						}
						world.setBlock(i1, j1, k1, block, meta3, 3);
						continue;
					}
					biome.plantFlower(world, rand, i1, j1, k1);
				}
				event.setResult(Event.Result.ALLOW);
			}
		}
	}

	@SubscribeEvent
	public void onUseHoe(UseHoeEvent event) {
		World world = event.world;
		int i = event.x;
		int j = event.y;
		int k = event.z;
		Block block = world.getBlock(i, j, k);
		GOTBlockGrapevine.hoeing = true;
		if (world.getBlock(i, j + 1, k).isAir(world, i, j + 1, k) && (block == GOTRegistry.mudGrass || block == GOTRegistry.mud)) {
			Block tilled = GOTRegistry.mudFarmland;
			world.playSoundEffect(i + 0.5f, j + 0.5f, k + 0.5f, tilled.stepSound.getStepResourcePath(), (tilled.stepSound.getVolume() + 1.0f) / 2.0f, tilled.stepSound.getPitch() * 0.8f);
			if (!world.isRemote) {
				world.setBlock(i, j, k, tilled);
			}
			event.setResult(Event.Result.ALLOW);
			return;
		}
		GOTBlockGrapevine.hoeing = true;
	}

	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event) {
		World world = event.world;
		if (!world.isRemote && world.provider.dimensionId == 0) {
			GOTTime.save();
		}
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event) {
		World world = event.world;
		if (world.provider instanceof GOTWorldProvider) {
			GOTBiomeVariantStorage.clearAllVariants(world);
		}
	}

	public static boolean dechant(ItemStack itemstack, EntityPlayer entityplayer) {
		if (!entityplayer.capabilities.isCreativeMode && itemstack != null && itemstack.isItemEnchanted() && !(itemstack.getItem() instanceof ItemFishingRod)) {
			itemstack.getTagCompound().removeTag("ench");
			return true;
		}
		return false;
	}

	public static String getUsernameWithoutWebservice(UUID player) {
		GameProfile profile = MinecraftServer.getServer().func_152358_ax().func_152652_a(player);
		if (profile != null && !StringUtils.isBlank(profile.getName())) {
			return profile.getName();
		}
		String cachedName = UsernameCache.getLastKnownUsername(player);
		if (cachedName != null && !StringUtils.isBlank(cachedName)) {
			return cachedName;
		}
		return player.toString();
	}

	public static boolean shouldApplyWinterOverlay(World world, BiomeGenBase biome, EntityLivingBase entity) {
		return biome instanceof GOTBiomeAlwaysWinter || GOTDate.AegonCalendar.getSeason() == GOTDate.Season.WINTER && (!(biome instanceof GOTBiome) || !((GOTBiome) biome).isNeverWinter) && world.isRaining() || entity.posY > 150.0;
	}
}