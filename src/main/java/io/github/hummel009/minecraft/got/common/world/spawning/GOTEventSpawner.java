package io.github.hummel009.minecraft.got.common.world.spawning;

import cpw.mods.fml.common.eventhandler.Event;
import io.github.hummel009.minecraft.got.GOT;
import io.github.hummel009.minecraft.got.common.GOTConfig;
import io.github.hummel009.minecraft.got.common.GOTJaqenHgharTracker;
import io.github.hummel009.minecraft.got.common.GOTLevelData;
import io.github.hummel009.minecraft.got.common.data.GOTInvasions;
import io.github.hummel009.minecraft.got.common.entity.GOTEntityRegistry;
import io.github.hummel009.minecraft.got.common.entity.other.GOTEntityBanditBase;
import io.github.hummel009.minecraft.got.common.entity.other.GOTEntityNPC;
import io.github.hummel009.minecraft.got.common.entity.other.inanimate.GOTEntityInvasionSpawner;
import io.github.hummel009.minecraft.got.common.util.GOTCrashHandler;
import io.github.hummel009.minecraft.got.common.world.GOTWorldProvider;
import io.github.hummel009.minecraft.got.common.world.biome.GOTBiome;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GOTEventSpawner {
	private static final Collection<Long> ELIGIBLE_SPAWN_CHUNKS = new HashSet<>();

	private static long[] shuffleBuffer = new long[0];

	private GOTEventSpawner() {
	}

	public static void performSpawning(World world) {
		if (world.getTotalWorldTime() % 20L == 0L) {
			GOTSpawnerNPCs.getSpawnableChunksWithPlayerInRange(world, ELIGIBLE_SPAWN_CHUNKS, 32);
			if (shuffleBuffer.length < ELIGIBLE_SPAWN_CHUNKS.size()) {
				shuffleBuffer = new long[ELIGIBLE_SPAWN_CHUNKS.size()];
			}
			int shuffledSize = GOTSpawnerNPCs.shuffleInto(shuffleBuffer, ELIGIBLE_SPAWN_CHUNKS, world.rand);
			spawnBandits(world, shuffledSize);
			if (GOTConfig.enableInvasions) {
				spawnInvasions(world, shuffledSize);
			}
		}
		GOTJaqenHgharTracker.performSpawning(world);
	}

	private static void spawnBandits(World world, int shuffledSize) {
		Random rand = world.rand;
		block0:
		for (int si = 0; si < shuffledSize; ++si) {
			long chunkKey = shuffleBuffer[si];
			int i;
			BiomeGenBase biome;
			int k;
			int range;
			ChunkPosition chunkposition = GOTSpawnerNPCs.getRandomSpawningPointInChunk(world, chunkKey);
			if (chunkposition == null || !((biome = GOTCrashHandler.getBiomeGenForCoords(world, i = chunkposition.chunkPosX, k = chunkposition.chunkPosZ)) instanceof GOTBiome)) {
				continue;
			}
			GOTBiome gotbiome = (GOTBiome) biome;
			Class<? extends GOTEntityNPC> banditClass = gotbiome.getBanditEntityClass();
			double chance = gotbiome.getBanditChance().getChancesPerSecondPerChunk()[16];
			if (chance <= 0.0 || world.rand.nextDouble() >= chance || world.selectEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(i - (range = 48), 0.0, k - range, i + range, world.getHeight(), k + range), GOT.selectNonCreativePlayers()).isEmpty()) {
				continue;
			}
			int banditsSpawned = 0;
			int maxBandits = MathHelper.getRandomIntegerInRange(world.rand, 1, 4);
			for (int attempts = 0; attempts < 32; ++attempts) {
				Block block;
				GOTEntityBanditBase bandit;
				int k1;
				int i1 = i + MathHelper.getRandomIntegerInRange(rand, -32, 32);
				int j1 = world.getHeightValue(i1, k1 = k + MathHelper.getRandomIntegerInRange(rand, -32, 32));
				if (j1 <= 60 || (block = world.getBlock(i1, j1 - 1, k1)) != biome.topBlock && block != biome.fillerBlock || world.getBlock(i1, j1, k1).isNormalCube() || world.getBlock(i1, j1 + 1, k1).isNormalCube() || (bandit = (GOTEntityBanditBase) EntityList.createEntityByName(GOTEntityRegistry.getStringFromClass(banditClass), world)) == null) {
					continue;
				}
				bandit.setLocationAndAngles(i1 + 0.5, j1, k1 + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
				Event.Result canSpawn = ForgeEventFactory.canEntitySpawn(bandit, world, (float) bandit.posX, (float) bandit.posY, (float) bandit.posZ);
				if (canSpawn != Event.Result.ALLOW && (canSpawn != Event.Result.DEFAULT || !bandit.getCanSpawnHere())) {
					continue;
				}
				bandit.onSpawnWithEgg(null);
				world.spawnEntityInWorld(bandit);
				bandit.setPersistent(false);
				banditsSpawned++;
				if (banditsSpawned >= maxBandits) {
					continue block0;
				}
			}
		}
	}

	private static void spawnInvasions(World world, int shuffledSize) {
		Random rand = world.rand;
		block0:
		for (int si = 0; si < shuffledSize; ++si) {
			long chunkKey = shuffleBuffer[si];
			int i;
			BiomeGenBase biome;
			int k;
			ChunkPosition chunkposition = GOTSpawnerNPCs.getRandomSpawningPointInChunk(world, chunkKey);
			if (chunkposition == null || !((biome = GOTCrashHandler.getBiomeGenForCoords(world, i = chunkposition.chunkPosX, k = chunkposition.chunkPosZ)) instanceof GOTBiome)) {
				continue;
			}
			GOTBiomeInvasionSpawns invasionSpawns = ((GOTBiome) biome).getInvasionSpawns();
			boolean lunarBoost = !world.isDaytime() && GOTWorldProvider.isLunarEclipse();
			for (EventChance invChance : EventChance.values()) {
				int range;
				List<GOTInvasions> invList = invasionSpawns.getInvasionsForChance(invChance);
				if (invList.isEmpty()) {
					continue;
				}
				GOTInvasions invasionType = invList.get(rand.nextInt(invList.size()));
				double chance = invChance.getChancesPerSecondPerChunk()[16];
				if (lunarBoost) {
					chance *= 5.0;
				}
				if (rand.nextDouble() >= chance || world.selectEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(i - (range = 48), 0.0, k - range, i + range, world.getHeight(), k + range), new EntitySelectorImpl(invasionType)).isEmpty()) {
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
					invasion.setLocationAndAngles(i1 + 0.5, j1 + 3 + rand.nextInt(3), k1 + 0.5, 0.0f, 0.0f);
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

		private final double[] chancesPerSecondPerChunk;

		EventChance(float prob, int s) {
			double chancePerSecond = getChance(prob, s);
			chancesPerSecondPerChunk = new double[64];
			for (int i = 0; i < chancesPerSecondPerChunk.length; ++i) {
				chancesPerSecondPerChunk[i] = getChance(chancePerSecond, i);
			}
		}

		private static double getChance(double prob, int trials) {
			if (prob == 0.0 || trials == 0) {
				return 0.0;
			}
			double d = prob;
			d = 1.0 - d;
			d = Math.pow(d, 1.0 / trials);
			return 1.0 - d;
		}

		private double[] getChancesPerSecondPerChunk() {
			return chancesPerSecondPerChunk;
		}
	}

	private static class EntitySelectorImpl implements IEntitySelector {
		private final GOTInvasions invasionType;

		private EntitySelectorImpl(GOTInvasions invasionType) {
			this.invasionType = invasionType;
		}

		@Override
		public boolean isEntityApplicable(Entity entity) {
			EntityPlayer entityplayer = (EntityPlayer) entity;
			return entity instanceof EntityPlayer && entityplayer.isEntityAlive() && !entityplayer.capabilities.isCreativeMode && GOTLevelData.getData(entityplayer).getReputation(invasionType.getInvasionFaction()) < 0.0f;
		}
	}
}