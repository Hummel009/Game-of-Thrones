package got.common.entity.essos.volantis;

import got.common.database.GOTCapes;
import got.common.database.GOTItems;
import got.common.database.GOTShields;
import got.common.entity.other.utils.GOTWeaponSetFactory;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GOTEntityVolantisSoldier extends GOTEntityVolantisMan {
	@SuppressWarnings({"WeakerAccess", "unused"})
	public GOTEntityVolantisSoldier(World world) {
		super(world);
		addTargetTasks(true);
		spawnRidingHorse = rand.nextInt(10) == 0;
		cape = GOTCapes.VOLANTIS;
		shield = GOTShields.VOLANTIS;
	}

	@Override
	public float getAlignmentBonus() {
		return 2.0f;
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		IEntityLivingData entityData = super.onSpawnWithEgg(data);

		GOTWeaponSetFactory.setupEssosWeaponSet(this, rand);

		setCurrentItemOrArmor(1, new ItemStack(GOTItems.volantisBoots));
		setCurrentItemOrArmor(2, new ItemStack(GOTItems.volantisLeggings));
		setCurrentItemOrArmor(3, new ItemStack(GOTItems.volantisChestplate));
		setCurrentItemOrArmor(4, new ItemStack(GOTItems.volantisHelmet));

		return entityData;
	}

	@Override
	public void setupNPCGender() {
		familyInfo.setMale(true);
	}
}
