package got.common.entity.essos.gold;

import got.common.database.GOTItems;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGoldenCompanyWarrior extends GOTEntityGoldenCompanyMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGoldenCompanyWarrior(World world) {
		super(world);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		super.onSpawnWithEgg(data);
		npcItemsInv.setMeleeWeapon(new ItemStack(GOTItems.westerosSword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());
		setCurrentItemOrArmor(1, new ItemStack(GOTItems.goldenCompanyBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.goldenCompanyLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.goldenCompanyChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.goldenCompanyHelmet));
		return data;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}