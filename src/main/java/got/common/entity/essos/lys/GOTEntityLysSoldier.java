package got.common.entity.essos.lys;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityLysSoldier extends GOTEntityLysLevyman {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLysSoldier(World world) {
		super(world);
		spawnRidingHorse = rand.nextInt(10) == 0;
		cape = GOTCapes.LYS;
		shield = GOTShields.LYS;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData data1 = super.onSpawnWithEgg(data);
		int i = rand.nextInt(6);
		switch (i) {
			case 0:
			case 1:
			case 2:
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosSword));
				break;
			case 4:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosHammer));
				break;
			case 5:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosPike));
				break;
			default:
				break;
		}
		if (rand.nextInt(3) == 0) {
			npcItemsInv.setMeleeWeaponMounted(new ItemStack(GOTItems.essosPike));
		} else {
			npcItemsInv.setMeleeWeaponMounted(npcItemsInv.getMeleeWeapon());
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.essosSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeaponMounted());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.lysBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.lysLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.lysChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.lysHelmet));
		return data1;
	}
}