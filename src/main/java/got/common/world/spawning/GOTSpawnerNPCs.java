package got.common.world.spawning;

import cpw.mods.fml.common.eventhandler.Event;
import got.common.GOTConfig;
import got.common.GOTSpawnDamping;
import got.common.entity.other.GOTEntityNPC;
import got.common.util.GOTCrashHandler;
import got.common.world.GOTWorldChunkManager;
import got.common.world.GOTWorldProvider;
import got.common.world.biome.GOTBiome;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.*;

public class GOTSpawnerNPCs {
	private static final Set<ChunkCoordIntPair> ELIGIBLE_SPAWN_CHUNKS = new HashSet<>();
	private static final Map<Integer, Integer> TICKS_SINCE_CYCLE = new HashMap<>();

	private GOTSpawnerNPCs() {
	}

	private static boolean canNPCSpawnAtLocation(IBlockAccess world, int i, int j, int k) {
		if (!World.doesBlockHaveSolidTopSurface(world, i, j - 1, k)) {
			return false;
		}
		Block block = world.getBlock(i, j - 1, k);
		world.getBlockMetadata(i, j - 1, k);
		boolean spawnBlock = block.canCreatureSpawn(EnumCreatureType.monster, world, i, j - 1, k);
		return spawnBlock && block != Blocks.bedrock && !world.getBlock(i, j, k).isNormalCube() && !world.getBlock(i, j, k).getMaterial().isLiquid() && !world.getBlock(i, j + 1, k).isNormalCube();
	}

	private static int countNPCs(World world) {
		int count = 0;
		for (Object element : world.loadedEntityList) {
			Entity entity = (Entity) element;
			if (!(entity instanceof GOTEntityNPC)) {
				continue;
			}
			int spawnCountValue = ((GOTEntityNPC) entity).getSpawnCountValue();
			count += spawnCountValue;
		}
		return count;
	}

	public static ChunkPosition getRandomSpawningPointInChunk(World world, ChunkCoordIntPair chunkCoords) {
		int i = chunkCoords.chunkXPos;
		int k = chunkCoords.chunkZPos;
		return getRandomSpawningPointInChunk(world, i, k);
	}

	private static ChunkPosition getRandomSpawningPointInChunk(World world, int i, int k) {
		if (!world.getChunkProvider().chunkExists(i, k)) {
			return null;
		}
		Chunk chunk = world.getChunkFromChunkCoords(i, k);
		int i1 = i * 16 + world.rand.nextInt(16);
		int k1 = k * 16 + world.rand.nextInt(16);
		int j = world.rand.nextInt(chunk == null ? world.getActualHeight() : chunk.getTopFilledSegment() + 16 - 1);
		return new ChunkPosition(i1, j, k1);
	}

	private static GOTSpawnEntry.Instance getRandomSpawnListEntry(World world, int i, int k) {
		GOTBiomeSpawnList spawnlist = null;
		BiomeGenBase biome = GOTCrashHandler.getBiomeGenForCoords(world, i, k);
		if (biome instanceof GOTBiome && world.provider instanceof GOTWorldProvider) {
			GOTBiome gotbiome = (GOTBiome) biome;
			GOTWorldChunkManager worldChunkMgr = (GOTWorldChunkManager) world.provider.worldChunkMgr;
			worldChunkMgr.getBiomeVariantAt(i, k);
			spawnlist = gotbiome.getNpcSpawnList();
		}
		if (spawnlist != null) {
			return spawnlist.getRandomSpawnEntry(world.rand, world, i, k);
		}
		return null;
	}

	public static void getSpawnableChunks(World world, Collection<ChunkCoordIntPair> set) {
		set.clear();
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer entityplayer : players) {
			int i = MathHelper.floor_double(entityplayer.posX / 16.0);
			int k = MathHelper.floor_double(entityplayer.posZ / 16.0);
			for (int i1 = -7; i1 <= 7; ++i1) {
				for (int k1 = -7; k1 <= 7; ++k1) {
					ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i + i1, k + k1);
					set.add(chunkcoordintpair);
				}
			}
		}
	}

	public static void getSpawnableChunksWithPlayerInRange(World world, Collection<ChunkCoordIntPair> set, int range) {
		getSpawnableChunks(world, set);
		Collection<EntityPlayer> validPlayers = new ArrayList<>();
		List<EntityPlayer> players = world.playerEntities;
		for (EntityPlayer entityplayer : players) {
			if (entityplayer.capabilities.isCreativeMode) {
				continue;
			}
			validPlayers.add(entityplayer);
		}
		int height = world.getHeight();
		Collection<ChunkCoordIntPair> removes = new HashSet<>();
		for (ChunkCoordIntPair chunkCoords : set) {
			int i = chunkCoords.getCenterXPos();
			int k = chunkCoords.getCenterZPosition();
			AxisAlignedBB playerCheckBox = AxisAlignedBB.getBoundingBox(i - range, 0.0, k - range, i + range, height, k + range);
			boolean anyPlayers = false;
			for (EntityPlayer entityplayer : validPlayers) {
				if (!entityplayer.boundingBox.intersectsWith(playerCheckBox)) {
					continue;
				}
				anyPlayers = true;
				break;
			}
			if (anyPlayers) {
				continue;
			}
			removes.add(chunkCoords);
		}
		set.removeAll(removes);
	}

	public static void performSpawning(World world) {
		int interval = GOTConfig.mobSpawnInterval;
		if (interval > 0) {
			int ticks = 0;
			int dimID = world.provider.dimensionId;
			if (TICKS_SINCE_CYCLE.containsKey(dimID)) {
				ticks = TICKS_SINCE_CYCLE.get(dimID);
			}
			ticks--;
			TICKS_SINCE_CYCLE.put(dimID, ticks);
			if (ticks > 0) {
				return;
			}
			ticks = interval;
			TICKS_SINCE_CYCLE.put(dimID, ticks);
		}
		getSpawnableChunks(world, ELIGIBLE_SPAWN_CHUNKS);
		ChunkCoordinates spawnPoint = world.getSpawnPoint();
		int totalSpawnCount = countNPCs(world);
		int maxSpawnCount = GOTSpawnDamping.getNPCSpawnCap(world) * ELIGIBLE_SPAWN_CHUNKS.size() / 196;
		if (totalSpawnCount <= maxSpawnCount) {
			int cycles = Math.max(1, interval);
			block2:
			for (int c = 0; c < cycles; ++c) {
				List<ChunkCoordIntPair> shuffled = shuffle(ELIGIBLE_SPAWN_CHUNKS);
				for (ChunkCoordIntPair chunkCoords : shuffled) {
					int i;
					int j;
					int k;
					ChunkPosition chunkposition = getRandomSpawningPointInChunk(world, chunkCoords);
					if (chunkposition == null || world.getBlock(i = chunkposition.chunkPosX, j = chunkposition.chunkPosY, k = chunkposition.chunkPosZ).isNormalCube() || world.getBlock(i, j, k).getMaterial() != Material.air) {
						continue;
					}
					int groups = 3;
					block4:
					for (int l = 0; l < groups; ++l) {
						int i1 = i;
						int j1 = j;
						int k1 = k;
						int range = 5;
						int yRange = 0;
						int rangeP1 = range + 1;
						int yRangeP1 = yRange + 1;
						GOTSpawnEntry.Instance spawnEntryInstance = getRandomSpawnListEntry(world, i1, k1);
						if (spawnEntryInstance == null) {
							continue;
						}
						GOTSpawnEntry spawnEntry = spawnEntryInstance.getSpawnEntry();
						boolean isConquestSpawn = spawnEntryInstance.isConquestSpawn();
						int spawnCount = MathHelper.getRandomIntegerInRange(world.rand, spawnEntry.minGroupCount, spawnEntry.maxGroupCount);
						int chance = spawnEntryInstance.getSpawnChance();
						if (chance != 0 && world.rand.nextInt(chance) != 0) {
							continue;
						}
						IEntityLivingData entityData = null;
						int spawned = 0;
						int attempts = spawnCount * 8;
						for (int a = 0; a < attempts; ++a) {
							float f1;
							float f4;
							float f2;
							float f5;
							float f3;
							float f;
							EntityLiving entity;
							if (!world.blockExists(i1 += world.rand.nextInt(rangeP1) - world.rand.nextInt(rangeP1), j1 += world.rand.nextInt(yRangeP1) - world.rand.nextInt(yRangeP1), k1 += world.rand.nextInt(rangeP1) - world.rand.nextInt(rangeP1)) || !canNPCSpawnAtLocation(world, i1, j1, k1) || world.getClosestPlayer(f = i1 + 0.5f, f1 = j1, f2 = k1 + 0.5f, 24.0) != null || (f3 = f - spawnPoint.posX) * f3 + (f4 = f1 - spawnPoint.posY) * f4 + (f5 = f2 - spawnPoint.posZ) * f5 < 576.0f) {
								continue;
							}
							try {
								entity = (EntityLiving) spawnEntry.entityClass.getConstructor(World.class).newInstance(world);
							} catch (Exception e) {
								e.printStackTrace();
								return;
							}
							entity.setLocationAndAngles(f, f1, f2, world.rand.nextFloat() * 360.0f, 0.0f);
							if (entity instanceof GOTEntityNPC && isConquestSpawn) {
								GOTEntityNPC npc = (GOTEntityNPC) entity;
								npc.setConquestSpawning(true);
							}
							Event.Result canSpawn = ForgeEventFactory.canEntitySpawn(entity, world, f, f1, f2);
							if (canSpawn != Event.Result.ALLOW && (canSpawn != Event.Result.DEFAULT || !entity.getCanSpawnHere())) {
								continue;
							}
							world.spawnEntityInWorld(entity);
							if (entity instanceof GOTEntityNPC) {
								GOTEntityNPC npc = (GOTEntityNPC) entity;
								npc.setNPCPersistent(false);
								npc.setConquestSpawning(false);
							}
							if (!ForgeEventFactory.doSpecialSpawn(entity, world, f, f1, f2)) {
								entityData = entity.onSpawnWithEgg(entityData);
							}
							if (c > 0 && (totalSpawnCount += entity instanceof GOTEntityNPC ? ((GOTEntityNPC) entity).getSpawnCountValue() : 1) > maxSpawnCount) {
								break block2;
							}
							spawned++;
							if (spawned >= spawnCount) {
								continue block4;
							}
						}
					}
				}
			}
		}
	}

	public static List<ChunkCoordIntPair> shuffle(Set<ChunkCoordIntPair> set) {
		List<ChunkCoordIntPair> list = new ArrayList<>(set);
		Collections.shuffle(list);
		return list;
	}
}