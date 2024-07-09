package got.common.entity.westeros.ice;

import got.GOT;
import got.common.GOTConfig;
import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAINearestAttackableTargetPatriot;
import got.common.entity.other.GOTEntitySpiderBase;
import got.common.entity.other.utils.IceUtils;
import got.common.faction.GOTFaction;
import got.common.world.biome.GOTBiome;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityIceSpider extends GOTEntitySpiderBase implements GOTBiome.ImmuneToFrost {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityIceSpider(World world) {
		super(world);
		isImmuneToFire = true;
		spawnsInDarkness = true;
		addTargetTasks(true, GOTEntityAINearestAttackableTargetPatriot.class);
		faction = GOTFaction.WHITE_WALKER;
		alignmentBonus = 5.0f;
		killAchievement = GOTAchievement.killIceSpider;
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
		boolean causeDamage = IceUtils.calculateDamage(this, damagesource, GOTConfig.walkerFireDamage);
		return super.attackEntityFrom(damagesource, causeDamage ? f : 0.0f);
	}

	@Override
	public void dropFewItems(boolean flag, int i) {
		if (rand.nextFloat() <= 0.525f) {
			dropItem(GOTItems.iceShard, rand.nextInt(2) + 1);
		}
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && riddenByEntity instanceof EntityPlayer && isMountSaddled()) {
			GOTLevelData.getData((EntityPlayer) riddenByEntity).addAchievement(GOTAchievement.rideIceSpider);
		}
		if (motionX * motionX + motionY * motionY + motionZ * motionZ >= 0.01) {
			double d = posX + MathHelper.randomFloatClamp(rand, -0.3f, 0.3f) * width;
			double d1 = boundingBox.minY + MathHelper.randomFloatClamp(rand, 0.2f, 0.7f) * height;
			double d2 = posZ + MathHelper.randomFloatClamp(rand, -0.3f, 0.3f) * width;
			GOT.proxy.spawnParticle("chill", d, d1, d2, -motionX * 0.5, 0.0, -motionZ * 0.5);
		}
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
		IceUtils.createNewWight(this, entity, worldObj);
	}
}