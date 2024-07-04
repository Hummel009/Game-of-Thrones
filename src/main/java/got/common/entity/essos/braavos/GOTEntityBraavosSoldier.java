package got.common.entity.essos.braavos;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityBraavosSoldier extends GOTEntityBraavosMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityBraavosSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		cape = GOTCapes.BRAAVOS;
		shield = GOTShields.BRAAVOS;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupIronWeaponSet(this, rand, false);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.braavosBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.braavosLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.braavosChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.braavosHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}