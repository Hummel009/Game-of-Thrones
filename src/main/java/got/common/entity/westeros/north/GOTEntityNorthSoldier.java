package got.common.entity.westeros.north;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityNorthSoldier extends GOTEntityNorthMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityNorthSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
	}

	@Override
	public GOTShields getShield() {
		return GOTShields.NORTH;
	}

	@Override
	public GOTCapes getCape() {
		return GOTCapes.NORTH;
	}

	@Override
	public float getReputationBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, false);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.northBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.northLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.northChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.northHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
