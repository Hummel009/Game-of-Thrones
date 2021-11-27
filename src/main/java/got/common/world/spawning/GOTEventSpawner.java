package got.common.world.spawning;

import java.util.*;

import cpw.mods.fml.common.eventhandler.Event;
import got.GOT;
import got.common.*;
import got.common.database.GOTInvasions;
import got.common.entity.other.*;
import got.common.world.GOTWorldProvider;
import got.common.world.biome.GOTBiome;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.ForgeEventFactory;

public class GOTEventSpawner {
	public static Set<ChunkCoordIntPair> eligibleSpawnChunks = new HashSet<>();

	public static void performSpawning(World world) {
		if (world.getTotalWorldTime() % 20L == 0L) {
			GOTSpawnerNPCs.getSpawnableChunksWithPlayerInRange(world, eligibleSpawnChunks, 32);
			List<ChunkCoordIntPair> shuffled = GOTSpawnerNPCs.shuffle(eligibleSpawnChunks);
			GOTEventSpawner.spawnBandits(world, shuffled);
			if (GOTConfig.enableInvasions) {
				GOTEventSpawner.spawnInvasions(world, shuffled);
			}
		}
		GOTJaqenHgharTracker.performSpawning(world);
	}

	public static void spawnBandits(World world, List<ChunkCoordIntPair> spawnChunks) {
		Random rand = world.rand;
		block0: for (ChunkCoordIntPair chunkCoords : spawnChunks) {
			int i;
			BiomeGenBase biome;
			int k;
			int range;
			ChunkPosition chunkposition = GOTSpawnerNPCs.getRandomSpawningPointInChunk(world, chunkCoords);
			if (chunkposition == null || !((biome = world.getBiomeGenForCoords(i = chunkposition.chunkPosX, k = chunkposition.chunkPosZ)) instanceof GOTBiome)) {
				continue;
			}
			GOTBiome gotbiome = (GOTBiome) biome;
			Class<? extends GOTEntityBandit> banditClass = gotbiome.getBanditEntityClass();
			double chance = gotbiome.getBanditChance().chancesPerSecondPerChunk[16];
			if ((chance <= 0.0) || (world.rand.nextDouble() >= chance) || (world.selectEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(i - (range = 48), 0.0, k - range, i + range, world.getHeight(), k + range), GOT.selectNonCreativePlayers())).isEmpty()) {
				continue;
			}
			int banditsSpawned = 0;
			int maxBandits = MathHelper.getRandomIntegerInRange(world.rand, 1, 4);
			for (int attempts = 0; attempts < 32; ++attempts) {
				Block block;
				GOTEntityBandit bandit;
				int k1;
				int i1 = i + MathHelper.getRandomIntegerInRange(rand, -32, 32);
				int j1 = world.getHeightValue(i1, k1 = k + MathHelper.getRandomIntegerInRange(rand, -32, 32));
				if (j1 <= 60 || (block = world.getBlock(i1, j1 - 1, k1)) != biome.topBlock && block != biome.fillerBlock || world.getBlock(i1, j1, k1).isNormalCube() || world.getBlock(i1, j1 + 1, k1).isNormalCube() || (bandit = (GOTEntityBandit) EntityList.createEntityByName((GOTEntityRegistry.getStringFromClass(banditClass)), world)) == null) {
					continue;
				}
				bandit.setLocationAndAngles(i1 + 0.5, j1, k1 + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
				Event.Result canSpawn = ForgeEventFactory.canEntitySpawn(bandit, world, ((float) bandit.posX), ((float) bandit.posY), ((float) bandit.posZ));
				if (canSpawn != Event.Result.ALLOW && (canSpawn != Event.Result.DEFAULT || !bandit.getCanSpawnHere())) {
					continue;
				}
				bandit.onSpawnWithEgg(null);
				world.spawnEntityInWorld(bandit);
				bandit.isNPCPersistent = false;
				if (++banditsSpawned >= maxBandits) {
					continue block0;
				}
			}
		}
	}

	public static void spawnInvasions(World world, List<ChunkCoordIntPair> spawnChunks) {
		Random rand = world.rand;
		block0: for (ChunkCoordIntPair chunkCoords : spawnChunks) {
			int i;
			BiomeGenBase biome;
			int k;
			ChunkPosition chunkposition = GOTSpawnerNPCs.getRandomSpawningPointInChunk(world, chunkCoords);
			if (chunkposition == null || !((biome = world.getBiomeGenForCoords(i = chunkposition.chunkPosX, k = chunkposition.chunkPosZ)) instanceof GOTBiome)) {
				continue;
			}
			GOTBiomeInvasionSpawns invasionSpawns = ((GOTBiome) biome).invasionSpawns;
			for (EventChance invChance : EventChance.values()) {
				int range;
				List<GOTInvasions> invList = invasionSpawns.getInvasionsForChance(invChance);
				if (invList.isEmpty()) {
					continue;
				}
				GOTInvasions invasionType = invList.get(rand.nextInt(invList.size()));
				double chance = invChance.chancesPerSecondPerChunk[16];
				if (!world.isDaytime() && GOTWorldProvider.isLunarEclipse()) {
					chance *= 5.0;
				}
				if ((rand.nextDouble() >= chance) || (world.selectEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(i - (range = 48), 0.0, k - range, i + range, world.getHeight(), k + range), new IEntitySelector() {

					@Override
					public boolean isEntityApplicable(Entity entity) {
						EntityPlayer entityplayer;
						if (entity instanceof EntityPlayer && (entityplayer = (EntityPlayer) entity).isEntityAlive() && !entityplayer.capabilities.isCreativeMode) {
							return GOTLevelData.getData(entityplayer).getAlignment(invasionType.invasionFaction) < 0.0f;
						}
						return false;
					}
				})).isEmpty()) {
					continue;
				}
				for (int attempts = 0; attempts < 16; ++attempts) {
					int k1;
					Block block;
					int i1 = i + MathHelper.getRandomIntegerInRange(rand, -32, 32);
					int j1 = world.getHeightValue(i1, k1 = k + MathHelper.getRandomIntegerInRange(rand, -32, 32));
					if (j1 <= 60 || (block = world.getBlock(i1, j1 - 1, k1)) != biome.topBlock && block != biome.fillerBlock || world.getBlock(i1, j1, k1).isNormalCube() || world.getBlock(i1, j1 + 1, k1).isNormalCube()) {
						continue;
					}
					GOTEntityInvasionSpawner invasion = new GOTEntityInvasionSpawner(world);
					invasion.setInvasionType(invasionType);
					invasion.setLocationAndAngles(i1 + 0.5, j1 += 3 + rand.nextInt(3), k1 + 0.5, 0.0f, 0.0f);
					if (!invasion.canInvasionSpawnHere()) {
						continue;
					}
					world.spawnEntityInWorld(invasion);
					invasion.selectAppropriateBonusFactions();
					invasion.startInvasion();
					continue block0;
				}
			}
		}
	}

	public enum EventChance {
		NEVER(0.0f, 0), RARE(0.1f, 3600), UNCOMMON(0.3f, 3600), COMMON(0.9f, 3600);

		public double chancePerSecond;
		public double[] chancesPerSecondPerChunk;

		EventChance(float prob, int s) {
			chancePerSecond = EventChance.getChance(prob, s);
			chancesPerSecondPerChunk = new double[64];
			for (int i = 0; i < chancesPerSecondPerChunk.length; ++i) {
				chancesPerSecondPerChunk[i] = EventChance.getChance(chancePerSecond, i);
			}
		}

		public static double getChance(double prob, int trials) {
			if (prob == 0.0 || trials == 0) {
				return 0.0;
			}
			double d = prob;
			d = 1.0 - d;
			d = Math.pow(d, 1.0 / trials);
			return 1.0 - d;
		}
	}

}
