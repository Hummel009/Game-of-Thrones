package got.common.entity.essos.yiti;

import got.common.database.*;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiSoldier extends GOTEntityYiTiLevyman {
	public GOTEntityYiTiSoldier(World world) {
		super(world);
		canBeMarried = false;
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(6) == 0;
		npcShield = GOTShields.YITI;
		npcCape = GOTCapes.YITI;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(8);
		switch (i) {
		case 0:
		case 1:
		case 2:
		case 3:
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.yitiSword));
			break;
		case 4:
		case 5:
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.yitiBattleaxe));
			break;
		case 6:
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.yitiPolearm));
			break;
		case 7:
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.yitiPike));
			break;
		default:
			break;
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.yitiSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.yitiBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.yitiLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.yitiChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.yitiHelmet));
		return data;
	}
}
