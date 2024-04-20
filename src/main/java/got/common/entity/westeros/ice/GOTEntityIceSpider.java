package got.common.entity.westeros.ice;

import got.common.GOTConfig;
import got.common.database.GOTAchievement;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import got.common.entity.other.GOTEntitySpiderBase;
import got.common.faction.GOTFaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class GOTEntityIceSpider extends GOTEntitySpiderBase {
	public GOTEntityIceSpider(World world) {
		super(world);
		isImmuneToFrost = true;
		isImmuneToFire = true;
		isChilly = true;
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
		boolean causeDamage = IceUtils.calculateDamage(damagesource, GOTConfig.walkerFireDamage);
		return super.attackEntityFrom(damagesource, causeDamage ? f : 0.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
	}

	@Override
	public float getAlignmentBonus() {
		return 5.0f;
	}

	@Override
	public GOTFaction getFaction() {
		return GOTFaction.WHITE_WALKER;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killIceSpider;
	}

	@Override
	public int getRandomSpiderScale() {
		return rand.nextInt(4);
	}

	@Override
	public int getRandomSpiderType() {
		return VENOM_SLOWNESS;
	}

	@Override
	public void onKillEntity(EntityLivingBase entity) {
		super.onKillEntity(entity);
		IceUtils.createNewWight(entity, worldObj);
	}
}