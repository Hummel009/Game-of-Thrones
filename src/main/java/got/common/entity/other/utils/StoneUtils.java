package got.common.entity.other.utils;

import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityStoneMan;
import got.common.item.weapon.GOTItemSword;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class StoneUtils {
	private StoneUtils() {
	}

	public static void createNewStoneMan(EntityLivingBase entity, World world) {
		if (entity instanceof GOTEntityHumanBase) {
			GOTEntityHumanBase target = (GOTEntityHumanBase) entity;
			GOTEntityStoneMan stoneMan = new GOTEntityStoneMan(world);
			stoneMan.copyLocationAndAnglesFrom(target);
			stoneMan.getFamilyInfo().setAge(target.getFamilyInfo().getAge());
			stoneMan.getFamilyInfo().setMale(target.getFamilyInfo().isMale());
			stoneMan.getNpcItemsInv().setMeleeWeapon(target.getNpcItemsInv().getMeleeWeapon());
			stoneMan.getNpcItemsInv().setIdleItem(target.getNpcItemsInv().getMeleeWeapon());
			stoneMan.setCurrentItemOrArmor(1, target.getEquipmentInSlot(1));
			stoneMan.setCurrentItemOrArmor(2, target.getEquipmentInSlot(2));
			stoneMan.setCurrentItemOrArmor(3, target.getEquipmentInSlot(3));
			stoneMan.setCurrentItemOrArmor(4, target.getEquipmentInSlot(4));
			world.removeEntity(entity);
			world.spawnEntityInWorld(stoneMan);
		}
	}

	public static boolean attackWithWither(Entity entity, boolean attackEntityAsMob) {
		if (attackEntityAsMob) {
			if (entity instanceof EntityLivingBase) {
				GOTItemSword.applyStandardWither((EntityLivingBase) entity);
			}
			return true;
		}
		return false;
	}
}