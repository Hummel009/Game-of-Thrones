package got.common.entity.westeros.reach;

import java.util.List;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTAlignmentValues;
import got.common.faction.GOTFaction;
import got.common.world.GOTWorldChunkManager;
import got.common.world.GOTWorldProvider;
import got.common.world.biome.variant.GOTBiomeVariant;
import got.common.world.biome.westeros.GOTBiomeReachArbor;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class GOTEntityReachSoldier extends GOTEntityReachLevyman {
	public static int MAX_GRAPE_ALERT = 3;
	public int grapeAlert;

	public GOTEntityReachSoldier(World world) {
		super(world);
		canBeMarried = false;
		spawnRidingHorse = rand.nextInt(10) == 0;
		npcShield = GOTShields.REACH;
		npcCape = GOTCapes.REACH;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
	}

	@Override
	public EntityAIBase createReachAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, false);
	}

	@Override
	public float getAlignmentBonus() {
		return 3.0f;
	}

	@Override
	public void onAttackModeChange(GOTEntityNPC.AttackMode mode, boolean mounted) {
		if (mode == GOTEntityNPC.AttackMode.IDLE) {
			if (mounted) {
				setCurrentItemOrArmor(0, npcItemsInv.getIdleItemMounted());
			} else {
				setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
			}
		} else if (mounted) {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeaponMounted());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public void onDeath(DamageSource damagesource) {
		super.onDeath(damagesource);
		if (!worldObj.isRemote && damagesource.getEntity() instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) damagesource.getEntity();
			if (grapeAlert >= 3) {
				GOTLevelData.getData(entityplayer).addAchievement(GOTAchievement.stealArborGrapes);
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(10);
		switch (i) {
			case 0:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosHammer));
				break;
			case 1:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosPike));
				break;
			case 2:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosLongsword));
				break;
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosGreatsword));
				break;
			default:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosSword));
				break;
		}
		if (rand.nextInt(3) == 0) {
			npcItemsInv.setMeleeWeaponMounted(new ItemStack(GOTItems.westerosLance));
		} else {
			npcItemsInv.setMeleeWeaponMounted(npcItemsInv.getMeleeWeapon());
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeaponMounted());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.reachBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.reachLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.reachChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.reachHelmet));
		return data;
	}

	public static void defendGrapevines(EntityPlayer entityplayer, World world, int i, int j, int k) {
		if (!entityplayer.capabilities.isCreativeMode) {
			GOTBiomeVariant variant;
			BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
			variant = world.provider instanceof GOTWorldProvider ? ((GOTWorldChunkManager) world.provider.worldChunkMgr).getBiomeVariantAt(i, k) : null;
			if (biome instanceof GOTBiomeReachArbor && variant == GOTBiomeVariant.VINEYARD) {
				GOTEntityReachSoldier guard;
				float alignment = GOTLevelData.getData(entityplayer).getAlignment(GOTFaction.REACH);
				boolean evil = alignment < 0.0f;
				float limit = 2000.0f;
				float chance = (limit - alignment) / limit;
				chance = Math.max(chance, 0.0f);
				chance = Math.min(chance, 1.0f);
				chance *= chance;
				if ((evil || world.rand.nextFloat() < chance) && world.rand.nextInt(4) == 0) {
					int nearbyGuards = 0;
					int spawnRange = 8;
					List<GOTEntityReachSoldier> guardList = world.getEntitiesWithinAABB(GOTEntityReachSoldier.class, entityplayer.boundingBox.expand(spawnRange, spawnRange, spawnRange));
					for (GOTEntityReachSoldier obj : guardList) {
						guard = obj;
						if (!guard.hiredNPCInfo.isActive) {
							++nearbyGuards;
						}
					}
					if (nearbyGuards < 8) {
						int guardSpawns = 1 + world.rand.nextInt(6);
						block1:
						for (int l = 0; l < guardSpawns; ++l) {
							guard = new GOTEntityReachSoldier(world);
							if (world.rand.nextBoolean()) {
								guard = new GOTEntityReachSoldierArcher(world);
							}
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
								guard.liftSpawnRestrictions = true;
								if (!guard.getCanSpawnHere()) {
									continue;
								}
								guard.liftSpawnRestrictions = false;
								world.spawnEntityInWorld(guard);
								guard.spawnRidingHorse = false;
								guard.onSpawnWithEgg(null);
								continue block1;
							}
						}
					}
				}
				int range = 16;
				List<GOTEntityReachSoldier> guardList = world.getEntitiesWithinAABB(GOTEntityReachSoldier.class, entityplayer.boundingBox.expand(range, range, range));
				boolean anyAlert = false;
				for (GOTEntityReachSoldier obj : guardList) {
					guard = obj;
					if (guard.hiredNPCInfo.isActive) {
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
				if (anyAlert && alignment >= 0.0f) {
					GOTLevelData.getData(entityplayer).addAlignment(entityplayer, GOTAlignmentValues.VINEYARD_STEAL_PENALTY, GOTFaction.REACH, i + 0.5, j + 0.5, k + 0.5);
				}
			}
		}
	}
}
