package got.common.event;

import codechicken.nei.NEIModContainer;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.api.ItemInfo;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.*;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import got.GOT;
import got.common.*;
import got.common.block.GOTVanillaSaplings;
import got.common.block.other.*;
import got.common.block.sapling.GOTBlockSaplingBase;
import got.common.block.table.GOTBlockCraftingTable;
import got.common.database.*;
import got.common.enchant.GOTEnchantment;
import got.common.enchant.GOTEnchantmentHelper;
import got.common.enchant.GOTEnchantmentWeaponSpecial;
import got.common.entity.animal.GOTEntityButterfly;
import got.common.entity.animal.GOTEntityJungleScorpion;
import got.common.entity.animal.GOTEntityZebra;
import got.common.entity.dragon.GOTDragonLifeStage;
import got.common.entity.dragon.GOTEntityDragon;
import got.common.entity.essos.GOTEntityStoneMan;
import got.common.entity.essos.asshai.GOTEntityAsshaiMan;
import got.common.entity.essos.ghiscar.GOTEntityGhiscarHarpy;
import got.common.entity.essos.mossovy.GOTEntityMarshWraith;
import got.common.entity.essos.yiti.GOTEntityYiTiBombardier;
import got.common.entity.other.*;
import got.common.entity.westeros.reach.GOTEntityReachSoldier;
import got.common.faction.GOTAlignmentValues;
import got.common.faction.GOTFaction;
import got.common.faction.GOTFactionBounties;
import got.common.faction.GOTFactionRelations;
import got.common.item.GOTPoisonedDrinks;
import got.common.item.GOTWeaponStats;
import got.common.item.other.*;
import got.common.item.weapon.GOTItemBow;
import got.common.item.weapon.GOTItemCrossbow;
import got.common.item.weapon.GOTItemLance;
import got.common.item.weapon.GOTItemSword;
import got.common.network.*;
import got.common.quest.GOTMiniQuest;
import got.common.tileentity.GOTTileEntityPlate;
import got.common.util.GOTEnumDyeColor;
import got.common.util.GOTModChecker;
import got.common.world.GOTTeleporter;
import got.common.world.GOTWorldProvider;
import got.common.world.GOTWorldType;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.essos.GOTBiomeGhiscarMeereen;
import got.common.world.biome.essos.GOTBiomeMossovyMarshes;
import got.common.world.biome.essos.GOTBiomeShadowLand;
import got.common.world.biome.essos.GOTBiomeValyria;
import got.common.world.biome.sothoryos.GOTBiomeSothoryosHell;
import got.common.world.biome.sothoryos.GOTBiomeYeen;
import got.common.world.biome.variant.GOTBiomeVariantStorage;
import integrator.NEIGOTIntegratorConfig;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import net.minecraftforge.event.world.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class GOTEventHandler implements IFuelHandler {
	public GOTItemBow proxyBowItemServer;
	public GOTItemBow proxyBowItemClient;

	public GOTEventHandler() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.TERRAIN_GEN_BUS.register(this);
		GameRegistry.registerFuelHandler(this);
	}

	public static void dechant(ItemStack itemstack, EntityPlayer entityplayer) {
		if (!entityplayer.capabilities.isCreativeMode && itemstack != null && itemstack.isItemEnchanted()) {
			Item item = itemstack.getItem();
			if (!(item instanceof ItemFishingRod)) {
				itemstack.getTagCompound().removeTag("ench");
			}
		}
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

	public void cancelAttackEvent(LivingAttackEvent event) {
		event.setCanceled(true);
		DamageSource source = event.source;
		if (source instanceof EntityDamageSourceIndirect) {
			source.getSourceOfDamage();
		}
	}

	@SubscribeEvent
	public void getBlockDrops(BlockEvent.HarvestDropsEvent event) {
		EntityPlayer entityplayer = event.harvester;
		Block block = event.block;
		if (entityplayer != null) {
			ItemStack itemstack = entityplayer.getCurrentEquippedItem();
			if (itemstack != null && block instanceof BlockWeb && itemstack.getItem() instanceof ItemShears) {
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
	}

	@Override
	public int getBurnTime(ItemStack itemstack) {
		Item item = itemstack.getItem();
		if (item instanceof ItemBlock && ((ItemBlock) item).field_150939_a instanceof GOTBlockSaplingBase) {
			return 100;
		}
		if (item == Items.reeds || item == Item.getItemFromBlock(GOTBlocks.reeds) || item == Item.getItemFromBlock(GOTBlocks.driedReeds) || item == Item.getItemFromBlock(GOTBlocks.cornStalk)) {
			return 100;
		}
		return 0;
	}

	@SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent event) {
		if (!GOTConfig.enchantingVanilla) {
			if (event.left != null && event.left.getItem() instanceof ItemEnchantedBook || event.right != null && event.right.getItem() instanceof ItemEnchantedBook) {
				event.setCanceled(true);
			}
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
				for (int j1 = 1; j1 <= 3; j1++) {
					int j2 = j + j1;
					Block above = world.getBlock(i, j2, k);
					int aboveMeta = world.getBlockMetadata(i, j2, k);
					if (GOTBlockGrapevine.isFullGrownGrapes(above, aboveMeta)) {
						grapesAbove = true;
					}
				}
				if (grapesAbove) {
					GOTEntityReachSoldier.defendGrapevines(entityplayer, world, i, j + 1, k);
				}
			}
		}
	}

	@SubscribeEvent
	public void onBlockInteract(PlayerInteractEvent event) {
		EntityPlayer entityplayer = event.entityPlayer;
		World world = entityplayer.worldObj;
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		int i = event.x;
		int j = event.y;
		int k = event.z;
		int side = event.face;
		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			Block block = world.getBlock(i, j, k);
			int meta = world.getBlockMetadata(i, j, k);
			GOTBannerProtection.Permission perm = GOTBannerProtection.Permission.FULL;
			boolean mightBeAbleToAlterWorld = entityplayer.isSneaking() && itemstack != null;
			if (!mightBeAbleToAlterWorld) {
				if (block instanceof BlockDoor || block instanceof BlockTrapDoor || block instanceof BlockFenceGate || block instanceof GOTBlockGate) {
					perm = GOTBannerProtection.Permission.DOORS;
				} else if (block instanceof BlockWorkbench || block instanceof GOTBlockCraftingTable || block instanceof BlockAnvil || block instanceof GOTBlockCommandTable) {
					perm = GOTBannerProtection.Permission.TABLES;
				} else if (world.getTileEntity(i, j, k) instanceof IInventory) {
					if (block instanceof GOTBlockBarrel || block instanceof GOTBlockKebabStand) {
						perm = GOTBannerProtection.Permission.FOOD;
					} else {
						perm = GOTBannerProtection.Permission.CONTAINERS;
					}
				} else if (block instanceof GOTBlockArmorStand || block instanceof GOTBlockWeaponRack || block == Blocks.bookshelf || block instanceof BlockJukebox) {
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
				} else if (block instanceof GOTBlockPlate && GOTBlockPlate.getFoodItem(world, i, j, k) != null) {
					world.markBlockForUpdate(i, j, k);
				}
				return;
			}
			if (block == Blocks.flower_pot && meta == 0 && itemstack != null && GOTBlockFlowerPot.canAcceptPlant(itemstack)) {
				GOT.proxy.placeFlowerInPot(world, i, j, k, side, itemstack);
				if (!entityplayer.capabilities.isCreativeMode) {
					itemstack.stackSize--;
				}
				event.setCanceled(true);
				return;
			}
			if (itemstack != null && block == Blocks.cauldron && meta > 0) {
				GOTItemMug.Vessel drinkVessel = null;
				for (GOTItemMug.Vessel v : GOTItemMug.Vessel.values()) {
					if (v.getEmptyVesselItem() == itemstack.getItem()) {
						drinkVessel = v;
						break;
					}
				}
				if (drinkVessel != null) {
					GOT.proxy.fillMugFromCauldron(world, i, j, k, side, itemstack);
					itemstack.stackSize--;
					ItemStack mugFill = new ItemStack(GOTItems.mugWater);
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
			if (!world.isRemote && block instanceof GOTBlockPlate && entityplayer.isSneaking()) {
				TileEntity tileentity = world.getTileEntity(i, j, k);
				if (tileentity instanceof GOTTileEntityPlate) {
					GOTTileEntityPlate plate = (GOTTileEntityPlate) tileentity;
					ItemStack plateItem = plate.getFoodItem();
					if (plateItem != null) {
						((GOTBlockPlate) block).dropOnePlateItem(plate);
						plateItem.stackSize--;
						plate.setFoodItem(plateItem);
						event.setCanceled(true);
						return;
					}
				}
			}
			if (!world.isRemote && block instanceof BlockCauldron && itemstack != null) {
				int cauldronMeta = BlockCauldron.func_150027_b(meta);
				if (cauldronMeta > 0) {
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
			}
			if (!world.isRemote && itemstack != null && itemstack.getItem() == Items.dye && itemstack.getItemDamage() == 15) {
				if (block instanceof BlockLog) {
					int logFacing = meta & 0xC;
					if (logFacing != 12) {
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
							meta |= 0xC;
							world.setBlockMetadataWithNotify(i, j, k, meta, 3);
							world.playAuxSFX(2005, i, j, k, 0);
							if (!entityplayer.capabilities.isCreativeMode) {
								itemstack.stackSize--;
							}
							event.setCanceled(true);
							return;
						}
					}
				}
			}
			if (!world.isRemote && GOTBlockGrapevine.isFullGrownGrapes(block, meta)) {
				GOTEntityReachSoldier.defendGrapevines(entityplayer, world, i, j, k);
			}
			if (block == Blocks.bookshelf && !entityplayer.isSneaking() && GOTBlockBookshelfStorage.canOpenBookshelf(world, i, j, k, entityplayer) && !world.isRemote) {
				world.setBlock(i, j, k, GOTBlocks.bookshelfStorage, 0, 3);
				boolean flag = GOTBlocks.bookshelfStorage.onBlockActivated(world, i, j, k, entityplayer, side, 0.5F, 0.5F, 0.5F);
				if (!flag) {
					world.setBlock(i, j, k, Blocks.bookshelf, 0, 3);
				}
				event.setCanceled(true);
				return;
			}
			if (block == Blocks.enchanting_table && !GOTConfig.isEnchantingEnabled(world) && !world.isRemote) {
				GOTLevelData.getData(entityplayer).sendMessageIfNotReceived(GOTGuiMessageTypes.ENCHANTING);
				event.setCanceled(true);
				return;
			}
			if (block == Blocks.anvil && (GOTConfig.isGOTEnchantingEnabled(world) || !GOTConfig.isEnchantingEnabled(world)) && !world.isRemote) {
				entityplayer.openGui(GOT.instance, GOTGuiID.ANVIL.ordinal(), world, i, j, k);
				event.setCanceled(true);
				return;
			}
		}
		if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
			world.getBlock(i, j, k);
			world.getBlockMetadata(i, j, k);
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
		if (itemstack != null) {
			float baseDigSpeed = itemstack.getItem().getDigSpeed(itemstack, block, meta);
			if (baseDigSpeed > 1.0F) {
				speed *= GOTEnchantmentHelper.calcToolEfficiency(itemstack);
			}
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
		if (GOTModChecker.hasNEI() && GOTModChecker.hasGuiContainer()) {
			for (IConfigureNEI element : NEIModContainer.plugins) {
				if (element.getClass() == NEIGOTIntegratorConfig.class) {
					NEIGOTIntegratorConfig configNEI = (NEIGOTIntegratorConfig) element;
					for (ItemStack element2 : configNEI.hiddenItems) {
						if (ItemInfo.hiddenItems.contains(element2)) {
							ItemInfo.hiddenItems.remove(element2);
						}
					}
					configNEI.loadConfig();
				}
			}
		}
	}

	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent event) {
		EntityPlayer entityplayer = event.player;
		ItemStack itemstack = event.crafting;
		if (!entityplayer.worldObj.isRemote) {
			if (itemstack.getItem() == Items.saddle) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.craftSaddle);
			}
			if (itemstack.getItem() == GOTItems.bronzeIngot) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.craftBronze);
			}
			if (itemstack.getItem() == Item.getItemFromBlock(GOTBlocks.bomb)) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.craftBomb);
			}
			if (itemstack.getItem() == Item.getItemFromBlock(GOTBlocks.wildFireJar)) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.craftWildFire);
			}
			for (GOTEnumDyeColor color : GOTEnumDyeColor.values()) {
				if (itemstack.getItem() == Item.getItemFromBlock(GOTBlocks.concretePowder.get(color))) {
					GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.getConcrete);
				}
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
	public void onEntityInteract(EntityInteractEvent event) {
		EntityPlayer entityplayer = event.entityPlayer;
		World world = entityplayer.worldObj;
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		Entity entity = event.target;
		if (!world.isRemote && (entity instanceof EntityHanging || entity instanceof GOTBannerProtectable) && GOTBannerProtection.isProtected(world, entity, GOTBannerProtection.forPlayer(entityplayer, GOTBannerProtection.Permission.FULL), true)) {
			event.setCanceled(true);
			return;
		}
		if ((entity instanceof EntityCow || entity instanceof GOTEntityZebra) && GOTItemMug.isItemEmptyDrink(itemstack)) {
			GOTItemMug.Vessel vessel = GOTItemMug.getVessel(itemstack);
			ItemStack milkItem = new ItemStack(GOTItems.mugMilk);
			GOTItemMug.setVessel(milkItem, vessel, true);
			if (!entityplayer.capabilities.isCreativeMode) {
				itemstack.stackSize--;
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
			if (itemstack != null) {
				int dyeType = GOTItemDye.isItemDye(itemstack);
				if (dyeType >= 0 && itemstack.getItem() != Items.dye) {
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
		}
		if (entity instanceof GOTTradeable && ((GOTTradeable) entity).canTradeWith(entityplayer)) {
			if (entity instanceof GOTUnitTradeable) {
				entityplayer.openGui(GOT.instance, GOTGuiID.TRADE_UNIT_TRADE_INTERACT.ordinal(), world, entity.getEntityId(), 0, 0);
			} else {
				entityplayer.openGui(GOT.instance, GOTGuiID.TRADE_INTERACT.ordinal(), world, entity.getEntityId(), 0, 0);
			}
			event.setCanceled(true);
			return;
		}
		if (entity instanceof GOTUnitTradeable && ((GOTHireableBase) entity).canTradeWith(entityplayer)) {
			entityplayer.openGui(GOT.instance, GOTGuiID.UNIT_TRADE_INTERACT.ordinal(), world, entity.getEntityId(), 0, 0);
			event.setCanceled(true);
			return;
		}
		if (entity instanceof GOTMercenary && ((GOTHireableBase) entity).canTradeWith(entityplayer)) {
			if (((GOTEntityNPC) entity).hiredNPCInfo.getHiringPlayerUUID() == null) {
				entityplayer.openGui(GOT.instance, GOTGuiID.MERCENARY_INTERACT.ordinal(), world, entity.getEntityId(), 0, 0);
				event.setCanceled(true);
				return;
			}
		}
		if (entity instanceof GOTEntityNPC) {
			GOTEntityNPC npc = (GOTEntityNPC) entity;
			if (npc.hiredNPCInfo.getHiringPlayer() == entityplayer) {
				if (entity instanceof GOTEntityProstitute) {
					entityplayer.openGui(GOT.instance, GOTGuiID.HIRED_INTERACT_NO_FUNC.ordinal(), world, entity.getEntityId(), 0, 0);
				} else {
					entityplayer.openGui(GOT.instance, GOTGuiID.HIRED_INTERACT.ordinal(), world, entity.getEntityId(), 0, 0);
				}
				event.setCanceled(true);
				return;
			}
			if (npc.hiredNPCInfo.isActive && entityplayer.capabilities.isCreativeMode && itemstack != null && itemstack.getItem() == Items.clock) {
				if (!world.isRemote && MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile())) {
					UUID hiringUUID = npc.hiredNPCInfo.getHiringPlayerUUID();
					if (hiringUUID != null) {
						String playerName = getUsernameWithoutWebservice(hiringUUID);
						if (playerName != null) {
							IChatComponent chatComponentText = new ChatComponentText("Hired unit belongs to " + playerName);
							chatComponentText.getChatStyle().setColor(EnumChatFormatting.GREEN);
							entityplayer.addChatMessage(chatComponentText);
						}
					}
				}
				event.setCanceled(true);
				return;
			}
		}
		if (!world.isRemote && entityplayer.capabilities.isCreativeMode && MinecraftServer.getServer().getConfigurationManager().func_152596_g(entityplayer.getGameProfile())) {
			if (itemstack != null && itemstack.getItem() == Items.clock && entity instanceof EntityLiving) {
				UUID brandingPlayer = GOTItemBrandingIron.getBrandingPlayer(entity);
				if (brandingPlayer != null) {
					String playerName = getUsernameWithoutWebservice(brandingPlayer);
					if (playerName != null) {
						IChatComponent chatComponentText = new ChatComponentText("Entity was branded by " + playerName);
						chatComponentText.getChatStyle().setColor(EnumChatFormatting.GREEN);
						entityplayer.addChatMessage(chatComponentText);
						event.setCanceled(true);
						return;
					}
				}
			}
		}
		if (entity instanceof EntityVillager && !GOTConfig.enableVillagerTrading) {
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
				Collection<ChunkPosition> removes = new ArrayList<>();
				for (ChunkPosition blockPos : blockList) {
					int i = blockPos.chunkPosX;
					int j = blockPos.chunkPosY;
					int k = blockPos.chunkPosZ;
					if (GOTBannerProtection.isProtected(world, i, j, k, protectFilter, false)) {
						removes.add(blockPos);
					}
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
				for (int i = 0; i < entityplayer.inventory.getSizeInventory(); i++) {
					ItemStack itemInSlot = entityplayer.inventory.getStackInSlot(i);
					if (itemInSlot != null && itemInSlot.getItem() == GOTItems.pouch) {
						GOTItemPouch.tryAddItemToPouch(itemInSlot, itemstack, true);
						if (itemstack.stackSize <= 0) {
							break;
						}
					}
				}
				if (itemstack.stackSize <= 0) {
					event.setResult(Event.Result.ALLOW);
				}
			}
			if (itemstack.getItem() == Item.getItemFromBlock(GOTBlocks.plantain)) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.findPlantain);
			}
			if (itemstack.getItem() == Item.getItemFromBlock(GOTBlocks.clover) && itemstack.getItemDamage() == 1) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.findFourLeafClover);
			}
			if (GOTConfig.enchantingAutoRemoveVanilla) {
				dechant(itemstack, entityplayer);
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
		EntityLivingBase attacker;
		if (event.source.getEntity() instanceof EntityLivingBase) {
			attacker = (EntityLivingBase) event.source.getEntity();
		} else {
			attacker = null;
		}
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
		if (event.source instanceof net.minecraft.util.EntityDamageSourceIndirect) {
			Entity projectile = event.source.getSourceOfDamage();
			if (projectile instanceof EntityArrow || projectile instanceof GOTEntityCrossbowBolt || projectile instanceof GOTEntityDart) {
				boolean wearingAllRoyce = true;
				for (int i = 0; i < 4; i++) {
					ItemStack armour = entity.getEquipmentInSlot(i + 1);
					if (armour == null || !(armour.getItem() instanceof ItemArmor) || ((ItemArmor) armour.getItem()).getArmorMaterial() != GOTMaterial.ROYCE) {
						wearingAllRoyce = false;
						break;
					}
				}
				if (wearingAllRoyce) {
					if (!world.isRemote && entity instanceof EntityPlayer) {
						((EntityPlayer) entity).inventory.damageArmor(event.ammount);
					}
					cancelAttackEvent(event);
				}
			}
		}
	}

	@SubscribeEvent
	@SuppressWarnings({"Convert2Lambda", "AnonymousInnerClassMayBeStatic"})
	public void onLivingDeath(LivingDeathEvent event) {
		EntityLivingBase entity = event.entityLiving;
		World world = entity.worldObj;
		DamageSource source = event.source;
		if (!world.isRemote && entity instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entity;
			int i = MathHelper.floor_double(entityplayer.posX);
			int j = MathHelper.floor_double(entityplayer.posY);
			int k = MathHelper.floor_double(entityplayer.posZ);
			GOTLevelData.getData(entityplayer).setDeathPoint(i, j, k);
			GOTLevelData.getData(entityplayer).setDeathDimension(entityplayer.dimension);
		}
		if (!world.isRemote) {
			EntityPlayer entityplayer = null;
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
					double nearbyDist = 64.0D;
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
				if (!creditHiredUnit && prevAlignment > 0.0F && entity instanceof GOTEntityNPC) {
					GOTEntityNPC npc = (GOTEntityNPC) entity;
					if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.wasAttackCommanded) {
						wasSelfDefenceAgainstAlliedUnit = true;
					}
				}
				GOTAlignmentValues.AlignmentBonus alignmentBonus = null;
				if (!wasSelfDefenceAgainstAlliedUnit && entity instanceof GOTEntityNPC) {
					GOTEntityNPC npc = (GOTEntityNPC) entity;
					alignmentBonus = new GOTAlignmentValues.AlignmentBonus(npc.getAlignmentBonus(), npc.getEntityClassName());
					alignmentBonus.needsTranslation = true;
					alignmentBonus.isCivilianKill = npc.isCivilianNPC();
				}
				if (alignmentBonus != null && alignmentBonus.bonus != 0.0F && (!creditHiredUnit || byNearbyUnit)) {
					alignmentBonus.isKill = true;
					if (creditHiredUnit) {
						alignmentBonus.killByHiredUnit = true;
					}
					playerData.addAlignment(entityplayer, alignmentBonus, entityFaction, forcedBonusFactions, entity);
				}
				if (!creditHiredUnit) {
					if (entityFaction.allowPlayer) {
						playerData.getFactionData(entityFaction).addNPCKill();
						List<GOTFaction> killBonuses = entityFaction.getBonusesForKilling();
						for (GOTFaction enemy : killBonuses) {
							playerData.getFactionData(enemy).addEnemyKill();
						}
						if (!entityplayer.capabilities.isCreativeMode) {
							boolean recordBountyKill = entityFaction.inDefinedControlZone(entityplayer, Math.max(entityFaction.getControlZoneReducedRange(), 50));
							if (recordBountyKill) {
								GOTFactionBounties.forFaction(entityFaction).forPlayer(entityplayer).recordNewKill();
							}
						}
						GOTFaction pledgeFac = playerData.getPledgeFaction();
						if (pledgeFac != null && (pledgeFac == entityFaction || pledgeFac.isAlly(entityFaction))) {
							playerData.onPledgeKill(entityplayer);
						}
					}
					float newAlignment = playerData.getAlignment(entityFaction);
					if (!wasSelfDefenceAgainstAlliedUnit && !entityplayer.capabilities.isCreativeMode && entityFaction != GOTFaction.UNALIGNED) {
						int sentSpeeches = 0;
						int maxSpeeches = 5;
						double range = 8.0D;
						List<EntityLiving> nearbyAlliedNPCs = world.selectEntitiesWithinAABB(EntityLiving.class, entity.boundingBox.expand(range, range, range), new IEntitySelector() {
							@Override
							public boolean isEntityApplicable(Entity entitySelect) {
								if (entitySelect.isEntityAlive()) {
									GOTFaction fac = GOT.getNPCFaction(entitySelect);
									return fac.isGoodRelation(entityFaction);
								}
								return false;
							}
						});
						for (EntityLiving npc : nearbyAlliedNPCs) {
							if (npc instanceof GOTEntityNPC) {
								GOTEntityNPC gotNPC = (GOTEntityNPC) npc;
								if (gotNPC.hiredNPCInfo.isActive && newAlignment > 0.0F || gotNPC.hiredNPCInfo.isActive && gotNPC.hiredNPCInfo.getHiringPlayer() == entityplayer) {
									continue;
								}
							}
							if (npc.getAttackTarget() == null) {
								npc.setAttackTarget(entityplayer);
								if (npc instanceof GOTEntityNPC && sentSpeeches < maxSpeeches) {
									GOTEntityNPC gotnpc = (GOTEntityNPC) npc;
									String speech = gotnpc.getSpeechBank(entityplayer);
									if (speech != null && gotnpc.getDistanceSqToEntity(entityplayer) < range) {
										gotnpc.sendSpeechBank(entityplayer, speech);
										sentSpeeches++;
									}
								}
							}
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
		if (!world.isRemote && source.getEntity() instanceof EntityPlayer && source.getSourceOfDamage() != null && source.getSourceOfDamage().getClass() == GOTEntitySpear.class) {
			EntityPlayer entityplayer = (EntityPlayer) source.getEntity();
			if (entity != entityplayer && entityplayer.getDistanceSqToEntity(entity) >= 2500.0D) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.useSpearFromFar);
			}
		}
		if (!world.isRemote && entity instanceof GOTEntityButterfly && source.getEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) source.getEntity();
			GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.killButterfly);
		}
		if (!world.isRemote) {
			EntityPlayer attackingPlayer = null;
			GOTEntityNPC attackingHiredUnit = null;
			if (source.getEntity() instanceof EntityPlayer) {
				attackingPlayer = (EntityPlayer) source.getEntity();
			} else if (source.getEntity() instanceof GOTEntityNPC) {
				GOTEntityNPC npc = (GOTEntityNPC) source.getEntity();
				if (npc.hiredNPCInfo.isActive && npc.hiredNPCInfo.getHiringPlayer() != null) {
					attackingPlayer = npc.hiredNPCInfo.getHiringPlayer();
					attackingHiredUnit = npc;
				}
			}
			if (attackingPlayer != null) {
				boolean isFoe = GOTLevelData.getData(attackingPlayer).getAlignment(GOT.getNPCFaction(entity)) < 0.0F;
				if (isFoe && attackingHiredUnit == null) {
					if (attackingPlayer.isPotionActive(Potion.confusion.id)) {
						GOTLevelData.getData(attackingPlayer).addAchievement(GOTAchievement.killWhileDrunk);
					}
					if (entity instanceof GOTEntityYiTiBombardier && ((GOTEntityNPC) entity).npcItemsInv.getBomb() != null) {
						GOTLevelData.getData(attackingPlayer).addAchievement(GOTAchievement.killBombardier);
					}
					if (source.getSourceOfDamage() instanceof GOTEntityCrossbowBolt) {
						GOTLevelData.getData(attackingPlayer).addAchievement(GOTAchievement.useCrossbow);
					}
					if (source.getSourceOfDamage() instanceof GOTEntityThrowingAxe) {
						GOTLevelData.getData(attackingPlayer).addAchievement(GOTAchievement.useThrowingAxe);
					}
				}
			}
		}
		if (!world.isRemote && entity instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entity;
			if (GOTEnchantmentHelper.hasMeleeOrRangedEnchant(source, GOTEnchantment.headhunting)) {
				ItemStack playerHead = new ItemStack(Items.skull, 1, 3);
				GameProfile profile = entityplayer.getGameProfile();
				NBTTagCompound profileData = new NBTTagCompound();
				NBTUtil.func_152460_a(profileData, profile);
				playerHead.setTagInfo("SkullOwner", profileData);
				entityplayer.entityDropItem(playerHead, 0.0F);
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
			for (int l = 0; l < meat; l++) {
				if (entity.isBurning()) {
					entity.dropItem(GOTItems.muttonCooked, 1);
				} else {
					entity.dropItem(GOTItems.muttonRaw, 1);
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		EntityLivingBase entity = event.entityLiving;
		EntityLivingBase attacker;
		if (event.source.getEntity() instanceof EntityLivingBase) {
			attacker = (EntityLivingBase) event.source.getEntity();
		} else {
			attacker = null;
		}
		World world = entity.worldObj;
		if (entity instanceof EntityPlayerMP && event.source == GOTDamage.frost) {
			GOTDamage.doFrostDamage((EntityPlayerMP) entity);
		}
		if (!world.isRemote) {
			int preMaxHurtResTime = entity.maxHurtResistantTime;
			int maxHurtResTime = 20;
			if (attacker != null) {
				ItemStack weapon = attacker.getHeldItem();
				if (GOTWeaponStats.isMeleeWeapon(weapon)) {
					maxHurtResTime = GOTWeaponStats.getAttackTimeWithBase(weapon, 20);
				}
			}
			maxHurtResTime = Math.min(maxHurtResTime, 20);
			entity.maxHurtResistantTime = maxHurtResTime;
			if (entity.hurtResistantTime == preMaxHurtResTime) {
				entity.hurtResistantTime = maxHurtResTime;
			}
		}
		if (attacker != null && event.source.getSourceOfDamage() == attacker) {
			ItemStack weapon = attacker.getHeldItem();
			if (!world.isRemote && entity instanceof EntityPlayerMP) {
				EntityPlayerMP entityplayer = (EntityPlayerMP) entity;
				if (entityplayer.isUsingItem()) {
					ItemStack usingItem = entityplayer.getHeldItem();
					if (GOTWeaponStats.isRangedWeapon(usingItem)) {
						entityplayer.clearItemInUse();
						IMessage packet = new GOTPacketStopItemUse();
						GOTPacketHandler.networkWrapper.sendTo(packet, entityplayer);
					}
				}
			}
			boolean wearingAllAsshai = true;
			for (int i = 0; i < 4; i++) {
				ItemStack armour = entity.getEquipmentInSlot(i + 1);
				if (armour == null || !(armour.getItem() instanceof ItemArmor) || ((ItemArmor) armour.getItem()).getArmorMaterial() != GOTMaterial.ASSHAI) {
					wearingAllAsshai = false;
					break;
				}
			}
			if (wearingAllAsshai && !world.isRemote && weapon != null && weapon.isItemStackDamageable()) {
				int damage = weapon.getItemDamage();
				int maxDamage = weapon.getMaxDamage();
				float durability = 1.0F - (float) damage / maxDamage;
				durability *= 0.9F;
				int newDamage = Math.round((1.0F - durability) * maxDamage);
				newDamage = Math.min(newDamage, maxDamage);
				weapon.damageItem(newDamage - damage, attacker);
			}
			if (weapon != null) {
				Item.ToolMaterial material = null;
				if (weapon.getItem() instanceof ItemTool) {
					material = ((ItemTool) weapon.getItem()).func_150913_i();
				} else if (weapon.getItem() instanceof ItemSword) {
					material = GOTMaterial.getToolMaterialByName(((ItemSword) weapon.getItem()).getToolMaterialName());
				}
				if (material != null && material == GOTMaterial.ASSHAI_TOOL && !world.isRemote) {
					entity.addPotionEffect(new PotionEffect(Potion.wither.id, 160));
				}
			}
		}
		if (event.source.getSourceOfDamage() instanceof GOTEntityArrowPoisoned && !world.isRemote) {
			GOTItemSword.applyStandardPoison(entity);
		}
		if (event.source.getSourceOfDamage() instanceof GOTEntityArrowFire && !world.isRemote) {
			GOTItemSword.applyStandardFire(entity);
		}
		if (!world.isRemote) {
			if (GOTEnchantmentHelper.hasMeleeOrRangedEnchant(event.source, GOTEnchantment.fire)) {
				IMessage packet = new GOTPacketWeaponFX(GOTPacketWeaponFX.Type.INFERNAL, entity);
				GOTPacketHandler.networkWrapper.sendToAllAround(packet, GOTPacketHandler.nearEntity(entity, 64.0D));
			}
			if (GOTEnchantmentHelper.hasMeleeOrRangedEnchant(event.source, GOTEnchantment.chill)) {
				GOTEnchantmentWeaponSpecial.doChillAttack(entity);
			}
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;
		World world = entity.worldObj;
		if (!world.isRemote) {
			GOTEnchantmentHelper.onEntityUpdate(entity);
		}
		if (GOTConfig.enchantingAutoRemoveVanilla && !world.isRemote && entity instanceof EntityPlayer && entity.ticksExisted % 60 == 0) {
			EntityPlayer entityplayer = (EntityPlayer) entity;
			for (int l = 0; l < entityplayer.inventory.getSizeInventory(); l++) {
				ItemStack itemstack = entityplayer.inventory.getStackInSlot(l);
				if (itemstack != null) {
					dechant(itemstack, entityplayer);
				}
			}
		}
		boolean inWater = entity.isInWater();
		if (!world.isRemote && GOT.canSpawnMobs(world) && entity.isEntityAlive() && inWater && entity.ridingEntity == null) {
			boolean flag = !(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isCreativeMode;
			if (entity instanceof EntityWaterMob || entity instanceof GOTEntityMarshWraith) {
				flag = false;
			}
			if (flag) {
				int i = MathHelper.floor_double(entity.posX);
				int k = MathHelper.floor_double(entity.posZ);
				int j = world.getTopSolidOrLiquidBlock(i, k);
				while (world.getBlock(i, j + 1, k).getMaterial().isLiquid() || world.getBlock(i, j + 1, k).getMaterial().isSolid()) {
					j++;
				}
				if (j - entity.boundingBox.minY < 2.0D && world.getBlock(i, j, k).getMaterial() == Material.water && world.getBiomeGenForCoords(i, k) instanceof GOTBiomeMossovyMarshes) {
					List<GOTEntityMarshWraith> nearbyWraiths = world.getEntitiesWithinAABB(GOTEntityMarshWraith.class, entity.boundingBox.expand(15.0D, 15.0D, 15.0D));
					boolean anyNearbyWraiths = false;
					for (GOTEntityMarshWraith wraith : nearbyWraiths) {
						if (wraith.getAttackTarget() == entity && wraith.getDeathFadeTime() == 0) {
							anyNearbyWraiths = true;
							break;
						}
					}
					if (!anyNearbyWraiths) {
						GOTEntityMarshWraith wraith = new GOTEntityMarshWraith(world);
						int i1 = i + MathHelper.getRandomIntegerInRange(world.rand, -3, 3);
						int k1 = k + MathHelper.getRandomIntegerInRange(world.rand, -3, 3);
						int j1 = world.getTopSolidOrLiquidBlock(i1, k1);
						wraith.setLocationAndAngles(i1 + 0.5D, j1, k1 + 0.5D, world.rand.nextFloat() * 360.0F, 0.0F);
						if (wraith.getDistanceSqToEntity(entity) <= 144.0D) {
							world.spawnEntityInWorld(wraith);
							wraith.setAttackTarget(entity);
							wraith.attackTargetUUID = entity.getUniqueID();
							world.playSoundAtEntity(wraith, "got:wraith.spawn", 1.0F, 0.7F + world.rand.nextFloat() * 0.6F);
						}
					}
				}
			}
		}
		if (!world.isRemote && GOT.canSpawnMobs(world) && entity.isEntityAlive() && world.isDaytime()) {
			float f = 0.0F;
			int bounders = 0;
			if (GOTFaction.GHISCAR.isBadRelation(GOT.getNPCFaction(entity))) {
				float health = entity.getMaxHealth() + entity.getTotalArmorValue();
				f = health * 2.5F;
				int i = (int) (health / 15.0F);
				bounders = 2 + world.rand.nextInt(i + 1);
			} else if (entity instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) entity;
				float alignment = GOTLevelData.getData(entityplayer).getAlignment(GOTFaction.GHISCAR);
				if (!entityplayer.capabilities.isCreativeMode && alignment < 0.0F) {
					f = -alignment;
					int i = (int) (f / 50.0F);
					bounders = 2 + world.rand.nextInt(i + 1);
				}
			}
			if (f > 0.0F) {
				f = Math.min(f, 2000.0F);
				int chance = (int) (2000000.0F / f);
				bounders = Math.min(bounders, 5);
				int i = MathHelper.floor_double(entity.posX);
				int k = MathHelper.floor_double(entity.posZ);
				world.getTopSolidOrLiquidBlock(i, k);
				if (world.rand.nextInt(chance) == 0 && world.getBiomeGenForCoords(i, k) instanceof GOTBiomeGhiscarMeereen) {
					List<GOTEntityGhiscarHarpy> nearbyHarpies = world.getEntitiesWithinAABB(GOTEntityGhiscarHarpy.class, entity.boundingBox.expand(12.0D, 6.0D, 12.0D));
					if (nearbyHarpies.isEmpty()) {
						boolean sentMessage = false;
						boolean playedHorn = false;
						for (int l = 0; l < bounders; l++) {
							GOTEntityGhiscarHarpy bounder = new GOTEntityGhiscarHarpy(world);
							for (int l1 = 0; l1 < 32; l1++) {
								int i1 = i - world.rand.nextInt(12) + world.rand.nextInt(12);
								int k1 = k - world.rand.nextInt(12) + world.rand.nextInt(12);
								int j1 = world.getTopSolidOrLiquidBlock(i1, k1);
								if (world.getBlock(i1, j1 - 1, k1).isSideSolid(world, i1, j1 - 1, k1, ForgeDirection.UP) && !world.getBlock(i1, j1, k1).isNormalCube() && !world.getBlock(i1, j1 + 1, k1).isNormalCube()) {
									bounder.setLocationAndAngles(i1 + 0.5D, j1, k1 + 0.5D, 0.0F, 0.0F);
									if (bounder.getCanSpawnHere() && entity.getDistanceToEntity(bounder) > 6.0D) {
										bounder.onSpawnWithEgg(null);
										world.spawnEntityInWorld(bounder);
										bounder.setAttackTarget(entity);
										if (!sentMessage && entity instanceof EntityPlayer) {
											EntityPlayer entityplayer = (EntityPlayer) entity;
											bounder.sendSpeechBank(entityplayer, bounder.getSpeechBank(entityplayer));
											sentMessage = true;
										}
										if (!playedHorn) {
											world.playSoundAtEntity(bounder, "got:item.horn", 2.0F, 2.0F);
											playedHorn = true;
										}
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		if (!world.isRemote && entity.isEntityAlive() && inWater && entity.ridingEntity == null && entity.ticksExisted % 10 == 0) {
			boolean flag = entity instanceof EntityPlayer || entity instanceof GOTEntityHumanBase;
			if (entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode) {
				flag = false;
			}
			if (entity instanceof GOTEntityAsshaiMan || entity instanceof GOTEntityJungleScorpion || entity instanceof GOTEntityStoneMan) {
				flag = false;
			}
			if (flag) {
				int i = MathHelper.floor_double(entity.posX);
				int k = MathHelper.floor_double(entity.posZ);
				BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
				if (biome instanceof GOTBiomeShadowLand || biome instanceof GOTBiomeYeen) {
					entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 600, 1));
					entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 600));
				} else if (biome instanceof GOTBiomeValyria) {
					entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 600, 1));
					entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 600));
					entity.addPotionEffect(new PotionEffect(Potion.wither.id, 600));
				}
			}
		}
		if (!world.isRemote && entity.isEntityAlive() && entity.ticksExisted % 10 == 0) {
			boolean flag = entity instanceof EntityPlayer || entity instanceof GOTEntityHumanBase;
			if (entity instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) entity;
				if (entityplayer.capabilities.isCreativeMode) {
					flag = false;
				} else {
					float alignment = GOTLevelData.getData(entityplayer).getAlignment(GOTFaction.SOTHORYOS);
					if (alignment > 50.0F) {
						flag = false;
					}
				}
			}
			if (GOT.getNPCFaction(entity).isGoodRelation(GOTFaction.SOTHORYOS)) {
				flag = false;
			}
			if (flag) {
				int i = MathHelper.floor_double(entity.posX);
				int k = MathHelper.floor_double(entity.posZ);
				BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
				if (biome instanceof GOTBiomeSothoryosHell || biome instanceof GOTBiomeYeen) {
					entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 600, 1));
					entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 600, 1));
					entity.addPotionEffect(new PotionEffect(Potion.weakness.id, 600));
					entity.addPotionEffect(new PotionEffect(Potion.poison.id, 100));
				}
			}
		}
		if (!world.isRemote && entity.isEntityAlive()) {
			ItemStack weapon = entity.getHeldItem();
			boolean lanceOnFoot = false;
			if (weapon != null && weapon.getItem() instanceof GOTItemLance && entity.ridingEntity == null) {
				lanceOnFoot = !(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isCreativeMode;
			}
			IAttributeInstance speedAttribute = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			if (speedAttribute.getModifier(GOTItemLance.lanceSpeedBoost_id) != null) {
				speedAttribute.removeModifier(GOTItemLance.lanceSpeedBoost);
			}
			if (lanceOnFoot) {
				speedAttribute.applyModifier(GOTItemLance.lanceSpeedBoost);
			}
		}
		if (!world.isRemote && entity.isEntityAlive() && entity.ticksExisted % 20 == 0) {
			boolean flag = (!(entity instanceof GOTEntityNPC) || !((GOTEntityNPC) entity).isImmuneToFrost) && !(entity instanceof GOTBiome.ImmuneToFrost);
			if (entity instanceof EntityPlayer) {
				flag = !((EntityPlayer) entity).capabilities.isCreativeMode;
			}
			if (flag) {
				int i = MathHelper.floor_double(entity.posX);
				int j = MathHelper.floor_double(entity.boundingBox.minY);
				int k = MathHelper.floor_double(entity.posZ);
				BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
				boolean standardColdBiome = biome instanceof GOTBiome && biome.temperature == 0.0f;
				boolean altitudeColdBiome = biome instanceof GOTBiome && ((GOTBiome) biome).getClimateType() != null && ((GOTBiome) biome).getClimateType().isAltitudeZone() && k >= 140;
				boolean isOpenAir = world.canBlockSeeTheSky(i, j, k);
				boolean noLightSource = world.getSavedLightValue(EnumSkyBlock.Block, i, j, k) < 10;
				if ((standardColdBiome || altitudeColdBiome) && (isOpenAir || inWater) && noLightSource) {
					int frostChance = 20;
					int frostProtection = 0;
					for (int l = 0; l < 4; l++) {
						ItemStack armor = entity.getEquipmentInSlot(l + 1);
						if (armor != null && armor.getItem() instanceof ItemArmor) {
							ItemArmor.ArmorMaterial material = ((ItemArmor) armor.getItem()).getArmorMaterial();
							if (material == GOTMaterial.FUR || material == GOTMaterial.GIFT) {
								frostProtection += 80;
							}
						}
					}
					frostChance += frostProtection;
					if (world.isRaining()) {
						frostChance /= 5;
					}
					if (inWater) {
						frostChance /= 10;
					}
					frostChance = Math.max(frostChance, 1);
					if (world.rand.nextInt(frostChance) == 0) {
						entity.attackEntityFrom(GOTDamage.frost, 1.0f);
					}
				}
			}
		}
		if (!world.isRemote && entity.isEntityAlive() && entity.ticksExisted % 20 == 0) {
			boolean flag = !(entity instanceof GOTBiome.ImmuneToHeat) && (!(entity instanceof GOTEntityNPC) || !entity.isImmuneToFire());
			if (entity instanceof EntityPlayer) {
				flag = !((EntityPlayer) entity).capabilities.isCreativeMode;
			}
			if (flag) {
				int i = MathHelper.floor_double(entity.posX);
				int j = MathHelper.floor_double(entity.boundingBox.minY);
				int k = MathHelper.floor_double(entity.posZ);
				BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
				boolean isOpenAir = world.canBlockSeeTheSky(i, j, k);
				if (biome instanceof GOTBiome.Desert && !inWater && isOpenAir && world.isDaytime()) {
					int burnChance = 20;
					int burnProtection = 0;
					for (int l = 0; l < 4; l++) {
						ItemStack armour = entity.getEquipmentInSlot(l + 1);
						if (armour != null && armour.getItem() instanceof ItemArmor) {
							ItemArmor.ArmorMaterial material = ((ItemArmor) armour.getItem()).getArmorMaterial();
							if (material == GOTMaterial.ROBES) {
								burnProtection += 80;
							}
						}
					}
					burnChance += burnProtection;
					burnChance = Math.max(burnChance, 1);
					if (world.rand.nextInt(burnChance) == 0) {
						boolean attacked = entity.attackEntityFrom(DamageSource.onFire, 1.0F);
						if (attacked && entity instanceof EntityPlayerMP) {
							GOTDamage.doBurnDamage((EntityPlayerMP) entity);
						}
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
				entityplayer.openGui(GOT.instance, GOTCommonProxy.packGuiIDWithSlot(GOTGuiID.POUCH_MINECART.ordinal(), pouchSlot), world, minecart.getEntityId(), 0, 0);
			}
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
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
	public void onPlayerInteract(PlayerInteractEvent evt) {
		if (FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER || evt.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		World world = evt.entity.worldObj;
		Block block = world.getBlock(evt.x, evt.y, evt.z);
		if (block != Blocks.dragon_egg) {
			return;
		}
		evt.useBlock = Event.Result.DENY;
		evt.useItem = Event.Result.DENY;
		world.setBlock(evt.x, evt.y, evt.z, Blocks.air);
		GOTEntityDragon dragon = new GOTEntityDragon(world);
		dragon.setPosition(evt.x + 0.5, evt.y + 0.5, evt.z + 0.5);
		dragon.getReproductionHelper().setBreederName(evt.entityPlayer.getCommandSenderName());
		dragon.getLifeStageHelper().setLifeStage(GOTDragonLifeStage.EGG);
		world.spawnEntityInWorld(dragon);
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		EntityPlayer entityplayer = event.player;
		World world = entityplayer.worldObj;
		if (!world.isRemote) {
			EntityPlayerMP entityplayermp = (EntityPlayerMP) entityplayer;
			if (world.provider.terrainType instanceof GOTWorldType && entityplayermp.dimension == 0 && !GOTLevelData.getData(entityplayermp).getTeleportedKW()) {
				int dimension = GOTDimension.GAME_OF_THRONES.dimensionID;
				GOTTeleporter teleporter = new GOTTeleporter(DimensionManager.getWorld(dimension), false);
				MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityplayermp, dimension, teleporter);
				GOTLevelData.getData(entityplayermp).setTeleportedKW(true);
			}
			GOTLevelData.sendLoginPacket(entityplayermp);
			GOTLevelData.sendPlayerData(entityplayermp);
			GOTLevelData.sendAlignmentToAllPlayersInWorld(entityplayer, world);
			GOTLevelData.sendAllAlignmentsInWorldToPlayer(entityplayer, world);
			GOTLevelData.sendShieldToAllPlayersInWorld(entityplayermp, world);
			GOTLevelData.sendAllShieldsInWorldToPlayer(entityplayermp, world);
			GOTLevelData.sendCapeToAllPlayersInWorld(entityplayermp, world);
			GOTLevelData.sendAllCapesInWorldToPlayer(entityplayermp, world);
			GOTDate.sendUpdatePacket(entityplayermp, false);
			GOTFactionRelations.sendAllRelationsTo(entityplayermp);
			GOTPlayerData pd = GOTLevelData.getData(entityplayermp);
			pd.updateFastTravelClockFromLastOnlineTime(entityplayermp, world);
		}
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		EntityPlayer entityplayer = event.player;
		World world = entityplayer.worldObj;
		if (!world.isRemote && entityplayer instanceof EntityPlayerMP && world instanceof WorldServer) {
			EntityPlayerMP entityplayermp = (EntityPlayerMP) entityplayer;
			WorldServer worldserver = (WorldServer) world;
			ChunkCoordinates deathPoint = GOTLevelData.getData(entityplayermp).getDeathPoint();
			int deathDimension = GOTLevelData.getData(entityplayermp).getDeathDimension();
			if (deathDimension == GOTDimension.GAME_OF_THRONES.dimensionID && GOTConfig.knownWorldRespawning) {
				ChunkCoordinates bedLocation = entityplayermp.getBedLocation(entityplayermp.dimension);
				boolean hasBed = bedLocation != null;
				ChunkCoordinates spawnLocation;
				double respawnThreshold;
				if (hasBed) {
					EntityPlayer.verifyRespawnCoordinates(worldserver, bedLocation, entityplayermp.isSpawnForced(entityplayermp.dimension));
					spawnLocation = bedLocation;
					respawnThreshold = GOTConfig.KWRBedRespawnThreshold;
				} else {
					spawnLocation = worldserver.getSpawnPoint();
					respawnThreshold = GOTConfig.KWRWorldRespawnThreshold;
				}
				if (deathPoint != null) {
					boolean flag = deathPoint.getDistanceSquaredToChunkCoordinates(spawnLocation) > respawnThreshold * respawnThreshold;
					if (flag) {
						double randomDistance = MathHelper.getRandomIntegerInRange(worldserver.rand, GOTConfig.KWRMinRespawn, GOTConfig.KWRMaxRespawn);
						float angle = worldserver.rand.nextFloat() * 3.1415927F * 2.0F;
						int i = deathPoint.posX + (int) (randomDistance * MathHelper.sin(angle));
						int k = deathPoint.posZ + (int) (randomDistance * MathHelper.cos(angle));
						int j = GOT.getTrueTopBlock(worldserver, i, k);
						entityplayermp.setLocationAndAngles(i + 0.5D, j, k + 0.5D, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
						entityplayermp.playerNetServerHandler.setPlayerLocation(i + 0.5D, j, k + 0.5D, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
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
		EntityPlayerMP entityplayer = event.player;
		String message = event.message;
		String username = event.username;
		ChatComponentTranslation chatComponent = event.component;
		if (GOTConfig.drunkMessages) {
			PotionEffect nausea = entityplayer.getActivePotionEffect(Potion.confusion);
			if (nausea != null) {
				int duration = nausea.getDuration();
				float chance = duration / 4800.0F;
				chance = Math.min(chance, 1.0F);
				chance *= 0.4F;
				String key = chatComponent.getKey();
				Object[] formatArgs = chatComponent.getFormatArgs();
				for (int a = 0; a < formatArgs.length; a++) {
					Object arg = formatArgs[a];
					String chatText = null;
					if (arg instanceof ChatComponentText) {
						IChatComponent componentText = (IChatComponent) arg;
						chatText = componentText.getUnformattedText();
					} else if (arg instanceof String) {
						chatText = (String) arg;
					}
					if (chatText != null && chatText.equals(message)) {
						String newText = GOTDrunkenSpeech.getDrunkenSpeech(chatText, chance);
						if (arg instanceof String) {
							formatArgs[a] = newText;
						} else {
							formatArgs[a] = new ChatComponentText(newText);
						}
					}
				}
				chatComponent = new ChatComponentTranslation(key, formatArgs);
			}
		}
		if (GOTConfig.enableTitles) {
			GOTTitle.PlayerTitle playerTitle = GOTLevelData.getData(entityplayer).getPlayerTitle();
			if (playerTitle != null) {
				Collection<Object> newFormatArgs = new ArrayList<>();
				for (Object arg : chatComponent.getFormatArgs()) {
					if (arg instanceof ChatComponentText) {
						IChatComponent componentText = (IChatComponent) arg;
						if (componentText.getUnformattedText().contains(username)) {
							IChatComponent titleComponent = playerTitle.getFullTitleComponent(entityplayer);
							IChatComponent fullUsernameComponent = new ChatComponentText("").appendSibling(titleComponent).appendSibling(componentText);
							newFormatArgs.add(fullUsernameComponent);
						} else {
							newFormatArgs.add(componentText);
						}
					} else {
						newFormatArgs.add(arg);
					}
				}
				ChatComponentTranslation newChatComponent = new ChatComponentTranslation(chatComponent.getKey(), newFormatArgs.toArray());
				newChatComponent.setChatStyle(chatComponent.getChatStyle().createShallowCopy());
				for (Object sibling : chatComponent.getSiblings()) {
					newChatComponent.appendSibling((IChatComponent) sibling);
				}
				chatComponent = newChatComponent;
			}
		}
		event.component = chatComponent;
	}

	@SubscribeEvent
	public void onSmelting(ItemSmeltedEvent event) {
		EntityPlayer entityplayer = event.player;
		ItemStack itemstack = event.smelting;
		if (!entityplayer.worldObj.isRemote) {
			if (itemstack.getItem() == GOTItems.bronzeIngot) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.craftBronze);
			}
			if (itemstack.getItem() == GOTItems.copperIngot) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.craftCopper);
			}
		}
	}

	@SubscribeEvent
	public void onStartTracking(PlayerEvent.StartTracking event) {
		if (event.target instanceof GOTEntityCart) {
			GOTEntityCart target = (GOTEntityCart) event.target;
			if (target.getPulling() != null) {
				GOTPacketHandler.networkWrapper.sendTo(new GOTPacketCargocartUpdate(target.getPulling().getEntityId(), target.getEntityId()), (EntityPlayerMP) event.entityPlayer);
			}
		}
		if (event.target instanceof GOTEntityCargocart) {
			GOTEntityCargocart target = (GOTEntityCargocart) event.target;
			GOTPacketHandler.networkWrapper.sendTo(new GOTPacketCargocart(target.getLoad(), target.getEntityId()), (EntityPlayerMP) event.entityPlayer);
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
			IMessage packet = new GOTPacketEntityUUID(entity.getEntityId(), entity.getUniqueID());
			GOTPacketHandler.networkWrapper.sendTo(packet, (EntityPlayerMP) entityplayer);
		}
		if (!entity.worldObj.isRemote && entity instanceof GOTEntityBanner) {
			((GOTEntityBanner) entity).sendBannerToPlayer(entityplayer, false, false);
		}
	}

	@SubscribeEvent
	public void onUseBonemeal(BonemealEvent event) {
		EntityPlayer entityplayer = event.entityPlayer;
		World world = event.world;
		Random rand = world.rand;
		int i = event.x;
		int j = event.y;
		int k = event.z;
		if (!world.isRemote) {
			if (event.block instanceof GOTBlockSaplingBase) {
				GOTBlockSaplingBase sapling = (GOTBlockSaplingBase) event.block;
				int meta = world.getBlockMetadata(i, j, k);
				if (rand.nextFloat() < 0.45D) {
					sapling.incrementGrowth(world, i, j, k, rand);
				}
				if (sapling == GOTBlocks.sapling4 && (meta & 0x7) == 1 && world.getBlock(i, j, k) == GOTBlocks.wood4 && world.getBlockMetadata(i, j, k) == 1) {
					GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.growBaobab);
				}
				event.setResult(Event.Result.ALLOW);
				return;
			}
			if (event.block.canSustainPlant(world, i, j, k, ForgeDirection.UP, Blocks.tallgrass) && event.block instanceof IGrowable) {
				BiomeGenBase biomegenbase = world.getBiomeGenForCoords(i, k);
				if (biomegenbase instanceof GOTBiome) {
					GOTBiome biome = (GOTBiome) biomegenbase;
					int attempts = 0;
					label46:
					while (attempts < 128) {
						int i1 = i;
						int j1 = j + 1;
						int k1 = k;
						int subAttempts = 0;
						while (subAttempts < attempts / 16) {
							i1 += rand.nextInt(3) - 1;
							j1 += (rand.nextInt(3) - 1) * rand.nextInt(3) / 2;
							k1 += rand.nextInt(3) - 1;
							Block below = world.getBlock(i1, j1 - 1, k1);
							if (below instanceof IGrowable && below.canSustainPlant(world, i1, j1 - 1, k1, ForgeDirection.UP, Blocks.tallgrass) && !world.getBlock(i1, j1, k1).isNormalCube()) {
								subAttempts++;
								continue;
							}
							continue label46;
						}
						if (world.getBlock(i1, j1, k1).getMaterial() == Material.air) {
							if (rand.nextInt(8) > 0) {
								GOTBiome.GrassBlockAndMeta obj = biome.getRandomGrass(rand);
								Block block = obj.block;
								int meta = obj.meta;
								if (block.canBlockStay(world, i1, j1, k1)) {
									world.setBlock(i1, j1, k1, block, meta, 3);
								}
							} else {
								biome.plantFlower(world, rand, i1, j1, k1);
							}
						}
						attempts++;
					}
					event.setResult(Event.Result.ALLOW);
				}
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
		if (world.getBlock(i, j + 1, k).isAir(world, i, j + 1, k) && (block == GOTBlocks.mudGrass || block == GOTBlocks.mud)) {
			Block tilled = GOTBlocks.mudFarmland;
			world.playSoundEffect(i + 0.5F, j + 0.5F, k + 0.5F, tilled.stepSound.getStepResourcePath(), (tilled.stepSound.getVolume() + 1.0F) / 2.0F, tilled.stepSound.getPitch() * 0.8F);
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
}
