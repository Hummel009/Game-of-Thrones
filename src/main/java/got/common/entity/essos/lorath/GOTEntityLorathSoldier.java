package got.common.entity.essos.lorath;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityLorathSoldier extends GOTEntityLorathMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityLorathSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		cape = GOTCapes.LORATH;
		shield = GOTShields.LORATH;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, false);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.lorathBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.lorathLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.lorathChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.lorathHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
