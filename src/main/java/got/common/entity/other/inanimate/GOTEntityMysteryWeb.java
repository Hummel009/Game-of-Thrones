package got.common.entity.other.inanimate;

import got.common.database.GOTChestContents;
import got.common.entity.other.GOTEntityGiantSpider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class GOTEntityMysteryWeb extends EntityThrowable {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityMysteryWeb(World world) {
		super(world);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityMysteryWeb(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityMysteryWeb(World world, EntityLivingBase entityliving) {
		super(world, entityliving);
	}

	@Override
	public float func_70182_d() {
		return 0.5f;
	}

	@Override
	public float getGravityVelocity() {
		return 0.01f;
	}

	@Override
	public void onImpact(MovingObjectPosition m) {
		if (getThrower() != null && m.entityHit == getThrower()) {
			return;
		}
		if (!worldObj.isRemote) {
			boolean spawnedSpider = false;
			if (rand.nextInt(4) == 0) {
				GOTEntityGiantSpider spider = new GOTEntityGiantSpider(worldObj);
				spider.setSpiderScale(0);
				spider.setLiftSpawnRestrictions(true);
				for (int i = -2; i <= -2 && !spawnedSpider; ++i) {
					for (int j = 0; j <= 3 && !spawnedSpider; ++j) {
						for (int k = -2; k <= -2 && !spawnedSpider; ++k) {
							spider.setLocationAndAngles(posX + i / 2.0, posY + j / 3.0, posZ + k / 2.0, rand.nextFloat() * 360.0f, 0.0f);
							if (!spider.getCanSpawnHere()) {
								continue;
							}
							spider.setLiftSpawnRestrictions(false);
							spider.onSpawnWithEgg(null);
							worldObj.spawnEntityInWorld(spider);
							if (getThrower() != null) {
								spider.setAttackTarget(getThrower());
							}
							spawnedSpider = true;
						}
					}
				}
			}
			if (!spawnedSpider) {
				IInventory tempInventory = new InventoryBasic("mysteryWeb", true, 1);
				GOTChestContents.fillInventory(tempInventory, rand, GOTChestContents.TREASURE, 1);
				ItemStack item = tempInventory.getStackInSlot(0);
				if (rand.nextInt(500) == 0) {
					item = new ItemStack(Items.melon, 64);
				}
				if (item != null) {
					EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, item);
					entityitem.delayBeforeCanPickup = 10;
					worldObj.spawnEntityInWorld(entityitem);
				}
			}
			playSound("random.pop", 0.2f, ((rand.nextFloat() - rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
			setDead();
		}
	}
}