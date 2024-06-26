package got.common.entity.westeros.dorne;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityDorneSoldier extends GOTEntityDorneMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityDorneSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		shield = GOTShields.DORNE;
		cape = GOTCapes.DORNE;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupWesterosWeaponSet(this, rand);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.dorneBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.dorneLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.dorneChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.dorneHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
