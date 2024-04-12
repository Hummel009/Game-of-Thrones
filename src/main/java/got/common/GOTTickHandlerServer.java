package got.common;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import got.GOT;
import got.common.entity.other.GOTEntityPortal;
import got.common.faction.GOTFactionBounties;
import got.common.faction.GOTFactionRelations;
import got.common.fellowship.GOTFellowshipData;
import got.common.item.other.GOTItemStructureSpawner;
import got.common.util.GOTReflection;
import got.common.world.GOTTeleporter;
import got.common.world.GOTWorldInfo;
import got.common.world.GOTWorldProvider;
import got.common.world.biome.GOTClimateType;
import got.common.world.biome.variant.GOTBiomeVariantStorage;
import got.common.world.map.GOTConquestGrid;
import got.common.world.spawning.GOTEventSpawner;
import got.common.world.spawning.GOTSpawnerNPCs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GOTTickHandlerServer {
	public static final Map<EntityPlayer, Integer> PLAYERS_IN_PORTALS = new HashMap<>();

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		World world = player.worldObj;
		if (world == null || world.isRemote) {
			return;
		}
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP entityplayer = (EntityPlayerMP) player;
			if (event.phase == TickEvent.Phase.START && entityplayer.playerNetServerHandler != null && !(entityplayer.playerNetServerHandler instanceof GOTNetHandlerPlayServer)) {
				entityplayer.playerNetServerHandler = new GOTNetHandlerPlayServer(MinecraftServer.getServer(), entityplayer.playerNetServerHandler.netManager, entityplayer);
			}
			if (event.phase == TickEvent.Phase.END) {
				GOTLevelData.getData(entityplayer).onUpdate(entityplayer, (WorldServer) world);
				NetHandlerPlayServer netHandler = entityplayer.playerNetServerHandler;
				if (netHandler instanceof GOTNetHandlerPlayServer) {
					((GOTNetHandlerPlayServer) netHandler).update();
				}
				ItemStack heldItem = entityplayer.getHeldItem();
				if (heldItem != null && (heldItem.getItem() instanceof ItemWritableBook || heldItem.getItem() instanceof ItemEditableBook)) {
					entityplayer.func_143004_u();
				}
				if (entityplayer.dimension == 0 && GOTLevelData.getMadePortal() == 0) {
					List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, entityplayer.boundingBox.expand(16.0D, 16.0D, 16.0D));
					for (EntityItem item : items) {
						if (GOTLevelData.getMadePortal() == 0 && item.getEntityItem() != null && item.getEntityItem().getItem() == Items.iron_sword && item.isBurning()) {
							GOTLevelData.setMadePortal(1);
							GOTLevelData.markOverworldPortalLocation(MathHelper.floor_double(item.posX), MathHelper.floor_double(item.posY), MathHelper.floor_double(item.posZ));
							item.setDead();
							world.createExplosion(entityplayer, item.posX, item.posY + 3.0D, item.posZ, 3.0F, true);
							GOTEntityPortal portal = new GOTEntityPortal(world);
							portal.setLocationAndAngles(item.posX, item.posY + 3.0D, item.posZ, 0.0F, 0.0F);
							world.spawnEntityInWorld(portal);
						}
					}
				}
				if ((entityplayer.dimension == 0 || entityplayer.dimension == GOTDimension.GAME_OF_THRONES.getDimensionID()) && PLAYERS_IN_PORTALS.containsKey(entityplayer)) {
					List<GOTEntityPortal> portals = world.getEntitiesWithinAABB(GOTEntityPortal.class, entityplayer.boundingBox.expand(8.0D, 8.0D, 8.0D));
					boolean inPortal = false;
					int i;
					for (i = 0; i < portals.size(); i++) {
						GOTEntityPortal portal = portals.get(i);
						if (portal.boundingBox.intersectsWith(entityplayer.boundingBox)) {
							inPortal = true;
							break;
						}
					}
					if (inPortal) {
						i = PLAYERS_IN_PORTALS.get(entityplayer);
						i++;
						PLAYERS_IN_PORTALS.put(entityplayer, i);
						if (i >= 100) {
							int dimension = 0;
							if (entityplayer.dimension == 0) {
								dimension = GOTDimension.GAME_OF_THRONES.getDimensionID();
							}
							MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityplayer, dimension, new GOTTeleporter(DimensionManager.getWorld(dimension), true));
							PLAYERS_IN_PORTALS.remove(entityplayer);
						}
					} else {
						PLAYERS_IN_PORTALS.remove(entityplayer);
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
			if (GOTLevelData.isNeedsLoad()) {
				GOTLevelData.load();
			}
			if (GOTTime.isNeedsLoad()) {
				GOTTime.load();
			}
			if (GOTFellowshipData.isNeedsLoad()) {
				GOTFellowshipData.loadAll();
			}
			if (GOTFactionBounties.isNeedsLoad()) {
				GOTFactionBounties.loadAll();
			}
			if (GOTFactionRelations.isNeedsLoad()) {
				GOTFactionRelations.load();
			}
			if (GOTConquestGrid.isNeedsLoad()) {
				GOTConquestGrid.loadAllZones();
			}
			for (WorldServer dimWorld : MinecraftServer.getServer().worldServers) {
				if (dimWorld.provider instanceof GOTWorldProvider) {
					WorldInfo prevWorldInfo = dimWorld.getWorldInfo();
					if (prevWorldInfo.getClass() != GOTWorldInfo.class) {
						GOTWorldInfo lOTRWorldInfo = new GOTWorldInfo(world.getWorldInfo());
						lOTRWorldInfo.setWorldName(prevWorldInfo.getWorldName());
						GOTReflection.setWorldInfo(dimWorld, lOTRWorldInfo);
						FMLLog.info("Hummel009: Successfully replaced world info in %s", GOTDimension.getCurrentDimensionWithFallback(dimWorld).getDimensionName());
					}
				}
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
					GOTFellowshipData.saveAll();
					GOTFactionBounties.saveAll();
					GOTFactionRelations.save();
				}
				int playerDataClearingInterval = GOTConfig.playerDataClearingInterval;
				playerDataClearingInterval = Math.max(playerDataClearingInterval, 600);
				if (world.getTotalWorldTime() % playerDataClearingInterval == 0L) {
					GOTLevelData.saveAndClearUnusedPlayerData();
				}
				if (GOTItemStructureSpawner.getLastStructureSpawnTick() > 0) {
					GOTItemStructureSpawner.setLastStructureSpawnTick(GOTItemStructureSpawner.getLastStructureSpawnTick() - 1);
				}
				GOTTime.update();
				GOTJaqenHgharTracker.updateCooldowns();
			}
			if (world == DimensionManager.getWorld(GOTDimension.GAME_OF_THRONES.getDimensionID())) {
				GOTDate.update(world);
				if (GOT.canSpawnMobs(world)) {
					GOTSpawnerNPCs.performSpawning(world);
					GOTEventSpawner.performSpawning(world);
				}
				GOTConquestGrid.updateZones(world);
				if (!world.playerEntities.isEmpty() && world.getTotalWorldTime() % 20L == 0L) {
					List<EntityPlayer> players = world.playerEntities;
					for (EntityPlayer entityplayer : players) {
						GOTLevelData.sendPlayerLocationsToPlayer(entityplayer, world);
					}
				}
			}
			if (world.provider instanceof GOTWorldProvider && world.getTotalWorldTime() % 100L == 0L) {
				GOTBiomeVariantStorage.performCleanup((WorldServer) world);
			}
			if (world.getTotalWorldTime() % 20L == 0L) {
				GOTClimateType.performSeasonalChangesServerSide();
				GOTClimateType.performSeasonalChangesClientSide();
			}
		}
	}
}