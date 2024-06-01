package got.common.entity.essos.yiti;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityYiTiSoldier extends GOTEntityYiTiLevyman {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityYiTiSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		shield = GOTShields.YITI;
		cape = GOTCapes.YITI;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(NPC_RANGED_ACCURACY).setBaseValue(0.75);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		int i = rand.nextInt(9);
		switch (i) {
			case 0:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.yitiBattleaxe));
				break;
			case 1:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.yitiPolearm));
				break;
			case 2:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.yitiPike));
				break;
			default:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.yitiSword));
				break;
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.yitiSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.yitiBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.yitiLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.yitiChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.yitiHelmet));
		return entityData;
	}
}