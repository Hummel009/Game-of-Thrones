package got.common.entity.essos.golden;

import got.common.database.GOTItems;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityGoldenCompanyWarrior extends GOTEntityGoldenCompanyMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityGoldenCompanyWarrior(World world) {
		super(world);
		addTargetTasks(true);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		super.onSpawnWithEgg(data);

		npcItemsInv.setMeleeWeapon(new ItemStack(Items.iron_sword));
		npcItemsInv.setIdleItem(npcItemsInv.getMeleeWeapon());

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.goldenCompanyBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.goldenCompanyLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.goldenCompanyChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.goldenCompanyHelmet));

		return data;
	}
}