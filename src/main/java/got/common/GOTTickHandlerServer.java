package got.common;

import java.util.*;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import got.GOT;
import got.common.entity.other.GOTEntityPortal;
import got.common.faction.*;
import got.common.fellowship.GOTFellowshipData;
import got.common.item.other.GOTItemStructureSpawner;
import got.common.util.GOTReflection;
import got.common.world.*;
import got.common.world.biome.GOTBiome;
import got.common.world.biome.sothoryos.*;
import got.common.world.biome.variant.GOTBiomeVariantStorage;
import got.common.world.map.GOTConquestGrid;
import got.common.world.spawning.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.*;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.potion.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.*;

public class GOTTickHandlerServer {
	private static HashMap playersInPortals = new HashMap();

	public GOTTickHandlerServer() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		World world = player.worldObj;
		if (world == null || world.isRemote) {
			return;
		}
		if (player.ticksExisted % 10 == 0 && !player.capabilities.isCreativeMode) {
			BiomeGenBase biomeGen = world.getBiomeGenForCoords((int) player.posX, (int) player.posZ);
			float alignmentSothoryos = GOTLevelData.getData(player).getAlignment(GOTFaction.SOTHORYOS);
			float alignmentSummer = GOTLevelData.getData(player).getAlignment(GOTFaction.SUMMER_ISLANDS);
			if (biomeGen instanceof GOTBiomeSothoryosHell && alignmentSothoryos < 50.0f || biomeGen instanceof GOTBiomeSummerIslands && alignmentSummer < 50.0f) {
				player.addPotionEffect(new PotionEffect(Potion.blindness.id, 600, 1));
				player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 600, 1));
				player.addPotionEffect(new PotionEffect(Potion.weakness.id, 600));
			}
			if (biomeGen instanceof GOTBiomeSothoryosHell && alignmentSothoryos < 0.0f || biomeGen instanceof GOTBiomeSummerIslands && alignmentSummer < 0.0f) {
				player.addPotionEffect(new PotionEffect(Potion.poison.id, 600));
			}
		}
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP entityplayer = (EntityPlayerMP) player;
			if (event.phase == TickEvent.Phase.START && entityplayer.playerNetServerHandler != null && !(entityplayer.playerNetServerHandler instanceof GOTNetHandlerPlayServer)) {
				entityplayer.playerNetServerHandler = new GOTNetHandlerPlayServer(MinecraftServer.getServer(), entityplayer.playerNetServerHandler.netManager, entityplayer);
			}
			if (event.phase == TickEvent.Phase.END) {
				List items;
				EntityItem item;
				ItemStack heldItem;
				GOTLevelData.getData(entityplayer).onUpdate(entityplayer, (WorldServer) world);
				NetHandlerPlayServer netHandler = entityplayer.playerNetServerHandler;
				if (netHandler instanceof GOTNetHandlerPlayServer) {
					((GOTNetHandlerPlayServer) netHandler).update();
				}
				heldItem = entityplayer.getHeldItem();
				if (heldItem != null && (heldItem.getItem() instanceof ItemWritableBook || heldItem.getItem() instanceof ItemEditableBook)) {
					entityplayer.func_143004_u();
				}
				if (entityplayer.dimension == 0 && GOTLevelData.getMadePortal() == 0) {
					items = world.getEntitiesWithinAABB(EntityItem.class, entityplayer.boundingBox.expand(16.0, 16.0, 16.0));
					for (Object obj : items) {
						item = (EntityItem) obj;
						if (GOTLevelData.getMadePortal() != 0 || item.getEntityItem() == null || item.getEntityItem().getItem() != Items.iron_sword || !item.isBurning()) {
							continue;
						}
						GOTLevelData.setMadePortal(1);
						GOTLevelData.markOverworldPortalLocation(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY), MathHelper.floor_double(item.posZ));
						item.setDead();
						world.createExplosion(entityplayer, item.posX, item.posY + 3.0, item.posZ, 3.0f, true);
						GOTEntityPortal portal = new GOTEntityPortal(world);
						portal.setLocationAndAngles(item.posX, item.posY + 3.0, item.posZ, 0.0f, 0.0f);
						world.spawnEntityInWorld(portal);
					}
				}

				if ((entityplayer.dimension == 0 || entityplayer.dimension == GOTDimension.GAME_OF_THRONES.getDimensionID()) && getPlayersInPortals().containsKey(entityplayer)) {
					int i;
					List portals = world.getEntitiesWithinAABB(GOTEntityPortal.class, entityplayer.boundingBox.expand(8.0, 8.0, 8.0));
					boolean inPortal = false;
					for (i = 0; i < portals.size(); ++i) {
						GOTEntityPortal portal = (GOTEntityPortal) portals.get(i);
						if (!portal.boundingBox.intersectsWith(entityplayer.boundingBox)) {
							continue;
						}
						inPortal = true;
						break;
					}
					if (inPortal) {
						i = (Integer) getPlayersInPortals().get(entityplayer);
						i++;
						getPlayersInPortals().put(entityplayer, i);
						if (i >= 100) {
							int dimension = 0;
							if (entityplayer.dimension == 0) {
								dimension = GOTDimension.GAME_OF_THRONES.getDimensionID();
							} else if (entityplayer.dimension == GOTDimension.GAME_OF_THRONES.getDimensionID()) {
								dimension = 0;
							}
							if (world instanceof WorldServer) {
								MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityplayer, dimension, new GOTTeleporter(DimensionManager.getWorld(dimension), true));
							}
							getPlayersInPortals().remove(entityplayer);
						}
					} else {
						getPlayersInPortals().remove(entityplayer);
					}
				}

			}
		}
	}

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		World world = event.world;
		if (world.isRemote) {
			return;
		}
		if (event.phase == TickEvent.Phase.START && world == DimensionManager.getWorld(0)) {
			World overworld = world;
			if (GOTLevelData.isNeedsLoad()) {
				GOTLevelData.load();
			}
			if (GOTTime.isNeedsLoad()) {
				GOTTime.load();
			}
			if (GOTFellowshipData.needsLoad) {
				GOTFellowshipData.loadAll();
			}
			if (GOTFactionBounties.needsLoad) {
				GOTFactionBounties.loadAll();
			}
			if (GOTFactionRelations.needsLoad) {
				GOTFactionRelations.load();
			}
			if (GOTConquestGrid.needsLoad) {
				GOTConquestGrid.loadAllZones();
			}
			for (WorldServer dimWorld : MinecraftServer.getServer().worldServers) {
				WorldInfo prevWorldInfo;
				if (!(dimWorld.provider instanceof GOTWorldProvider) || (prevWorldInfo = dimWorld.getWorldInfo()).getClass() == GOTWorldInfo.class) {
					continue;
				}
				GOTWorldInfo newWorldInfo = new GOTWorldInfo(overworld.getWorldInfo());
				((WorldInfo) newWorldInfo).setWorldName(prevWorldInfo.getWorldName());
				GOTReflection.setWorldInfo(dimWorld, newWorldInfo);
				FMLLog.info("Hummel009: Successfully replaced world info in %s", GOTDimension.getCurrentDimension(dimWorld).getDimensionName());
			}
			GOTBannerProtection.updateWarningCooldowns();
			GOTInterModComms.update();
		}
		if (event.phase == TickEvent.Phase.END) {
			if (world == DimensionManager.getWorld(0)) {
				if (GOTLevelData.anyDataNeedsSave()) {
					GOTLevelData.save();
				}
				if (GOTFellowshipData.anyDataNeedsSave()) {
					GOTFellowshipData.saveAll();
				}
				GOTFactionBounties.updateAll();
				if (GOTFactionBounties.anyDataNeedsSave()) {
					GOTFactionBounties.saveAll();
				}
				if (GOTFactionRelations.needsSave()) {
					GOTFactionRelations.save();
				}
				if (GOTConquestGrid.anyChangedZones()) {
					GOTConquestGrid.saveChangedZones();
				}
				if (world.getTotalWorldTime() % 600L == 0L) {
					GOTLevelData.save();
					GOTLevelData.saveAndClearUnusedPlayerData();
					GOTFellowshipData.saveAll();
					GOTFellowshipData.saveAndClearUnusedFellowships();
					GOTFactionBounties.saveAll();
					GOTFactionRelations.save();
				}
				if (GOTItemStructureSpawner.lastStructureSpawnTick > 0) {
					--GOTItemStructureSpawner.lastStructureSpawnTick;
				}
				GOTTime.update();
				GOTJaqenHgharTracker.updateCooldowns();
			}
			if (world == DimensionManager.getWorld(GOTDimension.GAME_OF_THRONES.getDimensionID())) {
				GOTDate.update(world);
				if (GOT.canSpawnMobs(world)) {
					GOTEventSpawner.performSpawning(world);
					GOTSpawnerNPCs.performSpawning(world);
				}
				if (world.provider instanceof GOTWorldProvider && world.getTotalWorldTime() % 100L == 0L) {
					GOTBiomeVariantStorage.performCleanup((WorldServer) world);
				}
				GOTBiome.performSeasonChanges();
				GOTConquestGrid.updateZones(world);
				if (!world.playerEntities.isEmpty() && world.getTotalWorldTime() % 20L == 0L) {
					for (Object element : world.playerEntities) {
						EntityPlayer entityplayer = (EntityPlayer) element;
						GOTLevelData.sendPlayerLocationsToPlayer(entityplayer, world);
					}
				}
			}
		}
	}

	public static HashMap getPlayersInPortals() {
		return playersInPortals;
	}

	public static void setPlayersInPortals(HashMap playersInPortals) {
		GOTTickHandlerServer.playersInPortals = playersInPortals;
	}
}
