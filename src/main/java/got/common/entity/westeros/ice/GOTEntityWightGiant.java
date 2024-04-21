package got.common.entity.westeros.ice;

import got.common.database.GOTAchievement;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import got.common.entity.other.GOTEntityGiantBase;
import got.common.faction.GOTFaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityWightGiant extends GOTEntityGiantBase {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityWightGiant(World world) {
		super(world);
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0);
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		return IceUtils.attackWithFrost(entity, super.attackEntityAsMob(entity));
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float f) {
		boolean causeDamage = IceUtils.calculateDamage(this, damagesource, true);
		return super.attackEntityFrom(damagesource, causeDamage ? f : 0.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
	}

	@Override
	public float getAlignmentBonus() {
		return 3.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.WHITE_WALKER;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killWightGiant;
	}

	@Override
	public void onKillEntity(EntityLivingBase entity) {
		super.onKillEntity(entity);
		IceUtils.createNewWight(this, entity, worldObj);
	}
}