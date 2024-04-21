package got.common.entity.essos.asshai;

import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayerMP;
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
		shield = null;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.22);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(25.0);
	}

	@Override
	public EntityAIBase createAsshaiAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 0, false);
	}

	@Override
	public float getAlignmentBonus() {
		return 3.0f;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!worldObj.isRemote && getAttackTarget() != null) {
			List<? extends Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(24.0, 24.0, 24.0));
			for (Entity entity : entities) {
				double dSq = getDistanceSqToEntity(entity);
				if (dSq <= 0.0) {
					dSq = 1.0E-5;
				}
				float strength = 0.0f;
				if (entity == getAttackTarget()) {
					strength = 0.8f;
				}
				double force = strength / dSq;
				double x = entity.posX - posX;
				double y = entity.posY - posY;
				double z = entity.posZ - posZ;
				x *= force;
				y *= force;
				z *= force;
				if (entity instanceof EntityPlayerMP) {
					((EntityPlayerMP) entity).playerNetServerHandler.sendPacket(new S27PacketExplosion(posX, posY, posZ, 0.0f, new ArrayList<>(), Vec3.createVectorHelper(x, y, z)));
					continue;
				}
				entity.motionX += x;
				entity.motionY += y;
				entity.motionZ += z;
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(null);
		npcItemsInv.setIdleItem(null);
		setCurrentItemOrArmor(4, null);
		return data1;
	}
}