package got.common.entity.westeros.arryn;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityArrynSoldier extends GOTEntityArrynMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityArrynSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		shield = GOTShields.ARRYN;
		cape = GOTCapes.ARRYN;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupWesterosWeaponSet(this, rand);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.arrynBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.arrynLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.arrynChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.arrynHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}