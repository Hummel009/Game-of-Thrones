package got.common.entity.westeros.ironborn;

import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.ai.GOTEntityAIAttackOnCollide;
import got.common.entity.other.GOTEntityNPC;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityIronbornSoldier extends GOTEntityIronbornLevyman {
	public GOTEntityIronbornSoldier(World world) {
		super(world);
		canBeMarried = false;
		npcShield = GOTShields.IRONBORN;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
	}

	@Override
	public EntityAIBase createIronbornAttackAI() {
		return new GOTEntityAIAttackOnCollide(this, 1.4, false);
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
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosHammer));
				break;
			case 1:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosPike));
				break;
			case 2:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosLongsword));
				break;
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosGreatsword));
				break;
			default:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosSword));
				break;
		}
		if (rand.nextInt(3) == 0) {
			npcItemsInv.setMeleeWeaponMounted(new ItemStack(GOTItems.westerosLance));
		} else {
			npcItemsInv.setMeleeWeaponMounted(npcItemsInv.getMeleeWeapon());
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeaponMounted());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.ironbornBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.ironbornLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.ironbornChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.ironbornHelmet));
		return data;
	}
}
