package got.common.entity.essos.qohor;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityQohorSoldier extends GOTEntityQohorMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityQohorSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
	}

	@Override
	public GOTCapes getCape() {
		return GOTCapes.QOHOR;
	}

	@Override
	public GOTShields getShield() {
		return GOTShields.QOHOR;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, false);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.qohorBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.qohorLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.qohorChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.qohorHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
