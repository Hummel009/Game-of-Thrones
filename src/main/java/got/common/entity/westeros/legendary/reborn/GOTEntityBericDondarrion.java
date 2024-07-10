package got.common.entity.westeros.legendary.reborn;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityBericDondarrion extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityBericDondarrion(World world) {
		super(world);
		addTargetTasks(true);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.RIVERLANDS;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		if (npc.isFriendly(entityPlayer)) {
			return "legendary/beric_friendly";
		}
		return GOTSpeech.HOSTILE;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.bericDondarrionSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	public static class BericDondarrionLife1 extends GOTEntityBericDondarrion {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public BericDondarrionLife1(World world) {
			super(world);
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				BericDondarrionLife2 stage = new BericDondarrionLife2(worldObj);
				stage.copyLocationAndAnglesFrom(this);
				stage.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage);
				setDead();
			}
		}
	}

	public static class BericDondarrionLife2 extends GOTEntityBericDondarrion {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public BericDondarrionLife2(World world) {
			super(world);
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				BericDondarrionLife3 stage = new BericDondarrionLife3(worldObj);
				stage.copyLocationAndAnglesFrom(this);
				stage.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage);
				setDead();
			}
		}
	}

	public static class BericDondarrionLife3 extends GOTEntityBericDondarrion {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public BericDondarrionLife3(World world) {
			super(world);
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				BericDondarrionLife4 stage = new BericDondarrionLife4(worldObj);
				stage.copyLocationAndAnglesFrom(this);
				stage.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage);
				setDead();
			}
		}
	}

	public static class BericDondarrionLife4 extends GOTEntityBericDondarrion {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public BericDondarrionLife4(World world) {
			super(world);
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				BericDondarrionLife5 stage = new BericDondarrionLife5(worldObj);
				stage.copyLocationAndAnglesFrom(this);
				stage.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage);
				setDead();
			}
		}
	}

	public static class BericDondarrionLife5 extends GOTEntityBericDondarrion {
		@SuppressWarnings({"WeakerAccess", "unused"})
		public BericDondarrionLife5(World world) {
			super(world);
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				BericDondarrionLife6 stage = new BericDondarrionLife6(worldObj);
				stage.copyLocationAndAnglesFrom(this);
				stage.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage);
				setDead();
			}
		}
	}

	public static class BericDondarrionLife6 extends GOTEntityBericDondarrion {

		@SuppressWarnings({"WeakerAccess", "unused"})
		public BericDondarrionLife6(World world) {
			super(world);
		}

		@Override
		public float getAlignmentBonus() {
			return 200.0f;
		}

		@Override
		public GOTAchievement getKillAchievement() {
			return GOTAchievement.killBericDondarrion;
		}

		@Override
		public void dropFewItems(boolean flag, int i) {
			dropItem(GOTItems.bericDondarrionSword, 1);
		}
	}
}