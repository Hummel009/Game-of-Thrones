package got.common.entity.westeros.arryn;

import got.common.database.*;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityArrynSoldier extends GOTEntityArrynLevyman {
	public GOTEntityArrynSoldier(World world) {
		super(world);
		canBeMarried = false;
		spawnRidingHorse = rand.nextInt(10) == 0;
		npcShield = GOTShields.ARRYN;
		npcCape = GOTCapes.ARRYN;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
	}

	@Override
	public float getAlignmentBonus() {
		return 3.0f;
	}

	@Override
	public void onAttackModeChange(GOTEntityNPC.AttackMode mode, boolean mounted) {
		if (mode == GOTEntityNPC.AttackMode.IDLE) {
			if (mounted) {
				setCurrentItemOrArmor(0, npcItemsInv.getIdleItemMounted());
			} else {
				setCurrentItemOrArmor(0, npcItemsInv.getIdleItem());
			}
		} else if (mounted) {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeaponMounted());
		} else {
			setCurrentItemOrArmor(0, npcItemsInv.getMeleeWeapon());
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(10);
		switch (i) {
		case 0:
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.westerosHammer));
			break;
		case 1:
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.westerosPike));
			break;
		case 2:
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.westerosLongsword));
			break;
		case 3:
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.westerosGreatsword));
			break;
		default:
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.westerosSword));
			break;
		}
			npcItemsInv.setMeleeWeaponMounted(npcItemsInv.getMeleeWeapon());
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.westerosSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeaponMounted());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.arrynBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.arrynLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.arrynChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.arrynHelmet));
		return data;
	}
}
