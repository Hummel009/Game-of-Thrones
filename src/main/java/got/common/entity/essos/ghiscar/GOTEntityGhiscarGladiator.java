package got.common.entity.essos.ghiscar;

import got.common.database.GOTAchievement;
import got.common.database.GOTShields;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetBasic;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.utils.GOTEntityUtils;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GOTEntityGhiscarGladiator extends GOTEntityGhiscarMan {

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGhiscarGladiator(World world) {
		super(world);
		int target = addTargetTasks(true);
		targetTasks.addTask(target + 1, new GOTEntityAINearestAttackableTargetBasic(this, GOTEntityGhiscarGladiator.class, 0, true));
	}

	@Override
	public GOTShields getShield() {
		return GOTShields.GHISCAR;
	}

	@Override
	public float getAlignmentBonus() {
		return 0.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killGhiscarGladiator;
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, true);
		GOTEntityUtils.setupLeathermanArmorTurban(this, rand);

		return entityData;
	}

	@Override
	public String getSpeechBank(GOTEntityNPC npc, EntityPlayer entityPlayer) {
		return "special/gladiator";
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}