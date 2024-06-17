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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class GOTEntityAsshaiSpherebinder extends GOTEntityAsshaiWarrior {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityAsshaiSpherebinder(World world) {
		super(world);
		isImmuneToFire = true;
	}

	@Override
	public EntityAIBase getAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 0.0, false);
	}

	@Override
	public float getAlignmentBonus() {
		return 3.0f;
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
	public GOTAchievement getKillAchievement() {
		return GOTAchievement.killAsshaiSpherebinder;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && getAttackTarget() != null) {
			List<? extends Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(24.0, 24.0, 24.0));
			for (Entity entity : entities) {
				if (entity instanceof EntityLivingBase) {
					EntityLivingBase target = (EntityLivingBase) entity;
					if (GOTAsshaiStaffSelector.canNpcAttackTarget(this, target)) {
						double dSq = getDistanceSqToEntity(target);
						if (dSq <= 0.0) {
							dSq = 1.0E-5;
						}
						float strength = 0.5f;
						double force = strength / dSq;
						double x = target.posX - posX;
						double y = target.posY - posY;
						double z = target.posZ - posZ;
						x *= force;
						y *= force;
						z *= force;
						target.motionX += x;
						target.motionY += y;
						target.motionZ += z;
						if (target instanceof EntityPlayerMP) {
							((EntityPlayerMP) target).playerNetServerHandler.sendPacket(new S27PacketExplosion(posX, posY, posZ, 0.0f, new ArrayList<>(), Vec3.createVectorHelper(x, y, z)));
						}
					}
				}
			}
		}
	}
}