package got.common.entity.essos.ghiscar;

import got.common.database.GOTRegistry;
import got.common.database.GOTShields;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class GOTEntityGhiscarCorsair extends GOTEntityGhiscarLevyman {
	public GOTEntityGhiscarCorsair(World world) {
		super(world);
		canBeMarried = false;
		npcShield = GOTShields.GHISCAR;
	}

	@Override
	public void onKillEntity(EntityLivingBase entity) {
		super.onKillEntity(entity);
		if (entity instanceof GOTEntityNPC && ((GOTEntityNPC) entity).canDropRares() && rand.nextInt(2) == 0) {
			int coins = getRandomCoinDropAmount();
			coins = (int) (coins * MathHelper.randomFloatClamp(rand, 1.0f, 3.0f));
			if (coins > 0) {
				entity.dropItem(GOTRegistry.coin, coins);
			}
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(6);
		switch (i) {
			case 0:
			case 1:
			case 2:
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.essosSword));
				break;
			case 4:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.essosHammer));
				break;
			case 5:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.essosPike));
				break;
			default:
				break;
		}
		if (rand.nextInt(3) == 0) {
			npcItemsInv.setMeleeWeaponMounted(new ItemStack(GOTRegistry.essosPike));
		} else {
			npcItemsInv.setMeleeWeaponMounted(npcItemsInv.getMeleeWeapon());
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.essosSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeaponMounted());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.ghiscarBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.ghiscarLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.ghiscarChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.ghiscarHelmet));
		return data;
	}
}
