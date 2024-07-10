package got.common.entity.essos.tyrosh;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityTyroshSoldier extends GOTEntityTyroshMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityTyroshSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
	}

	@Override
	public GOTCapes getCape() {
		return GOTCapes.TYROSH;
	}

	@Override
	public GOTShields getShield() {
		return GOTShields.TYROSH;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, true);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.tyroshBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.tyroshLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.tyroshChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.tyroshHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
