package got.common.entity.essos.lhazar;

import got.common.database.GOTRegistry;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityLhazarWarrior extends GOTEntityLhazarMan {
	public GOTEntityLhazarWarrior(World world) {
		super(world);
		canBeMarried = false;
		this.addTargetTasks(true);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(npcRangedAccuracy).setBaseValue(0.75);
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "standart/civilized/hired_soldier";
			}
			return "standart/civilized/usual_friendly";
		}
		return "standart/civilized/usual_hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		if (rand.nextInt(3) != 0) {
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.lhazarSword));
		} else {
			int i = rand.nextInt(5);
			switch (i) {
				case 0:
				case 1:
					npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.lhazarSword));
					break;
				case 2:
					npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.lhazarDagger));
					break;
				case 3:
					npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.lhazarDaggerPoisoned));
					break;
				case 4:
					npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.lhazarClub));
					break;
				default:
					break;
			}
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.lhazarSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.lhazarBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.lhazarLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.lhazarChestplate));
		if (rand.nextInt(10) == 0) {
			setCurrentItemOrArmor(4, null);
		} else {
			setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.lhazarHelmet));
		}
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
