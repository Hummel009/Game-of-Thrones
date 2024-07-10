package got.common.entity.westeros.legendary.reborn;

import got.common.database.GOTAchievement;
import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetBasic;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.westeros.legendary.quest.GOTEntityRamsayBolton;
import got.common.entity.westeros.legendary.warrior.GOTEntityRooseBolton;
import got.common.faction.GOTFaction;
import got.common.quest.GOTMiniQuestFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityJonSnow extends GOTEntityHumanBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityJonSnow(World world) {
		super(world);
		setupLegendaryNPC(true);
	}

	@Override
	public GOTCapes getCape() {
		return GOTCapes.NIGHT;
	}

	@Override
	public float getAlignmentBonus() {
		return 300.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.alloySteelSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}

	public static class JonSnowLife1 extends GOTEntityJonSnow {
		{
		}

		{
		}

		@SuppressWarnings({"WeakerAccess", "unused"})
		public JonSnowLife1(World world) {
			super(world);
			addTargetTasks(true);
		}

		@Override
		public GOTFaction getFaction() {
			return GOTFaction.NIGHT_WATCH;
		}

		@Override
		public GOTMiniQuestFactory getMiniQuestFactory() {
			return GOTMiniQuestFactory.JON_SNOW;
		}

		@Override
		public void onDeath(DamageSource damagesource) {
			super.onDeath(damagesource);
			if (!worldObj.isRemote) {
				JonSnowLife2 stage2 = new JonSnowLife2(worldObj);
				stage2.copyLocationAndAnglesFrom(this);
				stage2.onSpawnWithEgg(null);
				worldObj.spawnEntityInWorld(stage2);
				setDead();
			}
		}
	}

	public static class JonSnowLife2 extends GOTEntityJonSnow {
		{
		}

		{
		}

		@SuppressWarnings({"WeakerAccess", "unused"})
		public JonSnowLife2(World world) {
			super(world);
			addTargetTasks();
		}

		@Override
		public GOTFaction getFaction() {
			return GOTFaction.NORTH;
		}

		@Override
		public GOTAchievement getKillAchievement() {
			return GOTAchievement.killJonSnow;
		}

		protected void addTargetTasks() {
			int target = addTargetTasks(true);
			target++;
			targetTasks.addTask(target, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityRamsayBolton.class, 0, true));
			target++;
			targetTasks.addTask(target, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityRooseBolton.class, 0, true));
		}
	}
}