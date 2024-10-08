package got.common.entity.westeros.reach;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import got.common.faction.GOTFaction;
import got.common.faction.GOTReputationValues;
import got.common.util.GOTCrashHandler;
import got.common.world.GOTWorldChunkManager;
import got.common.world.GOTWorldProvider;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.biome.westeros.GOTBiomeReachArbor;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class GOTEntityReachSoldier extends GOTEntityReachMan {
	private int grapeAlert;

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityReachSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
	}

	public static void defendGrapevines(EntityPlayer entityplayer, World world, int i, int j, int k) {
		if (!entityplayer.capabilities.isCreativeMode) {
			BiomeGenBase biome = GOTCrashHandler.getBiomeGenForCoords(world, i, k);
			GOTBiomeVariant variant = world.provider instanceof GOTWorldProvider ? ((GOTWorldChunkManager) world.provider.worldChunkMgr).getBiomeVariantAt(i, k) : null;
			if (biome instanceof GOTBiomeReachArbor && variant == GOTBiomeVariant.VINEYARD) {
				float reputation = GOTLevelData.getData(entityplayer).getReputation(GOTFaction.REACH);
				boolean evil = reputation < 0.0f;
				float limit = 2000.0f;
				float chance = (limit - reputation) / limit;
				chance = Math.max(chance, 0.0f);
				chance = Math.min(chance, 1.0f);
				chance *= chance;
				if ((evil || world.rand.nextFloat() < chance) && world.rand.nextInt(4) == 0) {
					int nearbyGuards = 0;
					int spawnRange = 8;
					List<GOTEntityReachSoldier> guardList = world.getEntitiesWithinAABB(GOTEntityReachSoldier.class, entityplayer.boundingBox.expand(spawnRange, spawnRange, spawnRange));
					for (GOTEntityReachSoldier guard : guardList) {
						if (!guard.getHireableInfo().isActive()) {
							++nearbyGuards;
						}
					}
					if (nearbyGuards < 8) {
						int guardSpawns = 1 + world.rand.nextInt(6);
						block1:
						for (int l = 0; l < guardSpawns; ++l) {
							GOTEntityReachSoldier guard = new GOTEntityReachSoldier(world);
							if (world.rand.nextBoolean()) {
								guard = new GOTEntityReachSoldierArcher(world);
							}
							guard.setSpawnRidingHorse(false);
							int attempts = 16;
							for (int a = 0; a < attempts; ++a) {
								int i1 = i + MathHelper.getRandomIntegerInRange(world.rand, -spawnRange, spawnRange);
								int j1 = j + MathHelper.getRandomIntegerInRange(world.rand, -spawnRange / 2, spawnRange / 2);
								int k1 = k + MathHelper.getRandomIntegerInRange(world.rand, -spawnRange, spawnRange);
								Block belowBlock = world.getBlock(i1, j1 - 1, k1);
								world.getBlockMetadata(i1, j1 - 1, k1);
								boolean belowSolid = belowBlock.isSideSolid(world, i1, j1 - 1, k1, ForgeDirection.UP);
								if (!belowSolid || world.getBlock(i1, j1, k1).isNormalCube() || world.getBlock(i1, j1 + 1, k1).isNormalCube()) {
									continue;
								}
								guard.setLocationAndAngles(i1 + 0.5, j1, k1 + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
								guard.setLiftSpawnRestrictions(true);
								if (guard.getCanSpawnHere()) {
									guard.setLiftSpawnRestrictions(false);
									world.spawnEntityInWorld(guard);
									guard.setSpawnRidingHorse(false);
									guard.onSpawnWithEgg(null);
									continue block1;
								}
							}
						}
					}
				}
				int range = 16;
				List<GOTEntityReachSoldier> guardList = world.getEntitiesWithinAABB(GOTEntityReachSoldier.class, entityplayer.boundingBox.expand(range, range, range));
				boolean anyAlert = false;
				for (GOTEntityReachSoldier guard : guardList) {
					if (guard.getHireableInfo().isActive()) {
						continue;
					}
					if (evil) {
						guard.setAttackTarget(entityplayer);
						guard.sendSpeechBank(entityplayer, "reach/soldier/hostile");
						guard.grapeAlert = 3;
						anyAlert = true;
						continue;
					}
					if (world.rand.nextFloat() >= chance) {
						continue;
					}
					++guard.grapeAlert;
					if (guard.grapeAlert >= 3) {
						guard.setAttackTarget(entityplayer);
					}
					guard.sendSpeechBank(entityplayer, "reach/soldier/hostile");
					anyAlert = true;
				}
				if (anyAlert && reputation >= 0.0f) {
					GOTLevelData.getData(entityplayer).addReputation(entityplayer, GOTReputationValues.VINEYARD_STEAL_PENALTY, GOTFaction.REACH, i + 0.5, j + 0.5, k + 0.5);
				}
			}
		}
	}

	@Override
	public GOTShields getShield() {
		return GOTShields.REACH;
	}

	@Override
	public GOTCapes getCape() {
		return GOTCapes.REACH;
	}

	@Override
	public float getReputationBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, false);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.reachBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.reachLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.reachChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.reachHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	@Override
	public void onDeath(DamageSource damageSource) {
		super.onDeath(damageSource);
		if (!worldObj.isRemote && damageSource.getEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) damageSource.getEntity();
			if (grapeAlert >= 3) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.stealArborGrapes);
			}
		}
	}
}