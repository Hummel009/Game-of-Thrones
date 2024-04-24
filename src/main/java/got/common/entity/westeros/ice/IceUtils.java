package got.common.entity.westeros.ice;

import got.common.GOTDamage;
import got.common.database.GOTItems;
import got.common.database.GOTMaterial;
import got.common.entity.essos.legendary.warrior.GOTEntityAsshaiArchmag;
import got.common.entity.other.GOTEntityGiantBase;
import got.common.entity.other.GOTEntityHumanBase;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.GOTEntitySpiderBase;
import got.common.entity.other.inanimate.GOTEntityProjectileBase;
import got.common.entity.other.inanimate.GOTEntitySpear;
import got.common.entity.westeros.legendary.reborn.GOTEntityGregorClegane;
import got.common.entity.westeros.legendary.warrior.GOTEntityNightKing;
import got.common.item.GOTMaterialFinder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;

public class IceUtils {
	private static final Collection<Item.ToolMaterial> CAUSE_DAMAGE_MATERIALS = EnumSet.noneOf(Item.ToolMaterial.class);
	private static final Collection<Item> SPECIAL_ITEMS = new HashSet<>();

	static {
		CAUSE_DAMAGE_MATERIALS.add(GOTMaterial.VALYRIAN_TOOL);
		CAUSE_DAMAGE_MATERIALS.add(GOTMaterial.OBSIDIAN_TOOL);

		SPECIAL_ITEMS.add(GOTItems.crowbar);
		SPECIAL_ITEMS.add(GOTItems.baelishDagger);
	}

	private IceUtils() {
	}

	public static void createNewWight(Entity attacker, EntityLivingBase entity, World world) {
		if (entity instanceof GOTEntityHumanBase) {
			if (attacker instanceof GOTEntityNightKing) {
				GOTEntityNPC walker = new GOTEntityWhiteWalker(world);
				walker.getFamilyInfo().setAge(((GOTEntityNPC) entity).getFamilyInfo().getAge());
				walker.copyLocationAndAnglesFrom(entity);
				world.removeEntity(entity);
				walker.onSpawnWithEgg(null);
				world.spawnEntityInWorld(walker);
			} else {
				GOTEntityNPC wight = new GOTEntityWight(world);
				wight.getFamilyInfo().setAge(((GOTEntityNPC) entity).getFamilyInfo().getAge());
				wight.copyLocationAndAnglesFrom(entity);
				wight.getNpcItemsInv().setMeleeWeapon(((GOTEntityNPC) entity).getNpcItemsInv().getMeleeWeapon());
				wight.getNpcItemsInv().setIdleItem(((GOTEntityNPC) entity).getNpcItemsInv().getMeleeWeapon());
				wight.setCurrentItemOrArmor(1, entity.getEquipmentInSlot(1));
				wight.setCurrentItemOrArmor(2, entity.getEquipmentInSlot(2));
				wight.setCurrentItemOrArmor(3, entity.getEquipmentInSlot(3));
				wight.setCurrentItemOrArmor(4, entity.getEquipmentInSlot(4));
				wight.getFamilyInfo().setMale(((GOTEntityNPC) entity).getFamilyInfo().isMale());
				world.removeEntity(entity);
				world.spawnEntityInWorld(wight);
			}
		} else if (entity instanceof GOTEntityGiantBase) {
			GOTEntityNPC giant = new GOTEntityWightGiant(world);
			giant.copyLocationAndAnglesFrom(entity);
			world.removeEntity(entity);
			giant.onSpawnWithEgg(null);
			world.spawnEntityInWorld(giant);
		} else if (entity instanceof GOTEntitySpiderBase) {
			GOTEntityNPC spider = new GOTEntityIceSpider(world);
			spider.copyLocationAndAnglesFrom(entity);
			world.removeEntity(entity);
			spider.onSpawnWithEgg(null);
			world.spawnEntityInWorld(spider);
		}
	}

	public static boolean calculateDamage(GOTEntityNPC target, DamageSource damageSource, boolean additionalRule) {
		if (additionalRule && damageSource.isFireDamage()) {
			return true;
		}
		Entity entity = damageSource.getEntity();
		Entity sourceOfDamage = damageSource.getSourceOfDamage();
		if (entity instanceof EntityLivingBase && entity == damageSource.getSourceOfDamage()) {
			ItemStack itemStack = ((EntityLivingBase) entity).getHeldItem();
			if (itemStack != null) {
				Item item = itemStack.getItem();
				if (item instanceof GOTMaterialFinder && CAUSE_DAMAGE_MATERIALS.contains(((GOTMaterialFinder) item).getMaterial()) && !(target instanceof GOTEntityNightKing)) {
					return true;
				}
				if (SPECIAL_ITEMS.contains(item)) {
					return true;
				}
			}
		}
		if (sourceOfDamage instanceof GOTEntitySpear) {
			Item item = ((GOTEntityProjectileBase) sourceOfDamage).getProjectileItem().getItem();
			if (item instanceof GOTMaterialFinder && CAUSE_DAMAGE_MATERIALS.contains(((GOTMaterialFinder) item).getMaterial()) && !(target instanceof GOTEntityNightKing)) {
				return true;
			}
			if (SPECIAL_ITEMS.contains(item)) {
				return true;
			}
		}
		return (entity instanceof GOTEntityGregorClegane || entity instanceof GOTEntityAsshaiArchmag) && !(target instanceof GOTEntityNightKing);
	}

	public static boolean attackWithFrost(Entity entity, boolean attackEntityAsMob) {
		if (attackEntityAsMob) {
			if (entity instanceof EntityPlayerMP) {
				GOTDamage.doFrostDamage((EntityPlayerMP) entity);
			}
			return true;
		}
		return false;
	}
}