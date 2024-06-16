package got.common.entity.sothoryos.sothoryos;

import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
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
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupSothoryosWeaponSet(this, rand);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.sothoryosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.sothoryosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.sothoryosChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.sothoryosHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}