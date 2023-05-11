package got.common.entity.essos.volantis;

import got.common.database.GOTCapes;
import got.common.database.GOTRegistry;
import got.common.database.GOTShields;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityVolantisSoldier extends GOTEntityVolantisLevyman {
	public GOTEntityVolantisSoldier(World world) {
		super(world);
		canBeMarried = false;
		spawnRidingHorse = rand.nextInt(10) == 0;
		npcCape = GOTCapes.VOLANTIS;
		npcShield = GOTShields.VOLANTIS;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		data = super.onSpawnWithEgg(data);
		int i = rand.nextInt(6);
		switch (i) {
			case 0:
			case 1:
			case 2:
			case 3:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.essosSword));
				break;
			case 4:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.essosHammer));
				break;
			case 5:
				npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.essosPike));
				break;
			default:
				break;
		}
		if (rand.nextInt(3) == 0) {
			npcItemsInv.setMeleeWeaponMounted(new ItemStack(GOTRegistry.essosPike));
		} else {
			npcItemsInv.setMeleeWeaponMounted(npcItemsInv.getMeleeWeapon());
		}
		if (rand.nextInt(5) == 0) {
			npcItemsInv.setSpearBackup(npcItemsInv.getMeleeWeapon());
			npcItemsInv.setMeleeWeapon(new ItemStack(GOTRegistry.essosSpear));
		}
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		npcItemsInv.setIdleItemMounted(npcItemsInv.getMeleeWeaponMounted());
		setCurrentItemOrArmor(1, new ItemStack(GOTRegistry.volantisBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTRegistry.volantisLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTRegistry.volantisChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTRegistry.volantisHelmet));
		return data;
	}
}
