package got.common.entity.westeros.ironborn;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityIronbornSoldier extends GOTEntityIronbornMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityIronbornSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
	}

	@Override
	public GOTShields getShield() {
		return GOTShields.IRONBORN;
	}

	@Override
	public GOTCapes getCape() {
		return GOTCapes.IRONBORN;
	}

	@Override
	public float getReputationBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, false);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.ironbornBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.ironbornLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.ironbornChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.ironbornHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
