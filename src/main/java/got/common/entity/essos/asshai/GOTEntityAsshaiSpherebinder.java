package got.common.entity.essos.asshai;

import got.common.database.GOTAchievement;
import got.common.database.GOTBlocks;
import got.common.database.GOTItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
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
				if (entity instanceof EntityPlayerMP && entity == getAttackTarget()) {
					((EntityPlayerMP) entity).playerNetServerHandler.sendPacket(new S27PacketExplosion(posX, posY, posZ, 0.0f, new ArrayList<>(), Vec3.createVectorHelper(x, y, z)));
					continue;
				}
				entity.motionX += x;
				entity.motionY += y;
				entity.motionZ += z;
			}
		}
	}
}