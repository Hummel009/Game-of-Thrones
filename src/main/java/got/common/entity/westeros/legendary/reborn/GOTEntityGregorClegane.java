package got.common.entity.westeros.legendary.reborn;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetBasic;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.westeros.legendary.warrior.GOTEntitySandorClegane;
import got.common.faction.GOTFaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityGregorClegane extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGregorClegane(World world) {
		super(world);
		addTargetTasks();
		setupLegendaryNPC(true);
		setSize(0.6f * 1.3f, 1.8f * 1.3f);
	}

	private void addTargetTasks() {
		int target = addTargetTasks(true);
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntitySandorClegane.class, 0, true));
	}

	@Override
	public float getAlignmentBonus() {
		return 300.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.gregorCleganeSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	public static class GregorCleganeAlive extends GOTEntityGregorClegane {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public GregorCleganeAlive(World world) {
			super(world);
		}

		@Override
		public void applyEntityAttributes() {
			super.applyEntityAttributes();
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0);
		}

		@Override
		public boolean attackEntityFrom(DamageSource damageSource, float f) {
			ItemStack itemStack;
			Entity entity = damageSource.getEntity();

			if (entity instanceof EntityLivingBase && entity == damageSource.getSourceOfDamage()) {
				itemStack = ((EntityLivingBase) entity).getHeldItem();
				if (itemStack != null) {
					Item item = itemStack.getItem();
					if (item == GOTItems.sunspear || item == GOTItems.sandorCleganeSword || item == GOTItems.crowbar) {
						return super.attackEntityFrom(damageSource, f);
					}
				}
			}

			if (damageSource == DamageSource.fall) {
				return super.attackEntityFrom(damageSource, f);
			}

			return super.attackEntityFrom(damageSource, 1.0f);
		}

		@Override
		public void dropFewItems(boolean flag, int i) {
		}

		@Override
		public GOTFaction getFaction() {
			return GOTFaction.WESTERLANDS;
		}

		@Override
		public String getSpeechBank(EntityPlayer entityPlayer) {
			if (isFriendly(entityPlayer)) {
				return "legendary/gregor_friendly";
			}
			return "legendary/gregor_hostile";
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				GregorCleganeDead dead = new GregorCleganeDead(worldObj);
				dead.copyLocationAndAnglesFrom(this);
				dead.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(dead);
				setDead();
			}
		}
	}

	public static class GregorCleganeDead extends GOTEntityGregorClegane {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public GregorCleganeDead(World world) {
			super(world);
		}

		@Override
		public void applyEntityAttributes() {
			super.applyEntityAttributes();
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
		}

		@Override
		public boolean attackEntityFrom(DamageSource damageSource, float f) {
			ItemStack itemStack;
			Entity entity = damageSource.getEntity();

			if (entity instanceof EntityLivingBase && entity == damageSource.getSourceOfDamage()) {
				itemStack = ((EntityLivingBase) entity).getHeldItem();
				if (itemStack != null && itemStack.getItem() == GOTItems.crowbar) {
					return super.attackEntityFrom(damageSource, f);
				}
			}

			if (damageSource == DamageSource.fall) {
				return super.attackEntityFrom(damageSource, f);
			}

			return super.attackEntityFrom(damageSource, 0.0f);
		}

		@Override
		public void dropFewItems(boolean flag, int i) {
			dropItem(GOTItems.gregorCleganeSword, 1);
		}

		@Override
		public GOTFaction getFaction() {
			return GOTFaction.CROWNLANDS;
		}

		@Override
		public GOTAchievement getKillAchievement() {
			return GOTAchievement.killGregorClegane;
		}
	}
}