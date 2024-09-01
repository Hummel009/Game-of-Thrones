package got.common.entity.essos.asshai;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.item.weapon.GOTAsshaiStaffSelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class GOTEntityAsshaiSpherebinder extends GOTEntityAsshaiWarrior {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAsshaiSpherebinder(World world) {
		super(world);
		isImmuneToFire = true;
	}

	@Override
	public float getReputationBonus() {
		return 3.0f;
	}

	@Override
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killAsshaiSpherebinder;
	}

	@Override
	public EntityAIBase getAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 0.0, false);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(GOTBlocks.asshaiTorch));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(4, new ItemStack(GOTItems.asshaiMask));

		return entityData;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (getAttackTarget() != null && !worldObj.isRemote) {
			List<? extends Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(24.0, 24.0, 24.0));

			if (rand.nextInt(20) == 0) {
				worldObj.playSoundAtEntity(this, "portal.portal", 0.5f, rand.nextFloat() * 0.4f + 0.8f);
			}

			for (Entity entity : entities) {
				double x = entity.posX - posX;
				double y = entity.posY - posY;
				double z = entity.posZ - posZ;

				if (entity instanceof EntityLivingBase) {
					EntityLivingBase target = (EntityLivingBase) entity;
					if (GOTAsshaiStaffSelector.canNpcAttackTarget(this, target)) {
						double dSq = getDistanceSqToEntity(target);
						if (dSq <= 0.0) {
							dSq = 1.0E-5;
						}
						float strength = 0.5f;
						double force = strength / dSq;
						x *= force;
						y *= force;
						z *= force;
						target.motionX += x;
						target.motionY += y;
						target.motionZ += z;
					}
				}
			}
		}
	}
}