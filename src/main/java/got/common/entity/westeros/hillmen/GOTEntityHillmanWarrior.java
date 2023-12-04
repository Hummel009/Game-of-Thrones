package got.common.entity.westeros.hillmen;

import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityHillmanWarrior extends GOTEntityHillman {
	public GOTEntityHillmanWarrior(World world) {
		super(world);
		canBeMarried = false;
		npcShield = GOTShields.HILLMEN;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public String getSpeechBank(EntityPlayer entityplayer) {
		if (isFriendly(entityplayer)) {
			if (hiredNPCInfo.getHiringPlayer() == entityplayer) {
				return "standard/civilized/hired_soldier";
			}
			return "standard/civilized/usual_friendly";
		}
		return "standard/civilized/usual_hostile";
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		int i = rand.nextInt(7);
		switch (i) {
			case 0:
				npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_sword));
				break;
			case 1:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.bronzeSword));
				break;
			case 2:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.ironDagger));
				break;
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.bronzeDagger));
				break;
			case 4:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.ironBattleaxe));
				break;
			case 5:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.bronzeBattleaxe));
				break;
			case 6:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.ironPike));
				break;
			default:
				break;
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			if (rand.nextBoolean()) {
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.ironSpear));
			} else {
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.bronzeSpear));
			}
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.hillmenBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.hillmenLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.hillmenChestplate));
		if (rand.nextInt(10) != 0) {
			setCurrentItemOrArmor(4, new ItemStack(GOTItems.hillmenHelmet));
		}
		return data1;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
