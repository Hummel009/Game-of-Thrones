package got.common.entity.westeros.legendary.reborn;

import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.database.GOTSpeech;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetBasic;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.westeros.legendary.quest.GOTEntityRamsayBolton;
import got.common.faction.GOTFaction;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityTheonGreyjoy extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityTheonGreyjoy(World world) {
		super(world);
		setupLegendaryNPC(true);
	}

	@Override
	public float getAlignmentBonus() {
		return 300.0f;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	public static class TheonGreyjoyNormal extends GOTEntityTheonGreyjoy {

		@SuppressWarnings({"WeakerAccess", "unused"})
		public TheonGreyjoyNormal(World world) {
			super(world);
			addTargetTasks();
		}

		@Override
		public GOTFaction getFaction() {
			return GOTFaction.IRONBORN;
		}

		@Override
		public GOTAchievement getKillAchievement() {
			return GOTAchievement.killTheonGreyjoy;
		}

		protected void addTargetTasks() {
			int target = addTargetTasks(true);
			targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityRamsayBolton.class, 0, true));
		}

		@Override
		public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
			if (isFriendly(entityPlayer)) {
				return "legendary/theon_friendly";
			}
			return GOTSpeech.HOSTILE;
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				TheonGreyjoyTormented tormented = new TheonGreyjoyTormented(worldObj);
				tormented.copyLocationAndAnglesFrom(this);
				tormented.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(tormented);
				setDead();
			}
		}

		@Override
		public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
			IEntityLivingData entityData = super.onSpawnWithEgg(data);

			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelSword));
			npcItemsInv.setIdleItem(null);

			return entityData;
		}
	}

	public static class TheonGreyjoyTormented extends GOTEntityTheonGreyjoy {

		@SuppressWarnings({"WeakerAccess", "unused"})
		public TheonGreyjoyTormented(World world) {
			super(world);
		}

		@Override
		public GOTFaction getFaction() {
			return GOTFaction.NORTH;
		}

		@Override
		public EntityAIBase getAttackAI() {
			return new EntityAIPanic(this, 1.4);
		}

		@Override
		public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
			if (isFriendly(entityPlayer)) {
				return "legendary/theon_tormented_friendly";
			}
			return GOTSpeech.HOSTILE;
		}
	}
}