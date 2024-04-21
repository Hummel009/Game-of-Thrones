package got.common.entity.essos.lhazar;

import got.common.database.GOTItems;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityLhazarWarrior extends GOTEntityLhazarMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLhazarWarrior(World world) {
		super(world);
		addTargetTasks(true);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(NPC_RANGED_ACCURACY).setBaseValue(0.75);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hireableInfo.getHiringPlayer() == entityplayer) {
				return "standard/civilized/hired_soldier";
			}
			return "standard/civilized/usual_friendly";
		}
		return "standard/civilized/usual_hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);
		if (rand.nextInt(3) == 0) {
			int i = rand.nextInt(5);
			switch (i) {
				case 0:
				case 1:
					npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.lhazarSword));
					break;
				case 2:
					npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.lhazarDagger));
					break;
				case 3:
					npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.lhazarDaggerPoisoned));
					break;
				case 4:
					npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.lhazarClub));
					break;
				default:
					break;
			}
		} else {
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.lhazarSword));
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.lhazarSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.lhazarBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.lhazarLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.lhazarChestplate));
		if (rand.nextInt(10) == 0) {
			setCurrentItemOrArmor(4, null);
		} else {
			setCurrentItemOrArmor(4, new ItemStack(GOTItems.lhazarHelmet));
		}
		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}