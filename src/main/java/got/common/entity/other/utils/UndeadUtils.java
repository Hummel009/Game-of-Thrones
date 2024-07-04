package got.common.entity.other.utils;

import got.common.database.GOTItems;
import got.common.database.GOTMaterial;
import got.common.entity.essos.legendary.boss.GOTEntityAsshaiArchmag;
import got.common.entity.essos.mossovy.GOTEntityMossovyMan;
import got.common.entity.other.GOTEntityNPC;
import got.common.entity.other.inanimate.GOTEntityProjectileBase;
import got.common.entity.westeros.legendary.reborn.GOTEntityGregorClegane;
import got.common.entity.westeros.legendary.warrior.GOTEntityNightKing;
import got.common.item.GOTMaterialFinder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;

public class UndeadUtils {
	private static final Collection<Item.ToolMaterial> CAUSE_DAMAGE_MATERIALS = EnumSet.noneOf(Item.ToolMaterial.class);
	private static final Collection<Item> SPECIAL_ITEMS = new HashSet<>();

	static {
		CAUSE_DAMAGE_MATERIALS.add(GOTMaterial.ALLOY_STEEL_TOOL);
		CAUSE_DAMAGE_MATERIALS.add(GOTMaterial.VALYRIAN_TOOL);

		SPECIAL_ITEMS.add(GOTItems.crowbar);
	}

	private UndeadUtils() {
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
		if (sourceOfDamage instanceof GOTEntityProjectileBase) {
			Item item = ((GOTEntityProjectileBase) sourceOfDamage).getProjectileItem().getItem();
			if (item instanceof GOTMaterialFinder && CAUSE_DAMAGE_MATERIALS.contains(((GOTMaterialFinder) item).getMaterial()) && !(target instanceof GOTEntityNightKing)) {
				return true;
			}
			if (SPECIAL_ITEMS.contains(item)) {
				return true;
			}
		}
		return entity instanceof GOTEntityMossovyMan || entity instanceof GOTEntityGregorClegane || entity instanceof GOTEntityAsshaiArchmag;
	}
}