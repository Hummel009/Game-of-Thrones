package got.common.entity.essos.myr;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityMyrSoldier extends GOTEntityMyrMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityMyrSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
	}

	@Override
	public GOTCapes getCape() {
		return GOTCapes.MYR;
	}

	@Override
	public GOTShields getShield() {
		return GOTShields.MYR;
	}

	@Override
	public float getReputationBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, true);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.myrBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.myrLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.myrChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.myrHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
