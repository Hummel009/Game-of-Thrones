package got.common.item.weapon;

import got.common.GOTLevelData;
import got.common.database.GOTAchievement;
import got.common.database.GOTCreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GOTItemLegendaryWhip extends GOTItemSword {
	public GOTItemLegendaryWhip() {
		super(ToolMaterial.WOOD);
		setMaxDamage(1500);
		gotWeaponDamage = 5.0f;
		setCreativeTab(GOTCreativeTabs.TAB_STORY);
	}

	private static void launchWhip(EntityLivingBase user, EntityLivingBase hitEntity) {
		user.worldObj.playSoundAtEntity(user, "got:item.whip", 2.0f, 0.7f + itemRand.nextFloat() * 0.6f);
		double range = 16.0;
		Vec3 position = Vec3.createVectorHelper(user.posX, user.posY, user.posZ);
		Vec3 look = user.getLookVec();
		Vec3 sight = position.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
		float sightWidth = 1.0f;
		List<? extends Entity> list = user.worldObj.getEntitiesWithinAABBExcludingEntity(user, user.boundingBox.addCoord(look.xCoord * range, look.yCoord * range, look.zCoord * range).expand(sightWidth, sightWidth, sightWidth));
		Collection<EntityLivingBase> whipTargets = new ArrayList<>();
		for (Entity element : list) {
			EntityLivingBase entity;
			if (!(element instanceof EntityLivingBase) || (entity = (EntityLivingBase) element) == user.ridingEntity && !entity.canRiderInteract() || !entity.canBeCollidedWith()) {
				continue;
			}
			float width = 1.0f;
			AxisAlignedBB axisalignedbb = entity.boundingBox.expand(width, width, width);
			MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(position, sight);
			if (axisalignedbb.isVecInside(position)) {
				whipTargets.add(entity);
				continue;
			}
			if (movingobjectposition == null) {
				continue;
			}
			whipTargets.add(entity);
		}
		for (EntityLivingBase entity : whipTargets) {
			if (entity != hitEntity) {
				entity.attackEntityFrom(DamageSource.causeMobDamage(user), 1.0f);
			}
		}
		if (user instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) user;

			GOTLevelData.getData(player).addAchievement(GOTAchievement.useNymeriaWhip);
		}
	}

	@Override
	public boolean getIsRepairable(ItemStack itemstack, ItemStack repairItem) {
		return repairItem.getItem() == Items.stick;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemstack) {
		return 20;
	}

	@Override
	public boolean hitEntity(ItemStack itemstack, EntityLivingBase hitEntity, EntityLivingBase user) {
		if (super.hitEntity(itemstack, hitEntity, user)) {
			if (!user.worldObj.isRemote && hitEntity.hurtTime == hitEntity.maxHurtTime) {
				launchWhip(user, hitEntity);
			}
			return true;
		}
		return false;
	}

	@Override
	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.swingItem();
		if (!world.isRemote) {
			launchWhip(entityplayer, null);
		}
		itemstack.damageItem(1, entityplayer);
		return itemstack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
		return itemstack;
	}
}