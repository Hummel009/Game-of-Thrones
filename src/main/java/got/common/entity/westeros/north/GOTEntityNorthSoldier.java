package got.common.entity.westeros.north;

import got.common.database.GOTRegistry;
import got.common.database.GOTShields;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityNorthSoldier extends GOTEntityNorthLevyman {
	public GOTEntityNorthSoldier(World world) {
		super(world);
		canBeMarried = false;
		spawnRidingHorse = rand.nextInt(10) == 0;
		npcShield = GOTShields.NORTH;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
	}

	@Override
	public EntityAIBase createNorthAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, false);
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
		if (rand.nextInt(3) == 0) {
			npcItemsInv.setMeleeWeaponMounted(new ItemStack(GOTRegistry.westerosLance));
		} else {
			npcItemsInv.setMeleeWeaponMounted(npcItemsInv.getMeleeWeapon());
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.westerosSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeaponMounted());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.northBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.northLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.northChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.northHelmet));
		return data;
	}
}
