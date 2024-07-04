package got.common.entity.essos.qarth;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityQarthSoldier extends GOTEntityQarthMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityQarthSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		cape = GOTCapes.QARTH;
		shield = GOTShields.QARTH;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, true);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.qarthBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.qarthLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.qarthChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.qarthHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
