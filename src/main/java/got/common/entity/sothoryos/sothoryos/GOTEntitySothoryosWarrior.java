package got.common.entity.sothoryos.sothoryos;

import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntitySothoryosWarrior extends GOTEntitySothoryosMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntitySothoryosWarrior(World world) {
		super(world);
		addTargetTasks(true);
		shield = GOTShields.SOTHORYOS;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		int i = rand.nextInt(8);
		switch (i) {
			case 0:
			case 1:
			case 2:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.sothoryosSword));
				break;
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.sothoryosDagger));
				break;
			case 4:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.sothoryosDaggerPoisoned));
				break;
			case 5:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.sothoryosHammer));
				break;
			case 6:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.sothoryosBattleaxe));
				break;
			case 7:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.sothoryosPike));
				break;
			default:
				break;
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.sothoryosSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.sothoryosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.sothoryosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.sothoryosChestplate));
		if (rand.nextInt(5) != 0) {
			setCurrentItemOrArmor(4, new ItemStack(GOTItems.sothoryosHelmet));
		}
		return data1;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}